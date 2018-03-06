package cucumber.security;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

public class RequestStepDefs {

    @Given("^the application in an integration environment$")
    public void prepareIntegrationEnvironment() throws Throwable {
    }

    @Given("^the user is authenticated with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void authenticateUser(String username, String password) throws Throwable {
    }

    @When("^the request body is \"([^\"]*)\"$")
    public void setRequestBody(String requestBody) throws Throwable {
    }

    @When("^a \"([^\"]*)\" request is made to endpoint \"([^\"]*)\"$")
    public void makeRequest(String requestType, String endpoint) throws Throwable {
    }

    @Then("^the response should have status code (\\d+)$")
    public void thenStatement(int statusCode) throws Throwable {
    }

}
