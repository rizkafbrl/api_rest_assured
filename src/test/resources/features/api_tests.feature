@allcases
Feature: CRUD pet


@positivecase
Scenario Outline: Create a pet with valid pet id
  Given the pet store API is available
  When I send a "POST" request to create a pet with name "<name>", status "<status>", and pet ID "<id>"
  Then the response status code should be 200
  And the response should contain pet name "<name>" and status "<status>"
  Examples:
  | id  | name     | status    |
  | 2   | Rocky    | sold      |
  | 5   | Jane     | pending   |
  | 10  | George   | available |

@negativecase
Scenario: Create a pet with invalid pet id
  Given the pet store API is available
  When I send a "POST" request to create a pet with name "George", status "available", and pet ID "invalid_data"
  Then the response status code should be 500

@positivecase
Scenario: Update pet name
  Given the pet store API is available
  When I send a "PUT" request to create a pet with name "Tarzan", status "pending", and pet ID "5"
  Then the response status code should be 200
  And the response should contain pet name "Tarzan" and status "pending"

@negativecase
Scenario: Update pet id with null value
  Given the pet store API is available
  When I send a "PUT" request to create a pet with name "George", status "pending", and pet ID "<id>"
  Then the response status code should be 500
  Examples:
  | id  | status_code   |
  | {}  | 500           |
  | *&  | 400           |
  # And the response should contain pet name "Jane" and status "pending"

@positivecase
Scenario: Successfully upload an image for a pet
  Given the pet store API is available
  When I upload an image "cat.jpeg" for pet ID 10 with metadata "test"
  Then the response status code should be 200
  And the response should contains message "uploaded"

@negativecase
Scenario: Upload a non-existing file
  Given the pet store API is available
  When I upload an image "non_existing_file.jpeg" for pet ID 10 with metadata "test"
  Then the response status code should be 500

@positivecase
Scenario: Get pet by ID
  Given the pet store API is available
  When I retrieve pet details for pet ID 10
  Then the response status code should be 200
  And the response should contain pet ID 10

@negativecase
Scenario: Get pet by invalid ID
  Given the pet store API is available
  When I retrieve pet details for pet ID 0
  Then the response status code should be 404
  And the response should contains message "Pet not found"

@positivecase
Scenario Outline: Get pets by status
  Given the pet store API is available
  When I retrieve pets with status "<status>"
  Then the response status code should be 200
  # And the response should contain pets with status "<status>"
  Examples:
    | status     |
    | available  |
    | pending    |
    | sold       |

@negativecase
Scenario Outline: Update pet name and status by invalid ID
  Given the pet store API is available
  When I update the pet with ID "0" to name "John Doe" and status "other"
  Then the response status code should be 404

@positivecase
Scenario Outline: Update pet name and status by ID
  Given the pet store API is available
  When I update the pet with ID "<id>" to name "<name>" and status "<status>"
  Then the response status code should be 200

  Examples:
  | id  | name     | status    |
  | 2   | Rocky    | sold      |
  | 5   | Jane     | pending   |
  | 10  | Pimpi    | available |


@positivecase @negativecase
Scenario Outline: Delete the pet using ID
  Given the pet store API is available
  When I delete the pet by ID "<id>"
  Then the response status code should be <status_code>

  @positivecase
  Examples:
  | id  | status_code  | 
  | 10  | 200          |

  @negativecase
  Examples:
  | id  | status_code  | 
  | 10  | 404          |
 
