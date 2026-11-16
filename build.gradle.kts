import org.gradle.api.publish.maven.tasks.AbstractPublishToMaven
plugins {
    kotlin("multiplatform") version "2.1.21"
    id("com.android.library") version "8.10.0"
    id("com.vanniktech.maven.publish") version "0.34.0"
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
mavenPublishing {
    publishToMavenCentral()
    pom {
        name.set("satsumaimo")
        description.set("Japanese orthography normalisation")
        inceptionYear.set("2026")
        url.set("https://github.com/yosato/satsumaimo")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("yosato")
                name.set("Yo Sato")
                url.set("https://github.com/yosato")
            }
        }
        scm {
            url.set("https://github.com/yosato/satsumaimo")
            connection.set("scm:git:git://github.com/yosato/satsumaimo.git")
            developerConnection.set("scm:git:ssh://git@github.com/yosato/satsumaimo.git")
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
