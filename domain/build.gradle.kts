plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.domain"
    compileSdk = Versions.compileSdkVersion

    defaultConfig {
        minSdk = 26

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
    implementation(AndroidLibraries.coreKtx)
    implementation(AndroidLibraries.appCompat)
    implementation(AndroidLibraries.material)
    testImplementation(TestLibraries.jUnit)
    androidTestImplementation(TestLibraries.jUnitExt)
    androidTestImplementation(TestLibraries.espresso)

    // в идеале этого не должно быть
    implementation(Libraries.hiltAndroid)
    kapt(Libraries.hiltCompiler)
}