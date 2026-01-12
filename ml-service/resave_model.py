import joblib
from pathlib import Path

OLD_MODEL_PATH = Path("model", "modelo_sentimientos_2025.pkl")
OLD_VECTORIZER_PATH = Path("model", "vectorizador_tfidf.pkl")

NEW_MODEL_PATH = Path("model", "modelo_sentimientos_2025_311.joblib")
NEW_VECTORIZER_PATH = Path("model", "vectorizador_tfidf_311.joblib")

print("Cargando modelo original...")
model = joblib.load(OLD_MODEL_PATH)

print("Cargando vectorizador original...")
vectorizer = joblib.load(OLD_VECTORIZER_PATH)

print("Guardando modelo compatible Python 3.11...")
joblib.dump(model, NEW_MODEL_PATH)

print("Guardando vectorizador compatible Python 3.11...")
joblib.dump(vectorizer, NEW_VECTORIZER_PATH)

print("✅ Re-serialización completada correctamente")
