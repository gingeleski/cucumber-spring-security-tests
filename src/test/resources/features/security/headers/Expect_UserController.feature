@IgnoreTest @IntegrationTest @SecurityTest
Feature: Expect Header - UserController

  - Server responds with 417 status and does not leak information when request has Expect header set to "foo"

  Scenario: Server responds appropriately when logging in with Expect header "foo"

    Given the application in an integration environment
    When the request body is "username=rjohnson&password=Bananas3"
    And header "Content-Type" is set to "application/x-www-form-urlencoded"
    And header "Expect" is set to "foo"
    And a "GET" request is made to endpoint "/rooms"
    Then the response should have status code 417
    And the response body should be length 0