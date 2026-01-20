# Ejercicio 3: Contract Testing - Consumidor

## Objetivo

Configurar un consumidor que verifica su integración contra los stubs generados por el productor, sin necesitar el servicio real levantado.

## Archivo a Completar

`contract-consumer/src/test/java/com/example/contractconsumer/UserContractTest.java`

## Guía Paso a Paso

### Paso 1: Configurar Stub Runner

Anota la clase de test con `@AutoConfigureStubRunner`. Esta anotación descarga y levanta los stubs (mocks) generados por el productor.

- `ids`: Coordenadas del stub (`groupId:artifactId:version:classifier:port`). Usamos `verify` como versión mock y puerto `8080`.
- `stubsMode`: `LOCAL` indica que busque los stubs en tu repositorio Maven local (`~/.m2`).

```java
@AutoConfigureStubRunner(ids = "com.example:multiple-api-versions:+:stubs:8080", stubsMode = StubRunnerProperties.StubsMode.LOCAL)
```

### Paso 2: Consumir el Stub

Realiza una petición HTTP a la URL donde se levantó el stub (`http://localhost:8080`) y verifica la respuesta. Aunque no hay un servidor real, Stub Runner intercepta la llamada y devuelve la respuesta definida en el contrato.

```java
RestTemplate restTemplate = new RestTemplate();
ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/api/v2/users/1", String.class);
assertThat(response.getStatusCode().value()).isEqualTo(200);
// Puedes agregar más aserciones sobre el cuerpo de la respuesta si lo deseas
```

## Verificación

Ejecuta el test del consumidor. **Nota**: Asegúrate de haber instalado los stubs del productor primero (`mvn clean install` en `multiple-api-versions`).

```bash
mvn -pl contract-consumer -Dtest=UserContractTest test
```

## Conceptos Clave

- **Stub Runner**: Componente que "resucita" los stubs generados (archivos WireMock) y actúa como un servidor simulado.
- **Consumer-Driven Contracts**: El consumidor define qué espera (implícitamente al usar el stub), y el productor garantiza que cumple esa expectativa.

## Imports Necesarios

```java
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
```
