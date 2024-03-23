package org.example.api.controller.webMvcTests;

import lombok.AllArgsConstructor;
import org.example.api.controller.FileUploadController;
import org.example.business.TokenService;
import org.example.business.dao.FileUploadDAO;
import org.example.infrastructure.security.CustomUserDetailsService;
import org.example.util.OtherFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FileUploadController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(locations = "classpath:application-test.yaml")
class FileUploadControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private FileUploadDAO fileUploadDAO;

    @BeforeEach
    public void setup() {
        UserDetails userDetails = OtherFixtures.someUserDetails();

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
    }
    @Test
    public void whenUploadFile_thenRedirectToAddMenuPage() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test data".getBytes());

        when(fileUploadDAO.uploadFile(file)).thenReturn("http:/something/test.jpg");

        mockMvc.perform(multipart("/upload").file(file))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/owner/add/menu"));

    }

    @Test
    public void whenNoFileProvided_thenBadRequest() throws Exception {

        mockMvc.perform(multipart("/upload"))
                .andExpect(view().name("error"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Required part 'image' is not present.", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void whenFileUploadFails_thenInternalServerError() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test data".getBytes());

        when(fileUploadDAO.uploadFile(file)).thenThrow(new IOException());

        mockMvc.perform(multipart("/upload").file(file))
                .andExpect(view().name("error"))
                .andExpect(status().isOk())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals("500 INTERNAL_SERVER_ERROR \"Error uploading file\"", Objects.requireNonNull(result.getResolvedException()).getMessage()));

    }
}