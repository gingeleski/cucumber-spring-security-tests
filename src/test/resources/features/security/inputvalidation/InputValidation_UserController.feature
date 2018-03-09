@IntegrationTest @SecurityTest
Feature: Input Validation - UserController

  - Endpoints that accept user input in some form should response as expected
  - Usernames must be at least 3 characters in length and at most 24 characters
  - Passwords must be at least 8 characters in length and at most 128 characters
  - Usernames may only contain alphanumeric characters, hyphens, or underscores

  Scenario Outline: When logging in, invalid inputs should be responded to as expected

    Given the application in an integration environment
    When the request body is "<requestBody>"
    And a "POST" request is made to endpoint "/login"
    Then the response should have status code 400

    Examples:
      | requestBody                                                                                                                                                |
      | username=&password=                                                                                                                                        |
      | username=ab&password=Password1                                                                                                                             |
      | username=abcde12345abcde12345abcde&password=Password1                                                                                                      |
      | username=tsmith&password=abcdefg                                                                                                                           |
      | username=tsmith&password=abcdefgh12345678abcdefgh12345678abcdefgh12345678abcdefgh12345678abcdefgh12345678abcdefgh12345678abcdefgh12345678abcdefgh12345678a |
      | username=j$mcd@onald&password=Password1                                                                                                                    |
