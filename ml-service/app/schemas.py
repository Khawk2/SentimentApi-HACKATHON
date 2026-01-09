from pydantic import BaseModel, Field

class SentimentRequest(BaseModel):
    text: str = Field(..., min_length=1, description="Texto a analizar")

class SentimentResponse(BaseModel):
    prevision: str = Field(..., description="Clasificación del sentimiento")
    probabilidad: float = Field(..., ge=0.0, le=1.0)
