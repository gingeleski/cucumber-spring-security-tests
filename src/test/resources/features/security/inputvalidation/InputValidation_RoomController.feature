@IntegrationTest @SecurityTest
Feature: Input Validation - RoomController

  - Endpoints that accept user input in some form should response as expected

  Scenario Outline: When getting a room by its name, invalid inputs should be responded to as expected

    Given the application in an integration environment
    When the user is authenticated with username "<username>" and password "<password>"
    And a "GET" request is made to endpoint "/rooms/<roomName>"
    Then the response should have status code 400

    Examples:
      | username  | password | roomName |
      | rjohnson  | Bananas3 | 05..104  |
      | rjohnson  | Bananas3 | 1';--    |
      | rjohnson  | Bananas3 | a$%;000  |

  Scenario Outline: When getting a room's availability by its name, invalid inputs should be responded to as expected

    Given the application in an integration environment
    When the user is authenticated with username "<username>" and password "<password>"
    And a "GET" request is made to endpoint "/rooms/<roomName>/availability"
    Then the response should have status code 400

    Examples:
      | username  | password | roomName |
      | rjohnson  | Bananas3 | 05..104  |
      | rjohnson  | Bananas3 | 1';--    |
      | rjohnson  | Bananas3 | a$%;000  |
