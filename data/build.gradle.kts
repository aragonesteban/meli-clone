plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("plugin.serialization") version "1.6.21"
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
}

dependencies {
    implementation(project(":domain"))

    implementation(KotlinX.coroutines.core)

    // Hilt
    implementation(Google.dagger.hilt.android)
    kapt(Google.dagger.hilt.compiler)

    // Retrofit
    implementation(Square.retrofit2.retrofit)
    implementation(Square.okHttp3.okHttp)
    implementation(Square.okHttp3.loggingInterceptor)
    implementation("com.localebro:okhttpprofiler:1.0.8")

    // Kotlin Serialization
    implementation(JakeWharton.retrofit2.converter.kotlinxSerialization)
    implementation(KotlinX.serialization.core)
    implementation(KotlinX.serialization.json)

    // Room
    implementation(AndroidX.room.ktx)
    implementation(AndroidX.room.runtime)
    annotationProcessor(AndroidX.room.compiler)
    kapt(AndroidX.room.compiler)
}