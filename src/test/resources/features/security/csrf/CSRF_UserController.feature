@IntegrationTest @SecurityTest
Feature: Authorization - UserController

  Note: CSRF is not currently applicable in UserController, nothing to security test

  Scenario: Cucumber setup

    Given sample feature file is ready
    When I run the feature file
    Then run should be successful