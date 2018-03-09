@IntegrationTest @SecurityTest
Feature: Expect Header - UserController

  - TODO

  Scenario: Authentication is successful with a valid username and valid password

    Given the application in an integration environment
    When the request body is "username=rjohnson&password=Bananas3"
    And a "POST" request is made to endpoint "/login"
    Then the response should have status code 200