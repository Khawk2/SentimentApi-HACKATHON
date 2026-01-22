import joblib
import logging
import re
import numpy as np
from pathlib import Path
from typing import List, Dict, Tuple
from app.schemas import InfluentialWord

logger = logging.getLogger(__name__)


MODEL_PATH = Path("model", "modelo_sentimientos_2025.pkl")
VECTORIZER_PATH = Path("model", "vectorizador_tfidf.pkl")


def clean_text(text: str) -> str:
    if not text or not isinstance(text, str):
        return ""

    text = text.lower()
    # Eliminar puntuación (mantiene letras, números y espacios)
    text = re.sub(r'[^\w\s]', '', text)
    # No eliminar números si hay letras en el texto original
    # Permitir números solo si hay contexto (letras alrededor)
    has_letters = bool(re.search(r'[a-zA-ZáéíóúÁÉÍÓÚñÑ]', text))
    if not has_letters:
        # Si no hay letras, verificar si es solo números
        if re.match(r'^\d+$', text.strip()):
            return ""
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

    def _get_influential_words(self, text: str, prediction: str) -> List[InfluentialWord]:
        """
        Analiza qué palabras tuvieron mayor impacto en la predicción.
        Usa los coeficientes del modelo y los valores TF-IDF.
        """
        try:
            # Obtener feature names del vectorizador
            feature_names = self.vectorizer.get_feature_names_out()
            
            # Vectorizar el texto
            vectorized = self.vectorizer.transform([text])
            
            # Obtener los índices de las características no nulas
            feature_indices = vectorized.nonzero()[1]
            tfidf_values = vectorized.data
            
            # Obtener coeficientes del modelo para cada clase
            if hasattr(self.model, 'coef_'):
                # Para Logistic Regression
                coeficients = self.model.coef_
            else:
                # Para otros modelos, usar feature importance si existe
                if hasattr(self.model, 'feature_importances_'):
                    coeficients = np.array([self.model.feature_importances_])
                else:
                    return []
            
            # Mapear clases a índices
            class_to_idx = {cls: idx for idx, cls in enumerate(self.model.classes_)}
            prediction_idx = class_to_idx.get(prediction, 0)
            
            # Calcular importancia de cada palabra
            influential_words = []
            word_importance_map = {}
            
            for i, feature_idx in enumerate(feature_indices):
                if i < len(tfidf_values):
                    word = feature_names[feature_idx]
                    tfidf_val = tfidf_values[i]
                    
                    # Importancia = coeficiente * valor TF-IDF
                    if prediction_idx < coeficients.shape[0]:
                        importance = coeficients[prediction_idx, feature_idx] * tfidf_val
                        word_importance_map[word] = importance
            
            # Ordenar por importancia absoluta
            sorted_words = sorted(word_importance_map.items(), 
                                key=lambda x: abs(x[1]), reverse=True)
            
            # Tomar las 10 palabras más influyentes
            for word, importance in sorted_words[:10]:
                # Determinar el sentimiento de la palabra basado en el signo
                if importance > 0.1:
                    word_sentiment = "positivo"
                elif importance < -0.1:
                    word_sentiment = "negativo"
                else:
                    word_sentiment = "neutro"
                
                influential_words.append(InfluentialWord(
                    word=word,
                    importance=round(importance, 3),
                    sentiment=word_sentiment
                ))
            
            return influential_words
            
        except Exception as e:
            logger.warning(f"Error calculando palabras influyentes: {e}")
            return []

    def _generate_explanation(self, prediction: str, influential_words: List[InfluentialWord]) -> str:
        """Genera una explicación breve de la predicción."""
        if not influential_words:
            return f"El texto fue clasificado como '{prediction}' basado en el análisis general del contenido."
        
        # Contar palabras por sentimiento
        positive_words = [w for w in influential_words if w.sentiment == "positivo"]
        negative_words = [w for w in influential_words if w.sentiment == "negativo"]
        
        if prediction.lower() == "positivo" and positive_words:
            top_positive = positive_words[0].word
            return f"Clasificado como '{prediction}' principalmente por palabras como '{top_positive}' que tienen connotación positiva."
        elif prediction.lower() == "negativo" and negative_words:
            top_negative = negative_words[0].word
            return f"Clasificado como '{prediction}' principalmente por palabras como '{top_negative}' que tienen connotación negativa."
        else:
            return f"Clasificado como '{prediction}' basado en el análisis del contexto y las palabras clave del texto."

    def predict(self, text: str) -> dict:
        if self.model is None or self.vectorizer is None:
            raise RuntimeError("Modelo o vectorizador no cargados")

        cleaned_text = clean_text(text)

        if not cleaned_text:
            raise ValueError("no puedes escribir sólo números")

        vectorized = self.vectorizer.transform([cleaned_text])

        prediction = self.model.predict(vectorized)[0]
        probabilities = self.model.predict_proba(vectorized)[0]

        probabilidad = float(probabilities.max())
        
        # Obtener palabras influyentes
        influential_words = self._get_influential_words(cleaned_text, prediction)
        
        # Generar explicación
        explanation = self._generate_explanation(prediction, influential_words)

        return {
            "prevision": prediction,   # "Positivo", "Negativo", "Neutro"
            "probabilidad": probabilidad,
            "palabras_influyentes": influential_words,
            "explicacion": explanation
        }
