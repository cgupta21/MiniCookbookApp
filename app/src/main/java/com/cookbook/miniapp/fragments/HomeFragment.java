package com.cookbook.miniapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cookbook.miniapp.R;
import com.cookbook.miniapp.activities.RecipeListActivity;
import com.cookbook.miniapp.adapters.CategoryAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * HomeFragment - The main landing screen showing recipe categories as a 2-column grid.
 * Each category card has a unique color and emoji icon.
 * Clicking a category opens RecipeListActivity for that category.
 */
public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerCategories = view.findViewById(R.id.recyclerCategories);
        // 2-column grid layout for category cards
        recyclerCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Define categories with name, emoji icon, and unique color
        List<String[]> categories = new ArrayList<>();
        categories.add(new String[]{"Breakfast", "\ud83c\udf05", "#FF9800"}); // Orange
        categories.add(new String[]{"Lunch",     "\ud83c\udf5b", "#4CAF50"}); // Green
        categories.add(new String[]{"Dinner",    "\ud83c\udf7d\ufe0f", "#673AB7"}); // Purple
        categories.add(new String[]{"Snacks",    "\ud83e\udd59", "#009688"}); // Teal
        categories.add(new String[]{"Desserts",  "\ud83c\udf6e", "#E91E63"}); // Pink

        // Create adapter with click listener
        CategoryAdapter adapter = new CategoryAdapter(getContext(), categories, categoryData -> {
            String categoryName = categoryData[0];
            Toast.makeText(getContext(),
                    "Opening " + categoryName + " recipes...", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(), RecipeListActivity.class);
            intent.putExtra("category", categoryName);
            startActivity(intent);
        });

        recyclerCategories.setAdapter(adapter);
        return view;
    }
}
