package com.github.gastaldi.plugins.enforcer;

import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugins.enforcer.EnforcerDescriptor;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.codehaus.plexus.component.configurator.ComponentConfigurator;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.configuration.xml.XmlPlexusConfiguration;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * An enforcer rule that will invoke rules from an external resource
 * This is inside the `org.apache.maven.plugins.enforcer` package to ease configuration (and because it may be migrated to the built-in plugins soon)
 *
 * @see https://issues.apache.org/jira/browse/MENFORCER-422
 */
public class ExternalRule implements EnforcerRule {

    String descriptorRef;

    String descriptor;

    @Override
    public void execute(EnforcerRuleHelper helper) throws EnforcerRuleException {
        // Find descriptor
        EnforcerDescriptor enforcerDescriptor = getEnforcerDescriptor(helper);
        for (EnforcerRule rule : enforcerDescriptor.getRules()) {
            rule.execute(helper);
        }
    }

    private InputStream resolveDescriptor(EnforcerRuleHelper helper) throws EnforcerRuleException {
        InputStream descriptorStream;
        if (descriptorRef != null) {
            descriptorStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("enforcer-rules/" + descriptorRef + ".xml");
            if (descriptorStream == null) {
                throw new EnforcerRuleException("Descriptor Ref '" + descriptorRef + "' not found");
            }
        } else if (descriptor != null) {
            File descriptorFile = helper.alignToBaseDirectory(new File(descriptor));
            try {
                descriptorStream = Files.newInputStream(descriptorFile.toPath());
            } catch (IOException e) {
                throw new EnforcerRuleException("Could not read descriptor in "+descriptorFile, e);
            }
        } else {
            throw new EnforcerRuleException("No descriptorRef or descriptor provided");
        }
        return descriptorStream;
    }

    EnforcerDescriptor getEnforcerDescriptor(EnforcerRuleHelper helper)
            throws EnforcerRuleException {
        try (InputStream descriptorStream = resolveDescriptor(helper)) {
            EnforcerDescriptor descriptor = new EnforcerDescriptor();
            // To get configuration from the enforcer-plugin mojo do:
            //helper.evaluate(helper.getComponent(MojoExecution.class).getConfiguration().getChild("fail").getValue())
            // Get the enforcer plugin's class resolver
            ClassRealm realm = helper.getComponent(MojoExecution.class).getMojoDescriptor().getRealm();
            ComponentConfigurator configurator = helper.getComponent(ComponentConfigurator.class, "basic");
            // Configure EnforcerDescriptor from the XML
            configurator.configureComponent(descriptor, toPlexusConfiguration(descriptorStream), helper, realm);
            return descriptor;
        } catch (EnforcerRuleException e) {
            throw e;
        } catch (Exception e) {
            throw new EnforcerRuleException("Error while enforcing rules", e);
        }
    }

    private static PlexusConfiguration toPlexusConfiguration(InputStream descriptorStream)
            throws XmlPullParserException, IOException {
        return new XmlPlexusConfiguration(Xpp3DomBuilder.build(descriptorStream, "UTF-8"));
    }

    @Override
    public boolean isCacheable() {
        return true;
    }

    @Override
    public boolean isResultValid(EnforcerRule enforcerRule) {
        return false;
    }

    @Override
    public String getCacheId() {
        return descriptorRef;
    }

    public void setDescriptorRef(String descriptorRef) {
        this.descriptorRef = descriptorRef;
    }

    public String getDescriptorRef() {
        return descriptorRef;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getDescriptor() {
        return descriptor;
    }
}