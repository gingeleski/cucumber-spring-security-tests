@IgnoreTest @IntegrationTest @SecurityTest
Feature: Authorization - UserController

  Note: authorization in UserController is currently flat, nothing to security test

  Scenario: Cucumber setup

    Given sample feature file is ready
    When I run the feature file
    Then run should be successful