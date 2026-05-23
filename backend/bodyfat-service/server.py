from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field
from typing import List, Optional
import joblib
import pandas as pd
import numpy as np
import uvicorn

# The functions must be importable for joblib to work
from preprocessing_utils import compute_enhanced_physical, compute_log_lifestyle

app = FastAPI(title="Body Fat Prediction API")

# --- Models and Data Structures ---

class PredictionInput(BaseModel):
    # Required physical measurements
    Weight: float
    Height: float
    Waist: float
    Hip: float
    Thigh: float
    Arm: float
    Age: int
    
    # Optional lifestyle factors (defaults provided for imputer)
    Blood_Sugar: Optional[float] = None
    Sleep_Hours: Optional[float] = 8.0
    Calories: Optional[float] = None
    Protein: Optional[float] = None
    Carbs: Optional[float] = None
    Fat: Optional[float] = None
    Sedentary_Mins: Optional[float] = None
    Systolic_BP: Optional[float] = 120.0
    Diastolic_BP: Optional[float] = 80.0
    Vigorous_Mins: Optional[float] = None
    Moderate_Mins: Optional[float] = None
    Daily_Drinks: Optional[float] = 0.0
    Smoker_Status: float = 2.0  # 1=Yes, 2=No

class PredictionResponse(BaseModel):
    gender: str
    prediction: float

# --- Load Models once at startup ---

models = {}

@app.on_event("startup")
def load_models():
    try:
        models['male'] = joblib.load('bodyfat_model_male.joblib')
        models['female'] = joblib.load('bodyfat_model_female.joblib')
        print("Models loaded successfully.")
    except Exception as e:
        print(f"Error loading models: {e}")

# --- Endpoints ---

@app.get("/")
def health_check():
    return {"status": "healthy", "models_loaded": list(models.keys())}

@app.post("/predict/{gender}", response_model=PredictionResponse)
def predict(gender: str, data: PredictionInput):
    gender_key = gender.lower()
    if gender_key not in models:
        raise HTTPException(status_code=404, detail="Model for gender not found. Use 'male' or 'female'.")
    
    # Convert Pydantic model to DataFrame
    input_dict = data.dict()
    df = pd.DataFrame([input_dict])
    
    # The pipeline handles the rest
    try:
        prediction = models[gender_key].predict(df)[0]
        return {
            "gender": gender_key,
            "prediction": float(prediction)
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
