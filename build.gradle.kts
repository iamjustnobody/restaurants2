// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

//    id("com.android.application") version "8.2.0" apply false
    id("com.android.library") version "8.2.0" apply false
//    id("com.android.application") version "8.2.0" apply false
//    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
//    id("org.jetbrains.kotlin.kapt") version "1.9.23" apply false //alias(libs.plugins.kotlin.kapt) in App-level

    id("com.google.dagger.hilt.android") version "2.56.2" apply false
//    id("com.google.dagger.hilt.android") version "2.45" apply false
}