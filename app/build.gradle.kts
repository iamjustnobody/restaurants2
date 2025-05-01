plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

//    id("com.android.application")
//    id("org.jetbrains.kotlin.android")
//    id("org.jetbrains.kotlin.kapt")
//    alias(libs.plugins.kotlin.kapt)
    id ("kotlin-kapt") //dup as above; need this as alias not working
//    alias(libs.plugins.kotlin.kapt) //id("org.jetbrains.kotlin.kapt") version "1.9.23" apply false @project-level
//    id("com.android.application")
//    kotlin("android")
//    kotlin("kapt") //old
//    kotlin("android-extensions")

//    id("dagger.hilt.android.plugin")

    id("com.google.dagger.hilt.android") version "2.56.2"
//    id("com.google.dagger.hilt.android") version "2.45"

//    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
//    id("org.jetbrains.kotlin.plugin.compose") version "1.5.11"
}

android {
    namespace = "com.example.restaurantfinder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.restaurantfinder"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("com.squareup.moshi:moshi:1.13.0")
    implementation ("com.squareup.moshi:moshi-kotlin:1.13.0")

//    implementation ("androidx.compose.ui:ui:1.4.0")
//    implementation ("androidx.compose.material3:material3:1.1.0")
////    implementation ("androidx.compose.foundation:foundation:1.4.0")
////    implementation ("androidx.compose.foundation:foundation-layout:1.4.0")
//    implementation("androidx.compose.material:material:1.4.0") // For Material icons
////    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0")
//    implementation ("com.google.accompanist:accompanist-placeholder-material:0.24.13-rc")
    implementation("com.google.accompanist:accompanist-placeholder-material:0.34.0")

    implementation ("io.coil-kt:coil-compose:2.1.0")



    implementation ("androidx.room:room-runtime:2.6.1")
//    annotationProcessor ("androidx.room:room-compiler:2.6.1")

    // Kotlin? Use kapt instead of annotationProcessor
    kapt("androidx.room:room-compiler:2.6.1")

    // opt Kotlin coroutines support
    implementation ("androidx.room:room-ktx:2.6.1")

    // Gson (for TypeConverters)
    implementation ("com.google.code.gson:gson:2.10.1")

    implementation ("com.google.firebase:firebase-firestore-ktx:24.3.0")
    implementation ("com.google.firebase:firebase-auth-ktx:21.2.0")


    // Hilt
    implementation("com.google.dagger:hilt-android:2.56.2")
    kapt("com.google.dagger:hilt-compiler:2.56.2")
//    implementation("com.google.dagger:hilt-android:2.45")
//    kapt("com.google.dagger:hilt-compiler:2.45")

    // Hilt with ViewModel
//    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
//    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // other Hilt extensions
//    kapt("androidx.hilt:hilt-compiler:1.1.0")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
//    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Kotlin dependencies
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:2.0.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.9.0")
//    implementation("org.jetbrains.kotlin:kotlin-metadata-jvm:2.1.0")
//    implementation("org.jetbrains.kotlin:kotlin-metadata-jvm:2.1.20")
//    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")
}

//apply(plugin = "dagger.hilt.android.plugin")
kapt {
    correctErrorTypes = true
    useBuildCache = true
    includeCompileClasspath = false // safer
    mapDiagnosticLocations = true // shows actual source of error
}
