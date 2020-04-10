package cucumber.security;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/cucumber", "json:build/cucumber.json" },
                 features={ "src/test/resources/features/security" },
                 tags={ "@SecurityTest", "@IntegrationTest", "~@IgnoreTest" })
public class RunSecurityIntegrationCukeTests {
}
