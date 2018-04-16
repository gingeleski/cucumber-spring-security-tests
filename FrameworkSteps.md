
CUCUMBER SECURITY TEST FRAMEWORK STEP DEFS
-------------------------------------------

`Given the application in an integration environment`
- Launches an ephemeral server of the application
- Can mock out components of this on a scenario-by-scenario or test-by-test basis
- Base URL is set automatically to the ephemeral server

---

`Given the application up at "<baseUrl>"`
- Sets base URL for outgoing requests to the given string
- Meant for end-to-end testing

---

`Given the "<browserName>" as a headless client`
- Sets this scenario to be executed in a Selenium-wrapped headless browser
- Acceptable parameters are "CHROME" or "FIREFOX"

---

`When the user is authenticated with username "<username>" and password "<password>"`
- Starts a session with the given credentials
- Preserves the cookie or authorization header for this scenario (session)
- Will likely need to be customized by the test engineer to their application

---

`When a "<method>" request is made to endpoint "<endpoint>"`
- Sets request method and the target URL as the endpoint appended to base URL
- Base URL is derived from an earlier Given step
- Acceptable method parameters are "GET", "POST", "PUT", or "DELETE"
- Successive When steps can further build the request before it is executed

---

`When the request body is "<requestBody>"`
- Sets POST or PUT request body to the given string
- Does nothing if a GET or DELETE request is being prepared

---

`When the request is "<specialAttribute>"`
- Ensures some special attribute of the request
- The "HTTP/1.0" attribute will execute the request as that instead of default HTTP/1.1

---

`When request header "<headerName>" is set to "<headerValue>"`
- Adds a request header with the provided name and value

---

`When the request does not have header "<headerName>"`
- Ensures a header with this name will not be on the request
- Most applicable use case is keeping the "Host" header off the request

---

`Then the response should have status code <statusCode>`
- Asserts response is of the given status code

---

`Then the response body should contain "<searchString>"`
- Asserts the response body includes the provided string

---

`Then the response should have length <length>`
- Asserts the length of the response matches what's expected

---

`Then the response should have header "<headerName>"`
- Asserts the response has a header with the provided name

---

`Then the response should not have header "<headerName>"`
- Asserts the response does not have a header with the provided name

---

`Then the response header "<headerName>" should be set to "<headerValue>"`
- Asserts the response has a header with the provided name and value
