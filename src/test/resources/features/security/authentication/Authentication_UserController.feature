@IgnoreTest @IntegrationTest @SecurityTest
Feature: Authentication - UserController

  - Authentication is successful with a valid username and valid password
  - Authentication is unsuccessful with an invalid username and invalid password
  - Authentication is unsuccessful with a valid username but invalid password
  - Authentication is unsuccessful with a valid username and no password
  - Endpoints that require authentication cannot be accessed without it

  Scenario Outline: Authentication is successful with a valid username and valid password

    Given the application in an integration environment
    When the request body is "<requestBody>"
    And header "Content-Type" is set to "application/x-www-form-urlencoded"
    And a "POST" request is made to endpoint "/login"
    Then the response should have status code 200

      Examples:
        | requestBody                            |
        | username=rjohnson&password=Bananas3    |
        | username=tsmith&password=Grapefruit4   |
        | username=jmcdonald&password=Cranberry5 |
        | username=abrown&password=Watermelon6   |

  Scenario Outline: Authentication is unsuccessful with an invalid username and invalid password

    Given the application in an integration environment
    When the request body is "<requestBody>"
    And header "Content-Type" is set to "application/x-www-form-urlencoded"
    And a "POST" request is made to endpoint "/login"
    Then the response should have status code 401

      Examples:
        | requestBody                         |
        | username=mlauren&password=Password1 |
        | username=dwhite&password=Password1  |
        | username=cgrant&password=Password1  |
        | username=jwild&password=Password1   |

  Scenario Outline: Authentication is unsuccessful with a valid username but invalid password

    Given the application in an integration environment
    When the request body is "<requestBody>"
    And header "Content-Type" is set to "application/x-www-form-urlencoded"
    And a "POST" request is made to endpoint "/login"
    Then the response should have status code 401

      Examples:
        | requestBody                           |
        | username=rjohnson&password=Password1  |
        | username=tsmith&password=Password1    |
        | username=jmcdonald&password=Password1 |
        | username=abrown&password=Password1    |

  Scenario Outline: Authentication is unsuccessful with a valid username and no password

    Given the application in an integration environment
    When the request body is "<requestBody>"
    And header "Content-Type" is set to "application/x-www-form-urlencoded"
    And a "POST" request is made to endpoint "/login"
    Then the response should have status code 400

      Examples:
        | requestBody        |
        | username=rjohnson  |
        | username=tsmith    |
        | username=jmcdonald |
        | username=abrown    |

  Scenario Outline: An authenticated user can successfully access the logout endpoint

    Given the application in an integration environment
    When the user is authenticated with username "<username>" and password "<password>"
    And a "GET" request is made to endpoint "/logout"
    Then the response should have status code 200

      Examples:
        | username  | password    |
        | rjohnson  | Bananas3    |
        | tsmith    | Grapefruit4 |
        | jmcdonald | Cranberry5  |
        | abrown    | Watermelon6 |

  Scenario: An unauthenticated user cannot access the logout endpoint

    Given the application in an integration environment
    When a "GET" request is made to endpoint "/logout"
    Then the response should have access code 403
