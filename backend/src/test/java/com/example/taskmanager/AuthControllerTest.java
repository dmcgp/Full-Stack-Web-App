package com.example.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired MockMvc mvc;

        @Test
        void registerLoginAndAccessProtectedEndpoint() throws Exception {
        String email = "test@example.com";
        String password = "secret";
        String registerBody = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);
        mvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content(registerBody))
            .andExpect(status().isOk());

        String loginBody = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);
        MvcResult result = mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(loginBody))
            .andExpect(status().isOk())
            .andReturn();

        String json = result.getResponse().getContentAsString();
        // extract accessToken (naive)
        String token = json.replaceAll(".*\\\"accessToken\\\":\\\"([^\\\"]+)\\\".*", "$1");

        // access protected endpoint without token -> 401
        mvc.perform(get("/api/tasks")).andExpect(status().isUnauthorized());

        // access with token -> 200
        mvc.perform(get("/api/tasks").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
        }
}
