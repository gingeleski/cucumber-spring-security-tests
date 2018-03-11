package cucumber.security;

import org.junit.runner.RunWith;

import cucumber.api.junit.*;
import cucumber.api.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(format = {"pretty", "html:build/cucumber", "json:build/cucumber.json"},
        features={"src/test/resources/features/security"}, tags={"@SecurityTest", "@IntegrationTest", "~@IgnoreTest"})
public class RunSecurityIntegrationCukeTests {
}