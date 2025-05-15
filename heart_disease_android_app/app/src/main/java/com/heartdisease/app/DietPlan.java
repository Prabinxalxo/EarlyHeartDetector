package com.heartdisease.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DietPlan {
    private Map<String, List<String>> recommendations;
    
    public DietPlan() {
        recommendations = new HashMap<>();
    }
    
    /**
     * Add a category of diet recommendations
     * 
     * @param category The category name
     * @param items List of recommendation items for the category
     */
    public void addCategory(String category, List<String> items) {
        recommendations.put(category, items);
    }
    
    /**
     * Get all category names
     * 
     * @return Set of category names
     */
    public Set<String> getCategories() {
        return recommendations.keySet();
    }
    
    /**
     * Get recommendation items for a specific category
     * 
     * @param category The category name
     * @return List of recommendation items
     */
    public List<String> getItemsForCategory(String category) {
        return recommendations.getOrDefault(category, new ArrayList<>());
    }
    
    /**
     * Get all recommendations
     * 
     * @return Map of category name to list of recommendation items
     */
    public Map<String, List<String>> getAllRecommendations() {
        return recommendations;
    }
}