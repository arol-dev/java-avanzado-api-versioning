# Ejercicio 2: Contract Testing - Productor

## Objetivo

Configurar el lado del Productor para verificar que cumple con los contratos definidos usando Spring Cloud Contract.

## Archivo a Completar

`multiple-api-versions/src/test/java/com/example/multipleapiversions/BaseTest.java`

## Guía Paso a Paso

### Paso 1: Inyectar el Controlador V2

Spring Cloud Contract genera tests que heredan de `BaseTest`. Necesitamos asegurarnos de que el contexto de prueba tenga el controlador que queremos verificar.

```java
@Autowired
private UserControllerV2 userControllerV2;
```

### Paso 2: Configurar MockMvc

En el método `setup`, configura `RestAssuredMockMvc` para que use tus controladores. Esto permite que los tests generados interactúen con tu API simulada.

```java
StandaloneMockMvcBuilder standaloneMockMvcBuilder
        = MockMvcBuilders.standaloneSetup(userControllerV1, userControllerV2);
RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
```

## Verificación

Este ejercicio verifica que los contratos (`src/test/resources/contracts`) se cumplan. Ejecuta:

```bash
mvn -pl multiple-api-versions clean install
```

O específicamente la fase de verificación de contratos:

```bash
mvn -pl multiple-api-versions spring-cloud-contract:generateTests test
```

## Conceptos Clave

- **Base Class**: Clase padre para los tests generados por Spring Cloud Contract. Sirve para configurar el contexto (mocks, seguridad, datos).
- **RestAssuredMockMvc**: Librería usada por los tests generados para hacer peticiones al controlador mockeado.

## Imports Necesarios

```java
import com.example.multipleapiversions.UserControllerV2;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
```
