package com.example.microservices.unittests;

import com.example.microservices.shared.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ServiceOneControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    public void testPublicEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/public")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Public Endpoint"));
    }

    @Test
    public void testAuthenticatedEndpoint() throws Exception {
        String token = "valid-token";

        when(jwtUtil.extractUsername(token)).thenReturn("user");

        mockMvc.perform(MockMvcRequestBuilders.get("/authenticated")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Authenticated Endpoint"));
    }
}
