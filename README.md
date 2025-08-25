# Versionado de API y Contratos (Producer/Consumer)

Este repositorio contiene dos proyectos independientes que se complementan:

- **multiple-api-versions (Producer)**: Servicio Spring Boot que expone
  múltiples versiones de una API REST (v1, v2 y variantes por cabecera v3/v4).
  Incluye documentación OpenAPI/Swagger y un ejemplo de contratos con Spring
  Cloud Contract (SCC) en el lado productor.
- **contract-consumer (Consumer)**: Aplicación (plantilla) de consumidor que
  muestra cómo validar su integración usando stubs generados por Spring Cloud
  Contract.

Ambos proyectos son independientes y pueden ejecutarse por separado. El flujo
recomendado es:

1) Desarrollar y versionar la API en multiple-api-versions (producer).
2) Generar y publicar/instalar los stubs de contrato desde el producer (SCC).
3) Configurar el consumer para usar esos stubs en sus tests de integración (Stub
   Runner/WireMock).

## Requisitos

- Java 17+
- Maven 3.8+

## Árbol del repositorio

- /multiple-api-versions: servicio productor (con OpenAPI y contratos SCC)
- /contract-consumer: proyecto consumidor (tests con stubs de SCC)

## Cómo ejecutar (Producer)

1. Arrancar la app:
   ```bash
   mvn spring-boot:run -f multiple-api-versions/pom.xml
   ```

   La aplicación se inicia en http://localhost:8080

2. OpenAPI/Swagger:
    - Swagger UI: http://localhost:8080/swagger-ui.html
    - OpenAPI JSON: http://localhost:8080/v3/api-docs
    - OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml

3. Generar tests y stubs de contratos (SCC):
   ```bash
   mvn -f multiple-api-versions/pom.xml clean verify
   ```

    - Los tests de contrato generados se ejecutarán.
    - Se generará el artefacto stubs (classifier "stubs").

4. Instalar en el repositorio local de Maven (para que el consumer lo use):
   ```bash
   mvn -f multiple-api-versions/pom.xml clean install
   ```

   Esto instalará tanto el jar del producer como el jar de stubs en tu repo
   local `(~/.m2/repository)`.

## Cómo ejecutar (Consumer)

El consumer es una plantilla que muestra cómo configurar tests con Spring Cloud
Contract Stub Runner/WireMock.
Consulta [el README del consumidor](contract-consumer/README.md) para conocer:

- Qué es Spring Cloud Contract.
- Cómo apuntar a los stubs del producer.
- Cómo ejecutar los tests del consumer y la aplicación.

## Notas

- El producer define contratos de ejemplo en
  `multiple-api-versions/src/test/resources/contracts`.
- El plugin **spring-cloud-contract-maven-plugin** genera tests en el producer y
  stubs reutilizables por los consumidores.
- Para detalles ampliados de endpoints y ejemplos cURL,
  revisa [el README del productor](multiple-api-versions/README.md).
