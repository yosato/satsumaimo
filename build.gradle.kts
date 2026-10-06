plugins {
    kotlin("multiplatform") version "2.1.21"
    id("com.android.library") version "8.10.0"
}

kotlin {
    androidTarget()
    jvm()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.7.0")
        }
    }
}

android {
    namespace = "uk.co.satoama.satsumaimo"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
}
