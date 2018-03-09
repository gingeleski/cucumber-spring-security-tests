@IntegrationTest @SecurityTest
Feature: Missing Host - RoomController

  - TODO

  Scenario: An authenticated user can successfully get the rooms list

    Given the application in an integration environment
    When the user is authenticated with username "rjohnson" and password "Bananas3"
    And a "GET" request is made to endpoint "/rooms"
    Then the response should have status code 200
