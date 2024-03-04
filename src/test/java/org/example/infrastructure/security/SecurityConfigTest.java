package org.example.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.business.TokenService;
import org.example.infrastructure.database.entity.CustomerEntity;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.example.infrastructure.database.entity.RoleEntity;
import org.example.infrastructure.database.entity.UserEntity;
import org.example.infrastructure.database.repository.jpa.UserJpaRepository;
import org.example.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.example.business.TokenService.SECRET_KEY;
import static org.example.business.TokenService.getSignInKey;
import static org.example.util.EntityFixtures.someCustomer1;
import static org.example.util.EntityFixtures.someOwner1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserJpaRepository userJpaRepository;

    @Test
    public void testAccessDeniedForUnauthenticatedUser() throws Exception {
        mockMvc.perform(get("/customer"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAccessGrantedForAuthenticatedUserWithProperRole() throws Exception {
        UserEntity user = EntityFixtures.someUser1();
        Mockito.when(userJpaRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList()));
     SecretKey key = getSignInKey();
        String token = Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(key)
                .compact();

        mockMvc.perform(get("/customer").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "owner1", roles = {"OWNER"})
    public void givenOwnerRole_whenGetOwnerPage_thenOk() throws Exception {
        mockMvc.perform(get("/owner"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner_portal"));
    }

    @Test
    @WithMockUser(username = "owner1", roles = {"OWNER"})
    public void givenOwnerRole_whenGetCustomerPage_thenForbidden() throws Exception {
        mockMvc.perform(get("/customer"))
                .andExpect(status().isForbidden());
    }

}