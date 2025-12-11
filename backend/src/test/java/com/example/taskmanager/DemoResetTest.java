package com.example.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoResetTest {
    @Autowired MockMvc mvc;

        @Test
        void resetRequiresAdmin() throws Exception {
                // Without admin token -> 401 (unauthenticated)
                mvc.perform(post("/api/demo/reset")).andExpect(status().isUnauthorized());
        }

    @Test
    void resetIdempotentWithAdmin() throws Exception {
        // Register admin and login
        mvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"admin@demo.test\",\"password\":\"Admin123!\",\"role\":\"ADMIN\"}"))
                .andExpect(status().isOk());
        var login = mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"admin@demo.test\",\"password\":\"Admin123!\"}"))
                .andExpect(status().isOk()).andReturn();
        String json = login.getResponse().getContentAsString();
        String token = json.replaceAll(".*\\\"accessToken\\\":\\\"([^\\\"]+)\\\".*", "$1");

        // Call reset twice -> both 200
        mvc.perform(post("/api/demo/reset").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
        mvc.perform(post("/api/demo/reset").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
