class Deps {

    object Build {
        const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val splashscreen = "androidx.core:core-splashscreen:${Versions.splashscreen}"
        const val datastore = "androidx.datastore:datastore-preferences:${Versions.datastore}"

        const val coreKtx = "androidx.core:core-ktx:${Versions.core}"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
        const val preferenceKtx = "androidx.preference:preference-ktx:${Versions.preference}"

        private const val lifecycleVersion = "2.4.0"
        const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        const val lifecycleVLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
        const val lifecycleService = "androidx.lifecycle:lifecycle-service:$lifecycleVersion"
    }

    object Square {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val retrofitGson = "com.squareup.retrofit2:converter-gson:$version"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    }

    object KotlinX {
        private const val version = "1.5.2"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.material}"
        const val gson = "com.google.code.gson:gson:${Versions.gson}"
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    }

    object Any {
        const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
        const val networkResponseAdapter = "com.github.haroldadmin:NetworkResponseAdapter:${Versions.networkResponseAdapter}"
        const val markdownView = "com.github.tiagohm.MarkdownView:library:${Versions.markdownView}"
    }

    object Debug {
        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
        const val stetho = "com.facebook.stetho:stetho::${Versions.stetho}"
        const val jsonviewer = "com.yuyh.json:jsonviewer:${Versions.jsonViewer}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val androidxJunit = "androidx.test.ext:junit:${Versions.androidxJunit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }

    /*
    //TODO app_compose
    object Compose {
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        // Tooling support (Previews, etc.
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.
        const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        // Material Design
        const val material = "androidx.compose.material:material:${Versions.compose}"
        // Material design icons
        const val materialIconsCore = "androidx.compose.material:material-icons-core:${Versions.compose}"
        const val materialIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.compose}"
        // Integration with activities
        const val activity = "androidx.activity:activity-compose:1.4.0"
        // Integration with ViewModels
        const val lifecycle = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-alpha01"
        // Integration with observables
        const val livedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
        const val rxjava2 = "androidx.compose.runtime:runtime-rxjava2:${Versions.compose}"
    }*/
}