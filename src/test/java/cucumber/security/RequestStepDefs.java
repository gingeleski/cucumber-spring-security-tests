package cucumber.security;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RequestStepDefs extends IntegrationTestingBase
{
    private HttpHeaders reqHeaders = new HttpHeaders();
    private String reqBody;

    private ResponseEntity<String> res;

    @Given("^the application in an integration environment$")
    public void prepareIntegrationEnvironment() throws Throwable {
    }

    @Given("^the user is authenticated with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void authenticateUser(String username, String password) throws Throwable
    {
        // TODO
    }

    @When("^the request body is \"([^\"]*)\"$")
    public void setRequestBody(String reqBody) throws Throwable
    {
        this.reqBody = reqBody;
    }

    @When("^a \"([^\"]*)\" request is made to endpoint \"([^\"]*)\"$")
    public void makeRequest(String reqType, String endpoint) throws Throwable
    {
        //if (template == null) template = new TestRestTemplate();

        //String targetUrl = getCompleteLocalUrl(endpoint);

        if (reqType.equals("GET"))
        {
            res = template.exchange(endpoint, HttpMethod.GET, new HttpEntity<>(reqHeaders), String.class);
        }
        else if (reqType.equals("POST"))
        {
            reqHeaders.add("Content-Type", "application/x-www-form-urlencoded");
            res = template.exchange(endpoint, HttpMethod.POST, new HttpEntity<>(reqBody, reqHeaders), String.class);
        }
    }

    @Then("^the response should have status code (\\d+)$")
    public void assertResponseStatusCode(int statusCode) throws Throwable
    {
        HttpStatus currentStatusCode = res.getStatusCode();

        assertThat("Response status code is not as expected : " +
                res.getBody(), currentStatusCode.value(), is(statusCode));
    }

}
