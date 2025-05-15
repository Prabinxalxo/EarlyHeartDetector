/**
 * Returns diet recommendations based on heart disease status
 * 
 * @param {boolean} hasHeartDisease Whether the user has heart disease
 * @return {Object} Object with diet recommendations by category
 */
function getDietRecommendation(hasHeartDisease) {
    // Base recommendations (good for everyone)
    const baseRecommendations = {
        "Fruits & Vegetables": [
            "Aim for at least 5 servings of fruits and vegetables daily",
            "Include leafy greens like spinach and kale",
            "Choose a variety of colorful produce for diverse nutrients"
        ],
        "Whole Grains": [
            "Choose whole grain bread and pasta over refined options",
            "Include brown rice, quinoa, and oats in your diet"
        ],
        "Healthy Proteins": [
            "Include plant-based proteins like beans and lentils",
            "Choose lean poultry and fish over red meat when possible"
        ],
        "Healthy Fats": [
            "Use olive oil for cooking and dressings",
            "Include nuts and seeds as snacks in moderation"
        ]
    };
    
    if (hasHeartDisease) {
        // Additional recommendations for people with heart disease
        const heartDiseaseRecommendations = {
            "Sodium Reduction": [
                "Limit sodium intake to less than 1,500 mg per day",
                "Avoid processed foods which are typically high in sodium",
                "Use herbs and spices instead of salt for flavoring"
            ],
            "Cholesterol Management": [
                "Limit dietary cholesterol to less than 200 mg per day",
                "Avoid or strictly limit egg yolks, organ meats, and shellfish",
                "Include soluble fiber from oats, beans, and fruits to help lower cholesterol"
            ],
            "Specific Foods to Include": [
                "Fatty fish rich in omega-3 (salmon, mackerel) twice weekly",
                "Heart-healthy nuts like walnuts and almonds (in moderation)",
                "Berries rich in antioxidants and fiber"
            ],
            "Foods to Avoid": [
                "Eliminate trans fats from your diet (check labels for 'partially hydrogenated oils')",
                "Limit saturated fats from fatty meats, full-fat dairy, and tropical oils",
                "Reduce intake of refined carbohydrates and added sugars"
            ]
        };
        
        // Combine base and specific recommendations
        return {...baseRecommendations, ...heartDiseaseRecommendations};
    } else {
        // Additional recommendations for heart disease prevention
        const preventionRecommendations = {
            "Heart-Healthy Tips": [
                "Maintain a moderate sodium intake (less than 2,300 mg daily)",
                "Choose low-fat dairy products",
                "Limit added sugars in your diet"
            ],
            "Beneficial Foods": [
                "Include fatty fish rich in omega-3 at least once a week",
                "Add nuts and seeds to your diet in moderation",
                "Include potassium-rich foods like bananas, potatoes, and avocados"
            ]
        };
        
        // Combine base and prevention recommendations
        return {...baseRecommendations, ...preventionRecommendations};
    }
}