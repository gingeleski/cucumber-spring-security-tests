@IgnoreTest @IntegrationTest @SecurityTest
Feature: Authorization - RoomController

  Note: authorization in RoomController is currently flat, nothing to security test

  Scenario: Cucumber setup

    Given sample feature file is ready
    When I run the feature file
    Then run should be successful