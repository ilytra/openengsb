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

package org.openengsb.integrationtest.exam;

import static junit.framework.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openengsb.core.common.ServiceManager;
import org.openengsb.core.common.context.ContextCurrentService;
import org.openengsb.domains.test.maven.MavenTestDomainServiceImpl;
import org.openengsb.integrationtest.util.AbstractExamTestHelper;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

@RunWith(JUnit4TestRunner.class)
public class MaventestIT extends AbstractExamTestHelper {
    private MavenTestDomainServiceImpl testService;
    private ServiceManager serviceManager;

    @Before
    public void setUp() throws Exception {
        testService = retrieveService(getBundleContext(), MavenTestDomainServiceImpl.class);
        retrieveServiceManager(getBundleContext(), MavenTestDomainServiceImpl.class);
        ContextCurrentService contextService = retrieveService(getBundleContext(), ContextCurrentService.class);
        contextService.createContext("42");
        contextService.setThreadLocalContext("42");
        contextService.putValue("domains/TestDomain/defaultConnector/id", "mavenConnector");

        serviceManager = retrieveServiceManager(getBundleContext(), MavenTestDomainServiceImpl.class);
    }

    @Test
    public void testTestSuccess() {       
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("basedir", "target/test-classes/test-unit-success");
        serviceManager.update("mavenConnector", properties);

        assertTrue(testService.runTests());
    }
    @Test
    public void testTestFail() {       
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("basedir", "target/test-classes/test-unit-fail");
        serviceManager.update("mavenConnector", properties);
        
        assertTrue(testService.runTests());
    }
}
