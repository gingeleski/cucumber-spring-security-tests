@IntegrationTest @SecurityTest
Feature: Cache Control - UserController

  - All responses have header "Cache-Control" set to "no-cache, no-store, max-age=0, must-revalidate"
  - All responses have header "Pragma" set to "no-cache"
  - All responses have header "Expires" set to "0"

  Scenario: Response to successful user authentication has proper cache control headers set

    Given the application in an integration environment
    When the request body is "username=rjohnson&password=Bananas3"
    And header "Content-Type" is set to "application/x-www-form-urlencoded"
    And a "POST" request is made to endpoint "/login"
    Then the response should have status code 200
    And the response should have header "Cache-Control" set to "no-cache, no-store, max-age=0, must-revalidate"
    And the response should have header "Pragma" set to "no-cache"
    And the response should have header "Expires" set to "0"
