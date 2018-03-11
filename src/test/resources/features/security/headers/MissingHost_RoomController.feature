@IgnoreTest @IntegrationTest @SecurityTest
Feature: Missing Host - RoomController

  - TODO

  Scenario: TODO Missing Host - Room Controller

    Given the application in an integration environment
    When the user is authenticated with username "rjohnson" and password "Bananas3"
    And a "GET" request is made to endpoint "/rooms"
    Then the response should have status code 200
