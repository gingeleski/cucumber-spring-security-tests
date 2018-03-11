@IgnoreTest @IntegrationTest @SecurityTest
Feature: Missing Host - UserController

  - TODO

  Scenario: TODO Missing Host - UserController

    Given the application in an integration environment
    When the request body is "username=rjohnson&password=Bananas3"
    And a "POST" request is made to endpoint "/login"
    Then the response should have status code 200