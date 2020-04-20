@IgnoreTest @IntegrationTest @SecurityTest
Feature: Authentication - RoomController

  - Endpoints that require authentication cannot be accessed without it

  Scenario Outline: An authenticated user can successfully get the rooms list

    Given the application in an integration environment
    When the user is authenticated with username "<username>" and password "<password>"
    And a "GET" request is made to endpoint "/rooms"
    Then the response should have status code 200

      Examples:
        | username  | password    |
        | rjohnson  | Bananas3    |
        | tsmith    | Grapefruit4 |
        | jmcdonald | Cranberry5  |
        | abrown    | Watermelon6 |

  Scenario Outline: An authenticated user can successfully get a room by its name

    Given the application in an integration environment
    When the user is authenticated with username "<username>" and password "<password>"
    And a "GET" request is made to endpoint "/rooms/<roomName>"
    Then the response should have status code 200

      Examples:
        | username  | password    | roomName |
        | rjohnson  | Bananas3    | 05-104   |
        | tsmith    | Grapefruit4 | 05-104   |
        | jmcdonald | Cranberry5  | 05-104   |
        | abrown    | Watermelon6 | 05-104   |

  Scenario Outline: An authenticated user can successfully get a room's availability by its name

    Given the application in an integration environment
    When the user is authenticated with username "<username>" and password "<password>"
    And a "GET" request is made to endpoint "/rooms/<roomName>/availability"
    Then the response should have status code 200

      Examples:
        | username  | password    | roomName |
        | rjohnson  | Bananas3    | 05-104   |
        | tsmith    | Grapefruit4 | 05-104   |
        | jmcdonald | Cranberry5  | 05-104   |
        | abrown    | Watermelon6 | 05-104   |

  Scenario Outline: An unauthenticated user cannot access the protected room controller endpoints
    Given the application in an integration environment
    When a "GET" request is made to endpoint "<endpoint>"
    Then the response should have status code 403

    Examples:
      | endpoint                   |
      | /rooms                     |
      | /rooms/05-104              |
      | /rooms/05-104/availability |
