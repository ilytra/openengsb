package org.openengsb.domains.test.maven;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openengsb.core.common.util.AliveState;
import org.openengsb.domains.test.maven.internal.MavenTestDomainServiceInstanceFactory;

public class MavenTestDomainServiceImplTest {

    public MavenTestDomainServiceImpl getService(String baseDir) {
        MavenTestDomainServiceInstanceFactory factory = new MavenTestDomainServiceInstanceFactory();
        Map<String, String> attributes = new HashMap<String, String>(1);
        attributes.put("basedir", baseDir);
        return factory.createServiceInstance("bla", attributes);
    }

    @Test
    public void executeRunTestsOnGreenTests_shouldReturnTrue() {
        assertTrue(getService("target/test-classes/test-unit-success").runTests());
    }

    @Test
    public void executeRunTestsOnRedTests_shouldReturnFalse() {
        assertFalse(getService("target/test-classes/test-unit-fail").runTests());
    }

    @Test
    public void executeGetAliveStateOnValidProject_shouldReturnOnlien() {
        assertThat(getService("target/test-classes/test-unit-success").getAliveState(), is(AliveState.ONLINE));
    }

    @Test
    public void executeGetAliveStateOnInvalidProject_shouldReturnDisconnected() {
        assertThat(getService("target/test-classes/test-invalid-pom").getAliveState(), is(AliveState.DISCONNECTED));
    }
}
