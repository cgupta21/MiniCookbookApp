# 🍲 MiniCookbookApp

An Android application for browsing, searching, saving, and managing Indian recipes.

![Platform](https://img.shields.io/badge/platform-Android-3DDC84?logo=android&logoColor=white)
![Language](https://img.shields.io/badge/language-Java-007396?logo=openjdk&logoColor=white)
![Database](https://img.shields.io/badge/database-SQLite-003B57?logo=sqlite&logoColor=white)

---

## 📖 About

MiniCookbook is a simple recipe app with a category-based home screen. Recipes are organized into **Breakfast**, **Lunch**, **Dinner**, **Snacks**, and **Desserts**. You can browse recipe details, search for recipes, save favorites, and add or edit your own recipes. Everything is stored locally on the device, so no internet connection or remote server is needed.

## ✨ Features

- **Recipe categories** – Browse recipes by meal category.
- **Recipe details** – View ingredients, cooking steps, cooking time, and servings.
- **Search** – Find recipes by name or ingredient.
- **Favorites** – Save recipes to a favorites list.
- **Add & edit recipes** – Create new recipes or update existing ones.
- **Local storage** – Recipe data is stored on-device in an SQLite database.
- **Sample data** – Ships with 15 sample Indian recipes across the five categories.
- **Simple interface** – Category cards, recipe lists, and bottom navigation.

## 🛠️ Tech Stack

| Area      | Technology                                        |
| --------- | ------------------------------------------------- |
| Language  | Java                                              |
| Platform  | Android                                           |
| IDE       | Android Studio                                    |
| Database  | SQLite                                            |
| UI        | XML layouts, Activities, Fragments, RecyclerView  |
| Build     | Gradle                                            |

## 📁 Project Structure

```
MiniCookbookApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/cookbook/miniapp/
│   │   │   ├── activities/     # Screens (Activities)
│   │   │   ├── adapters/       # RecyclerView adapters
│   │   │   ├── database/       # SQLite helper and data access
│   │   │   ├── fragments/      # UI fragments
│   │   │   ├── models/         # Data models (e.g. Recipe)
│   │   │   └── utils/          # Helper classes
│   │   ├── res/
│   │   │   ├── drawable/
│   │   │   ├── layout/
│   │   │   ├── menu/
│   │   │   └── values/
│   │   └── AndroidManifest.xml
│   └── build.gradle
├── gradle/
├── build.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle
```

## 🚀 Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio)
- Android SDK (installed via Android Studio)
- An Android emulator or a physical Android device

### Run the App

1. Clone the repository:
   ```bash
   git clone https://github.com/cgupta21/MiniCookbookApp.git
   ```
2. Open **Android Studio** and choose **Open**, then select the `MiniCookbookApp` folder.
3. Wait for the Gradle sync to finish.
4. Select an emulator or connect an Android device.
5. Click **Run ▶** to build and launch the app.

## 💾 Data Storage

The app uses a local SQLite database named `cookbook.db`. All recipe data lives on the device, so no remote database or backend is required.

## 🔮 Future Enhancements

- User-uploaded recipe images
- Filters for cooking time
- Meal planning
- Cloud backup and sync

## 📸 Screenshots

_Add screenshots of the home screen, recipe details, search, and favorites here._

## 📄 License

No license has been specified for this repository yet. Consider adding one (for example, [MIT](https://choosealicense.com/licenses/mit/)) if you want others to use or contribute to the project.

## 🔗 Repository

[github.com/cgupta21/MiniCookbookApp](https://github.com/cgupta21/MiniCookbookApp)
