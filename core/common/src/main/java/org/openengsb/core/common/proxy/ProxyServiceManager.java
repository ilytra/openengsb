/**
 * Copyright 2010 OpenEngSB Division, Vienna University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.core.common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.openengsb.core.common.AbstractServiceManagerParent;
import org.openengsb.core.common.Domain;
import org.openengsb.core.common.DomainProvider;
import org.openengsb.core.common.ServiceManager;
import org.openengsb.core.common.descriptor.ServiceDescriptor;
import org.openengsb.core.common.validation.MultipleAttributeValidationResult;
import org.openengsb.core.common.validation.MultipleAttributeValidationResultImpl;
import org.osgi.framework.ServiceRegistration;

/**
 * Proxy Service Manager to instantiate Proxies to communicate with external systems.
 *
 * The proxy for the specified Domain are created upon request for a ServiceDescriptor.
 *
 * The ProxyServiceManager is completely generic. Business logic to interpret a certain call is handled via the
 *
 * @see InvocationHandler handed to the constructor.
 */
public class ProxyServiceManager extends AbstractServiceManagerParent implements ServiceManager {

    private final DomainProvider provider;
    private final InvocationHandler handler;

    private final Map<String, ServiceRegistration> services = new HashMap<String, ServiceRegistration>();

    public ProxyServiceManager(DomainProvider provider, InvocationHandler handler) {
        this.provider = provider;
        this.handler = handler;
    }

    @Override
    public ServiceDescriptor getDescriptor() {
        return ServiceDescriptor.builder(getStrings()).id(provider.getId()).serviceType(getDomainInterface())
            .implementationType(getDomainInterface())
            .name("jms.name", provider.getName().getString(Locale.getDefault())).description("jms.description").build();
    }

    @Override
    protected Class<? extends Domain> getDomainInterface() {
        return provider.getDomainInterface();
    }

    @Override
    public MultipleAttributeValidationResult update(String id, Map<String, String> attributes) {
        synchronized (services) {
            if (!services.containsKey(id)) {
                Domain newProxyInstance =
                    (Domain) Proxy.newProxyInstance(getDomainInterface().getClassLoader(),
                        new Class[]{getDomainInterface()}, handler);
                ServiceRegistration registration =
                    getBundleContext().registerService(
                        new String[]{getDomainInterface().getName(), Domain.class.getName()}, newProxyInstance,
                        createNotificationServiceProperties(id));
                services.put(id, registration);
            }
        }
        return new MultipleAttributeValidationResultImpl(true, new HashMap<String, String>());
    }

    @Override
    public Map<String, String> getAttributeValues(String id) {
        return new HashMap<String, String>();
    }

    @Override
    public void delete(String id) {
        synchronized (services) {
            services.get(id).unregister();
            services.remove(id);
        }
    }

    @Override
    protected Class<? extends Domain> getImplementationClass() {
        return provider.getDomainInterface();
    }
}
