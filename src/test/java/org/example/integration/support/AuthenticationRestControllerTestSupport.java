package org.example.integration.support;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jakarta.servlet.http.HttpServletRequest;
import org.example.api.controller.rest.AuthenticationRestController;
import org.example.api.dto.LoginDTO;
import org.example.api.dto.rest.RestOwnerDTO;
import org.example.api.dto.rest.RestRegistrationDTO;
import org.springframework.http.HttpStatus;

import java.security.Principal;

public interface AuthenticationRestControllerTestSupport {

    RequestSpecification requestSpecification();

    default ExtractableResponse<Response> register(final RestRegistrationDTO body) {
        return requestSpecification()
                .body(body)
                .post(AuthenticationRestController.BASE_PATH + AuthenticationRestController.REGISTER)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract();
    }

    default ExtractableResponse<Response> loginUser(final LoginDTO body) {
        return requestSpecification()
                .body(body)
                .post(AuthenticationRestController.BASE_PATH + AuthenticationRestController.LOGIN)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract();
    }

    default ExtractableResponse<Response> updateUserAsOwner(Principal principal, final RestOwnerDTO body){
        return requestSpecification()
                .auth()
                .form(principal.getName(), "password")
                .body(body)
                .patch(AuthenticationRestController.BASE_PATH + AuthenticationRestController.BECOME_OWNER)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract();
    }
//    default ExtractableResponse<Response> deleteAccount(final LoginDTO loginDTO, HttpServletRequest request, Principal principal) {
//        String endpoint =AuthenticationRestController.BASE_PATH + AuthenticationRestController.DELETE;
//        requestSpecification()
//                .delete(endpoint, )
//                .then()
//                .statusCode(HttpStatus.OK.value());
//    }

}
