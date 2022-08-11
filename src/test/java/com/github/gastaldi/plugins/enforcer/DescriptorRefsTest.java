package com.github.gastaldi.plugins.enforcer;

import com.github.gastaldi.plugins.enforcer.util.EnforcerTestUtils;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class DescriptorRefsTest {

    @Test
    void shouldFailIfRefIsNotFound() {
        DescriptorRefs rule = new DescriptorRefs();
        rule.setDescriptorRef("foo");
        EnforcerRuleHelper helper = EnforcerTestUtils.getHelper();
        assertThatExceptionOfType(EnforcerRuleException.class).isThrownBy(() -> rule.execute(helper))
                .withMessage("Descriptor Ref 'foo' not found");
    }
}