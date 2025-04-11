# Robot Framework API Tests for Pet Store

This project contains automated tests for the Pet Store API using Robot Framework. The tests cover various functionalities such as adding new pets and users, finding pets by status, and retrieving a pet by ID.

## Project Structure

```
robot-petstore-api-tests
├── tests
│   ├── pets
│   │   ├── add_new_pet.robot
│   │   ├── find_pets_by_status.robot
│   │   └── retrieve_pet_by_id.robot
│   ├── users
│   │   └── add_new_user.robot
├── resources
│   ├── keywords
│   │   ├── pet_keywords.robot
│   │   └── user_keywords.robot
│   ├── variables
│       └── common_variables.robot
├── reports
├── logs
├── robot.yaml
└── README.md
```

## Setup Instructions

1. **Install Robot Framework**: Make sure you have Python installed, then install Robot Framework using pip:
   ```
   pip install robotframework
   ```

2. **Install Requests Library**: This project uses the Requests library for API calls. Install it with:
   ```
   pip install requests
   ```

3. **Clone the Repository**: Clone this repository to your local machine:
   ```
   git clone <repository-url>
   ```

4. **Navigate to the Project Directory**:
   ```
   cd robot-petstore-api-tests
   ```

## Running the Tests

To run the tests, use the following command:
```
robot tests/
```

This command will execute all the test cases located in the `tests` directory.

## Test Cases Overview

- **Add New Pet**: Tests the creation of a new pet with the name "Cat1".
- **Find Pets by Status**: Retrieves pets with the status "available" and verifies the response.
- **Retrieve Pet by ID**: Gets the pet with a specific ID and checks the response against the API contract.
- **Add New User**: Tests the creation of a new user with the name "Cat2".

## Reports and Logs

After running the tests, the reports will be generated in the `reports` directory, and logs will be stored in the `logs` directory.

## Contributing

Feel free to contribute to this project by submitting issues or pull requests. 

## License

This project is licensed under the MIT License.