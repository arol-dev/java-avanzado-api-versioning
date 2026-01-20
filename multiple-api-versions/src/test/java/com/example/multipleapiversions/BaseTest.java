package com.example.multipleapiversions;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@AutoConfigureMessageVerifier
public class BaseTest {

    @Autowired
    private UserControllerV1 userControllerV1;

    // TODO: Ejercicio 2 - Inyectar UserControllerV2

    @BeforeEach
    void setup() {
        // TODO: Ejercicio 2 - Configurar MockMvc para soportar ambos controladores
        // StandaloneMockMvcBuilder standaloneMockMvcBuilder = ...
        // RestAssuredMockMvc.standaloneSetup(...);

        throw new UnsupportedOperationException("Exercise 2 not implemented");
    }
}
