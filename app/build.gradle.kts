plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = Application.id
    compileSdk = 34

    defaultConfig {
        applicationId = Application.id
        minSdk = 26
        targetSdk = 33
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
    implementation(project(Modules.data))
    implementation(project(Modules.domain))
    implementation(project(Modules.presentation))

    implementation(AndroidLibraries.coreKtx)
    implementation(AndroidLibraries.appCompat)
    implementation(AndroidLibraries.material)
    implementation(AndroidLibraries.constraintlayout)
    testImplementation(TestLibraries.jUnit)
    androidTestImplementation(TestLibraries.jUnitExt)
    androidTestImplementation(TestLibraries.espresso)

    implementation(Libraries.hiltAndroid)
    kapt(Libraries.hiltCompiler)

    implementation(Libraries.retrofit)
    implementation(Libraries.converterGson)
    implementation(Libraries.okhttp)
    implementation(Libraries.loggingInterceptor)
}