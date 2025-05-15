import numpy as np
from sklearn.ensemble import RandomForestClassifier

# This is a pre-trained model for heart disease prediction
# In a real application, you would load a saved model file or train on actual data
def get_model():
    # Create a simple model (in production, you would load a properly trained model)
    model = RandomForestClassifier(n_estimators=100, random_state=42)
    
    # Example training data (simulated)
    # These coefficients roughly align with known heart disease risk factors
    # Age: positive correlation
    # Sex (1=male, 0=female): positive correlation (males at higher risk)
    # Chest pain (0-3): positive correlation
    # Blood pressure: positive correlation
    # Cholesterol: positive correlation
    
    # Create synthetic model behavior that reflects medical knowledge
    # This is for demonstration purposes only - a real app would use a properly trained model
    X = np.array([
        [40, 1, 0, 120, 200],  # Healthy young male
        [60, 1, 2, 140, 240],  # Older male with moderate chest pain and higher BP/cholesterol
        [45, 0, 0, 130, 210],  # Healthy middle-aged female
        [70, 0, 3, 160, 300],  # Elderly female with severe chest pain and high BP/cholesterol
        [55, 1, 1, 150, 250],  # Middle-aged male with mild chest pain and elevated factors
        [65, 0, 2, 145, 230],  # Older female with moderate pain
        [50, 1, 3, 160, 280],  # Middle-aged male with severe chest pain and high risk factors
        [75, 1, 3, 170, 320],  # Elderly male with severe symptoms
    ])
    
    y = np.array([0, 1, 0, 1, 0, 1, 1, 1])  # Target: 0=no disease, 1=disease
    
    # Train the model
    model.fit(X, y)
    return model

def predict_heart_disease(input_data):
    """
    Predict heart disease based on input features
    
    Parameters:
    input_data (numpy.array): Array of shape (1, 5) containing:
        - Age
        - Sex (1=male, 0=female)
        - Chest pain type (0-3)
        - Blood pressure
        - Cholesterol
    
    Returns:
    bool: True if heart disease predicted, False otherwise
    """
    model = get_model()
    prediction = model.predict(input_data)[0]
    return bool(prediction)
