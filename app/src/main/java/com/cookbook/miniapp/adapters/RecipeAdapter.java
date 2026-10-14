package com.cookbook.miniapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.cookbook.miniapp.R;
import com.cookbook.miniapp.models.Recipe;
import com.cookbook.miniapp.utils.RecipeImageHelper;
import java.util.List;

/**
 * RecipeAdapter - RecyclerView adapter for displaying a list of recipes.
 * Each card shows title, image, category badge, cook time, servings and favorite star.
 * Color-coded left border indicates the recipe category.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final Context context;
    private final List<Recipe> recipeList;
    private final OnRecipeClickListener listener;

    // Interface for click events
    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public RecipeAdapter(Context context, List<Recipe> recipeList, OnRecipeClickListener listener) {
        this.context = context;
        this.recipeList = recipeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe_card, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        String categoryColor = getCategoryColor(recipe.getCategory());

        // Set recipe title
        holder.tvTitle.setText(recipe.getTitle());

        // Load recipe food image safely
        int imageRes = RecipeImageHelper.getRecipeImageResource(context, recipe.getTitle());
        if (imageRes != 0) {
            holder.cardRecipeImage.setVisibility(View.VISIBLE);
            holder.imgRecipeCard.setImageResource(imageRes);
        } else {
            holder.cardRecipeImage.setVisibility(View.GONE);
        }

        // Set category badge with matching color
        holder.tvCategory.setText(recipe.getCategory());
        Drawable badge = holder.tvCategory.getBackground().mutate();
        if (badge instanceof GradientDrawable) {
            ((GradientDrawable) badge).setColor(Color.parseColor(categoryColor)); // keeps rounded corners
        } else {
            holder.tvCategory.setBackgroundColor(Color.parseColor(categoryColor));
        }

        // Set cook time and servings info
        holder.tvCookTime.setText("\u23f1 " + recipe.getCookTime() + " mins");
        holder.tvServings.setText("\ud83d\udc65 " + recipe.getServings() + " servings");

        // Show favorite star if marked as favorite
        if (recipe.isFavorite()) {
            holder.tvFavoriteIcon.setVisibility(View.VISIBLE);
        } else {
            holder.tvFavoriteIcon.setVisibility(View.INVISIBLE);
        }

        // Color the left accent strip by category
        holder.viewCategoryStrip.setBackgroundColor(Color.parseColor(categoryColor));

        // Set click listener - opens recipe detail
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRecipeClick(recipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList != null ? recipeList.size() : 0;
    }

    /**
     * Returns the hex color string for each food category.
     * Used to color-code recipe cards and badges.
     */
    private String getCategoryColor(String category) {
        if (category == null) return "#FF6B35";
        switch (category) {
            case "Breakfast": return "#FF9800";
            case "Lunch":     return "#4CAF50";
            case "Dinner":    return "#673AB7";
            case "Snacks":    return "#009688";
            case "Desserts":  return "#E91E63";
            default:          return "#FF6B35";
        }
    }

    /**
     * ViewHolder class holding references to all views in the recipe card.
     */
    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCategory, tvCookTime, tvServings, tvFavoriteIcon;
        View viewCategoryStrip;
        ImageView imgRecipeCard;
        CardView cardRecipeImage;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvRecipeTitle);
            tvCategory = itemView.findViewById(R.id.tvRecipeCategory);
            tvCookTime = itemView.findViewById(R.id.tvCookTime);
            tvServings = itemView.findViewById(R.id.tvServings);
            tvFavoriteIcon = itemView.findViewById(R.id.tvFavoriteIcon);
            viewCategoryStrip = itemView.findViewById(R.id.viewCategoryStrip);
            imgRecipeCard = itemView.findViewById(R.id.imgRecipeCard);
            cardRecipeImage = itemView.findViewById(R.id.cardRecipeImage);
        }
    }
}
