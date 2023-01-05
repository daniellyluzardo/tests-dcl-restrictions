# README #

Please refer to this document regarding any doubts
### What is this repository for? ###

* Tests are built upon the Rest Assured automation lib, written in Java and executed using the TestNG unit framework.
* Version 1.0
  * To use testng and other lib, you can either add as a JAR file in your library through Project Structure > Modules > Dependencies > Add Jar or directories
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

### Who do I talk to? ###

* Repo owner [GITHUB](https://github.com/daniellyluzardo)
* 
### How to run? ###
* All tests are set with the logical priority to run without any data conflict. This logical can be enhanced in the future but it's imperative that the tests run in the defined order to return the expected statuses.
* Go to ValidateResponse class and select "Run ValidateResponse"(ctrl+shift+f10)

### Other Scenarios to automate ###
* Some scenarios can be added for later use:
  * Create simulation with two invalid values
  * create more simulations with values that i couldn't cover in the scenarios, as per invalid nome, invalid valor and so on
  * create some exceptions to deal with unseen errors (example: deleting twice will not display errors)

### Bugs to Report ###
* As per documentation, the duplicate scenario has to return the code 409 (Uma simulação para um mesmo CPF retorna um HTTP Status 409 com a mensagem
  "CPF já existente"), but the endpoint is returning 400 to this scenario: the Expected status code <409> but was <400>. 
* Delete endpoint has some twitches, such as:
  * as per documentation, a successfully deleted simulation will display the status 204 (Retorna o HTTP Status 204 se simulação for removida com sucesso) but the endpoint is returning 200 as per swagger 
  link.
    *When we perform the delete operation twice with the same id, it should display an error 404 (Retorna o HTTP Status 404 com a mensagem "Simulação não encontrada" se não
    existir a simulação pelo ID informado), but it is displaying a success message, leading the user to think that another simulation is also been deleted. So it is not possible to replicate the 404 status;