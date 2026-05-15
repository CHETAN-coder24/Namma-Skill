# Namma Skill ProGuard Rules

# Firebase Firestore
-keep class com.google.firebase.** { *; }
-keep class com.nammaskill.app.data.model.** { *; }

# Hilt
-keep class dagger.hilt.** { *; }

# Compose
-dontwarn androidx.compose.**
