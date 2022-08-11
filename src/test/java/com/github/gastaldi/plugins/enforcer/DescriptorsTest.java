package com.github.gastaldi.plugins.enforcer;

import com.github.gastaldi.plugins.enforcer.util.EnforcerTestUtils;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class DescriptorsTest {

    @Test
    void shouldFailIfNoDescriptorIsSet() {
        Descriptors rule = new Descriptors();
        EnforcerRuleHelper helper = EnforcerTestUtils.getHelper();
        assertThatExceptionOfType(EnforcerRuleException.class).isThrownBy(() -> rule.execute(helper))
                .withMessage("No descriptorRef or descriptor provided");
    }

    @Test
    void shouldFailIfRefIsNotFound() {
        Descriptors rule = new Descriptors();
        rule.setDescriptorRef("foo");
        EnforcerRuleHelper helper = EnforcerTestUtils.getHelper();
        assertThatExceptionOfType(EnforcerRuleException.class).isThrownBy(() -> rule.execute(helper))
                .withMessage("Descriptor Ref 'foo' not found");
    }

    @Test
    void shouldFailIfDescriptorIsNotFound() {
        Descriptors rule = new Descriptors();
        rule.setDescriptor("blah.xml");
        EnforcerRuleHelper helper = EnforcerTestUtils.getHelper();
        assertThatExceptionOfType(EnforcerRuleException.class).isThrownBy(() -> rule.execute(helper))
                .withMessageMatching("Could not read descriptor in .*blah.xml");
    }


}