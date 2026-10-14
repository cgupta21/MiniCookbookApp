package com.cookbook.miniapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import com.cookbook.miniapp.R;
import com.cookbook.miniapp.database.RecipeDAO;
import com.cookbook.miniapp.models.Recipe;
import com.cookbook.miniapp.utils.RecipeImageHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * RecipeDetailActivity - Shows complete details of a selected recipe.
 * Displays: recipe name, food image, category, cook time, servings, ingredients, cooking steps.
 * Allows user to: mark/unmark as favorite (FAB), edit recipe (menu), delete recipe (menu).
 */
public class RecipeDetailActivity extends AppCompatActivity {

    private Recipe currentRecipe;
    private RecipeDAO recipeDAO;
    private FloatingActionButton fabFavorite;
    private TextView tvTitle, tvCategory, tvCookTime, tvServings;
    private TextView tvIngredients, tvSteps;
    private ImageView imgRecipe;
    private CardView cardDetailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Get recipe ID from Intent
        int recipeId = getIntent().getIntExtra("recipe_id", -1);
        if (recipeId == -1) {
            Toast.makeText(this, "Error: Recipe not found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load recipe from database
        recipeDAO = new RecipeDAO(this);
        currentRecipe = recipeDAO.getRecipeById(recipeId);

        if (currentRecipe == null) {
            Toast.makeText(this, "Error: Recipe data is missing!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(currentRecipe.getTitle());
        }
        toolbar.setBackgroundColor(Color.parseColor(getCategoryColor(currentRecipe.getCategory())));
        findViewById(R.id.detailHeader).setBackgroundColor(
                Color.parseColor(getCategoryColor(currentRecipe.getCategory())));

        // Initialize views
        tvTitle         = findViewById(R.id.tvDetailTitle);
        tvCategory      = findViewById(R.id.tvDetailCategory);
        tvCookTime      = findViewById(R.id.tvDetailCookTime);
        tvServings      = findViewById(R.id.tvDetailServings);
        tvIngredients   = findViewById(R.id.tvDetailIngredients);
        tvSteps         = findViewById(R.id.tvDetailSteps);
        imgRecipe       = findViewById(R.id.imgDetailRecipe);
        cardDetailImage = findViewById(R.id.cardDetailImage);
        fabFavorite     = findViewById(R.id.fabFavorite);

        // Populate all recipe details
        displayRecipeData();

        // FAB toggles favorite status
        fabFavorite.setOnClickListener(v -> toggleFavorite());
    }

    /**
     * Fills all views with the current recipe's data.
     */
    private void displayRecipeData() {
        tvTitle.setText(currentRecipe.getTitle());
        tvCategory.setText(currentRecipe.getCategory());
        tvCookTime.setText("\u23f1 " + currentRecipe.getCookTime() + " minutes");
        tvServings.setText("\ud83d\udc65 Serves " + currentRecipe.getServings());
        tvIngredients.setText(currentRecipe.getIngredients());
        tvSteps.setText(currentRecipe.getSteps());

        // Load food image safely
        int imageRes = RecipeImageHelper.getRecipeImageResource(this, currentRecipe.getTitle());
        if (imageRes != 0) {
            cardDetailImage.setVisibility(View.VISIBLE);
            imgRecipe.setImageResource(imageRes);
        } else {
            cardDetailImage.setVisibility(View.GONE);
        }

        // Color category badge to match its category
        tvCategory.setBackgroundColor(Color.parseColor(getCategoryColor(currentRecipe.getCategory())));

        // Update favorite button icon
        updateFavoriteButton();
    }

    /**
     * Updates the FAB icon based on current favorite status.
     */
    private void updateFavoriteButton() {
        if (currentRecipe.isFavorite()) {
            fabFavorite.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            fabFavorite.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    /**
     * Toggles the recipe's favorite status and updates the database.
     */
    private void toggleFavorite() {
        boolean newState = !currentRecipe.isFavorite();
        currentRecipe.setFavorite(newState);
        recipeDAO.toggleFavorite(currentRecipe.getId(), newState);
        updateFavoriteButton();

        if (newState) {
            Toast.makeText(this, "\u2b50 " + currentRecipe.getTitle() + " added to Favorites!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, currentRecipe.getTitle() + " removed from Favorites",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_edit) {
            // Open AddEditActivity in edit mode
            Intent intent = new Intent(this, AddEditActivity.class);
            intent.putExtra("mode", "edit");
            intent.putExtra("recipe_id", currentRecipe.getId());
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.action_share) {
            shareRecipe();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            confirmDeleteDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Shares the recipe as plain text using an implicit intent (ACTION_SEND),
     * so the user can send it through WhatsApp, Messages, Email, etc.
     */
    private void shareRecipe() {
        String text = currentRecipe.getTitle() + " (" + currentRecipe.getCategory() + ")\n"
                + "Cook time: " + currentRecipe.getCookTime() + " mins | Serves "
                + currentRecipe.getServings() + "\n\n"
                + "INGREDIENTS\n" + currentRecipe.getIngredients() + "\n\n"
                + "STEPS\n" + currentRecipe.getSteps() + "\n\n"
                + "- Shared from Mini Cookbook";
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_SUBJECT, currentRecipe.getTitle());
        send.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(send, "Share recipe via"));
    }

    /**
     * Shows a confirmation dialog before deleting the recipe.
     */
    private void confirmDeleteDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Recipe")
                .setMessage("Are you sure you want to delete \"" + currentRecipe.getTitle() + "\"?\nThis cannot be undone.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    boolean deleted = recipeDAO.deleteRecipe(currentRecipe.getId());
                    if (deleted) {
                        Toast.makeText(this,
                                "\"" + currentRecipe.getTitle() + "\" deleted successfully.",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Error deleting recipe. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data in case recipe was edited
        if (currentRecipe != null) {
            Recipe refreshed = recipeDAO.getRecipeById(currentRecipe.getId());
            if (refreshed != null) {
                currentRecipe = refreshed;
                displayRecipeData();
            }
        }
    }

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
