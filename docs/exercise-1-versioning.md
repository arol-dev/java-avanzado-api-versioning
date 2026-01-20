# Ejercicio 1: Versionado de API

## Objetivo

Aprender a implementar clientes de prueba para diferentes estrategias de versionado de API (URL y Header) usando `MockMvc`.

## Archivo a Completar

`multiple-api-versions/src/test/java/com/example/multipleapiversions/UserVersioningTest.java`

## Guía Paso a Paso

### Paso 1: Versionado por URL (API V1)

En el método `testGetV1_URL`, implementa una petición GET a `/api/v1/users/1`.

```java
mockMvc.perform(get("/api/v1/users/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value("a@b.com"));
```

### Paso 2: Versionado por Header (API V3)

En el método `testGetV3_Header`, implementa una petición GET a `/api/users/1` enviando el header `X-API-VERSION`.

```java
mockMvc.perform(get("/api/users/1")
        .header("X-API-VERSION", "3"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value("a@b.com"));
```

## Verificación

Ejecuta el siguiente comando en la terminal:

```bash
mvn -pl multiple-api-versions -Dtest=UserVersioningTest test
```

## Conceptos Clave

- **URL Versioning**: La versión es parte de la ruta del recurso.
- **Header Versioning**: La versión se envía en metadatos (headers), manteniendo la URL limpia.
- **MockMvc**: Herramienta de Spring para testear controladores sin levantar un servidor HTTP completo.

## Imports Necesarios

```java
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
```
