Feature: Counterparty Risk Profile API

  Scenario: Create a new counterparty risk profile
    When the client sends a POST to "/api/v1/counterparty-risk-profiles" with body:
      """
      {
        "legalName": "Acme Corp",
        "countryCode": "USA",
        "creditRating": "AA+",
        "riskScore": 85.50,
        "exposureLimit": 1000000.00
      }
      """
    Then the response status code should be 201
    And the response body should contain "legalName" with value "Acme Corp"

  Scenario: Create fails with validation error when legal name is missing
    When the client sends a POST to "/api/v1/counterparty-risk-profiles" with body:
      """
      {
        "countryCode": "USA",
        "creditRating": "AA+"
      }
      """
    Then the response status code should be 400

  Scenario: Get counterparty risk profile returns 404 for non-existent id
    When the client calls GET "/api/v1/counterparty-risk-profiles/00000000-0000-0000-0000-000000000999"
    Then the response status code should be 404
