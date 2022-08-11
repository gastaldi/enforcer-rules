package com.github.gastaldi.plugins.enforcer;

import com.github.gastaldi.plugins.enforcer.util.EnforcerTestUtils;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class DescriptorsTest {

    @Test
    void shouldFailIfRefIsNotFound() {
        Descriptors rule = new Descriptors();
        rule.setDescriptorRef("foo");
        EnforcerRuleHelper helper = EnforcerTestUtils.getHelper();
        assertThatExceptionOfType(EnforcerRuleException.class).isThrownBy(() -> rule.execute(helper))
                .withMessage("Descriptors Ref 'foo' not found");
    }
}