Feature: Cryptocurrency API

  Scenario: Create a new cryptocurrency
    When the client sends a POST to "/api/v1/cryptocurrencies" with body:
      """
      {
        "name": "Bitcoin",
        "symbol": "BTC",
        "slug": "bitcoin",
        "marketCap": 500000000000.00,
        "priceUsd": 30000.00000000,
        "volume24h": 20000000000.00,
        "circulatingSupply": 19000000.00000000,
        "maxSupply": 21000000.00000000,
        "percentChange1h": 0.50,
        "percentChange24h": 1.20,
        "percentChange7d": -3.40,
        "rankPosition": 1,
        "isActive": true
      }
      """
    Then the response status code should be 201
    And the response body should contain "name" with value "Bitcoin"

  Scenario: Create fails with validation error when name is missing
    When the client sends a POST to "/api/v1/cryptocurrencies" with body:
      """
      {
        "symbol": "BTC",
        "slug": "bitcoin"
      }
      """
    Then the response status code should be 400

  Scenario: Get cryptocurrency returns 404 for non-existent id
    When the client calls GET "/api/v1/cryptocurrencies/999"
    Then the response status code should be 404
