// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    //core and compose
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    //room
    alias(libs.plugins.google.devtools.ksp) apply false
    //navigation
    alias(libs.plugins.kotlin.serialization) apply false
    //Hilt
    alias(libs.plugins.dagger.hilt) apply false
}