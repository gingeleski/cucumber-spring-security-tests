package cucumber.security;

import org.junit.runner.RunWith;

import cucumber.api.junit.*;
import cucumber.api.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features={"src/test/resources/features/security"}, tags={"@SecurityTest", "@IntegrationTest"})
public class RunSecurityIntegrationCukeTests {
}