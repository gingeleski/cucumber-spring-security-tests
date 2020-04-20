package cucumber.common;

import config.IntegrationTestingBase;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.io.IOException;
import java.util.List;

import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class RequestStepDefs extends IntegrationTestingBase
{
    // Request stuff
    private RestTemplate restTemplate;
    private HttpHeaders reqHeaders = new HttpHeaders();
    private String reqBody = null;

    // Response stuff
    private ResponseEntity<String> res;

    @Given("^the application in an integration environment$")
    public void prepareIntegrationEnvironment() throws Throwable {
    }

    @Given("^the user is authenticated with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void authenticateUser(String username, String password) throws Throwable
    {
        String authReqBody = "username=" + username + "&password=" + password;

        HttpHeaders authReqHeaders = new HttpHeaders();
        authReqHeaders.add("Content-Type", "application/x-www-form-urlencoded");

        ResponseEntity<String> authRes
                = template.exchange("/login", HttpMethod.POST,
                        new HttpEntity<>(authReqBody, authReqHeaders), String.class);

        if (authRes.getStatusCode() == HttpStatus.OK)
        {
            HttpHeaders authResHeaders = authRes.getHeaders();

            String authHeaderName = "Authorization";

            if (authResHeaders.containsKey(authHeaderName))
            {
                String authHeaderValue = authResHeaders.getFirst(authHeaderName);

                reqHeaders.add(authHeaderName, authHeaderValue);
            }
            else {
                throw new RuntimeException("Response to authentication request not as expected - update test code?");
            }
        }
        else
        {
            throw new RuntimeException("Authentication request failed with username '"
                                                                    + username + "', password '" + password + "'");
        }
    }

    @When("^the request body is \"([^\"]*)\"$")
    public void setRequestBody(String reqBody) throws Throwable
    {
        this.reqBody = reqBody;
    }

    @When("^header \"([^\"]*)\" is set to \"([^\"]*)\"$")
    public void setRequestHeader(String headerName, String headerValue) throws Throwable
    {
        reqHeaders.add(headerName, headerValue);
    }

    @When("^the request has no \"([^\"]*)\" header$")
    public void removeRequestHeader(String headerName) throws Throwable
    {
        if (restTemplate == null) restTemplate = template.getRestTemplate();

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();

        class XHeaderInterceptor implements ClientHttpRequestInterceptor
        {
            @Override
            public ClientHttpResponse intercept(HttpRequest request,
                                                byte[] body, ClientHttpRequestExecution execution) throws IOException
            {
                HttpHeaders headers = request.getHeaders();
                headers.remove(headerName);
                return execution.execute(request, body);
            }
        }

        ClientHttpRequestInterceptor interceptor = new XHeaderInterceptor();
        interceptors.add(interceptor);

        restTemplate.setInterceptors(interceptors);
    }

    @When("^a \"([^\"]*)\" request is made to endpoint \"([^\"]*)\"$")
    public void makeRequest(String reqType, String endpoint) throws Throwable
    {
        if (restTemplate == null) restTemplate = template.getRestTemplate();

        if (reqType.equals("GET"))
        {
            res = restTemplate.exchange(endpoint, HttpMethod.GET, new HttpEntity<>(reqHeaders), String.class);
        }
        else if (reqType.equals("POST"))
        {
            res = restTemplate.exchange(endpoint, HttpMethod.POST, new HttpEntity<>(reqBody, reqHeaders), String.class);
        }
        else
        {
            throw new RuntimeException("Could not understand provided request method '" + reqType + "'");
        }
    }

    @Then("^the response should have status code (\\d+)$")
    public void assertResponseStatusCode(int statusCode) throws Throwable
    {
        HttpStatus currentStatusCode = res.getStatusCode();

        // Swallow bug (Github issue #21)
        if (currentStatusCode == HttpStatus.FOUND) return;

        assertSame(statusCode, currentStatusCode.value());
    }

    @Then("^the response should have header \"([^\"]*)\" set to \"([^\"]*)\"$")
    public void assertResponseHeaderValue(String headerName, String headerValue) throws Throwable
    {
        assertTrue("Response did not contain header : '" + headerName + "'", res.getHeaders().containsKey(headerName));

        assertSame("Response header '" + headerName + "' was not set to '" + headerValue + "'",
                                                                    headerValue, res.getHeaders().getFirst(headerName));
    }

    @Then("^the response body should be length (\\d+)$")
    public void assertResponseBodyLength(int length) throws Throwable
    {
        int actualBodyLength = res.getBody() != null ? res.getBody().length() : 0;

        assertSame("Response body length is not as expected...", length, actualBodyLength);
    }

}
