# Sentiment API - Análisis de Sentimientos

Sistema completo para análisis de sentimientos en español que integra Data Science y Backend. Desarrollado para Hackathon One.

## Objetivo del Proyecto

Crear una API simple que recibe textos (comentarios, reseñas o tweets), aplica un modelo de Data Science para clasificar el sentimiento (Positivo/Neutro/Negativo) y devuelve el resultado en formato JSON.

## Arquitectura del Sistema

```
├── backend/          # API REST Spring Boot (Java 21)
├── ml-service/       # Servicio ML FastAPI (Python)
├── frontend/         # Dashboard React + TypeScript
├── data/            # Dataset y archivos de modelo
└── docker-compose.yaml
```

## Ejecución Rápida

### Prerrequisitos
- Docker y Docker Compose
- Git

### Pasos de Ejecución

1. **Clonar el repositorio:**
```bash
git clone <repository-url>
cd SentimentApi-HACKATHON
```

2. **Configurar variables de entorno:**
```bash
cp .env.example .env
# Editar .env si es necesario
```

3. **Levantar todos los servicios:**
```bash
docker-compose up --build
```

4. **Acceder a los servicios:**
- Frontend: http://localhost:80
- Backend API: http://localhost:8080
- ML Service: http://localhost:8000
- Documentación Swagger: http://localhost:8080/swagger-ui/index.html

## Contrato de API

### Endpoint Principal
**POST** `/api/sentiment`

**Request:**
```json
{
  "text": "El servicio fue excelente y la atención muy buena"
}
```

**Response:**
```json
{
  "prevision": "Positivo",
  "probabilidad": 0.87
}
```

### Endpoint de Estadísticas
**GET** `/api/stats`

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

## Ejemplos de Uso

### cURL
```bash
# Ejemplo Positivo
curl -X POST http://localhost:8080/api/sentiment \
  -H "Content-Type: application/json" \
  -d '{"text": "Me encantó el producto, excelente calidad"}'

# Ejemplo Negativo  
curl -X POST http://localhost:8080/api/sentiment \
  -H "Content-Type: application/json" \
  -d '{"text": "El servicio fue terrible, no lo recomiendo"}'

# Ejemplo Neutro
curl -X POST http://localhost:8080/api/sentiment \
  -H "Content-Type: application/json" \
  -d '{"text": "El producto cumple su función, es aceptable"}'
```

### Postman
1. Crear request POST a `http://localhost:8080/api/sentiment`
2. Headers: `Content-Type: application/json`
3. Body (raw JSON): `{"text": "tu texto aquí"}`

## Modelo de Machine Learning

### Características
- **Dataset:** Amazon Reviews Spanish (200,000 registros)
- **Algoritmo:** Logistic Regression
- **Vectorización:** TF-IDF con unigramas y bigramas (25,000 features)
- **Precisión:** 74%
- **Categorías:** Positivo, Negativo, Neutro

### Métricas de Desempeño
| Categoría | Precision | Recall | F1-Score |
|-----------|-----------|--------|----------|
| Positivo  | 0.80      | 0.85   | 0.83     |
| Negativo  | 0.76      | 0.85   | 0.80     |
| Neutro    | 0.46      | 0.29   | 0.36     |

## Tecnologías Utilizadas

### Backend
- **Spring Boot 4.0.1**
- **Java 21**
- **PostgreSQL**
- **Spring Data JPA**
- **Google Gemini AI** (traducción inteligente)
- **Lombok** (reducción de código boilerplate)
- **Spring Validation** (validación de inputs)

### ML Service
- **FastAPI**
- **Python 3.11**
- **Scikit-learn**
- **Pandas**

### Frontend
- **React 18**
- **TypeScript**
- **Tailwind CSS**
- **Vite**

## Estructura del Proyecto

### Backend (Java/Spring Boot)
- `src/main/java/com/example/demo/`
  - `controller/` - Endpoints REST
  - `service/` - Lógica de negocio
    - `SentimentService.java` - Análisis de sentimientos con traducción
    - `GeminiConsultation.java` - Cliente Google Gemini AI
    - `TranslationService.java` - Servicio de traducción centralizado
    - `TranslationCache.java` - Caché de traducciones
    - `StatsService.java` - Estadísticas y métricas
  - `model/` - Entidades JPA
  - `dto/` - Objetos de transferencia
  - `exception/` - Manejo de errores
  - `config/` - Configuración de REST clients

### ML Service (Python/FastAPI)
- `app/` - API FastAPI
- `model/` - Modelo y vectorizador serializados
- `notebooks/` - Notebook de entrenamiento

### Frontend (React/TypeScript)
- `src/components/` - Componentes UI
- `src/services/` - Cliente API
- `src/` - Aplicación principal

## Docker

### Servicios
- **postgres:** Base de datos PostgreSQL
- **ml-service:** API de Machine Learning (puerto 8000)
- **sentiment-api:** Backend Spring Boot (puerto 8080)
- **frontend:** Frontend React (puerto 80)

### Volumenes
- `postgres_data:` Persistencia de datos

## Validación de Input

El sistema valida que:
- El campo `text` exista en el request
- El texto tenga longitud mínima de 3 caracteres
- El texto no exceda 1000 caracteres

**Errores:**
```json
{
  "error": "El texto es requerido y debe tener entre 3 y 1000 caracteres"
}
```

## Características Destacadas

### Flujo de Procesamiento Mejorado
1. **Recepción de texto** en cualquier idioma
2. **Traducción automática** con Google Gemini 2.5 Flash Lite
3. **Análisis de sentimientos** con modelo ML entrenado
4. **Almacenamiento** de texto original y resultado
5. **Caching inteligente** para optimizar rendimiento

### Ventajas de la Implementación
- **Soporte multilingüe** automático sin configuración adicional
- **Alta disponibilidad** con fallbacks robustos
- **Rendimiento optimizado** con sistema de caché
- **Escalabilidad** con arquitectura de microservicios

## Funcionalidades Avanzadas Implementadas

### Traducción Inteligente con IA
- **Integración con Google Gemini 2.5 Flash Lite** para traducción automática
- **Detección de idioma** y traducción al español antes del análisis
- **Caching de traducciones** para optimizar rendimiento
- **Fallback seguro** en caso de fallo del servicio de traducción

### Sistema de Traducción
- **TranslationService:** Servicio centralizado para traducciones
- **TranslationCache:** Sistema de caché en memoria para traducciones repetidas
- **GeminiConsultation:** Cliente optimizado para Google Gemini API
- **Procesamiento multilingüe:** Soporte implícito para múltiples idiomas

### MVP Requerido
- [x] Endpoint `/sentiment` con clasificación y probabilidad
- [x] Modelo entrenado y cargable
- [x] Validación de input
- [x] Respuesta clara con label y probabilidad
- [x] Ejemplos de uso
- [x] README con instrucciones

### Funcionalidades Adicionales
- [x] Endpoint `/stats` con estadísticas
- [x] Persistencia en PostgreSQL
- [x] Frontend con dashboard interactivo
- [x] Docker Compose para despliegue completo
- [x] Documentación Swagger/OpenAPI
- [x] Manejo de errores global
- [x] Traducción automática con Gemini AI
- [x] Sistema de caché para traducciones
- [x] Detección y procesamiento multilingüe

### Funcionalidades Opcionales No Implementadas
- [x] Soporte multilingüe (implementado via Gemini AI)
- [ ] Explicabilidad básica (palabras influyentes)
- [ ] Batch processing (CSV)
- [ ] Pruebas automatizadas completas
- [ ] Configuración de threshold dinámico

## Monitoreo y Salud

### Health Checks
- **Backend:** `GET /actuator/health`
- **ML Service:** `GET /health`
- **Frontend:** Disponibilidad en puerto 80

### Logs
- Los servicios generan logs estructurados
- Configuración de logging por ambiente

## Despliegue en Producción

### Variables de Entorno
```bash
# Base de datos
POSTGRES_DB=sentimentApi
POSTGRES_USER=postgresUser
POSTGRES_PASSWORD=postgresPassword
POSTGRES_PORT=5432

# Servicios
ML_SERVICE_URL=http://ml-service:8000
SERVER_PORT=8080

# Google Gemini AI
GEMINI_KEY=tu_api_key_aqui
```

### Escalabilidad
- Cada servicio es stateless (excepto PostgreSQL)
- Puede escalar horizontalmente con Kubernetes
- Frontend servido por Nginx con caché

## Contribución

1. Fork del proyecto
2. Crear feature branch
3. Commit con cambios
4. Push y Pull Request

## Licencia

Proyecto desarrollado para Hackathon One - Clasificación de Sentimientos

## Equipo

- **Data Science:** Modelo y análisis de datos
- **Backend:** API REST e integración
- **Frontend:** Interfaz de usuario

---

**Versión:** 1.0.0  
**Última Actualización:** Enero 2025
