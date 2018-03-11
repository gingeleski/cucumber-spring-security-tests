@IgnoreTest @IntegrationTest @SecurityTest @OnlyThis
Feature: Content-Type - UserController

  - Server responds appropriately to incoming requests with no Content-Type header

  Scenario: Request missing Content-Type header receives appropriate response from server

    Given the application in an integration environment
    When the request body is "username=rjohnson&password=Bananas3"
    # Error in this case because RestTemplate is automatically setting Content-Type header on request
    And the request has no "Content-Type" header
    And a "POST" request is made to endpoint "/login"
    Then the response should have status code 400
