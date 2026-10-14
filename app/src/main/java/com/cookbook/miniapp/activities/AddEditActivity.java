package com.cookbook.miniapp.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.cookbook.miniapp.R;
import com.cookbook.miniapp.database.RecipeDAO;
import com.cookbook.miniapp.models.Recipe;

/**
 * AddEditActivity - Screen to add a new recipe or edit an existing one.
 * Mode is set by Intent extra "mode": "add" (default) or "edit".
 * In edit mode, "recipe_id" extra is required to load the existing recipe.
 * Validates all inputs before saving to the database.
 */
public class AddEditActivity extends AppCompatActivity {

    private EditText etTitle, etIngredients, etSteps, etCookTime, etServings;
    private Spinner spinnerCategory;
    private Button btnSave;
    private RecipeDAO recipeDAO;
    private String mode;
    private int recipeId = -1;
    private Recipe existingRecipe;

    // Available food categories
    private final String[] categories = {"Breakfast", "Lunch", "Dinner", "Snacks", "Desserts"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        // Get mode from Intent ("add" or "edit")
        mode = getIntent().getStringExtra("mode");
        if (mode == null) mode = "add";

        // Setup toolbar with title based on mode
        Toolbar toolbar = findViewById(R.id.toolbarAddEdit);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(mode.equals("edit") ? "Edit Recipe" : "Add New Recipe");
        }

        // Initialize all form views
        etTitle        = findViewById(R.id.etRecipeTitle);
        etIngredients  = findViewById(R.id.etIngredients);
        etSteps        = findViewById(R.id.etSteps);
        etCookTime     = findViewById(R.id.etCookTime);
        etServings     = findViewById(R.id.etServings);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSave        = findViewById(R.id.btnSaveRecipe);

        recipeDAO = new RecipeDAO(this);

        // Setup category spinner dropdown
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnerAdapter);

        // Load existing data in edit mode
        if (mode.equals("edit")) {
            recipeId = getIntent().getIntExtra("recipe_id", -1);
            if (recipeId != -1) {
                existingRecipe = recipeDAO.getRecipeById(recipeId);
            }
            if (existingRecipe == null) {
                // Nothing to edit - never silently turn "edit" into "add"
                Toast.makeText(this, "Error: Recipe not found!", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            populateFormFields(existingRecipe);
            Toast.makeText(this, "Editing: " + existingRecipe.getTitle(),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Fill in the recipe details below", Toast.LENGTH_SHORT).show();
        }

        // Save button click handler
        btnSave.setOnClickListener(v -> saveRecipe());
    }

    /**
     * Populates form fields with existing recipe data (edit mode).
     * @param recipe The recipe to populate form with
     */
    private void populateFormFields(Recipe recipe) {
        etTitle.setText(recipe.getTitle());
        etIngredients.setText(recipe.getIngredients());
        etSteps.setText(recipe.getSteps());
        etCookTime.setText(String.valueOf(recipe.getCookTime()));
        etServings.setText(String.valueOf(recipe.getServings()));

        // Set the spinner to the recipe's current category
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equals(recipe.getCategory())) {
                spinnerCategory.setSelection(i);
                break;
            }
        }
    }

    /**
     * Validates form inputs and saves/updates the recipe to the database.
     * Shows Toast messages for all validation errors and success/failure.
     */
    private void saveRecipe() {
        String title       = etTitle.getText().toString().trim();
        String ingredients = etIngredients.getText().toString().trim();
        String steps       = etSteps.getText().toString().trim();
        String cookTimeStr = etCookTime.getText().toString().trim();
        String servingsStr = etServings.getText().toString().trim();
        String category    = spinnerCategory.getSelectedItem().toString();

        // Input validation
        if (title.isEmpty()) {
            etTitle.setError("Recipe name is required!");
            etTitle.requestFocus();
            Toast.makeText(this, "Please enter a recipe name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ingredients.isEmpty()) {
            etIngredients.setError("Please list the ingredients!");
            etIngredients.requestFocus();
            Toast.makeText(this, "Please enter ingredients", Toast.LENGTH_SHORT).show();
            return;
        }
        if (steps.isEmpty()) {
            etSteps.setError("Please add cooking steps!");
            etSteps.requestFocus();
            Toast.makeText(this, "Please enter the cooking steps", Toast.LENGTH_SHORT).show();
            return;
        }

        int cookTime = 0, servings = 1;
        try {
            if (!cookTimeStr.isEmpty()) cookTime = Integer.parseInt(cookTimeStr);
            if (!servingsStr.isEmpty()) servings  = Integer.parseInt(servingsStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers for cook time and servings",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (mode.equals("edit") && existingRecipe != null) {
            // UPDATE existing recipe
            existingRecipe.setTitle(title);
            existingRecipe.setCategory(category);
            existingRecipe.setIngredients(ingredients);
            existingRecipe.setSteps(steps);
            existingRecipe.setCookTime(cookTime);
            existingRecipe.setServings(servings);

            int rowsUpdated = recipeDAO.updateRecipe(existingRecipe);
            if (rowsUpdated > 0) {
                Toast.makeText(this, "\"" + title + "\" updated successfully! \u2705",
                        Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Error updating recipe. Please try again.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // INSERT new recipe
            Recipe newRecipe = new Recipe(title, category, ingredients, steps, cookTime, servings);
            long newId = recipeDAO.addRecipe(newRecipe);
            if (newId > 0) {
                Toast.makeText(this, "\"" + title + "\" added to your cookbook! \ud83c\udf89",
                        Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Error saving recipe. Please try again.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
