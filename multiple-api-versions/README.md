# Versionado con múltiples versiones de API

Este proyecto demuestra cómo exponer múltiples versiones de una API REST
utilizando rutas versionadas (v1 y v2) y cómo documentarlas con OpenAPI/Swagger.

## Requisitos

- Java 17+
- Maven 3.8+

## Cómo ejecutar

```bash
mvn spring-boot:run -f multiple-api-versions/pom.xml
```

La aplicación se inicia por defecto en `http://localhost:8080`.

## Documentación OpenAPI / Swagger

- [Swagger UI](http://localhost:8080/swagger-ui.html)
- [OpenAPI JSON](http://localhost:8080/v3/api-docs)
- [OpenAPI YAML](http://localhost:8080/v3/api-docs.yaml)

La especificación incluye metadatos (título, versión) y tags:

- `users-v1`: Operaciones de la API v1 (campos básicos)
- `users-v2`: Operaciones de la API v2 (campos extendidos)

## Endpoints

### V1 (campos básicos)

Base path: `/api/v1/users`

- GET `/{id}`
    - Respuesta 200: `{ "id": number, "email": string }`
    - Respuesta 404: Not found
- POST `/`
    - Body: `{ "email": string }`
    - Respuesta 201: `{ "id": number, "email": string }`

### V2 (campos extendidos)

Base path: `/api/v2/users`

- GET `/{id}`
    - Respuesta 200:
      `{ "id": number, "email": string, "name": string, "status": "ACTIVE|PENDING|BLOCKED" }`
    - Respuesta 404: Not found
- POST `/`
    - Body: `{ "email": string, "name": string }`
    - Respuesta 201:
      `{ "id": number, "email": string, "name": string, "status": string }`

## Ejemplos cURL

- Obtener usuario v1:

```bash
curl -s http://localhost:8080/api/v1/users/1
```

- Crear usuario v1:

```bash
curl -s -X POST http://localhost:8080/api/v1/users \
  -H 'Content-Type: application/json' \
  -d '{"email":"a@b.com"}'
```

- Obtener usuario v2:

```bash
curl -s http://localhost:8080/api/v2/users/1
```

- Crear usuario v2:

```bash
curl -s -X POST http://localhost:8080/api/v2/users \
  -H 'Content-Type: application/json' \
  -d '{"email":"a@b.com","name":"Ada"}'
```

## Notas sobre versionado

- El versionado se realiza por ruta (`/api/v1/...`, `/api/v2/...`).
- Además, se incluyen versiones por cabecera para un mismo path (`/api/users`)
  usando el header `X-API-Version`:
    - `X-API-Version: 3` => se comporta como la v1 (campos básicos).
    - `X-API-Version: 4` => se comporta como la v2 (campos extendidos).
- Cada versión tiene su propio conjunto de modelos (DTOs) y puede evolucionar
  independientemente.
- Las anotaciones de OpenAPI en los controladores describen cada operación y sus
  respuestas.

### V3 y V4 (versionado por cabecera)

Base path compartido: `/api/users`

- V3 (equivalente a v1):
    - Header requerido: `X-API-Version: 3`
    - GET `/{id}` -> `{ "id": number, "email": string }`
    - POST `/` con body `{ "email": string }` -> 201
      `{ "id": number, "email": string }`
- V4 (equivalente a v2):
    - Header requerido: `X-API-Version: 4`
    - GET `/{id}` ->
      `{ "id": number, "email": string, "name": string, "status": "ACTIVE|PENDING|BLOCKED" }`
    - POST `/` con body `{ "email": string, "name": string }` -> 201
      `{ "id": number, "email": string, "name": string, "status": string }`

## Ejemplos cURL para v3/v4 (header)

- Obtener usuario v3 (header):

```bash
curl -s http://localhost:8080/api/users/1 \
  -H 'X-API-Version: 3'
```

- Crear usuario v3 (header):

```bash
curl -s -X POST http://localhost:8080/api/users \
  -H 'Content-Type: application/json' \
  -H 'X-API-Version: 3' \
  -d '{"email":"a@b.com"}'
```

- Obtener usuario v4 (header):

```bash
curl -s http://localhost:8080/api/users/1 \
  -H 'X-API-Version: 4'
```

- Crear usuario v4 (header):

```bash
curl -s -X POST http://localhost:8080/api/users \
  -H 'Content-Type: application/json' \
  -H 'X-API-Version: 4' \
  -d '{"email":"a@b.com","name":"Ada"}'
```

## Desarrollo

- Dependencia utilizada:
  `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0` (Spring Boot 3).
- Metadatos de OpenAPI configurados con `@OpenAPIDefinition` en la clase
  `MultipleApiVersionsApplication`.