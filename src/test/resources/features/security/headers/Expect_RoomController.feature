@IntegrationTest @SecurityTest
Feature: Expect header - RoomController

  - Server responds with 417 status and does not leak information when request has Expect header set to "foo"

  Scenario: Server responds appropriately when getting the rooms list with Expect header "foo"

    Given the application in an integration environment
    When the user is authenticated with username "rjohnson" and password "Bananas3"
    And header "Expect" is set to "foo"
    And a "GET" request is made to endpoint "/rooms"
    Then the response should have status code 417
    And the response body should be length 0

  Scenario: Server responds appropriately when getting a room by its name with Expect header "foo"

    Given the application in an integration environment
    When the user is authenticated with username "rjohnson" and password "Bananas3"
    And header "Expect" is set to "foo"
    And a "GET" request is made to endpoint "/rooms/05-104"
    Then the response should have status code 417
    And the response body should be length 0

  Scenario: Server responds appropriately when getting a room's availability by its name with Expect header "foo"

    Given the application in an integration environment
    When the user is authenticated with username "rjohnson" and password "Bananas3"
    And header "Expect" is set to "foo"
    And a "GET" request is made to endpoint "/rooms/05-104/availability"
    Then the response should have status code 417
    And the response body should be length 0
