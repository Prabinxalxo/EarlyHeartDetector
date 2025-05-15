# Heart Disease Prediction App

A mobile-friendly application for predicting heart disease risk and providing personalized diet recommendations.

## Features

- User-friendly interface for inputting health information
- Heart disease prediction based on key health indicators
- Personalized diet recommendations based on heart health status
- PDF report generation with detailed health assessment
- Mobile-friendly design with offline capabilities
- Can be packaged as an Android APK

## Running the Web App

1. Clone or download this repository
2. Open the folder in a web server or local development environment
3. Access index.html in your browser

## Converting to Android APK

### Using Cordova

1. Install Cordova globally:
   ```
   npm install -g cordova
   ```

2. Create a new Cordova project:
   ```
   cordova create HeartDiseaseApp
   ```

3. Navigate to the project folder:
   ```
   cd HeartDiseaseApp
   ```

4. Add Android platform:
   ```
   cordova platform add android
   ```

5. Replace the contents of the `www` folder with the files from this repository

6. Copy the `config.xml` file from this repository to the root of your Cordova project

7. Check if your setup meets requirements:
   ```
   cordova requirements
   ```

8. Build the APK:
   ```
   cordova build android
   ```

9. The APK file will be located at:
   ```
   platforms/android/app/build/outputs/apk/debug/app-debug.apk
   ```

### Using Capacitor

1. Create a new folder for your project and initialize npm:
   ```
   mkdir heart-disease-app-capacitor
   cd heart-disease-app-capacitor
   npm init -y
   ```

2. Install Capacitor:
   ```
   npm install @capacitor/core @capacitor/cli
   npx cap init HeartDiseaseApp com.heartdisease.app
   ```

3. Install Android platform:
   ```
   npm install @capacitor/android
   npx cap add android
   ```

4. Copy all files from this repository to the project folder

5. Run the build:
   ```
   npx cap sync
   npx cap open android
   ```

6. This will open Android Studio where you can build the APK

## App Structure

- `index.html` - Main HTML file containing the app structure
- `css/style.css` - Styling for the app
- `js/app.js` - Main application logic
- `js/prediction.js` - Heart disease prediction algorithm
- `js/diet.js` - Diet recommendations generator
- `service-worker.js` - For offline capabilities
- `manifest.json` - Web app manifest for installable PWA
- `config.xml` - Configuration for Cordova build
- `img/` - Icons and images

## Development

This project is built with:
- HTML5
- CSS3
- JavaScript (ES6+)
- PWA capabilities for offline use

## Building for Production

When building for production, consider the following steps:

1. Minify CSS and JavaScript files
2. Optimize images
3. Update service worker cache version
4. Test on various devices and screen sizes

## License

This project is open source and available for educational and personal use.