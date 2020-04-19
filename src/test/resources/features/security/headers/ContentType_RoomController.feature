@IgnoreTest @IntegrationTest @SecurityTest
Feature: Content-Type - RoomController

  - Endpoints that return content should have response header Content-Type with an appropriate setting

  Scenario: Response Content-Type header is set for JSON when getting the rooms list

    Given the application in an integration environment
    When the user is authenticated with username "rjohnson" and password "Bananas3"
    And a "GET" request is made to endpoint "/rooms"
    Then the response should have status code 200
    And the response should have header "Content-Type" set to "application/json;charset=UTF-8"

  Scenario: Response Content-Type header is set for JSON when getting a room by its name

    Given the application in an integration environment
    When the user is authenticated with username "rjohnson" and password "Bananas3"
    And a "GET" request is made to endpoint "/rooms/05-104"
    Then the response should have status code 200
    And the response should have header "Content-Type" set to "application/json;charset=UTF-8"

  Scenario: Response Content-Type header is set for JSON when getting a room's availability by its name

    Given the application in an integration environment
    When the user is authenticated with username "rjohnson" and password "Bananas3"
    And a "GET" request is made to endpoint "/rooms/05-104/availability"
    Then the response should have status code 200
    And the response should have header "Content-Type" set to "application/json;charset=UTF-8"
