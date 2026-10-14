package com.cookbook.miniapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.cookbook.miniapp.R;
import java.util.List;

/**
 * CategoryAdapter - RecyclerView adapter for displaying food categories as colorful cards.
 * Each card shows a large emoji icon and the category name on a colored background.
 * The color is unique to each category for easy visual identification.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final Context context;
    // Each String[] = { categoryName, emoji, colorHex }
    private final List<String[]> categoryList;
    private final OnCategoryClickListener listener;

    // Interface for category selection callbacks
    public interface OnCategoryClickListener {
        void onCategoryClick(String[] categoryData);
    }

    public CategoryAdapter(Context context, List<String[]> categoryList, OnCategoryClickListener listener) {
        this.context = context;
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_card, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String[] categoryData = categoryList.get(position);
        String name  = categoryData[0];
        String emoji = categoryData[1];
        String color = categoryData[2];

        // Set category name and emoji
        holder.tvCategoryName.setText(name);
        holder.tvCategoryEmoji.setText(emoji);

        // Set colorful background for this category
        holder.categoryBackground.setBackgroundColor(Color.parseColor(color));

        // Handle click - notify host fragment
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoryClick(categoryData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList != null ? categoryList.size() : 0;
    }

    /**
     * ViewHolder class holding references to category card views.
     */
    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName, tvCategoryEmoji;
        LinearLayout categoryBackground;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvCategoryEmoji = itemView.findViewById(R.id.tvCategoryEmoji);
            categoryBackground = itemView.findViewById(R.id.categoryCardBackground);
        }
    }
}
