
package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import java.util.HashMap;
import org.junit.Assert;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CommonSteps {
    
    private Response response;
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private String jsonTemplate;
    private String finalEndpoint;


    @Given("the pet store API is available")
    public void the_pet_store_API_is_available() {
        RestAssured.baseURI = BASE_URL;
    }

    @When("I send a {string} request to create a pet with name {string}, status {string}, and pet ID {string}")
    public void send_request_to_create_a_pet(String method, String name, String status, String petId) {
        Map<String, Object> petData = new HashMap<>();
   
        petData.put("id", petId.isEmpty() ? null : petId); //Check if petId is empty
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", 0);  
        category.put("name", "string");
        petData.put("category", category);

        petData.put("name", name.isEmpty() ? null : name); //Check if name is empty

        petData.put("photoUrls", new String[]{"string"});

        Map<String, Object> tag = new HashMap<>();
        tag.put("id", 0);
        tag.put("name", "string");
        petData.put("tags", new Map[]{tag});
        petData.put("status", status);

        if (method.equalsIgnoreCase("POST")) { //Check if method is POST
          response = given()
                  .accept(ContentType.JSON)
                  .contentType(ContentType.JSON)
                  .body(petData)
                  .when()
                  .post(BASE_URL + "/pet")
                  .then()
                  .extract()
                  .response();
      } else if (method.equalsIgnoreCase("PUT")) { //Check if method is PUT
          response = given()
                  .accept(ContentType.JSON)
                  .contentType(ContentType.JSON)
                  .body(petData)
                  .when()
                  .put(BASE_URL + "/pet")
                  .then()
                  .extract()
                  .response();
      } else {
          throw new IllegalArgumentException("Invalid HTTP method: " + method);
      }

    }

    @When("I upload an image {string} for pet ID {int} with metadata {string}")
    public void upload_an_image_for_pet_with_metadata(String filePath, int petId, String metadata) {
      File file = new File("src/test/resources/images/" + filePath);

         // Check if file exists
    if (!file.exists()) {
      System.out.println(" File not found: " + file.getAbsolutePath());
      response = given()
              .contentType(ContentType.MULTIPART)
              .accept(ContentType.JSON)
              .multiPart("additionalMetadata", metadata) 
              .when()
              .post(BASE_URL + "/" + petId + "/uploadImage")
              .then()
              .extract()
              .response();
      return;
  }

  // If file exists, attach it to the request
  response = given()
          .contentType(ContentType.MULTIPART)
          .accept(ContentType.JSON)
          .multiPart("file", file)
          .multiPart("additionalMetadata", metadata)
          .when()
          .post(BASE_URL + "/pet" + "/" + petId + "/uploadImage")
          .then()
          .extract()
          .response();
}

    @When("I retrieve pet details for pet ID {int}")
    public void retrieve_pet_details_for_pet_ID(int petId) {
        response = given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/pet" + "/" +petId)
                .then()
                .extract()
                .response();
    }

    @When("I retrieve pets with status {string}")
    public void retrieve_pets_with_status(String status) {
    response = given()
            .accept(ContentType.JSON)
            .queryParam("status", status)
            .when()
            .get(BASE_URL + "/pet" + "/findByStatus")
            .then()
            .extract()
            .response();
    }

    @When("I update the pet with ID {string} to name {string} and status {string}")
    public void update_the_pet_with_id_to_name_and_status(String petId, String name, String status) {
    response = given()
            .accept(ContentType.JSON)
            .contentType("application/x-www-form-urlencoded")
            .formParam("name", name)
            .formParam("status", status)
            .when()
            .post(BASE_URL + "/pet" + "/"+ petId)
            .then()
            .extract()
            .response();

          String extractedId = JsonPath.from(response.getBody().asString()).getString("message");
          if (extractedId != null) {
            Serenity.setSessionVariable("savedPetId").to(extractedId);
            System.out.println("Saved Pet ID: " + extractedId);
        } else {
            System.out.println("No pet ID found in response");
        }

}

    @When("I delete the pet by ID {string}")
    public void i_delete_the_pet_using_the_saved_ID(String petId) {
        response = given()
                .accept(ContentType.JSON)
                .header("api_key", "your_api_key") // Replace with actual API key
                .when()
                .delete(BASE_URL + "/pet" + "/" + petId)
                .then()
                .extract()
                .response();

        System.out.println(" Successfully deleted pet with ID: " + petId);
    }

    @Then("the response should contain pet ID {int}")
    public void the_response_should_contain_pet_ID(int petId) {
        response.then().body("id", equalTo(petId));
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Then("the response should contain pet name {string} and status {string}")
    public void the_response_should_contain_pet_name_and_status(String expectedName, String expectedStatus) {
        response.then()
                .body("name", equalTo(expectedName))
                .body("status", equalTo(expectedStatus));
    }

    @Then("the response should contains message {string}")
    public void the_response_should_contain_message(String expectedMessage) {
      response.then().body("message", containsString(expectedMessage));
    }
    
    @Given("User retrieves the JSON template {string}")
    public void user_retrieves_the_json_template(String templateName) throws IOException {
        // Read JSON template from file
        String filePath = "src/test/resources/templates/" + templateName + ".json";
        jsonTemplate = new String(Files.readAllBytes(Paths.get(filePath)));
    }

    
    @When("User sends a POST request to {string}")
    public void user_sends_a_post_request_to(String finalEndpoint) {

                response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonTemplate)
                .when()
                .post(finalEndpoint)
                .then()
                .extract()
                .response();

        // Debugging: Print response body
        System.out.println("Response Body: " + response.getBody().asString());
        }

     @When("User updates the id field to a string")
    public void user_updates_the_id_field_to_a_string() {
        JsonPath jsonPath = new JsonPath(jsonTemplate);
        List<Map<String, Object>> users = jsonPath.getList("$");
        users.get(0).put("id", "test");
        jsonTemplate = users.toString().replace("=", ":");
        System.out.println("Updated JSON Template: " + jsonTemplate);
    }


    @Given("User sets the endpoint {string} with username {string}")
    public void user_sets_the_endpoint_with_username(String endpoint, String username) {
        finalEndpoint = endpoint + "/" + username;

        System.out.println("Final Endpoint: " + BASE_URL + finalEndpoint);
    }

    @When("User sends a GET request")
    public void user_sends_a_get_request() {
        response = given()
                .baseUri(BASE_URL)
                .accept(ContentType.JSON)
                .when()
                .get(finalEndpoint)
                .then()
                .extract()
                .response();

        System.out.println("Response Body: " + response.getBody().asString());
    }

    @When("User sends a PUT request")
    public void user_sends_a_put_request() {
        response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonTemplate)
                .when()
                .put(finalEndpoint)
                .then()
                .extract()
                .response();

        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Then("the response should contains username {string}")
    public void response_verify_username(String username) {
        response.then()
                .body("username", equalTo(username));
             
    }
}
