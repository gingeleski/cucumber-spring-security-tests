@IgnoreTest @IntegrationTest @SecurityTest
Feature: X-Content-Type-Options - RoomController

  - Endpoints that return content should have response header X-Content-Type-Options set to "nosniff"

  Scenario: Response X-Content-Type-Options header is set when getting the rooms list

    Given the application in an integration environment
    When the user is authenticated with username "rjohnson" and password "Bananas3"
    And a "GET" request is made to endpoint "/rooms"
    Then the response should have status code 200
    And the response should have header "X-Content-Type-Options" set to "nosniff"

  Scenario: Response X-Content-Type-Options header is set when getting a room by its name

    Given the application in an integration environment
    When the user is authenticated with username "rjohnson" and password "Bananas3"
    And a "GET" request is made to endpoint "/rooms/05-104"
    Then the response should have status code 200
    And the response should have header "X-Content-Type-Options" set to "nosniff"

  Scenario: Response X-Content-Type-Options header is set when getting a room's availability by its name

    Given the application in an integration environment
    When the user is authenticated with username "rjohnson" and password "Bananas3"
    And a "GET" request is made to endpoint "/rooms/05-104/availability"
    Then the response should have status code 200
    And the response should have header "X-Content-Type-Options" set to "nosniff"
