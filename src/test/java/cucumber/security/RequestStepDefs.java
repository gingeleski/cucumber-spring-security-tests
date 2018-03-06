package cucumber.security;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RequestStepDefs extends IntegrationTestingBase {

    private ResponseEntity<String> response;

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
        if (requestType.equals("GET")) {
            response = template.getForEntity("endpoint", String.class);
        }
    }

    @Then("^the response should have status code (\\d+)$")
    public void assertResponseStatusCode(int statusCode) throws Throwable {
        HttpStatus currentStatusCode = response.getStatusCode();

        assertThat("Response status code is not as expected : " +
                response.getBody(), currentStatusCode.value(), is(statusCode));
    }

}
