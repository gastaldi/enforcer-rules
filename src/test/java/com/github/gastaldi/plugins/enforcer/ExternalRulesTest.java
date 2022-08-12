package com.github.gastaldi.plugins.enforcer;

import com.github.gastaldi.plugins.enforcer.util.EnforcerTestUtils;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugins.enforcer.EnforcerDescriptor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ExternalRulesTest {

    @Test
    void shouldFailIfNoLocationIsSet() {
        ExternalRules rule = new ExternalRules();
        EnforcerRuleHelper helper = EnforcerTestUtils.getHelper();
        assertThatExceptionOfType(EnforcerRuleException.class).isThrownBy(() -> rule.execute(helper))
                .withMessage("No location provided");
    }

    @Test
    void shouldFailIfClasspathLocationIsNotFound() {
        ExternalRules rule = new ExternalRules("classpath:foo");
        EnforcerRuleHelper helper = EnforcerTestUtils.getHelper();
        assertThatExceptionOfType(EnforcerRuleException.class).isThrownBy(() -> rule.execute(helper))
                .withMessage("Location 'foo' not found in classpath");
    }

    @Test
    void shouldFailIfFileLocationIsNotFound() {
        ExternalRules rule = new ExternalRules("blah.xml");
        EnforcerRuleHelper helper = EnforcerTestUtils.getHelper();
        assertThatExceptionOfType(EnforcerRuleException.class).isThrownBy(() -> rule.execute(helper))
                .withMessageMatching("Could not read descriptor in .*blah.xml");
    }

    @Test
    void shouldReadRules() throws Exception {
        ExternalRules rule = new ExternalRules("classpath:enforcer-rules/quarkus.xml");
        EnforcerRuleHelper helper = EnforcerTestUtils.getHelper();
        EnforcerDescriptor descriptor = rule.getEnforcerDescriptor(helper);
        assertThat(descriptor.getRules()).hasSize(1);
    }
}