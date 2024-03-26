package org.example.integration;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.example.api.dto.rest.RestRegistrationDTO;
import org.example.integration.configuration.RestAssuredIntegrationTestBase;
import org.example.integration.support.AuthenticationRestControllerTestSupport;
import org.example.util.RestFixtures;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItems;
//@ActiveProfiles("test")
@RunWith(SpringRunner.class)
//@TestPropertySource(locations = "classpath:application-test.yaml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationRestControllerRestAssuredIT
        extends RestAssuredIntegrationTestBase
        implements AuthenticationRestControllerTestSupport {



    @Test
    public void testRegister() {

        //given
        RestRegistrationDTO restRegistrationDTO = RestFixtures.someRegistrationDTO();

        //when
        ExtractableResponse<Response> response = register(restRegistrationDTO);
        //then
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

    }

    @Test
    public void testLogin() {

    }

    @Test
    public void updateUserAsOwnerTest() {

    }

    @Test
    public void deleteAccountTest() {

    }
}