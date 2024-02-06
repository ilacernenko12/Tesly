object Application {
    const val id = "com.example.tesly"
}

object AndroidLibraries {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
}

object TestLibraries {
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val jUnitExt = "androidx.test.ext:junit:${Versions.jUnitExt}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

object Libraries {
    // ROOM
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

    // DAGGER HILT
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    // RETROFIT
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    // OkHttp
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    // viewModel delegate
    const val fragmentVmDelegate = "androidx.fragment:fragment-ktx:${Versions.fragmentVmDelegate}"
    const val activityVmDelegate = "androidx.activity:activity-ktx:${Versions.activityVmDelegate}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelKtx}"

    // Glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"

    // viewBinding delegate
    const val viewBindingNoReflection = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:${Versions.viewBindingNoReflectionglide}"
}

object Modules {
    const val presentation = ":presentation"
    const val domain = ":domain"
    const val data = ":data"
}
