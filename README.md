# 🍲 MiniCookbookApp

An Android application for browsing, searching, saving, and managing Indian recipes.

![Platform](https://img.shields.io/badge/platform-Android-3DDC84?logo=android&logoColor=white)
![Language](https://img.shields.io/badge/language-Java-007396?logo=openjdk&logoColor=white)
![Database](https://img.shields.io/badge/database-SQLite-003B57?logo=sqlite&logoColor=white)

---

## 📖 About

**MiniCookbookApp** is an Android recipe application designed to make discovering and managing recipes simple. It organizes recipes into five categories: **Breakfast, Lunch, Dinner, Snacks, and Desserts**.

Users can explore recipes, view ingredients and cooking instructions, search for recipes, save favorites, and add or edit recipes. The application uses a local SQLite database to store recipe data on the device.

## 📸 Screenshots

<p align="center">
  <img src="screenshots/Home_Screen.png" width="220" alt="MiniCookbook home screen" />
  <img src="screenshots/Recipe_List.png" width="220" alt="Recipe list screen" />
  <img src="screenshots/Recipe_Details.png" width="220" alt="Recipe details screen" />
  <img src="screenshots/Search_Screen.png" width="220" alt="Recipe search screen" />
</p>

## ✨ Features

- **Recipe Categories** – Browse recipes by meal category.
- **Recipe Details** – View ingredients, cooking instructions, preparation time, and servings.
- **Search Recipes** – Find recipes by name or ingredient.
- **Favorites** – Save recipes for convenient access.
- **Add and Edit Recipes** – Create new recipes and update existing ones.
- **Local Database** – Store recipe data using SQLite.
- **Sample Recipes** – Explore 15 sample Indian recipes across five categories.
- **User Interface** – Navigate through category cards, recipe lists, and bottom navigation.

## 🛠️ Tech Stack

| Area | Technology |
|---|---|
| Programming Language | Java |
| Platform | Android |
| IDE | Android Studio |
| Database | SQLite |
| UI | XML layouts, Activities, Fragments, RecyclerView |
| Build System | Gradle |

## 🧠 Concepts Demonstrated

- Android Activities and Fragments for screen navigation.
- XML layouts for designing user interfaces.
- RecyclerView and adapters for displaying recipe lists.
- SQLite database operations for local data storage.
- Java classes for recipe models and application logic.
- Gradle for project configuration and builds.

## 📁 Project Structure

```text
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
│   └── build.gradle
├── gradle/
├── .gitignore
├── build.gradle
├── gradle.properties
├── settings.gradle
└── README.md
```

*Note: This is a simplified overview of the project structure.*

## 🚀 Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio)
- Android SDK configured in Android Studio
- An Android emulator or a physical Android device

### Installation and Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/cgupta21/MiniCookbookApp.git
   ```

2. Open Android Studio.
3. Select **Open** and choose the cloned `MiniCookbookApp` folder.
4. Wait for Gradle synchronization to complete.
5. Select an emulator or connect an Android device.
6. Click **Run ▶** to build and launch the application.

## 💾 Data Storage

The application uses a local SQLite database named `cookbook.db` to store recipe data on the device. A remote database server is not required for the local data storage described here.

## 🔮 Future Enhancements

- Recipe images and image uploads.
- Filters based on cooking time.
- Meal planning functionality.
- Cloud backup and synchronization.

## 👤 Author

**GitHub:** [@cgupta21](https://github.com/cgupta21)

## 🤝 Collaboration

This repository supports collaboration through GitHub. Collaborators can help review, maintain, and improve the project.

## 📄 License

No license has been specified for this repository. All rights remain reserved by default, subject to applicable law and GitHub's terms. Add a license if you want to define how others may use, modify, or distribute the code.
