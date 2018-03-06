@IntegrationTest @SecurityTest
Feature: Authentication - RoomController

  - Endpoints that require authentication cannot be accessed without it.

  Scenario: Cucumber setup

    Given sample feature file is ready
    When I run the feature file
    Then run should be successful