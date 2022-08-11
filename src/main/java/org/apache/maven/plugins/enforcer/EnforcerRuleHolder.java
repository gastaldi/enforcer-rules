package org.apache.maven.plugins.enforcer;

import org.apache.maven.enforcer.rule.api.EnforcerRule;

/**
 * This class needs to be in the same package as the enforcer plugin and the built-in rules
 */
public class EnforcerRuleHolder {
    EnforcerRule[] rules;

    public EnforcerRule[] getRules() {
        return rules;
    }
}
