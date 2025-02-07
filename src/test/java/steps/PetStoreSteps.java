package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import java.util.HashMap;
import java.util.Map;

public class PetStoreSteps {
    private static final String BASE_URL = "https://petstore.swagger.io/v2/pet";
    private Response response;
    private Map<String, Object> petPayload;

    @Given("the pet update payload is ready")
    public void preparePetPayload() {
        petPayload = new HashMap<>();
        petPayload.put("id", 0);
        petPayload.put("name", "doggie");
        petPayload.put("status", "available");

        Map<String, Object> category = new HashMap<>();
        category.put("id", 0);
        category.put("name", "string");
        petPayload.put("category", category);

        petPayload.put("photoUrls", new String[]{"string"});

        Map<String, Object> tag = new HashMap<>();
        tag.put("id", 0);
        tag.put("name", "string");
        petPayload.put("tags", new Map[]{tag});
    }

    @When("I send a PUT request to update the pet")
    public void sendPutRequest() {
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(petPayload)
                .when()
                .put(BASE_URL);
    }

    @Then("the response status code should be 200")
    public void verifyStatusCode() {
        Assertions.assertEquals(200, response.getStatusCode(), "Expected status code 200");
    }

    @Then("the response status code should be 500")
    public void verifyStatusCodeFailed() {
        Assertions.assertEquals(500, response.getStatusCode(), "Expected status code 500");
    }

    @Then("the response should contain the updated pet name {string}")
    public void verifyResponseBody(String expectedName) {
        String actualName = response.jsonPath().getString("name");
        Assertions.assertEquals(expectedName, actualName, "Pet name should match");
    }
    @Given("the pet update request with an invalid category ID")
    public void invalidIDPayload() {
      petPayload = new HashMap<>();
      petPayload.put("id", "invalidID");
      petPayload.put("name", "doggie");
      petPayload.put("status", "available");

      Map<String, Object> category = new HashMap<>();
      category.put("id", 0);
      category.put("name", "string");
      petPayload.put("category", category);

      petPayload.put("photoUrls", new String[]{"string"});

      Map<String, Object> tag = new HashMap<>();
      tag.put("id", 0);
      tag.put("name", "string");
      petPayload.put("tags", new Map[]{tag});
  }

}
