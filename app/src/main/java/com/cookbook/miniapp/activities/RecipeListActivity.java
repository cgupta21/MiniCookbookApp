package com.cookbook.miniapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cookbook.miniapp.R;
import com.cookbook.miniapp.adapters.RecipeAdapter;
import com.cookbook.miniapp.database.RecipeDAO;
import com.cookbook.miniapp.models.Recipe;
import java.util.List;

/**
 * RecipeListActivity - Displays all recipes belonging to a selected category.
 * Receives the category name via Intent extra "category".
 * Each recipe card navigates to RecipeDetailActivity on click.
 */
public class RecipeListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private RecipeDAO recipeDAO;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        // Get category name from the Intent
        category = getIntent().getStringExtra("category");
        if (category == null) category = "All";

        // Setup toolbar with back button
        Toolbar toolbar = findViewById(R.id.toolbarRecipeList);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(category + " Recipes");
        }

        // Apply category-specific color to toolbar
        toolbar.setBackgroundColor(Color.parseColor(getCategoryColor(category)));

        // Initialize DAO and RecyclerView
        recipeDAO = new RecipeDAO(this);
        recyclerView = findViewById(R.id.recyclerViewRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadRecipes();
    }

    /**
     * Loads recipes from database and sets up the RecyclerView adapter.
     */
    private void loadRecipes() {
        List<Recipe> recipes;
        if (category.equals("All")) {
            recipes = recipeDAO.getAllRecipes();
        } else {
            recipes = recipeDAO.getRecipesByCategory(category);
        }

        // Inform user only when there is nothing to show
        if (recipes.isEmpty()) {
            Toast.makeText(this, "No recipes found in " + category + " category.",
                    Toast.LENGTH_SHORT).show();
        }

        // Create adapter with click listener to open detail screen
        adapter = new RecipeAdapter(this, recipes, recipe -> {
            Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
            intent.putExtra("recipe_id", recipe.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecipes(); // Refresh list when returning from detail (in case of delete/edit)
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Returns the hex color for each category's toolbar.
     */
    private String getCategoryColor(String category) {
        switch (category) {
            case "Breakfast": return "#FF9800";
            case "Lunch":     return "#4CAF50";
            case "Dinner":    return "#673AB7";
            case "Snacks":    return "#009688";
            case "Desserts":  return "#E91E63";
            default:          return "#FF6B35";
        }
    }
}
