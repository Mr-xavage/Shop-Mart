plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.hilt.gradle.plugin) apply false
    alias(libs.plugins.google.services) apply false
//    id("com.google.gms.google-services") version "4.4.2" apply false
}