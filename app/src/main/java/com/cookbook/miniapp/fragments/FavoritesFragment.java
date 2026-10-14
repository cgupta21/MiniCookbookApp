package com.cookbook.miniapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cookbook.miniapp.R;
import com.cookbook.miniapp.activities.RecipeDetailActivity;
import com.cookbook.miniapp.adapters.RecipeAdapter;
import com.cookbook.miniapp.database.RecipeDAO;
import com.cookbook.miniapp.models.Recipe;
import java.util.List;

/**
 * FavoritesFragment - Displays all recipes the user has marked as favorite.
 * Shows an empty state message when no favorites are set.
 * Refreshes automatically whenever the user navigates to this tab.
 */
public class FavoritesFragment extends Fragment {

    private RecipeDAO recipeDAO;
    private RecipeAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tvEmptyFavorites;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recipeDAO = new RecipeDAO(getContext());
        recyclerView      = view.findViewById(R.id.recyclerFavorites);
        tvEmptyFavorites  = view.findViewById(R.id.tvEmptyFavorites);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadFavorites();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites(); // Refresh when user comes back to this tab
    }

    /**
     * Loads favorite recipes from database and updates the UI.
     * Shows empty state message if no favorites exist.
     */
    private void loadFavorites() {
        List<Recipe> favorites = recipeDAO.getFavoriteRecipes();

        if (favorites.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmptyFavorites.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyFavorites.setVisibility(View.GONE);
        }

        // Create adapter with click listener
        adapter = new RecipeAdapter(getContext(), favorites, recipe -> {
            Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
            intent.putExtra("recipe_id", recipe.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }
}
