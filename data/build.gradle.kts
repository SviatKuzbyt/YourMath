plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    //room
    alias(libs.plugins.google.devtools.ksp)
    //hilt
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
    //python
    alias(libs.plugins.chaquo.python)
}

android {
    namespace = "ua.sviatkuzbyt.yourmath.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        ndk {
            abiFilters += listOf("arm64-v8a", "x86_64")
        }
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    //core and test
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    //room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //json
    implementation(libs.gson)

    //modules
    implementation(project(":domain"))
}