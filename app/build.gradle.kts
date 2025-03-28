plugins {
    //core and compose
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    //room
    alias(libs.plugins.google.devtools.ksp)
    //navigation
    alias(libs.plugins.kotlin.serialization)
    //hilt
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "ua.sviatkuzbyt.yourmath.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "ua.sviatkuzbyt.yourmath.app"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("profile"){
            initWith(getByName("debug"))
            isDebuggable = false
            isProfileable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    //Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    //room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    //navigation
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.androidx.navigation.compose)

    //hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)
}