package com.cookbook.miniapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cookbook.miniapp.models.Recipe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * RecipeDAO - Data Access Object for Recipe database operations.
 * Provides all CRUD operations: Create, Read, Update, Delete.
 * Also supports search and favorites filtering.
 */
public class RecipeDAO {

    private final Context context;

    public RecipeDAO(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Get all recipes from the database.
     * @return List of all Recipe objects
     */
    public List<Recipe> getAllRecipes() {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_RECIPES,
                null, null, null, null, null,
                DatabaseHelper.COL_TITLE + " ASC"
        );

        return readList(cursor);
    }

    /**
     * Get all recipes belonging to a specific category.
     * @param category The category name (e.g., "Breakfast", "Dinner")
     * @return List of matching Recipe objects
     */
    public List<Recipe> getRecipesByCategory(String category) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_RECIPES,
                null,
                DatabaseHelper.COL_CATEGORY + " = ?",
                new String[]{category},
                null, null,
                DatabaseHelper.COL_TITLE + " ASC"
        );

        return readList(cursor);
    }

    /**
     * Get a single recipe by its ID.
     * @param id The recipe's database ID
     * @return Recipe object, or null if not found
     */
    public Recipe getRecipeById(int id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_RECIPES,
                null,
                DatabaseHelper.COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        Recipe recipe = null;
        try {
            if (cursor != null && cursor.moveToFirst()) {
                recipe = cursorToRecipe(cursor);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return recipe;
    }

    /**
     * Get all recipes marked as favorite by the user.
     * @return List of favorite Recipe objects
     */
    public List<Recipe> getFavoriteRecipes() {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_RECIPES,
                null,
                DatabaseHelper.COL_IS_FAVORITE + " = 1",
                null, null, null,
                DatabaseHelper.COL_TITLE + " ASC"
        );

        return readList(cursor);
    }

    /**
     * Search recipes by title or ingredients (case-insensitive).
     * @param query The search term entered by user
     * @return List of matching Recipe objects
     */
    public List<Recipe> searchRecipes(String query) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();

        String searchPattern = "%" + query + "%";
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_RECIPES,
                null,
                DatabaseHelper.COL_TITLE + " LIKE ? OR " + DatabaseHelper.COL_INGREDIENTS + " LIKE ?",
                new String[]{searchPattern, searchPattern},
                null, null,
                DatabaseHelper.COL_TITLE + " ASC"
        );

        return readList(cursor);
    }

    /**
     * Add a new recipe to the database.
     * @param recipe The Recipe object to insert
     * @return The new row ID, or -1 if error occurred
     */
    public long addRecipe(Recipe recipe) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_TITLE, recipe.getTitle());
        values.put(DatabaseHelper.COL_CATEGORY, recipe.getCategory());
        values.put(DatabaseHelper.COL_INGREDIENTS, recipe.getIngredients());
        values.put(DatabaseHelper.COL_STEPS, recipe.getSteps());
        values.put(DatabaseHelper.COL_COOK_TIME, recipe.getCookTime());
        values.put(DatabaseHelper.COL_SERVINGS, recipe.getServings());
        values.put(DatabaseHelper.COL_IS_FAVORITE, recipe.isFavorite() ? 1 : 0);
        values.put(DatabaseHelper.COL_CREATED_AT, new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date()));

        return db.insert(DatabaseHelper.TABLE_RECIPES, null, values);
    }

    /**
     * Update an existing recipe in the database.
     * @param recipe The Recipe object with updated values (must have valid ID)
     * @return Number of rows affected (1 if successful, 0 if not found)
     */
    public int updateRecipe(Recipe recipe) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_TITLE, recipe.getTitle());
        values.put(DatabaseHelper.COL_CATEGORY, recipe.getCategory());
        values.put(DatabaseHelper.COL_INGREDIENTS, recipe.getIngredients());
        values.put(DatabaseHelper.COL_STEPS, recipe.getSteps());
        values.put(DatabaseHelper.COL_COOK_TIME, recipe.getCookTime());
        values.put(DatabaseHelper.COL_SERVINGS, recipe.getServings());
        values.put(DatabaseHelper.COL_IS_FAVORITE, recipe.isFavorite() ? 1 : 0);

        return db.update(
                DatabaseHelper.TABLE_RECIPES,
                values,
                DatabaseHelper.COL_ID + " = ?",
                new String[]{String.valueOf(recipe.getId())}
        );
    }

    /**
     * Delete a recipe from the database.
     * @param id The ID of the recipe to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteRecipe(int id) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();

        int rowsDeleted = db.delete(
                DatabaseHelper.TABLE_RECIPES,
                DatabaseHelper.COL_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        return rowsDeleted > 0;
    }

    /**
     * Toggle the favorite status of a recipe.
     * @param id The recipe ID
     * @param isFavorite true to mark as favorite, false to remove
     */
    public void toggleFavorite(int id, boolean isFavorite) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_IS_FAVORITE, isFavorite ? 1 : 0);

        db.update(
                DatabaseHelper.TABLE_RECIPES,
                values,
                DatabaseHelper.COL_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    /**
     * Reads every row of the cursor into a list and ALWAYS closes the cursor
     * (the old version leaked the cursor when the result was empty).
     */
    private List<Recipe> readList(Cursor cursor) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            while (cursor != null && cursor.moveToNext()) {
                recipes.add(cursorToRecipe(cursor));
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return recipes;
    }

    /**
     * Converts a database Cursor row into a Recipe object.
     * @param cursor A Cursor positioned at the row to read
     * @return Recipe object populated with cursor data
     */
    private Recipe cursorToRecipe(Cursor cursor) {
        Recipe recipe = new Recipe();
        recipe.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID)));
        recipe.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TITLE)));
        recipe.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CATEGORY)));
        recipe.setIngredients(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_INGREDIENTS)));
        recipe.setSteps(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_STEPS)));
        recipe.setCookTime(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_COOK_TIME)));
        recipe.setServings(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SERVINGS)));
        recipe.setFavorite(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_IS_FAVORITE)) == 1);
        recipe.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CREATED_AT)));
        return recipe;
    }
}
