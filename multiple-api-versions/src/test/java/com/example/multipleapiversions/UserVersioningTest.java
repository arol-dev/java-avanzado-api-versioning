package com.example.multipleapiversions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserVersioningTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetV1_URL() throws Exception {
        // TODO: Ejercicio 1 - Implementar test para API V1 usando URL versioning
        // Goal: GET /api/v1/users/1 -> Expect 200 OK and email "a@b.com"
        // Hint: mockMvc.perform(get("..."))

        throw new UnsupportedOperationException("Exercise 1 not implemented");
    }

    @Test
    void testGetV3_Header() throws Exception {
        // TODO: Ejercicio 1 - Implementar test para API V3 usando Header versioning
        // Goal: GET /api/users/1 with Header "X-API-VERSION: 3" -> Expect 200 OK and
        // email "a@b.com"

        throw new UnsupportedOperationException("Exercise 1 not implemented");
    }
}
