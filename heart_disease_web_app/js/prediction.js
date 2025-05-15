/**
 * Predicts heart disease based on input features
 * 
 * @param {number} age Age of the person
 * @param {number} sex Sex (1=male, 0=female)
 * @param {number} chestPainType Chest pain type (0-3)
 * @param {number} bloodPressure Blood pressure in mm Hg
 * @param {number} cholesterol Cholesterol in mg/dL
 * @return {boolean} true if heart disease predicted, false otherwise
 */
function predictHeartDisease(age, sex, chestPainType, bloodPressure, cholesterol) {
    // This is a simplified model that mimics the behavior of a trained machine learning model
    // In a production app, you would use a more sophisticated model or API
    
    // Risk score calculation based on medical knowledge
    let riskScore = 0;
    
    // Age risk (age over 50 increases risk)
    if (age > 70) {
        riskScore += 3;
    } else if (age > 60) {
        riskScore += 2;
    } else if (age > 50) {
        riskScore += 1;
    }
    
    // Sex risk (male has higher risk)
    if (sex === 1) {
        riskScore += 1;
    }
    
    // Chest pain risk (types 2 and 3 indicate higher risk)
    if (chestPainType >= 2) {
        riskScore += 2;
    } else if (chestPainType === 1) {
        riskScore += 1;
    }
    
    // Blood pressure risk
    if (bloodPressure > 160) {
        riskScore += 3;
    } else if (bloodPressure > 140) {
        riskScore += 2;
    } else if (bloodPressure > 120) {
        riskScore += 1;
    }
    
    // Cholesterol risk
    if (cholesterol > 280) {
        riskScore += 3;
    } else if (cholesterol > 240) {
        riskScore += 2;
    } else if (cholesterol > 200) {
        riskScore += 1;
    }
    
    // Determine heart disease risk based on total score
    // Higher score means higher likelihood of heart disease
    return riskScore >= 5;
}