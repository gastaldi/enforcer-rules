# descriptor-rule
An Enforcer Rule that allows reusing Enforcer Rules by reading rule descriptor files


This rule supports rule descriptors to allow reusing enforcer rules as maven dependencies 

See https://issues.apache.org/jira/browse/MENFORCER-422 for more detail

## Usage

Add the following to your pom.xml:

```xml

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-enforcer-plugin</artifactId>
    <version>3.1.0</version>
    <dependencies>
        <dependency>
            <groupId>com.github.gastaldi</groupId>
            <artifactId>descriptor-rule</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
    <executions>
        <execution>
            <id>enforce</id>
            <configuration>
                <rules>
                    <!-- This is where we use our shared enforcer descriptor -->
                    <descriptorRefs>
                        <descriptorRef>quarkus</descriptorRef>
                    </descriptorRefs>
                </rules>
            </configuration>
            <goals>
                <goal>enforce</goal>
            </goals>
        </execution>
    </executions>
</plugin>

```

In the example above `<descriptorRef>quarkus</descriptorRef>` is an XML file in `enforcer-rules/quarkus.xml` existing in the plugin dependencies.

Heavily based on https://maven.apache.org/plugins/maven-assembly-plugin/examples/sharing-descriptors.html****
