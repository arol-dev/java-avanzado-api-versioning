# Ejercicio 4: Persistencia con Testcontainers

## Objetivo

Aprender a realizar tests de integración reales contra una base de datos PostgreSQL efímera usando Testcontainers, en lugar de usar H2 (memoria).

## Archivo a Completar

`multiple-api-versions/src/test/java/com/example/multipleapiversions/UserRepositoryTest.java`

## Guía Paso a Paso

### Paso 1: Definir el Contenedor

Declara un contenedor de PostgreSQL estático. Testcontainers se encargará de descargarlo, iniciarlo y detenerlo. Requerimos la imagen `postgres:15-alpine`.

```java
@Container
static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");
```

### Paso 2: Configuración Dinámica

Spring Boot necesita saber a qué puerto aleatorio se mapeó el puerto 5432 de Postgres. Usamos `@DynamicPropertySource` para inyectar estas propiedades en tiempo de ejecución.

```java
@DynamicPropertySource
static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
}
```

### Paso 3: Test de Persistencia

Escribe un test que guarde una entidad y luego verifique que se generó un ID. Esto confirma que la interacción con la base de datos es real.

```java
UserEntity user = new UserEntity("Test User", "test@example.com", "ACTIVE");
UserEntity saved = userRepository.save(user);

assertThat(saved.getId()).isNotNull();
assertThat(saved.getEmail()).isEqualTo("test@example.com");
```

## Verificación

Ejecuta el test de repositorio. Necesitas tener Docker corriendo en tu máquina.

```bash
mvn -pl multiple-api-versions -Dtest=UserRepositoryTest test
```

## Conceptos Clave

- **Testcontainers**: Librería Java para pruebas de integración con contenedores Docker desechables.
- **@DynamicPropertySource**: Permite sobreescribir propiedades de `application.properties` con valores generados dinámicamente (como puertos de contenedores).
- **Ambiente Efímero**: Cada ejecución de test tiene una base de datos limpia y aislada.

## Imports Necesarios

```java
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import com.example.multipleapiversions.UserEntity;
import static org.assertj.core.api.Assertions.assertThat;
```
