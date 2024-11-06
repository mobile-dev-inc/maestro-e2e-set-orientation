plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.maestro.orientation"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.maestro.orientation"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}