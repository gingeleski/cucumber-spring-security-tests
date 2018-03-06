@IntegrationTest @SecurityTest
Feature: Authentication - UserController

  - Authentication is unsuccessful with invalid username.
  - Authentication is unsuccessful with invalid password.
  - Authentication is unsuccessful without password.
  - Endpoints that require authentication cannot be accessed without it.

  Scenario: User authenticates successfully with valid username and password.

    Given the application in an integration environment
    When the request body is "username=rjohnson&password=Banana3"
    And a POST request is made to endpoint "/login"
    Then the response should have status code 200
