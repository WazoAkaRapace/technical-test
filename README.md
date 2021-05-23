# BlaBlaCar Technical test for Backend Engineer
## Compile the project
### Requirement
- Java 11
- Maven

### Proper build instruction
The project lifecycle is managed by Maven. Build by running the following command :
````shell
mvn clean install
````

## Testing
### Automatic test
Unit tests can be run with JUnit 5 independently, or with maven surefire for convenience.
````shell
mvn test
````

### Manual test
Build the project first, then run the runnable jar using ``java -jar technical-test-1.0-SNAPSHOT-jar-with-dependencies.jar``
ie :
````shell
java -jar technical-test-1.0-SNAPSHOT-jar-with-dependencies.jar ../src/test/resources/Lawn.txt
````