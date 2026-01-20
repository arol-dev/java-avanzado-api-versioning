package com.example.multipleapiversions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
class UserRepositoryTest {

    // TODO: Ejercicio 4 - Definir el contenedor de PostgreSQL
    // @Container
    // static PostgreSQLContainer<?> postgres = new
    // PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // TODO: Ejercicio 4 - Configurar las propiedades de conexión dinámicamente
        // registry.add("spring.datasource.url", postgres::getJdbcUrl);
        // registry.add("spring.datasource.username", postgres::getUsername);
        // registry.add("spring.datasource.password", postgres::getPassword);
        throw new UnsupportedOperationException("Exercise 4 configuration not implemented");
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFind() {
        // TODO: Ejercicio 4 - Implementar test de persistencia
        // UserEntity user = new UserEntity(null, "Test User", "test@example.com",
        // "ACTIVE");
        // UserEntity saved = userRepository.save(user);
        // assertThat(saved.getId()).isNotNull();

        throw new UnsupportedOperationException("Exercise 4 test not implemented");
    }
}
