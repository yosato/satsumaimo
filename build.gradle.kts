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
        commonTest.dependencies {
            implementation(kotlin("test"))
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
