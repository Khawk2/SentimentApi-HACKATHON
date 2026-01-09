import joblib
import logging
import re
from pathlib import Path

logger = logging.getLogger(__name__)

MODEL_PATH = Path("model", "modelo_sentimientos_2025.pkl")
VECTORIZER_PATH = Path("model", "vectorizador_tfidf.pkl")


def clean_text(text: str) -> str:
    if not text or not isinstance(text, str):
        return ""

    text = text.lower()
    # Eliminar puntuación (mantiene letras, números y espacios)
    text = re.sub(r'[^\w\s]', '', text)
    # Eliminar números (como en el entrenamiento del modelo)
    text = re.sub(r'\d+', '', text)
    # Normalizar espacios múltiples
    text = re.sub(r'\s+', ' ', text)
    return text.strip()

class SentimentModel:
    def __init__(self):
        self.model = None
        self.vectorizer = None
        self._load()

    def _load(self):
        if not MODEL_PATH.exists() or not VECTORIZER_PATH.exists():
            raise FileNotFoundError("Modelo o vectorizador no encontrados")

        self.model = joblib.load(MODEL_PATH)
        self.vectorizer = joblib.load(VECTORIZER_PATH)
        logger.info("Modelo cargado exitosamente")

    def predict(self, text: str) -> dict:

        if self.model is None or self.vectorizer is None:
            raise RuntimeError("Modelo o vectorizador no cargados")

        cleaned_text = clean_text(text)

        if not cleaned_text:
            raise ValueError("Texto inválido para predicción")

        vectorized = self.vectorizer.transform([cleaned_text])

        prediction = self.model.predict(vectorized)[0]
        probabilities = self.model.predict_proba(vectorized)[0]

        probabilidad = float(probabilities.max())

        return {
            "prevision": prediction,   # "Positivo", "Negativo", "Neutro"
            "probabilidad": probabilidad
        }
