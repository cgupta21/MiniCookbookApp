package com.cookbook.miniapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DatabaseHelper - Manages the SQLite database for Mini Cookbook App.
 * Creates the recipes table and pre-populates 15 authentic Indian recipes.
 * Uses Singleton pattern to ensure only one database connection exists.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database info
    private static final String DB_NAME = "cookbook.db";
    private static final int DB_VERSION = 1;

    // Table name
    public static final String TABLE_RECIPES = "recipes";

    // Column names
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_CATEGORY = "category";
    public static final String COL_INGREDIENTS = "ingredients";
    public static final String COL_STEPS = "steps";
    public static final String COL_COOK_TIME = "cook_time";
    public static final String COL_SERVINGS = "servings";
    public static final String COL_IS_FAVORITE = "is_favorite";
    public static final String COL_CREATED_AT = "created_at";

    // Singleton instance
    private static DatabaseHelper instance;

    // Private constructor for singleton
    private DatabaseHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }

    /**
     * Get the singleton instance of DatabaseHelper.
     * @param context Application context
     * @return DatabaseHelper instance
     */
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the recipes table
        String CREATE_TABLE = "CREATE TABLE " + TABLE_RECIPES + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT NOT NULL, "
                + COL_CATEGORY + " TEXT NOT NULL, "
                + COL_INGREDIENTS + " TEXT, "
                + COL_STEPS + " TEXT, "
                + COL_COOK_TIME + " INTEGER DEFAULT 0, "
                + COL_SERVINGS + " INTEGER DEFAULT 1, "
                + COL_IS_FAVORITE + " INTEGER DEFAULT 0, "
                + COL_CREATED_AT + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);

        // Insert all 15 Indian sample recipes
        insertSampleRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table and recreate
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    /**
     * Helper method to insert a single sample recipe into the database.
     */
    private void insertRecipe(SQLiteDatabase db, String title, String category,
                               String ingredients, String steps, int cookTime, int servings) {
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, title);
        values.put(COL_CATEGORY, category);
        values.put(COL_INGREDIENTS, ingredients);
        values.put(COL_STEPS, steps);
        values.put(COL_COOK_TIME, cookTime);
        values.put(COL_SERVINGS, servings);
        values.put(COL_IS_FAVORITE, 0);
        values.put(COL_CREATED_AT, "2024-01-01");
        db.insert(TABLE_RECIPES, null, values);
    }

    /**
     * Pre-populate the database with 15 authentic Indian recipes.
     * Categories: Breakfast(3), Lunch(3), Dinner(3), Snacks(3), Desserts(3)
     */
    private void insertSampleRecipes(SQLiteDatabase db) {

        // ===== BREAKFAST RECIPES =====

        insertRecipe(db, "Masala Dosa", "Breakfast",
                "2 cups Rice\n1/2 cup Urad Dal\n3 Potatoes (boiled)\n1 Onion\n2 Green Chillies\n1 tsp Mustard Seeds\nCurry Leaves\n1/2 tsp Turmeric\n2 tbsp Oil\nSalt to taste",
                "1. Soak rice and dal separately for 6 hours.\n2. Grind into smooth batter and ferment overnight.\n3. For filling: heat oil, add mustard seeds and curry leaves.\n4. Add onion and green chillies, saute till golden.\n5. Add potatoes, turmeric and salt. Mix well.\n6. Heat tawa, pour dosa batter and spread thin.\n7. Add filling in center, fold and serve hot with coconut chutney.",
                45, 2);

        insertRecipe(db, "Poha", "Breakfast",
                "2 cups Flattened Rice (Poha)\n1 Onion (chopped)\n1 Potato (diced)\n2 Green Chillies\n1 tsp Mustard Seeds\nCurry Leaves\n1/2 tsp Turmeric\n2 tbsp Peanuts\nJuice of 1 Lemon\nFresh Coriander\nSalt to taste",
                "1. Rinse poha in water and drain. Keep aside.\n2. Heat oil, add mustard seeds and let them splutter.\n3. Add peanuts and fry till golden.\n4. Add curry leaves, onion and green chillies. Saute 2 mins.\n5. Add diced potato and cook till tender.\n6. Add turmeric and salt, mix well.\n7. Add poha, mix gently. Cook 2 minutes.\n8. Squeeze lemon juice, garnish with coriander and serve hot.",
                15, 2);

        insertRecipe(db, "Aloo Paratha", "Breakfast",
                "2 cups Wheat Flour\n3 Potatoes (boiled, mashed)\n2 Green Chillies (chopped)\n2 tbsp Fresh Coriander\n1/2 tsp Garam Masala\n1/2 tsp Red Chilli Powder\nButter for serving\nSalt to taste",
                "1. Mix wheat flour with water, knead into soft dough. Rest 20 mins.\n2. Mix mashed potatoes with chillies, coriander, spices and salt.\n3. Divide dough into balls and flatten each ball.\n4. Place potato filling in center, seal dough around it.\n5. Roll gently into round paratha shape.\n6. Cook on hot tawa with oil/ghee until golden on both sides.\n7. Serve hot with butter, curd and pickle.",
                30, 3);

        // ===== LUNCH RECIPES =====

        insertRecipe(db, "Dal Tadka", "Lunch",
                "1 cup Yellow Lentils (Toor Dal)\n1 Onion (chopped)\n2 Tomatoes (chopped)\n4 Garlic Cloves\n2 Green Chillies\n1 tsp Cumin Seeds\n1 tsp Mustard Seeds\n2 tbsp Ghee\n1/2 tsp Turmeric\n1 tsp Red Chilli Powder\nFresh Coriander\nSalt to taste",
                "1. Pressure cook dal with turmeric and salt for 3 whistles.\n2. Mash dal lightly and keep aside.\n3. Heat ghee in pan, add mustard and cumin seeds.\n4. Add garlic and fry till golden brown.\n5. Add onion and cook till light brown.\n6. Add tomatoes and cook till soft and mushy.\n7. Add red chilli powder and mix well.\n8. Pour this tadka over dal, garnish with coriander. Serve with rice.",
                30, 4);

        insertRecipe(db, "Chole Bhature", "Lunch",
                "2 cups Chickpeas (soaked overnight)\n2 Onions\n3 Tomatoes\n1 inch Ginger\n5 Garlic Cloves\n2 tbsp Chole Masala\n2 cups Maida (All Purpose Flour)\n1/2 cup Yogurt\nOil for frying\nSalt to taste",
                "1. Pressure cook chickpeas with salt for 5-6 whistles.\n2. Grind onion, tomato, ginger, garlic into smooth paste.\n3. Heat oil, add the paste and cook for 10 minutes.\n4. Add chole masala and cooked chickpeas. Mix well.\n5. Simmer 15 minutes till gravy thickens.\n6. For bhature: mix maida, yogurt, salt into soft dough. Rest 2 hours.\n7. Roll into oval shapes and deep fry till puffed and golden.\n8. Serve hot chole with bhature and sliced onions.",
                60, 4);

        insertRecipe(db, "Paneer Butter Masala", "Lunch",
                "250g Paneer (cubed)\n2 tbsp Butter\n2 Onions\n4 Tomatoes\n10 Cashews\n1/4 cup Fresh Cream\n1 tsp Garam Masala\n1 tsp Kasuri Methi\n2 Cardamom Pods\n1 tsp Red Chilli Powder\nSalt to taste",
                "1. Boil onion, tomato, cashew and cardamom together. Cool and blend smooth.\n2. Heat butter in pan, add the blended paste.\n3. Cook on medium heat for 10 minutes, stirring constantly.\n4. Add garam masala, red chilli powder and salt.\n5. Add paneer cubes and mix gently.\n6. Pour in cream and add crushed kasuri methi.\n7. Simmer 5 minutes till gravy is rich and creamy.\n8. Garnish with cream swirl and serve with naan or rice.",
                35, 3);

        // ===== DINNER RECIPES =====

        insertRecipe(db, "Chicken Biryani", "Dinner",
                "500g Chicken\n2 cups Basmati Rice\n2 Onions (sliced)\n1 cup Yogurt\n2 tbsp Biryani Masala\nPinch of Saffron\n3 tbsp Ghee\nFresh Mint Leaves\nFresh Coriander\nWhole Spices (bay leaf, cloves, cardamom)\nSalt to taste",
                "1. Marinate chicken with yogurt, biryani masala and salt for 1 hour.\n2. Fry sliced onions in ghee till golden brown. Set aside.\n3. Cook rice 70% with whole spices and salt. Drain water.\n4. Cook marinated chicken till half done in the same pot.\n5. Layer: chicken at bottom, then rice, then fried onions, mint and coriander.\n6. Dissolve saffron in warm milk, drizzle over the rice layer.\n7. Seal pot tightly and cook on very low heat for 30 minutes (dum cooking).\n8. Gently mix before serving. Serve hot with raita and salad.",
                90, 4);

        insertRecipe(db, "Butter Chicken", "Dinner",
                "500g Chicken\n3 tbsp Butter\n400g Tomato Puree\n1/2 cup Fresh Cream\n1 tbsp Ginger-Garlic Paste\n1 tsp Garam Masala\n1 tsp Kasuri Methi\n1 tsp Honey\n1 tsp Red Chilli Powder\nSalt to taste",
                "1. Marinate chicken with ginger-garlic paste and spices. Grill or pan-fry till cooked.\n2. Heat butter in a pan, add ginger-garlic paste and fry 1 minute.\n3. Add tomato puree and cook 15 minutes till oil separates.\n4. Add red chilli powder, garam masala and salt.\n5. Add cooked chicken pieces and mix well.\n6. Pour in cream and add crushed kasuri methi.\n7. Add honey for slight sweetness. Mix gently.\n8. Simmer 10 minutes. Serve hot with naan or butter rice.",
                45, 4);

        insertRecipe(db, "Dal Makhani", "Dinner",
                "1 cup Black Lentils (Urad Dal)\n1/4 cup Kidney Beans (Rajma)\n2 tbsp Butter\n2 tbsp Fresh Cream\n2 Onions\n3 Tomatoes\n1 tbsp Ginger-Garlic Paste\n1 tsp Garam Masala\n1 tsp Red Chilli Powder\nSalt to taste",
                "1. Soak dal and rajma overnight. Pressure cook for 8-10 whistles.\n2. Heat butter, fry ginger-garlic paste for 1 minute.\n3. Add onion and cook till golden brown.\n4. Add tomatoes and cook till completely mushy.\n5. Add spices and the cooked dal-rajma mixture.\n6. Mash slightly and simmer on low heat for 30 minutes.\n7. Add cream and a small piece of butter, stir gently.\n8. Serve hot with butter on top. Best with roti or rice.",
                60, 4);

        // ===== SNACKS RECIPES =====

        insertRecipe(db, "Samosa", "Snacks",
                "2 cups Maida (All Purpose Flour)\n4 Potatoes (boiled, mashed)\n1/2 cup Green Peas\n1 tsp Cumin Seeds\n1 tsp Coriander Powder\n1/2 tsp Garam Masala\n2 Green Chillies (chopped)\n2 tbsp Oil (for dough)\nOil for deep frying\nSalt to taste",
                "1. Mix maida with oil and salt. Add water gradually to make stiff dough. Rest 30 mins.\n2. Mix mashed potatoes with peas, cumin, all spices and green chillies.\n3. Divide dough into balls. Roll each into oval shape. Cut in half.\n4. Form each half into a cone shape.\n5. Fill cone with the potato-pea mixture.\n6. Seal edges firmly with water-flour paste.\n7. Deep fry on medium heat till golden and crispy all over.\n8. Serve hot with mint chutney and tamarind sauce.",
                45, 8);

        insertRecipe(db, "Onion Pakora", "Snacks",
                "1 cup Gram Flour (Besan)\n2 Onions (sliced thin)\n2 Potatoes (sliced thin)\n2 Green Chillies (chopped)\n1/2 tsp Ajwain (Carom Seeds)\n1 tsp Red Chilli Powder\n1/2 tsp Turmeric\nOil for deep frying\nSalt to taste",
                "1. Mix gram flour with all spices, ajwain and salt.\n2. Add just enough water to make a thick batter.\n3. Add sliced onions, potatoes and green chillies into the batter.\n4. Mix well so all vegetables are well coated.\n5. Heat oil in a deep pan for frying.\n6. Drop spoonfuls of the vegetable batter into hot oil.\n7. Fry on medium heat till golden and crispy.\n8. Drain on paper towel. Serve hot with chutney and masala chai.",
                20, 4);

        insertRecipe(db, "Pani Puri", "Snacks",
                "1 cup Semolina (Sooji)\n2 tbsp All Purpose Flour\n2 tbsp Tamarind\n1 cup Mint Leaves\n1 cup Coriander\n4 Green Chillies\n1 tsp Cumin Powder\n1 tsp Black Salt\n2 Potatoes (boiled)\n1/2 cup Chickpeas (boiled)\nOil for frying",
                "1. Mix semolina, flour and salt with water. Knead firm dough. Rest 20 mins.\n2. Roll very thin and cut into small circles.\n3. Deep fry till puffed up and crispy. Drain and cool.\n4. For pani: blend mint, coriander, chillies with water.\n5. Add cumin powder, black salt and tamarind pulp. Chill the pani.\n6. For filling: mix mashed potato with chickpeas, salt and cumin.\n7. Make a small hole on top of each puri.\n8. Fill with potato mixture, pour chilled pani and eat immediately!",
                40, 4);

        // ===== DESSERT RECIPES =====

        insertRecipe(db, "Gulab Jamun", "Desserts",
                "1 cup Milk Powder\n3 tbsp Maida\n1 tbsp Ghee\n3 tbsp Warm Milk\n2 cups Sugar\n1.5 cups Water\n4 Cardamom Pods\nPinch of Saffron\n1 tsp Rose Water\nOil for frying",
                "1. Make sugar syrup: boil water and sugar with cardamom and saffron. Add rose water. Set aside.\n2. Mix milk powder, maida and ghee together until crumbly.\n3. Add warm milk slowly to make a soft, smooth dough.\n4. Shape into small smooth balls - ensure no cracks on surface.\n5. Heat oil on low-medium flame (not too hot).\n6. Fry balls slowly, turning gently till dark golden-brown all over.\n7. Immediately drop hot fried balls into warm sugar syrup.\n8. Soak at least 30 minutes before serving. Serve warm or chilled.",
                40, 6);

        insertRecipe(db, "Rice Kheer", "Desserts",
                "1 litre Full Fat Milk\n3 tbsp Basmati Rice\n5 tbsp Sugar\n4 Cardamom Pods (crushed)\n10 Cashews\n2 tbsp Raisins\nPinch of Saffron\n2 tbsp Pistachios (sliced)",
                "1. Wash and soak rice for 15 minutes. Drain.\n2. Bring milk to boil in heavy-bottomed pan on medium heat.\n3. Add soaked rice and stir frequently to prevent sticking.\n4. Cook on low heat for 25-30 minutes, stirring, till milk thickens.\n5. Add sugar and crushed cardamom, stir well.\n6. Dissolve saffron in 1 tbsp warm milk, add to kheer.\n7. Add cashews and raisins. Mix gently.\n8. Serve warm or chilled, garnished with sliced pistachios.",
                45, 4);

        insertRecipe(db, "Gajar Halwa", "Desserts",
                "500g Carrots (grated)\n1 litre Full Fat Milk\n5 tbsp Sugar\n3 tbsp Ghee\n1/2 tsp Cardamom Powder\n10 Cashews\n10 Raisins\n2 tbsp Khoya (optional)",
                "1. Heat ghee in a heavy pan, add grated carrots.\n2. Saute carrots on medium heat for 5-7 minutes.\n3. Add milk and cook on medium heat, stirring frequently.\n4. Cook until all the milk is completely absorbed (about 25-30 mins).\n5. Add sugar and mix well. Cook 5 more minutes.\n6. Add khoya if using, and mix thoroughly.\n7. Add cardamom powder, cashews and raisins.\n8. Cook till halwa leaves the sides of pan. Serve warm garnished with nuts.",
                50, 4);
    }
}
