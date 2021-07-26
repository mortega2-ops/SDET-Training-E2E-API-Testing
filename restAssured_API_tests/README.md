# Rest Assured API tests

## Description
Tests written in Java with the rest-assured library. Done for Catalyte SDET Training program excercise. Used to test API status codes and response objects.

The postman collection was the first part of the assignment and can be found here:
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/37f2482c34a6d53e1a02?action=collection%2Fimport)

## Dependencies
This project runs tests against a todo website running on localhost:3000.

You can download the project from the following git repo: [https://github.com/filiphric/udemy-cypress-course](https://github.com/filiphric/udemy-cypress-course)

Once downloaded open up the dirctory and run npm install to install dependencies.

Once all the dependcies have been downloaded, change directory into the todo-mvc folder and run the command npm start.

Ensure the server is running on localhost:3000

## Testing Instructions
Assuming the todo website is up and running on localhost:3000

Configure pom.xml file dependencies for use with intellij junit, rest-assured

To run tests, open project in intellij, navigate to src/test/java/test/SeleniumTests.java and click run SeleniumTests.java next to the class declaration.


## Linting Instructions
As long as Google's Intellij linter is configured, ctrl+alt+l can be used to automatically lint each file.


## Contributing
Feedback is welcome. If you see issues, or have ideas for improvement, please submit feedback

### Credits
Project Source

### License
Catalyte © mortega2
