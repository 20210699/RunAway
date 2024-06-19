plugins {
//    id("com.android.application")
////    alias(libs.plugins.androidApplication)
//    alias(libs.plugins.jetbrainsKotlinAndroid)
//    id("kotlin-kapt")
//    id("com.google.gms.google-services")

    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")

    //firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.runaway"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.runaway"
        minSdk = 24
        targetSdk = 34
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
    viewBinding{
        enable = true
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //splash
    implementation ("com.airbnb.android:lottie:6.4.0") //Lottie
    implementation("androidx.core:core-splashscreen:1.0.0")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    //glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
    implementation("androidx.multidex:multidex:2.0.1")

    //google auth
    implementation("com.google.android.gms:play-services-auth:21.1.1")

    //firestore
    implementation("com.google.firebase:firebase-firestore-ktx:25.0.0")

    //firebase storage
    implementation("com.google.firebase:firebase-storage-ktx:21.0.0")

    //json
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //naver map API
    implementation ("com.naver.maps:map-sdk:3.18.0")

    // tikxml: xml 변환기
    implementation("com.tickaroo.tikxml:annotation:0.8.13")
    implementation("com.tickaroo.tikxml:core:0.8.13")
    implementation("com.tickaroo.tikxml:retrofit-converter:0.8.13")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt ("com.tickaroo.tikxml:processor:0.8.13") // kapt : 어노테이션과 같은 처리기

    // setting 구현
    implementation("androidx.preference:preference:1.2.1")
}