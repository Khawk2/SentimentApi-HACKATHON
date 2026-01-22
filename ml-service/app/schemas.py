from pydantic import BaseModel, Field
from typing import List, Dict

class SentimentRequest(BaseModel):
    text: str = Field(..., min_length=1, description="Texto a analizar")

class InfluentialWord(BaseModel):
    word: str = Field(..., description="Palabra influyente")
    importance: float = Field(..., description="Nivel de importancia (-1 a 1)")
    sentiment: str = Field(..., description="Sentimiento asociado: positivo, negativo, neutro")

class SentimentResponse(BaseModel):
    prevision: str = Field(..., description="Clasificación del sentimiento")
    probabilidad: float = Field(..., ge=0.0, le=1.0)
    palabras_influyentes: List[InfluentialWord] = Field(default=[], description="Palabras que más influyeron en la predicción")
    explicacion: str = Field(default="", description="Explicación breve de la predicción")
