package org.example.integration.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.ApplicationRunner;
import org.example.integration.support.AuthenticationTestSupport;
import org.example.integration.support.ControllerTestSupport;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "classpath:application-test.yaml")
@SpringBootTest(
        classes = {ApplicationRunner.class}
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class RestAssuredIntegrationTestBase
        implements ControllerTestSupport, AuthenticationTestSupport {


    @Autowired
    protected ObjectMapper objectMapper;
    @LocalServerPort
    private int serverPort;
    @Value("${server.servlet.context-path}")
    private String basePath;

    private String jSessionIdValue;

//
//    @BeforeEach
//    void beforeEach() {
//        jSessionIdValue = login("user1", "test")
//                .and()
//                .cookie("JSESSIONID")
//                .header(HttpHeaders.LOCATION, "http://localhost:%s%s/".formatted(serverPort, basePath))
//                .extract()
//                .cookie("JSESSIONID");
//    }
//    @AfterEach
//    void afterEach() {
//        logout().and().cookie("JSESSIONID", "");
//        jSessionIdValue = null;
//    }
//
//    @Test
//    void contextLoad() {
//        Assertions.assertTrue(true, "Context loaded");
//    }


    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public RequestSpecification requestSpecification() {
        return restAssuredBase()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .cookie("JSESSIONID", jSessionIdValue);
    }

    public RequestSpecification requestSpecificationNoAuthorization() {
        return restAssuredBase();
    }

    private RequestSpecification restAssuredBase() {
        return RestAssured
                .given()
                .config(getConfig())
                .basePath(basePath)
                .port(serverPort);
    }

    private RestAssuredConfig getConfig() {
        return RestAssuredConfig
                .config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((p1, p2) -> objectMapper));
    }
}
