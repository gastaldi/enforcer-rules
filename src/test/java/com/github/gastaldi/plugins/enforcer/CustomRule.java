package com.github.gastaldi.plugins.enforcer;

import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CustomRule implements EnforcerRule {
    @Override public void execute(@Nonnull EnforcerRuleHelper helper) throws EnforcerRuleException {

    }

    @Override public boolean isCacheable() {
        return false;
    }

    @Override public boolean isResultValid(@Nonnull EnforcerRule cachedRule) {
        return false;
    }

    @Nullable @Override public String getCacheId() {
        return null;
    }
}
