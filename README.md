# Enforcer Rules

## ExternalRules 
An Enforcer Rule that allows reusing Enforcer Rules by reading rule descriptor files


This rule supports rule descriptors to allow reusing enforcer rules as maven dependencies 

See https://issues.apache.org/jira/browse/MENFORCER-422 for more detail

### Usage

Add the following to your pom.xml:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-enforcer-plugin</artifactId>
    <version>3.1.0</version>
    <dependencies>
        <dependency>
            <groupId>com.github.gastaldi</groupId>
            <artifactId>enforcer-rules</artifactId>
            <version>0.0.1</version>
        </dependency>
    </dependencies>
    <executions>
        <execution>
            <id>enforce</id>
            <configuration>
                <rules>
                    <!-- This is where we use our External Rules -->
                    <ExternalRule>
                        <location>classpath:enforcer-rules/my-rules.xml</location>
                        <!-- You can also use a file path -->
                        <!--<location>enforcer-rules.xml</location> -->
                    </ExternalRule>
                </rules>
            </configuration>
            <goals>
                <goal>enforce</goal>
            </goals>
        </execution>
    </executions>
</plugin>

```

In the example above `<location>classpath:enforcer-rules/my-rules.xml</location>` is an XML file in `enforcer-rules/my-rules.xml` existing in the plugin dependencies.

The `enforcer-rules/my-rules.xml` has the following content: 

```xml
<enforcer>
    <rules>
        <dependencyConvergence/>
        <requireJavaVersion>
            <version>[${maven.compiler.release},)</version>
        </requireJavaVersion>
        <requireMavenVersion>
            <version>${supported-maven-versions}</version>
        </requireMavenVersion>
        <bannedDependencies>
            <excludes>
                <exclude>org.jboss.spec.javax.annotation:jboss-annotations-api_1.2_spec</exclude>
            </excludes>
            <includes>
                <include>jakarta.xml.bind:jakarta.xml.bind-api:*:*:test</include>
            </includes>
        </bannedDependencies>
    </rules>
</enforcer>
```

Heavily based on https://maven.apache.org/plugins/maven-assembly-plugin/examples/sharing-descriptors.html
