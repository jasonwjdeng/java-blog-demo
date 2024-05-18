package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Stepdefs {
  private HttpClient client;
  private HttpRequest request;
  private HttpResponse<String> response;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setup(Scenario scenario) {
    client = HttpClient.newHttpClient();
    request = HttpRequest.newBuilder().uri(URI.create("http://httpbin.org/get")).build();
  }

  @Given("the HTTPBin service is up and running")
  public void the_HTTPBin_service_is_up_and_running() {
    // This step is just a placeholder. In a real test, you might check the service status.
  }

  @When("a GET request is made to {string}")
  public void a_GET_request_is_made_to(String url) throws IOException, InterruptedException {
    response = client.send(request, BodyHandlers.ofString());
    log.debug("status={},body={}", response.statusCode(), response.body());
  }

  @Then("the response status should be {int}")
  public void the_response_status_should_be(int expectedStatus) {
    assertEquals(expectedStatus, response.statusCode());
  }

  @And("the response should contain the {string} property")
  public void the_response_should_contain_the_property(String property)
      throws JsonProcessingException {
    JsonNode jsonResponse = objectMapper.readTree(response.body());
    assertTrue(jsonResponse.has(property));
  }
}
