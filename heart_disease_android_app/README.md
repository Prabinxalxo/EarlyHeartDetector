# Heart Disease Prediction Android App

A mobile application for predicting heart disease risk and providing personalized diet recommendations.

## Features

- User-friendly interface for inputting health information
- Heart disease prediction based on key health indicators
- Personalized diet recommendations based on heart health status
- PDF report generation with detailed health assessment
- Mobile-friendly design with smooth navigation

## Building the APK

### Requirements

- Android Studio 4.2 or higher
- JDK 8 or newer
- Gradle 7.4.2 or compatible version

### Steps to Build

1. **Open the project in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to and select the `heart_disease_android_app` folder

2. **Sync Gradle**
   - Wait for the project to load
   - Click "Sync Project with Gradle Files" (elephant icon in the toolbar)

3. **Build the APK**
   - Select "Build" from the menu
   - Choose "Build Bundle(s) / APK(s)"
   - Select "Build APK(s)"

4. **Locate the APK**
   - After successful build, click the notification "APK generated successfully"
   - Or navigate to `app/build/outputs/apk/debug/app-debug.apk`

5. **Install on Device**
   - Transfer the APK to your Android device
   - On your device, enable "Install from Unknown Sources" in Settings
   - Open the APK file on your device to install

## Development Notes

### Project Structure

- **MainActivity**: Handles user input form and navigation
- **ResultsActivity**: Displays prediction results and diet recommendations
- **HeartDiseasePredictor**: Contains the prediction algorithm
- **DietRecommendations**: Provides personalized diet plans
- **PDFGenerator**: Creates downloadable PDF reports

### Key Technical Components

- Material Design components for modern UI
- PDF generation using Android's PdfDocument
- File sharing via FileProvider
- Vector drawables for clean, scalable graphics

### Build Configuration

The app targets:
- minSdkVersion: 21 (Android 5.0 Lollipop)
- targetSdkVersion: 33 (Android 13)
- compileSdkVersion: 33

## Language and Build Command for Hosting

When hosting this project on a platform like Render:

- **Language**: Java
- **Build Command**: `./gradlew assembleDebug`
- **Start Command**: Not applicable for APK builds (this is a mobile app, not a server)