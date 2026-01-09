# ML Service - Análisis de Sentimientos

API REST para análisis de sentimientos en español que clasifica texto en tres categorías: **Positivo**, **Negativo** o **Neutro**.

## Descripción

Servicio de Machine Learning desarrollado con FastAPI que permite a las empresas analizar automáticamente el tono de comentarios y mensajes en español, sin necesidad de revisarlos manualmente.

## Características

- Clasificación de sentimientos en tres categorías: Positivo, Negativo, Neutro
- API REST con FastAPI
- Modelo entrenado con Logistic Regression
- Precisión del modelo: 74%
- Preprocesamiento automático de texto
- Respuestas con probabilidad de confianza

## Arquitectura

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
│   └── Hackathon_1.ipynb # Notebook con entrenamiento del modelo
├── Dockerfile            # Configuración Docker
└── requirements.txt      # Dependencias Python
```

## Instalación

### Requisitos

- Python 3.11+
- pip

### Instalación local

1. Clonar el repositorio:
```bash
git clone <https://github.com/Khawk2/SENTIMENTAPI.git>
cd ml-service
```

2. Crear entorno virtual:
```bash
python -m venv venv
source venv/bin/activate  # En Windows: venv\Scripts\activate
```

3. Instalar dependencias:
```bash
pip install -r requirements.txt
```

4. Ejecutar la aplicación:
```bash
uvicorn app.main:app --host 0.0.0.0 --port 8000
```

## Docker

### Construir la imagen:
```bash
docker build -t ml-service .
```

### Ejecutar el contenedor:
```bash
docker run -p 8000:8000 ml-service
```

## Endpoints

### GET /health

Verifica el estado del servicio y si el modelo está cargado.

**Respuesta:**
```json
{
  "status": "healthy",
  "model_loaded": true
}
```

### POST /predict

Realiza una predicción de sentimiento sobre un texto.

**Request:**
```json
{
  "text": "Me encantó el producto, muy buena calidad"
}
```

**Response:**
```json
{
  "prevision": "Bueno",
  "probabilidad": 0.85
}
```

## Modelo

- **Dataset**: Amazon Reviews Spanish (200,000 registros)
- **Algoritmo**: Logistic Regression
- **Vectorización**: TF-IDF con unigramas y bigramas (25,000 features)
- **Precisión**: 74%
- **Categorías**: Bueno, Malo, Regular

### Métricas de desempeño

| Categoría | Precision | Recall | F1-Score |
|-----------|-----------|--------|----------|
| Bueno     | 0.80      | 0.85   | 0.83     |
| Malo      | 0.76      | 0.85   | 0.80     |
| Regular   | 0.46      | 0.29   | 0.36     |

## Uso

### Ejemplo con cURL:

```bash
curl -X POST "http://localhost:8000/predict" \
  -H "Content-Type: application/json" \
  -d '{"text": "El servicio fue excelente"}'
```

### Ejemplo con Python:

```python
import requests

response = requests.post(
    "http://localhost:8000/predict",
    json={"text": "El servicio fue excelente"}
)

print(response.json())
# {"prevision": "Positivo", "probabilidad": 0.82}
```

## Documentación API

Una vez que el servicio esté ejecutándose, puedes acceder a la documentación interactiva en:

- **Swagger UI**: http://localhost:8000/docs
- **ReDoc**: http://localhost:8000/redoc

## Desarrollo

El modelo fue entrenado en el notebook `notebooks/Hackathon_1.ipynb` usando:
- Dataset de Amazon Reviews en español de Hugging Face
- Scikit-learn para el modelo y vectorización
- Joblib para serialización



