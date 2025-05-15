package com.heartdisease.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DietRecommendations {
    
    /**
     * Returns diet recommendations based on heart disease status
     * 
     * @param hasHeartDisease Whether the user has heart disease
     * @return A DietPlan object with recommended diet
     */
    public DietPlan getDietRecommendation(boolean hasHeartDisease) {
        DietPlan dietPlan = new DietPlan();
        
        // Add base recommendations (good for everyone)
        addBaseRecommendations(dietPlan);
        
        // Add specific recommendations based on heart disease status
        if (hasHeartDisease) {
            addHeartDiseaseRecommendations(dietPlan);
        } else {
            addPreventionRecommendations(dietPlan);
        }
        
        return dietPlan;
    }
    
    private void addBaseRecommendations(DietPlan dietPlan) {
        // Fruits & Vegetables recommendations
        List<String> fruitsVeggies = new ArrayList<>();
        fruitsVeggies.add("Aim for at least 5 servings of fruits and vegetables daily");
        fruitsVeggies.add("Include leafy greens like spinach and kale");
        fruitsVeggies.add("Choose a variety of colorful produce for diverse nutrients");
        dietPlan.addCategory("Fruits & Vegetables", fruitsVeggies);
        
        // Whole Grains recommendations
        List<String> wholeGrains = new ArrayList<>();
        wholeGrains.add("Choose whole grain bread and pasta over refined options");
        wholeGrains.add("Include brown rice, quinoa, and oats in your diet");
        dietPlan.addCategory("Whole Grains", wholeGrains);
        
        // Healthy Proteins recommendations
        List<String> proteins = new ArrayList<>();
        proteins.add("Include plant-based proteins like beans and lentils");
        proteins.add("Choose lean poultry and fish over red meat when possible");
        dietPlan.addCategory("Healthy Proteins", proteins);
        
        // Healthy Fats recommendations
        List<String> fats = new ArrayList<>();
        fats.add("Use olive oil for cooking and dressings");
        fats.add("Include nuts and seeds as snacks in moderation");
        dietPlan.addCategory("Healthy Fats", fats);
    }
    
    private void addHeartDiseaseRecommendations(DietPlan dietPlan) {
        // Sodium Reduction recommendations
        List<String> sodium = new ArrayList<>();
        sodium.add("Limit sodium intake to less than 1,500 mg per day");
        sodium.add("Avoid processed foods which are typically high in sodium");
        sodium.add("Use herbs and spices instead of salt for flavoring");
        dietPlan.addCategory("Sodium Reduction", sodium);
        
        // Cholesterol Management recommendations
        List<String> cholesterol = new ArrayList<>();
        cholesterol.add("Limit dietary cholesterol to less than 200 mg per day");
        cholesterol.add("Avoid or strictly limit egg yolks, organ meats, and shellfish");
        cholesterol.add("Include soluble fiber from oats, beans, and fruits to help lower cholesterol");
        dietPlan.addCategory("Cholesterol Management", cholesterol);
        
        // Specific Foods to Include recommendations
        List<String> includeFood = new ArrayList<>();
        includeFood.add("Fatty fish rich in omega-3 (salmon, mackerel) twice weekly");
        includeFood.add("Heart-healthy nuts like walnuts and almonds (in moderation)");
        includeFood.add("Berries rich in antioxidants and fiber");
        dietPlan.addCategory("Specific Foods to Include", includeFood);
        
        // Foods to Avoid recommendations
        List<String> avoidFood = new ArrayList<>();
        avoidFood.add("Eliminate trans fats from your diet (check labels for 'partially hydrogenated oils')");
        avoidFood.add("Limit saturated fats from fatty meats, full-fat dairy, and tropical oils");
        avoidFood.add("Reduce intake of refined carbohydrates and added sugars");
        dietPlan.addCategory("Foods to Avoid", avoidFood);
    }
    
    private void addPreventionRecommendations(DietPlan dietPlan) {
        // Heart-Healthy Tips
        List<String> tips = new ArrayList<>();
        tips.add("Maintain a moderate sodium intake (less than 2,300 mg daily)");
        tips.add("Choose low-fat dairy products");
        tips.add("Limit added sugars in your diet");
        dietPlan.addCategory("Heart-Healthy Tips", tips);
        
        // Beneficial Foods
        List<String> beneficialFoods = new ArrayList<>();
        beneficialFoods.add("Include fatty fish rich in omega-3 at least once a week");
        beneficialFoods.add("Add nuts and seeds to your diet in moderation");
        beneficialFoods.add("Include potassium-rich foods like bananas, potatoes, and avocados");
        dietPlan.addCategory("Beneficial Foods", beneficialFoods);
    }
}