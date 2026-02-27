Feature: Transferencia API

  Scenario: Create a new transfer
    When the client sends a POST to "/api/v1/transferencias" with body:
      """
      {
        "cuentaOrigenId": 100,
        "cuentaDestinoId": 200,
        "importe": 500.00,
        "divisa": "EUR",
        "concepto": "Test payment",
        "referenciaExterna": "REF-001"
      }
      """
    Then the response status code should be 201
    And the response body should contain "divisa" with value "EUR"

  Scenario: Create fails with validation error when amount is missing
    When the client sends a POST to "/api/v1/transferencias" with body:
      """
      {
        "cuentaOrigenId": 100,
        "cuentaDestinoId": 200,
        "divisa": "EUR"
      }
      """
    Then the response status code should be 400

  Scenario: Get transfer returns 404 for non-existent id
    When the client calls GET "/api/v1/transferencias/999999"
    Then the response status code should be 404
