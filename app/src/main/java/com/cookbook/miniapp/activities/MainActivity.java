package com.cookbook.miniapp.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.cookbook.miniapp.R;
import com.cookbook.miniapp.fragments.FavoritesFragment;
import com.cookbook.miniapp.fragments.HomeFragment;
import com.cookbook.miniapp.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * MainActivity - The main navigation hub of the Mini Cookbook app.
 * Contains BottomNavigationView with three tabs: Home, Search, Favorites.
 * A FloatingActionButton (FAB) allows adding new recipes from any tab.
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabAddRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fabAddRecipe = findViewById(R.id.fabAddRecipe);

        // Load the Home fragment as the default screen (only on first launch,
        // so rotating the phone does not reset the selected tab)
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        // Handle bottom navigation tab selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_search) {
                selectedFragment = new SearchFragment();
            } else if (itemId == R.id.nav_favorites) {
                selectedFragment = new FavoritesFragment();
            } else {
                selectedFragment = new HomeFragment();
            }
            return loadFragment(selectedFragment);
        });

        // FAB opens AddEditActivity in "add" mode
        fabAddRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            intent.putExtra("mode", "add");
            startActivity(intent);
        });
    }

    /**
     * Loads a fragment into the main container layout.
     * @param fragment The Fragment to display
     * @return true if loaded successfully
     */
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
