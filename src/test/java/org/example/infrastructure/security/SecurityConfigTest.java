package org.example.infrastructure.security;

import org.example.infrastructure.database.entity.CustomerEntity;
import org.example.infrastructure.database.entity.OwnerEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.example.util.EntityFixtures.someCustomer1;
import static org.example.util.EntityFixtures.someOwner1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

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

    @Test
    public void givenNoRole_whenGetOwnerPage_thenRedirectToLogin() throws Exception {
        mockMvc.perform(get("/owner"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }
}