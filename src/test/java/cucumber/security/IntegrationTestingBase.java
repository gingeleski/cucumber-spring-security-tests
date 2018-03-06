package cucumber.security;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import roombook.Application;

import java.util.Collections;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("INTEGRATION_TEST")
@ContextConfiguration
public class IntegrationTestingBase {

    @Autowired
    protected TestRestTemplate template;

    @Before
    public void before() {
	/*
        // Cookie cutter how to add custom header globally
        template.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().add("iv-user", "user");
            return execution.execute(request, body);
        }));
	*/
    }

}
