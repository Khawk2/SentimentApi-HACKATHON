# Sentiment API - Análisis de Sentimientos

Sistema completo para análisis de sentimientos multilingüe que integra Data Science, Backend y Frontend. Desarrollado para Hackathon One con arquitectura de microservicios y capacidades de traducción automática con IA.

## Objetivo del Proyecto

Crear una API robusta que recibe textos en cualquier idioma (comentarios, reseñas o tweets), aplica traducción automática con Google Gemini AI, utiliza un modelo de Machine Learning para clasificar el sentimiento (Positivo/Neutro/Negativo) y presenta los resultados en un dashboard interactivo.

## Arquitectura del Sistema

```
├── backend/          # API REST Spring Boot 4.0.1 (Java 21)
├── ml-service/       # Servicio ML FastAPI (Python 3.11)
├── frontend/         # Dashboard React + TypeScript
├── data/            # Dataset y archivos de modelo
├── docker-compose.yaml
└── .env.example     # Variables de entorno
```

### Flujo de Arquitectura

1. **Frontend** → Recibe texto del usuario
2. **Backend** → Traduce con Gemini AI si no es español
3. **ML Service** → Analiza sentimiento con modelo entrenado
4. **PostgreSQL** → Almacena resultados y estadísticas
5. **Frontend** → Muestra resultados y visualizaciones

## Ejecución Rápida

### Prerrequisitos
- Docker y Docker Compose
- Git
- API Key de Google Gemini (para traducción automática)

### Pasos de Ejecución

1. **Clonar el repositorio:**
```bash
git clone https://github.com/Khawk2/SentimentApi-HACKATHON.git
cd SentimentApi-HACKATHON
```

2. **Configurar variables de entorno:**
```bash
cp .env.example .env
# Editar .env con tu API Key de Gemini
```

3. **Levantar todos los servicios:**
```bash
docker-compose up --build
```

4. **Acceder a los servicios:**
- **Frontend**: http://localhost:80
- **Backend API**: http://localhost:8080
- **ML Service**: http://localhost:8000
- **Documentación Swagger**: http://localhost:8080/swagger-ui/index.html
- **PostgreSQL**: localhost:5432

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

### Ejemplos de Uso

#### cURL
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

# Ejemplo en Inglés (se traducirá automáticamente)
curl -X POST http://localhost:8080/api/sentiment \
  -H "Content-Type: application/json" \
  -d '{"text": "The product is amazing, I love it!"}'
```

#### Postman
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
- **Archivo:** `data/dataset_2014_full.csv` (9.9MB)

### Métricas de Desempeño
| Categoría | Precision | Recall | F1-Score |
|-----------|-----------|--------|----------|
| Positivo  | 0.80      | 0.85   | 0.83     |
| Negativo  | 0.76      | 0.85   | 0.80     |
| Neutro    | 0.46      | 0.29   | 0.36     |

## Tecnologías Utilizadas

### Backend (Java/Spring Boot)
- **Spring Boot 4.0.1** - Framework principal
- **Java 21** - Última versión LTS
- **PostgreSQL** - Base de datos relacional
- **Spring Data JPA** - ORM y persistencia
- **Google Gemini AI** - Traducción inteligente (v1.0.0)
- **Lombok** - Reducción de código boilerplate
- **Spring Validation** - Validación de inputs
- **SpringDoc OpenAPI** - Documentación API (v2.8.0)
- **Spring Actuator** - Monitoreo y health checks

### ML Service (Python/FastAPI)
- **FastAPI 0.104.1** - API REST moderna
- **Python 3.11** - Última versión estable
- **Scikit-learn 1.6.1** - Machine Learning
- **Pandas** - Manipulación de datos
- **Uvicorn** - Servidor ASGI
- **Pydantic 2.5.0** - Validación de datos

### Frontend (React/TypeScript)
- **React 18** - Biblioteca de UI moderna
- **TypeScript** - Tipado estático
- **Tailwind CSS** - Framework de estilos
- **Vite 5.0.8** - Build tool ultrarrápido
- **Axios 1.6.2** - Cliente HTTP
- **Recharts 2.10.3** - Visualización de datos
- **Lucide React 0.294.0** - Iconos modernos

## Estructura del Proyecto

### Backend (Java/Spring Boot)
```
backend/
├── src/main/java/com/example/demo/
│   ├── controller/         # Endpoints REST
│   ├── service/           # Lógica de negocio
│   │   ├── SentimentService.java      # Análisis con traducción
│   │   ├── GeminiConsultation.java    # Cliente Google Gemini AI
│   │   ├── TranslationService.java    # Traducción centralizada
│   │   ├── TranslationCache.java     # Caché de traducciones
│   │   └── StatsService.java          # Estadísticas y métricas
│   ├── model/             # Entidades JPA
│   ├── dto/               # Objetos de transferencia
│   ├── exception/         # Manejo de errores
│   └── config/            # Configuración REST clients
├── pom.xml                # Dependencias Maven
└── Dockerfile             # Containerización
```

### ML Service (Python/FastAPI)
```
ml-service/
├── app/
│   ├── main.py           # API FastAPI con endpoints
│   ├── model_loader.py   # Carga y predicción del modelo
│   └── schemas.py        # Esquemas Pydantic para validación
├── model/
│   ├── modelo_sentimientos_2025.pkl    # Modelo entrenado
│   └── vectorizador_tfidf.pkl          # Vectorizador TF-IDF
├── notebooks/
│   └── Hackathon_1.ipynb # Notebook de entrenamiento
├── requirements.txt      # Dependencias Python
└── Dockerfile            # Configuración Docker
```

### Frontend (React/TypeScript)
```
frontend/
├── src/
│   ├── components/       # Componentes UI reutilizables
│   ├── services/         # Cliente API y llamadas HTTP
│   ├── types/            # Definiciones TypeScript
│   └── App.tsx           # Aplicación principal
├── public/               # Assets estáticos
├── package.json          # Dependencias npm
├── tailwind.config.js    # Configuración Tailwind
└── Dockerfile            # Configuración Docker
```

## Docker y Despliegue

### Servicios Docker
- **postgres:** Base de datos PostgreSQL (puerto 5432)
- **ml-service:** API de Machine Learning (puerto 8000)
- **sentiment-api:** Backend Spring Boot (puerto 8080)
- **frontend:** Frontend React con Nginx (puerto 80)

### Volúmenes
- `postgres_data:` Persistencia de datos PostgreSQL

### Health Checks
- **PostgreSQL:** `pg_isready` cada 10s
- **ML Service:** `/health` cada 30s
- **Backend:** `/actuator/health` cada 30s

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
- **Dashboard interactivo** con visualizaciones en tiempo real

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

### Dashboard Interactivo
- **Análisis en tiempo real** de textos ingresados
- **Visualizaciones dinámicas** con gráficos de barras y pastel
- **Estadísticas históricas** de análisis realizados
- **Interfaz responsive** adaptable a cualquier dispositivo

## MVP y Funcionalidades

### MVP Requerido
- [x] Endpoint `/sentiment` con clasificación y probabilidad
- [x] Modelo entrenado y cargable
- [x] Notebook(jupyter)
- [x] Validación de input
- [x] Respuesta clara con label y probabilidad
- [x] Ejemplos de uso
- [x] README con instrucciones completas

### Funcionalidades Adicionales Implementadas
- [x] Endpoint `/stats` con estadísticas en tiempo real
- [x] Persistencia en PostgreSQL con Docker
- [x] Frontend con dashboard interactivo (React + TypeScript)
- [x] Docker Compose para despliegue completo
- [x] Documentación Swagger/OpenAPI integrada
- [x] Manejo de errores global y personalizado
- [x] Traducción automática con Google Gemini AI
- [x] Sistema de caché para traducciones repetidas
- [x] Detección y procesamiento multilingüe automático
- [x] Health checks para todos los servicios
- [x] Visualización de datos con gráficos interactivos
- [x] Soporte multilingüe (implementado via Gemini AI)

### Funcionalidades Opcionales No Implementadas Aún
- [ ] Explicabilidad básica (palabras influyentes)
- [ ] Batch processing (CSV upload)
- [ ] Pruebas automatizadas completas
- [ ] Configuración de threshold dinámico
- [ ] Autenticación y autorización de usuarios

## Monitoreo y Salud

### Health Checks
- **Backend:** `GET http://localhost:8080/actuator/health`
- **ML Service:** `GET http://localhost:8000/health`
- **Frontend:** Disponibilidad en puerto 80
- **PostgreSQL:** Health check con `pg_isready`

### Logs
- Los servicios generan logs estructurados
- Configuración de logging por ambiente
- Monitoreo de errores y rendimiento

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

# Google Gemini AI (requerido para traducción)
GEMINI_KEY=tu_api_key_aqui
```

### Escalabilidad
- Cada servicio es stateless (excepto PostgreSQL)
- Puede escalar horizontalmente con Kubernetes
- Frontend servido por Nginx con caché
- Sistema de caché para traducciones reduce carga

### Configuración de Red
- **Network:** `sentiment-network` (bridge)
- **Ports:** 80 (frontend), 8080 (backend), 8000 (ML), 5432 (DB)
- **Health checks:** Configurados para todos los servicios

##Contribución

1. **Fork** del proyecto
2. Crear **feature branch** (`git checkout -b feature/amazing-feature`)
3. **Commit** con cambios (`git commit -m 'Add amazing feature'`)
4. **Push** y **Pull Request**

### Guías de Contribución
- Seguir convenciones de código establecidas
- Agregar tests para nuevas funcionalidades
- Actualizar documentación relevante
- Respetar estructura de microservicios

## Licencia

Proyecto desarrollado para el **Hackathon One** - Clasificación de Sentimientos

## Equipo

- **Data Science:** Modelo y análisis de datos
- **Backend:** API REST e integración de servicios
- **Frontend:** Interfaz de usuario y experiencia

## Enlaces Útiles

- **Repositorio:** https://github.com/Khawk2/SentimentApi-HACKATHON
- **Documentación API:** http://localhost:8080/swagger-ui/index.html
- **Dashboard:** http://localhost:80
- **ML Service:** http://localhost:8000/docs

---

**Versión:** 0.0.1
**Última Actualización:** Enero 2026  
**Estado:** Producción Ready

## Resumen del Proyecto

Este sistema de análisis de sentimientos representa una solución completa y moderna que combina:

- **Inteligencia Artificial** con Google Gemini para traducción
- **Machine Learning** con modelo entrenado en español
- **Alto Rendimiento** con sistema de caché y microservicios
- **Experiencia Usuario** con dashboard interactivo y responsive
- **Producción Ready** con Docker, health checks y monitoreo

Ideal para empresas que necesitan analizar sentimientos en múltiples idiomas con alta precisión y escalabilidad.
