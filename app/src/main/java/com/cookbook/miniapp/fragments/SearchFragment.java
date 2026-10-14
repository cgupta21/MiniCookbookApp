package com.cookbook.miniapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.List;

/**
 * SearchFragment - Allows real-time searching of recipes by name or ingredient.
 * Results update instantly as the user types in the search box.
 * Supports partial matching on both recipe title and ingredients list.
 */
public class SearchFragment extends Fragment {

    private RecipeDAO recipeDAO;
    private RecipeAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tvResultCount;
    private List<Recipe> searchResults;
    private EditText etSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchResults = new ArrayList<>();
        recipeDAO = new RecipeDAO(getContext());
        recyclerView   = view.findViewById(R.id.recyclerSearchResults);
        tvResultCount  = view.findViewById(R.id.tvResultCount);
        etSearch = view.findViewById(R.id.etSearchRecipe);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Setup adapter (initially empty)
        adapter = new RecipeAdapter(getContext(), searchResults, recipe -> {
            Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
            intent.putExtra("recipe_id", recipe.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // Real-time search: update results as user types each character
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString().trim());
            }
        });

        Toast.makeText(getContext(),
                "Search by recipe name or ingredient", Toast.LENGTH_SHORT).show();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Re-run the current search so favourites/edits/deletes made elsewhere show up
        if (etSearch != null) {
            performSearch(etSearch.getText().toString().trim());
        }
    }

    /**
     * Queries the database for matching recipes and updates the RecyclerView.
     * @param query The text entered by the user
     */
    private void performSearch(String query) {
        searchResults.clear();

        if (query.isEmpty()) {
            tvResultCount.setText("Type above to search all recipes...");
        } else {
            List<Recipe> results = recipeDAO.searchRecipes(query);
            searchResults.addAll(results);

            if (results.isEmpty()) {
                tvResultCount.setText("No recipes found for \"" + query + "\"");
            } else {
                tvResultCount.setText(results.size() + " recipe(s) found for \"" + query + "\"");
            }
        }
        adapter.notifyDataSetChanged();
    }
}
