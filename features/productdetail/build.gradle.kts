plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-rc02"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        named("main") {
            kotlin.srcDir("build/generated/source/navigation-args/")
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":features:shared"))
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.fragment.ktx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraintLayout)
    implementation(Google.android.material)
    implementation(AndroidX.Lifecycle.viewModelKtx)
    implementation(AndroidX.Lifecycle.runtimeKtx)

    // Navigation Component
    implementation(AndroidX.navigation.fragmentKtx)

    // Compose
    implementation(AndroidX.compose.compiler)
    implementation(AndroidX.compose.runtime)
    implementation(AndroidX.compose.foundation)
    implementation(AndroidX.compose.foundation.layout)
    implementation(AndroidX.compose.ui.tooling)
    implementation(AndroidX.compose.material)
    implementation(COIL.compose)
    implementation(Google.accompanist.pager)

    // Hilt
    implementation(Google.dagger.hilt.android)
    kapt(Google.dagger.hilt.compiler)
}