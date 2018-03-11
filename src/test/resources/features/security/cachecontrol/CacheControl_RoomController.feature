@IntegrationTest @SecurityTest
Feature: Cache Control - RoomController

  - All responses have header "Cache-Control" set to "no-cache, no-store, max-age=0, must-revalidate"
  - All responses have header "Pragma" set to "no-cache"
  - All responses have header "Expires" set to "0"

  Scenario: Response to get the rooms list has proper cache control headers set

    Given the application in an integration environment
    When the user is authenticated with username "rjohnson" and password "Bananas3"
    And a "GET" request is made to endpoint "/rooms"
    Then the response should have status code 200
    And the response should have header "Cache-Control" set to "no-cache, no-store, max-age=0, must-revalidate"
    And the response should have header "Pragma" set to "no-cache"
    And the response should have header "Expires" set to "0"
