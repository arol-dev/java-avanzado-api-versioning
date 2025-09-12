# Contract Consumer

Este módulo es un consumidor de ejemplo que muestra cómo verificar integraciones
usando Spring Cloud Contract (SCC) y sus stubs.

## ¿Qué es Spring Cloud Contract?

Spring Cloud Contract es un enfoque y un conjunto de herramientas para asegurar
la compatibilidad entre productores y consumidores de APIs mediante contratos.

- **Contrato**: una especificación (por ejemplo, Groovy DSL, YAML/JSON) que
  define
  una interacción (request/response) entre consumidor y productor.
- **Producer**: valida que su implementación cumple el contrato generando tests
  automáticos a partir del contrato y publicando un artefacto de stubs
  (WireMock) para los consumidores.
- **Consumer**: ejecuta sus tests contra los stubs del producer (sin levantar el
  servicio real), garantizando que la integración funcionará cuando se
  despliegue
  el producer.

### Beneficios:

- Feedback temprano entre equipos (contract-first o contract-sync).
- Tests repetibles y rápidos sin dependencias externas.
- Menos acoplamiento y menor riesgo de roturas en producción.

## Cómo se usa aquí

En el proyecto hermano **multiple-api-versions** (Producer) se define al menos
un
contrato en `src/test/resources/contracts`. Con el plugin de SCC, el producer:

1) Genera tests a partir del contrato y los ejecuta.
2) Genera un artefacto "stubs" (WireMock) con classifier `stubs`.

Este consumer puede ejecutar tests de integración contra esos stubs usando
Spring Cloud Contract Stub Runner (modo LOCAL o REMOTE).

## Requisitos

- Java 17+
- Maven 3.8+

## Pasos recomendados

1) Generar e instalar los stubs del Producer en tu repositorio local de Maven:
   ```bash
   mvn -f ../multiple-api-versions/pom.xml clean install
   ```
   Esto dejará disponible en `~/.m2/repository` el artefacto de stubs del
   producer. Con la configuración actual del producer, las coordenadas son:

    - GroupId: `com.example`
    - ArtifactId: `multiple-api-versions`
    - Version: `0.0.1-SNAPSHOT`
    - Classifier: `stubs`

2) Ajustar las coordenadas del stub en el test del consumer (si hace falta):

   En `src/test/java/.../UserControllerIntegrationTest.java`, la anotación
   `@AutoConfigureStubRunner` trae un ejemplo con ids
   `com.example:contract-producer`.
   Para usar los stubs del producer de este repo, actualiza el parámetro `ids` a
   algo como:

    ```java
       @AutoConfigureStubRunner(
           stubsMode = StubRunnerProperties.StubsMode.LOCAL,
           ids = "com.example:multiple-api-versions:+:stubs:6565"
       )
    ```

    - `+` usará la última versión disponible en local.
    - `6565` es el puerto donde se levantará WireMock con los stubs.

3) Ejecutar los tests del consumer:

   Por defecto, el POM del consumer tiene `skipTests=true` en Surefire. Puedes:
    - Ejecutar forzando tests: `mvn -f pom.xml test -DskipTests=false`
    - O bien, editar el POM para quitar el `skipTests` si quieres habilitarlos
      permanentemente.

4) Asserciones en los tests del consumidor:

   A partir de este cambio, podemos realizar aserciones sobre la petición en los
   tests de JUnit. Por ejemplo,
   en `UserControllerIntegrationTest` se valida el código de estado y que el
   cuerpo
   contenga los campos definidos en el contrato (id, email, name, status):

   ```java
   assertThat(response.statusCode()).isEqualTo(200);
   assertThat(body)
       .contains("\"id\":1")
       .contains("\"email\":\"a@b.com\"")
       .contains("\"name\":\"Ada\"")
       .contains("\"status\":\"ACTIVE\"");
   ```

5) Ejecutar la aplicación del consumer (si aplica):
   ```bash
   mvn spring-boot:run -f pom.xml
   ```

   **Nota**: Este módulo está pensado como plantilla para demostrar el uso de
   SCC
   en tests. Adapta controladores/servicios para consumir la API real y valida
   con stubs durante el desarrollo.

## Troubleshooting

- No encuentra stubs en LOCAL: asegúrate de haber ejecutado `mvn clean install`
  en el producer y de que las coordenadas (`groupId`, `artifactId`, `version`,
  `classifier`) coinciden.
- Puerto ocupado (6565): cambia el puerto en `ids` a uno libre, por ejemplo
  `...:stubs:6566`.
- Versiones incompatibles: el repositorio usa Spring Boot 3.5.x y Spring Cloud
  2025.0.x; mantén versiones alineadas entre módulos.
