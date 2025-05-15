package com.heartdisease.app;

import java.util.Arrays;

public class HeartDiseasePredictor {
    
    /**
     * Predicts heart disease based on input features
     * 
     * @param age Age of the person
     * @param sex Sex (1=male, 0=female)
     * @param chestPainType Chest pain type (0-3)
     * @param bloodPressure Blood pressure in mm Hg
     * @param cholesterol Cholesterol in mg/dL
     * @return true if heart disease predicted, false otherwise
     */
    public boolean predictHeartDisease(int age, int sex, int chestPainType, int bloodPressure, int cholesterol) {
        // This is a simplified model that mimics the behavior of a trained machine learning model
        // In a production app, you would use a proper ML model loaded from TensorFlow Lite or similar
        
        // Risk score calculation based on medical knowledge
        double riskScore = 0;
        
        // Age risk (age over 50 increases risk)
        if (age > 70) {
            riskScore += 3;
        } else if (age > 60) {
            riskScore += 2;
        } else if (age > 50) {
            riskScore += 1;
        }
        
        // Sex risk (male has higher risk)
        if (sex == 1) {
            riskScore += 1;
        }
        
        // Chest pain risk (types 2 and 3 indicate higher risk)
        if (chestPainType >= 2) {
            riskScore += 2;
        } else if (chestPainType == 1) {
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
}