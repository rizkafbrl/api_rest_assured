Feature: Update Pet in the Petstore Negative Cases

Scenario: Update pet with invalid category ID
  Given the pet update request with an invalid category ID
  When I send a PUT request to update the pet
  Then the response status code should be 500
  # And the response should contain an error message