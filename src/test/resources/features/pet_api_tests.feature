@allcases
Feature: CRUD pet


@regression @petpositivecase @TC_PET_01
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

@regression @petnegativecase @TC_PET_02
Scenario: Create a pet with invalid pet id
  Given the pet store API is available
  When I send a "POST" request to create a pet with name "George", status "available", and pet ID "invalid_data"
  Then the response status code should be 500

@regression @petpositivecase @TC_PET_03
Scenario: Update pet name
  Given the pet store API is available
  When I send a "PUT" request to create a pet with name "Tarzan", status "pending", and pet ID "5"
  Then the response status code should be 200
  And the response should contain pet name "Tarzan" and status "pending"

@regression @petnegativecase @TC_PET_04
Scenario Outline: Update pet id with characters
  Given the pet store API is available
  When I send a "PUT" request to create a pet with name "George", status "pending", and pet ID "<id>"
  Then the response status code should be 500
  Examples:
  | id  | status_code   |
  | {}  | 500           |
  | *&  | 400           |
  # And the response should contain pet name "Jane" and status "pending"

@regression @petpositivecase @TC_PET_05
Scenario: Successfully upload an image for a pet
  Given the pet store API is available
  When I upload an image "cat.jpeg" for pet ID 10 with metadata "test"
  Then the response status code should be 200
  And the response should contains message "uploaded"

@regression @petnegativecase @TC_PET_06
Scenario: Upload a non-existing file
  Given the pet store API is available
  When I upload an image "non_existing_file.jpeg" for pet ID 10 with metadata "test"
  Then the response status code should be 404

@regression @petpositivecase @TC_PET_07
Scenario: Get pet by ID
  Given the pet store API is available
  When I retrieve pet details for pet ID 10
  Then the response status code should be 200
  And the response should contain pet ID 10

@regression @petnegativecase @TC_PET_08
Scenario: Get pet by invalid ID
  Given the pet store API is available
  When I retrieve pet details for pet ID 0
  Then the response status code should be 404
  And the response should contains message "Pet not found"

@regression @petpositivecase @TC_PET_09
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

@regression @petnegativecase @TC_PET_10
Scenario: Update pet name and status by invalid ID
  Given the pet store API is available
  When I update the pet with ID "0" to name "John Doe" and status "other"
  Then the response status code should be 404

@regression @petpositivecase @TC_PET_11
Scenario Outline: Update pet name and status by ID
  Given the pet store API is available
  When I update the pet with ID "<id>" to name "<name>" and status "<status>"
  Then the response status code should be 200

  Examples:
  | id  | name     | status    |
  | 2   | Rocky    | sold      |
  | 5   | Jane     | pending   |
  | 10  | Pimpi    | available |


@regression @petpositivecase @petnegativecase @TC_PET_12
Scenario Outline: Delete the pet using ID
  Given the pet store API is available
  When I delete the pet by ID "<id>"
  Then the response status code should be <status_code>

  @regression @petpositivecase @TC_PET_13
  Examples:
  | id  | status_code  | 
  | 10  | 200          |

  @regression @petnegativecase @TC_PET_14
  Examples:
  | id  | status_code  | 
  | 10  | 404          |
 
