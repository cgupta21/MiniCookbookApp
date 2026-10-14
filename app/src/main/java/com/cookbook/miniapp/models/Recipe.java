package com.cookbook.miniapp.models;

/**
 * Recipe model class representing a single recipe in the Mini Cookbook.
 * Stores all recipe details: title, category, ingredients, steps, cook time, servings.
 */
public class Recipe {
    private int id;
    private String title;
    private String category;
    private String ingredients;
    private String steps;
    private int cookTime;   // in minutes
    private int servings;
    private boolean isFavorite;
    private String createdAt;

    // Default constructor required for database operations
    public Recipe() {}

    // Constructor for creating new recipes
    public Recipe(String title, String category, String ingredients,
                  String steps, int cookTime, int servings) {
        this.title = title;
        this.category = category;
        this.ingredients = ingredients;
        this.steps = steps;
        this.cookTime = cookTime;
        this.servings = servings;
        this.isFavorite = false;
    }

    // --- Getters and Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getSteps() { return steps; }
    public void setSteps(String steps) { this.steps = steps; }

    public int getCookTime() { return cookTime; }
    public void setCookTime(int cookTime) { this.cookTime = cookTime; }

    public int getServings() { return servings; }
    public void setServings(int servings) { this.servings = servings; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
