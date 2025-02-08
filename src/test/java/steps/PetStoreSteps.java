
package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;

import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;
import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetStoreSteps {
    
    private Response response;
    private static final String BASE_URL = "https://petstore.swagger.io/v2/pet";
    private static String savedPetId;


    @Given("the pet store API is available")
    public void the_pet_store_API_is_available() {
        RestAssured.baseURI = BASE_URL;
    }

    @When("I send a {string} request to create a pet with name {string}, status {string}, and pet ID {string}")
    public void i_send_a_POST_request_to_create_a_pet(String method, String name, String status, String petId) {
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
                  .post(BASE_URL)
                  .then()
                  .extract()
                  .response();
      } else if (method.equalsIgnoreCase("PUT")) { //Check if method is PUT
          response = given()
                  .accept(ContentType.JSON)
                  .contentType(ContentType.JSON)
                  .body(petData)
                  .when()
                  .put(BASE_URL)
                  .then()
                  .extract()
                  .response();
      } else {
          throw new IllegalArgumentException("Invalid HTTP method: " + method);
      }

    }

    @When("I upload an image {string} for pet ID {int} with metadata {string}")
    public void i_upload_an_image_for_pet_with_metadata(String filePath, int petId, String metadata) {
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
          .post(BASE_URL + "/" + petId + "/uploadImage")
          .then()
          .extract()
          .response();
}

    @When("I retrieve pet details for pet ID {int}")
    public void i_retrieve_pet_details_for_pet_ID(int petId) {
        response = given()
                .accept(ContentType.JSON)
                .when()
                .get(BASE_URL + "/" +petId)
                .then()
                .extract()
                .response();
    }

    @When("I retrieve pets with status {string}")
    public void i_retrieve_pets_with_status(String status) {
    response = given()
            .accept(ContentType.JSON)
            .queryParam("status", status)
            .when()
            .get(BASE_URL + "/findByStatus")
            .then()
            .extract()
            .response();
    }

    @When("I update the pet with ID {string} to name {string} and status {string}")
    public void i_update_the_pet_with_id_to_name_and_status(String petId, String name, String status) {
    response = given()
            .accept(ContentType.JSON)
            .contentType("application/x-www-form-urlencoded")
            .formParam("name", name)
            .formParam("status", status)
            .when()
            .post(BASE_URL + "/"+ petId)
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
                .delete(BASE_URL + "/" + petId)
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
}
