Feature: Validate HTTPBin GET Response

  Scenario: Validate the response from HTTPBin GET request
    Given the HTTPBin service is up and running
    When a GET request is made to "http://httpbin.org/get"
    Then the response status should be 200
    And the response should contain the "args" property
    And the response should contain the "headers" property
    And the response should contain the "origin" property
