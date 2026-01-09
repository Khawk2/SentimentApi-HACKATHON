from fastapi import FastAPI, HTTPException
from contextlib import asynccontextmanager
import logging

from app.model_loader import SentimentModel
from app.schemas import SentimentRequest, SentimentResponse

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

model_service: SentimentModel | None = None


@asynccontextmanager
async def lifespan(app: FastAPI):
    global model_service
    try:
        logger.info("Cargando modelo...")
        model_service = SentimentModel()
        logger.info("Modelo y vectorizador cargados correctamente")
    except Exception as e:
        logger.exception("FALLO CARGANDO MODELO")
        model_service = None
    yield


app = FastAPI(
    title="Sentiment Analysis API",
    version="1.0.0",
    lifespan=lifespan
)

@app.get("/health")
async def health():
    return {
        "status": "healthy",
        "model_loaded": model_service is not None
    }


@app.post("/predict", response_model=SentimentResponse)
async def predict(req: SentimentRequest):
    if not model_service:
        raise HTTPException(status_code=503, detail="Modelo no disponible")

    try:
        return model_service.predict(req.text)
    except Exception as e:
        logger.exception("ERROR REAL EN PREDICT")  # 👈 ESTO
        raise HTTPException(status_code=500, detail=str(e))  # 👈 ESTO