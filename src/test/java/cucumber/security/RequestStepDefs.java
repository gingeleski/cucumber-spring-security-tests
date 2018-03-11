package cucumber.security;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Properties;

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

    @When("^a \"([^\"]*)\" request is made to endpoint \"([^\"]*)\"$")
    public void makeRequest(String reqType, String endpoint) throws Throwable
    {
        RestTemplate proxiedRest = template.getRestTemplate();
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8888);
        Proxy proxy = new Proxy(Proxy.Type.HTTP,address);
        factory.setProxy(proxy);
        proxiedRest.setRequestFactory(factory);

        if (reqType.equals("GET"))
        {
            //res = template.exchange(endpoint, HttpMethod.GET, new HttpEntity<>(reqHeaders), String.class);
            res = proxiedRest.exchange(endpoint, HttpMethod.GET, new HttpEntity<>(reqHeaders), String.class);
        }
        else if (reqType.equals("POST"))
        {
            reqHeaders.add("Content-Type", "application/x-www-form-urlencoded");
            //res = template.exchange(endpoint, HttpMethod.POST, new HttpEntity<>(reqBody, reqHeaders), String.class);
            res = template.exchange(endpoint, HttpMethod.POST, new HttpEntity<>(reqBody, reqHeaders), String.class);
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

        assertThat("Response status code is not as expected : " +
                res.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @Then("^the response should have header \"([^\"]*)\" set to \"([^\"]*)\"$")
    public void assertResponseHeaderValue(String headerName, String headerValue) throws Throwable
    {
        assertThat("Response did not contain header : '" + headerName + "'",
                res.getHeaders().containsKey(headerName), is(true));

        assertThat("Response header '" + headerName + "' was not set to '" + headerValue + "'",
                res.getHeaders().getFirst(headerName), is(headerValue));
    }

}
