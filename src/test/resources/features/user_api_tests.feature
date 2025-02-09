@allcases
Feature: Create and retreieve user

@regression @userpositive @TC_USR_API_01
Scenario: Send a POST request to create users with a list
  Given User retrieves the JSON template "postCreateWithList"
  When User sends a POST request to "/user/createWithList"
  Then the response status code should be 200
  And the response should contains message "ok" 

@regression @usernegative @TC_USR_API_01
Scenario: Send a POST request to create users with a list
  Given User retrieves the JSON template "postCreateWithList"
  When User updates the id field to a string
  And User sends a POST request to "/user/createWithList"
  Then the response status code should be 500

@regression @userpositive @TC_USR_API_01
Scenario: Successfully retrieve user detail
  Given User sets the endpoint "/user" with username "janedoe"
  When User sends a GET request
  Then the response status code should be 200
  And the response should contains username "janedoe"

@regression @usernegative @TC_USR_API_01
Scenario: Failed retrieve user detail
  Given User sets the endpoint "/user" with username "johndoe"
  When User sends a GET request
  Then the response status code should be 404
  And the response should contains message "User not found"


  


