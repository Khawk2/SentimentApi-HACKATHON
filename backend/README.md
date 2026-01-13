# Sentiment Analysis API

API REST desarrollada con Spring Boot para el análisis de sentimientos de texto. Esta aplicación se integra con un servicio de Machine Learning para realizar predicciones y almacena los resultados en una base de datos PostgreSQL.

## Características

- Análisis de sentimientos de texto mediante integración con servicio ML
- Traducción automática con Google Gemini 2.5 Flash Lite
- Soporte multilingüe automático
- Sistema de caché para traducciones
- Almacenamiento persistente de análisis en PostgreSQL
- Estadísticas de análisis realizados
- Documentación API con Swagger/OpenAPI
- Soporte para Docker
- Manejo de excepciones global
- Validación de entrada

## Requisitos Previos

- Java 21 o superior
- Maven 3.9 o superior
- PostgreSQL 12 o superior
- Docker (opcional, para ejecución con contenedores)

## Tecnologías Utilizadas

- **Spring Boot 4.0.1**
- **Java 21**
- **PostgreSQL** - Base de datos
- **Spring Data JPA** - Persistencia de datos
- **Google Gemini AI** - Traducción inteligente
- **Lombok** - Reducción de código boilerplate
- **SpringDoc OpenAPI** - Documentación de API
- **Spring Actuator** - Monitoreo y métricas
- **Spring Validation** - Validación de inputs
- **Docker** - Containerización

## Instalación

### 1. Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd backend
```

### 2. Configurar la base de datos

Crear una base de datos PostgreSQL:

```sql
CREATE DATABASE sentimentApi;
```

### 3. Configurar las propiedades de la aplicación

Editar `src/main/resources/application.properties` con tus credenciales de PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sentimentApi
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

## Ejecución

### Ejecución local

```bash
# Compilar el proyecto
mvn clean package

# Ejecutar la aplicación
mvn spring-boot:run
```

O usando el JAR compilado:

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### Ejecución con Docker

```bash
# Construir la imagen
docker build -t sentiment-api .

# Ejecutar el contenedor
docker run -p 8080:8080 sentiment-api
```

**Nota:** Asegúrate de que PostgreSQL esté disponible y accesible desde el contenedor.

## Endpoints de la API

### 1. Analizar Sentimiento

**POST** `/api/sentiment`

Analiza el sentimiento de un texto en cualquier idioma, lo traduce automáticamente al español y lo almacena en la base de datos.

**Request Body:**
```json
{
  "text": "Este es un texto de ejemplo para analizar"
}
```

**Response:**
```json
{
  "prevision": "Positivo",
  "probabilidad": 0.95
}
```

**Valores posibles de `prevision`:**
- `Positivo`
- `Negativo`
- `Neutro`

### 2. Obtener Estadísticas

**GET** `/api/stats`

Obtiene estadísticas sobre los análisis realizados.

**Response:**
```json
{
  "totalAnalisis": 150,
  "positivos": 80,
  "negativos": 40,
  "neutros": 30,
  "porcentajePositivos": 53.33,
  "porcentajeNegativos": 26.67,
  "porcentajeNeutros": 20.0
}
```

### 3. Obtener Estado del servicio Sentiment API

**GET** `/api/health`

Verifica el estado de salud del servicio.

**Response:**
```json
{
  "status": "UP",
  "service": "Sentiment Api"
}
```




## Documentación de la API

Una vez que la aplicación esté ejecutándose, puedes acceder a la documentación interactiva de Swagger en:

- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

## Estructura de la Base de Datos

La aplicación crea automáticamente la tabla `sentiment_analysis` con la siguiente estructura:

- `id` (Long) - Identificador único
- `text` (String) - Texto original analizado
- `prevision` (String) - Sentimiento predicho (Positivo/Negativo/Neutro)
- `probabilidad` (Double) - Probabilidad de la predicción
- `created_at` (Timestamp) - Fecha y hora de creación

## Configuración

### Variables de Entorno

Puedes configurar la aplicación mediante variables de entorno:

- `SPRING_DATASOURCE_URL` - URL de conexión a PostgreSQL
- `SPRING_DATASOURCE_USERNAME` - Usuario de PostgreSQL
- `SPRING_DATASOURCE_PASSWORD` - Contraseña de PostgreSQL
- `ML_SERVICE_URL` - URL del servicio de Machine Learning (por defecto: http://localhost:8000)
- `GEMINI_KEY` - API key de Google Gemini para traducción
- `SERVER_PORT` - Puerto del servidor (por defecto: 8080)

### Logging

El nivel de logging se configura en `application.properties`:

```properties
logging.level.com.example.demo=INFO
```

## Testing

Ejecutar los tests:

```bash
mvn test
```

## Notas Importantes

- El servicio requiere que el servicio de Machine Learning esté disponible en la URL configurada
- El servicio ML debe exponer un endpoint `/predict` que acepte JSON con el campo `text`
- La respuesta del servicio ML debe incluir los campos `prevision` (Bueno/Malo/Regular) y `probabilidad`
- El sistema mapea automáticamente las etiquetas del ML service a Positivo/Negativo/Neutro
- **Traducción Automática**: Los textos en cualquier idioma se traducen automáticamente al español usando Google Gemini 2.5 Flash Lite
- **Caching**: Las traducciones se almacenan en caché para optimizar el rendimiento
- **Fallback**: En caso de fallo de traducción, se usa el texto original

## Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Proyecto desarrollado para el **Hackathon One** - Clasificación de Sentimientos

## Equipo

- **Data Science:** Modelo y análisis de datos
- **Backend:** API REST e integración de servicios
- **Frontend:** Interfaz de usuario y experiencia
