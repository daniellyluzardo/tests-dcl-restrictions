# README #

Please refer to this document regarding any doubts
### What is this repository for? ###

* Tests are built upon the Rest Assured automation lib, written in Java and executed using the TestNG unit framework.
* Version 1.0
* Please make sure to add the latest stable version dependency of the selenium webdriver library
  * To use selenium, you can either add as a JAR file in your library through Project Structure > Modules > Dependencies > Add Jar or directories
  * Or add the dependency through Maven or Gradle
* [REST Assured Documentation](https://github.com/rest-assured/rest-assured/wiki/GettingStarted)
* [MVN repository for REST Assured](https://mvnrepository.com/artifact/io.rest-assured)
* [MVN repository for TestNG](https://mvnrepository.com/artifact/org.testng/testng/)
* [MVN repository for JUnit](https://mvnrepository.com/artifact/junit/junit/)

### How do I get set up? ###

* You must have a proper IDE to be able to run Java projects (Intellij, Eclipse or Visual Studio Code)
* For this code, we are using jdk-17.0.5, refer to java --version to make sure your java is compatible
* Configuration
* For dependencies:
  * add the latest dependency for REST Assured (please refer to the links above)
  * add the latest dependency for TestNG (please refer to the links above)
* How to run tests
  * You may run TestSteps on tests package or run each scenario separately
  * You may change test priority to run all scenarios in a certain order
* ----------------------------------------------------------Screenshots will be stored in C:\Screenshots\

### Who do I talk to? ###

* Repo owner [GITHUB](https://github.com/daniellyluzardo)
* 
### How to run? ###
* All tests are set with the logical priority to run without any data conflict. This logical can be enhanced in the future but it's imperative that the tests run in the defined order to return the expected statuses.
*  
### Other Scenarios to automate ###
* Some scenarios can be added for later use:



### Bugs to Report ###
* As per documentation, the duplicate scenario has to return the code 409; the Expected status code <409> but was <400>. 
