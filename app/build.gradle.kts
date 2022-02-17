plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.a.hub"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    externalNativeBuild {
        /*cmake {
            path = file("CMakeLists.txt")
        }*/
    }
}

dependencies {

    implementation(Deps.AndroidX.appCompat)
    implementation(Deps.AndroidX.constraintLayout)
    implementation(Deps.AndroidX.splashscreen)
    implementation(Deps.AndroidX.datastore)

    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.preferenceKtx)
    implementation(Deps.AndroidX.fragmentKtx)

    implementation(Deps.Google.material)
    implementation(Deps.Google.gson)
    implementation(Deps.Google.hilt)
    kapt(Deps.Google.hiltCompiler)

    implementation(Deps.AndroidX.lifecycleRuntimeKtx)
    implementation(Deps.AndroidX.lifecycleViewModelKtx)
    implementation(Deps.AndroidX.lifecycleVLivedataKtx)
    implementation(Deps.AndroidX.lifecycleService)

    implementation(Deps.KotlinX.coroutinesCore)
    implementation(Deps.KotlinX.coroutinesAndroid)

    implementation(Deps.Square.retrofit)
    implementation(Deps.Square.retrofitGson)
    implementation(Deps.Square.loggingInterceptor)

    implementation(Deps.Any.networkResponseAdapter)

    implementation(Deps.Any.glide)
    kapt(Deps.Any.glideCompiler)

    implementation(Deps.Any.markdownView)

    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.androidxJunit)
    androidTestImplementation(Deps.Test.espresso)

}