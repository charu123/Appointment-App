# Recipe App - Kotlin Multiplatform

A beautiful and functional recipe management app built with Kotlin Multiplatform (KMP) and Compose Multiplatform.

## Features

- 📱 **Cross-platform**: Shared codebase for Android and iOS
- 🔍 **Search Recipes**: Find recipes by name, description, or ingredients
- 🏷️ **Category Filtering**: Browse recipes by categories (Appetizer, Main Course, Dessert, etc.)
- ❤️ **Favorites**: Mark recipes as favorites and access them quickly
- 📋 **Detailed View**: View complete recipe details with ingredients and step-by-step instructions
- 🎨 **Modern UI**: Beautiful Material Design interface with Compose Multiplatform

## Architecture

The app follows a clean architecture pattern with:

- **Shared Module**: Contains business logic, data models, repository pattern, and UI components
- **Android App**: Android-specific implementation using the shared module
- **Repository Pattern**: Clean separation of data access logic
- **MVVM**: ViewModel pattern for state management
- **Compose Multiplatform**: Shared UI code across platforms

## Tech Stack

- **Kotlin Multiplatform**: For sharing code between platforms
- **Compose Multiplatform**: For shared UI components
- **Kotlin Coroutines**: For asynchronous programming
- **StateFlow**: For reactive state management
- **Material Design**: For beautiful and consistent UI
- **Kotlinx Serialization**: For data serialization
- **SQLDelight**: For local database (configured but not yet implemented)
- **Ktor**: For networking (configured but not yet implemented)

## Project Structure

```
RecipeApp/
├── shared/                          # Shared KMP module
│   ├── src/commonMain/kotlin/
│   │   ├── com/recipeapp/
│   │   │   ├── model/              # Data models
│   │   │   ├── repository/         # Data access layer
│   │   │   ├── viewmodel/          # Business logic
│   │   │   └── ui/                 # Shared UI components
│   │   │       ├── screens/        # Screen composables
│   │   │       └── RecipeApp.kt    # Main app composable
│   └── build.gradle.kts
├── androidApp/                      # Android-specific module
│   ├── src/androidMain/
│   │   ├── kotlin/                 # Android-specific code
│   │   ├── res/                    # Android resources
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/libs.versions.toml        # Version catalog
├── build.gradle.kts                # Root build script
├── settings.gradle.kts             # Project settings
└── README.md
```

## Sample Data

The app comes with three sample recipes:

1. **Classic Chocolate Chip Cookies** - Easy dessert recipe
2. **Grilled Chicken Caesar Salad** - Medium difficulty salad
3. **Homemade Pizza Margherita** - Medium difficulty main course

Each recipe includes:
- Detailed ingredients list
- Step-by-step instructions
- Preparation and cooking times
- Serving size
- Difficulty level
- Category classification

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- JDK 11 or later
- Android SDK with API level 24 or higher

### Building and Running

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd RecipeApp
   ```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Run the Android app:
   - Select the `androidApp` run configuration
   - Click the Run button or press `Ctrl+R`

### Building for iOS (Future)

The project is configured for iOS development but requires:
- Xcode 14 or later
- macOS development environment
- iOS target configuration (to be added)

## App Flow

1. **Recipe List Screen**: 
   - Browse all recipes
   - Search by name, description, or ingredients
   - Filter by category
   - Access favorites

2. **Recipe Detail Screen**:
   - View complete recipe information
   - See ingredients with bullet points
   - Follow numbered cooking instructions
   - Toggle favorite status

3. **Favorites Screen**:
   - View all favorite recipes
   - Quick access to loved recipes
   - Remove from favorites

## Customization

### Adding New Recipes

Recipes are currently stored in memory. To add new recipes, modify the `getSampleRecipes()` function in `RecipeRepositoryImpl.kt`.

### Adding New Categories

Add new categories to the `Category` enum in `Recipe.kt`.

### Styling

The app uses Material Design theming. Customize colors in `RecipeApp.kt` MaterialTheme configuration.

## Future Enhancements

- 🗄️ **Persistent Storage**: Implement SQLDelight for local recipe storage
- 🌐 **Recipe API**: Add Ktor client for fetching recipes from online sources
- 📸 **Image Support**: Add recipe images and photo capture
- 📤 **Recipe Sharing**: Share recipes with friends
- 🍽️ **Meal Planning**: Plan meals and create shopping lists
- 📊 **Nutrition Info**: Add nutritional information
- 🔐 **User Accounts**: User authentication and cloud sync
- 📱 **iOS App**: Complete iOS implementation

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Built with Kotlin Multiplatform and Compose Multiplatform
- Inspired by modern recipe management apps
- Uses Material Design principles for excellent UX 
