import org.gradle.api.publish.maven.tasks.AbstractPublishToMaven
plugins {
    kotlin("multiplatform") version "2.1.21"
    id("com.android.library") version "8.10.0"
    id("maven-publish")
}
group = "io.github.yosato"
version = "0.1.0"

kotlin {
    androidTarget {
        publishLibraryVariants("release")
    }
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

tasks.withType<AbstractTestTask> {
    testLogging {
        events("passed", "failed", "skipped")
    }
    afterSuite(KotlinClosure2<TestDescriptor, TestResult, Unit>({ descriptor, result ->
        if (descriptor.parent == null) {
            println("${result.testCount} tests: ${result.successfulTestCount} passed, ${result.failedTestCount} failed, ${result.skippedTestCount} skipped")
        }
    }))
}
tasks.withType<AbstractPublishToMaven> {
    dependsOn("allTests")
}

android {
    namespace = "uk.co.satoama.satsumaimo"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
}
