import numpy as np

def compute_enhanced_physical(X):
    # physical_raw_cols = ['Weight', 'Height', 'Waist', 'Hip', 'Thigh', 'Arm', 'Age']
    # Indices based on physical_raw_cols order
    weight, height, waist, hip, age = X[:, 0], X[:, 1], X[:, 2], X[:, 3], X[:, 6]
    
    # Avoid division by zero
    height_m = height / 100.0
    # Use a small epsilon to avoid divide by zero if data is bad
    bmi = weight / (height_m ** 2 + 1e-9)
    w_h_ratio = weight / (height + 1e-9)
    waist_h_ratio = waist / (height + 1e-9)
    waist_hip_ratio = waist / (hip + 1e-9)
    
    # Engineering interactions and polynomials
    age_waist = age * waist
    age_sq = age ** 2
    waist_sq = waist ** 2
    
    return np.column_stack([bmi, w_h_ratio, waist_h_ratio, waist_hip_ratio, age_waist, age_sq, waist_sq])

def compute_log_lifestyle(X):
    X_logged = X.copy()
    # lifestyle_num_cols indices: Calories(2), Sedentary_Mins(6), Daily_Drinks(11)
    for idx in [2, 6, 11]:
        X_logged[:, idx] = np.log1p(X_logged[:, idx])
    return X_logged
