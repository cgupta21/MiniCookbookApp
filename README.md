MiniCookbookApp
An Android application for browsing, searching, saving, and managing
Indian recipes.
About the Project
MiniCookbook is a simple recipe application with a category-based home
screen. Recipes are organized into Breakfast, Lunch, Dinner, Snacks,
and Desserts. Users can browse recipe details, search for recipes,
save favorites, and add or edit recipes.
Features
- Recipe categories: Browse recipes by meal category.
- Recipe details: View ingredients, cooking steps, cooking time,
  and servings.
- Search: Search for recipes by name or ingredient.
- Favorites: Save recipes to a favorites list.
- Add and edit recipes: Create new recipes or update existing
  ones.
- Local storage: Uses an SQLite database to store recipe
  information on the device.
- Sample recipes: Includes 15 sample Indian recipes across five
  categories.
- Simple interface: Category cards, recipe lists, and bottom
  navigation.
Technology Stack
- Language: Java
- Platform: Android
- IDE: Android Studio
- Database: SQLite
- UI: XML layouts, Android Activities and Fragments, RecyclerView
Project Structure
MiniCookbookApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/cookbook/miniapp/
│   │   │   ├── activities/
│   │   │   ├── adapters/
│   │   │   ├── database/
│   │   │   ├── fragments/
│   │   │   ├── models/
│   │   │   └── utils/
│   │   ├── res/
│   │   │   ├── drawable/
│   │   │   ├── layout/
│   │   │   ├── menu/
│   │   │   └── values/
│   │   └── AndroidManifest.xml
│   ├── build.gradle
├── gradle/
├── build.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle
Getting Started
Requirements
- Android Studio
- Android SDK configured through Android Studio
- An Android emulator or Android device
Run the Application
1. Clone or download this repository.
2. Open Android Studio.
3. Select Open and choose the MiniCookbookApp project folder.
4. Allow Gradle sync to complete.
5. Select an emulator or connect an Android device.
6. Click Run to build and launch the app.
Data Storage
The app uses a local SQLite database named cookbook.db. Recipe data is
stored on the device, so the project does not require a remote database
server for its current functionality.
Future Enhancements
Possible improvements include adding recipe images from users, filters
for cooking time, meal planning, and cloud backup or synchronization.
Repository
GitHub: https://github.com/cgupta21/MiniCookbookApp
License
No license has been specified for this repository yet.
