package com.cookbook.miniapp.utils;

import android.content.Context;
import com.cookbook.miniapp.R;

/**
 * Helper class for mapping recipe titles to their corresponding drawable food images.
 * Provides explicit resource references for standard recipes and dynamic fallback
 * based on normalized titles. Returns 0 if an image is missing or unknown.
 */
public class RecipeImageHelper {

    /**
     * Returns the drawable resource ID for a given recipe title.
     *
     * @param context Application or Activity context
     * @param title The title of the recipe
     * @return Drawable resource ID or 0 if missing/unknown
     */
    public static int getRecipeImageResource(Context context, String title) {
        if (title == null || title.trim().isEmpty()) {
            return 0;
        }

        String normalized = title.trim().toLowerCase();

        // Explicit mapping for all available recipe drawables
        switch (normalized) {
            case "masala dosa":
                return R.drawable.masala_dosa;
            case "poha":
                return R.drawable.poha;
            case "aloo paratha":
                return R.drawable.aloo_paratha;
            case "dal tadka":
                return R.drawable.dal_tadka;
            case "chole bhature":
                return R.drawable.chole_bhature;
            case "paneer butter masala":
                return R.drawable.paneer_butter_masala;
            case "chicken biryani":
                return R.drawable.chicken_biryani;
            case "butter chicken":
                return R.drawable.butter_chicken;
            case "dal makhani":
                return R.drawable.dal_makhani;
            case "samosa":
                return R.drawable.samosa;
            case "onion pakora":
                return R.drawable.onion_pakora;
            case "pani puri":
                return R.drawable.pani_puri;
            case "gulab jamun":
                return R.drawable.gulab_jamun;
            case "rice kheer":
                return R.drawable.rice_kheer;
            case "gajar halwa":
                return R.drawable.gajar_halwa;
        }

        // Dynamic fallback: convert title (e.g. "Paneer Butter Masala") to resource name ("paneer_butter_masala")
        String resourceName = normalized.replaceAll("[^a-z0-9_]", "_").replaceAll("_+", "_");
        if (context != null) {
            try {
                int resId = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
                if (resId != 0) {
                    return resId;
                }
            } catch (Exception ignored) {
            }
        }

        return 0; // Unknown or missing image
    }
}
