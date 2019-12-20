plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Build.Build.compileSdk)
    defaultConfig {
        applicationId = Build.appId
        minSdkVersion(Build.Build.minSdk)
        targetSdkVersion(Build.Build.targetSdk)
        versionCode = Build.versionCode
        versionName = Build.versionName
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
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
    dataBinding {
        isEnabled = true
    }

    flavorDimensions("env")

    productFlavors {
        create("preprod") {
            setDimension("env")
            applicationId = PreProd.applicationId
            buildConfigField(ConfigHelper.Type.STRING, PreProd.urls.baseUrl.first, PreProd.urls.baseUrl.second)
        }
        create("prod") {
            setDimension("env")
            applicationId = Prod.applicationId
            buildConfigField(ConfigHelper.Type.STRING, Prod.urls.baseUrl.first, Prod.urls.baseUrl.second)
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Libs.kotlin_stdlib_jdk7)

    implementation(Libs.kotlinx_coroutines_android)

    // koin for Android
    implementation(Libs.koin_android)
    implementation(Libs.koin_androidx_scope)
    implementation(Libs.koin_androidx_viewmodel)
    implementation(Libs.koin_androidx_ext)

    // androidX
    implementation(Libs.appcompat)
    implementation(Libs.core_ktx)
    implementation(Libs.constraintlayout)
    implementation(Libs.recyclerview)
    implementation(Libs.lifecycle_extensions)
    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.lifecycle_livedata_ktx)
    implementation(Libs.room_runtime)
    implementation(Libs.room_ktx)
    kapt(Libs.room_compiler)

    // material
    implementation(Libs.material)

    // glide
    implementation(Libs.glide)

    //retrofit
    implementation(Libs.retrofit2_kotlin_coroutines_adapter)
    implementation(Libs.converter_gson)
    implementation(Libs.gson)

    // stetho
    implementation(Libs.stetho)
    implementation(Libs.stetho_okhttp3)

    //own ktx
    implementation(Libs.android_ktx)

    // lottie
    implementation(Libs.lottie)

    // debug
    implementation(Libs.timber)
    implementation(Libs.logging_interceptor)

    testImplementation(Libs.junit)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.espresso_core)
}
