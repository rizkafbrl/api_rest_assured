Feature: Update Pet in the Petstore

Scenario: Update an existing pet
  Given the pet update payload is ready
  When I send a PUT request to update the pet
  Then the response status code should be 200
  And the response should contain the updated pet name "doggie"
