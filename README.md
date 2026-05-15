# Namma Skill – Training Opportunity Tracker

A modern Android app to help rural and small-town youth discover government skill development courses and apply easily.

## Tech Stack
- **Language**: Kotlin
- **UI**: Jetpack Compose (Material 3)
- **Architecture**: MVVM + Repository pattern
- **DI**: Hilt
- **Backend**: Firebase Firestore
- **Notifications**: Firebase Cloud Messaging
- **Maps**: Google Maps SDK for Android

## Setup

### Prerequisites
- Android Studio Hedgehog or later
- JDK 17+
- A Firebase project

### Steps
1. Clone the repository
2. Replace `app/google-services.json` with your Firebase config
3. Add your Google Maps API key in `local.properties`:
   ```
   MAPS_API_KEY=your_api_key_here
   ```
4. Open in Android Studio and sync Gradle
5. Run on device/emulator (API 26+)

## Features
- 📚 Browse vocational courses with category & duration filters
- 📝 Apply for courses with form validation
- ❤️ Express interest and get trainer callback
- 🗺️ Find skill centers on Google Maps
- ⭐ Read success stories from alumni
- 🔔 Get notified about new batches

## Project Structure
```
app/src/main/java/com/nammaskill/app/
├── data/model/          # Data classes
├── data/repository/     # Firebase repositories
├── di/                  # Hilt dependency injection
├── navigation/          # Nav graph & routes
├── service/             # FCM service
└── ui/
    ├── theme/           # Material 3 design system
    ├── components/      # Reusable UI components
    ├── home/            # Course listing
    ├── details/         # Course details
    ├── apply/           # Application form
    ├── map/             # Google Maps
    ├── stories/         # Success stories
    └── notifications/   # Notifications
```
