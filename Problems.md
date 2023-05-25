# PROBLEMI PRINCIPALI E SOLUZIONI

## Cosa significa List<?>
Significa semplicemente che è una lista di un oggetto generico, che non si sa cosa sia. (<? extends Object>)

## SPIEGAZIONE DI CLASSNOTFOUNDEXCEPTION
Maven resolves dependencies when building your project, but doesn't put all the dependencies magically in your jar.
You're supposed to run your app with all its dependencies in the classpath:
```bash 
    java -classpath X.jar;Y.jar com.foo.bar.Main
```
Or you have to customize the maven jar plugin in order to create an executable jar.
Some plugins to solve the problem:
* maven assembly plugin (quello usato attualmente nel pom): permette di creare un unico jar con tutte le dipendenze
  The main goal in the assembly plugin is the single goal, which is used to create all assemblies.
  In the descriptorRefs part of the configuration code, we provided the name that will be added to the project name.
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-assembly-plugin</artifactId>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>single</goal>
            </goals>
            <configuration>
                <archive>
                <manifest>
                    <mainClass>
                        com.baeldung.executable.ExecutableMavenJar
                    </mainClass>
                </manifest>
                </archive>
                <descriptorRefs>
                    <descriptorRef>jar-with-dependencies</descriptorRef>
                </descriptorRefs>
            </configuration>
        </execution>
    </executions>
</plugin>
```
* maven dependency plugin: ci permette di mettere i jar delle dipendenze in un jar a parte, e di collegarlo a quello del nostro progetto tramite una configurazione del classpath:
  Il problema consiste nel fatto che le dipendenze in questo modo sarebbero separate.
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <executions>
        <execution>
            <id>copy-dependencies</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>copy-dependencies</goal>
            </goals>
            <configuration>
                <outputDirectory>
                    ${project.build.directory}/libs
                </outputDirectory>
            </configuration>
        </execution>
    </executions>
</plugin>
...
<plugin>
<groupId>org.apache.maven.plugins</groupId>
<artifactId>maven-jar-plugin</artifactId>
<configuration>
    <archive>
        <manifest>
            <addClasspath>true</addClasspath>
            <classpathPrefix>libs/</classpathPrefix>
            <mainClass>
                com.baeldung.executable.ExecutableMavenJar
            </mainClass>
        </manifest>
    </archive>
</configuration>
</plugin>
```
* apache maven shade plugin: the capability to package the artifact in an uber-jar, which consists of all dependencies required to run the project. Moreover, it supports shading — i.e. renaming — the packages of some of the dependencies.
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <executions>
        <execution>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <shadedArtifactAttached>true</shadedArtifactAttached>
                <transformers>
                    <transformer implementation= "org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <mainClass>com.baeldung.executable.ExecutableMavenJar</mainClass>
                    </transformer>
                </transformers>
            </configuration>
        </execution>
    </executions>
</plugin>
```
First, <shadedArtifactAttached> marks all dependencies to be packaged into the jar.
Second, we need to specify the transformer implementation; we used the standard one in our example.
Finally, we need to specify the main class of our application.
