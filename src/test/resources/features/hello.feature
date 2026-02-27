Feature: Hello World endpoint

  Scenario: Client calls GET /hello
    When the client calls GET "/hello"
    Then the response status code should be 200
    And the response body should be "Hello World"
