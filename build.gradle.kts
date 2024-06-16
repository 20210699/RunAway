// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}
allprojects {
    repositories {
        maven {
            url = uri("https://repository.map.naver.com/archive/maven")
        }
    }
}