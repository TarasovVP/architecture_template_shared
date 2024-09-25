plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("kotlinx-serialization")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }
    task("testClasses")
    sourceSets {
        commonMain.dependencies {
            implementation("junit:junit:4.13.2")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
            //Ktor
            implementation("io.ktor:ktor-client-core:2.3.7")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
            //SQLDelight
            implementation("com.squareup.sqldelight:runtime:1.5.5")
            implementation("com.squareup.sqldelight:coroutines-extensions:1.5.5")
            // Koin
            implementation("io.insert-koin:koin-core:3.5.3")
            implementation("io.insert-koin:koin-compose:1.1.2")
        }
        androidMain.dependencies {
            implementation("io.ktor:ktor-client-android:2.3.7")
            implementation("com.squareup.sqldelight:android-driver:1.5.5")
        }
        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:2.3.7")
            implementation("com.squareup.sqldelight:native-driver:1.5.5")
        }
        nativeMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:2.3.7")
            implementation("com.squareup.sqldelight:native-driver:1.5.5")
        }
    }
}

android {
    namespace = "com.vnteam.architecturetemplates"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.vnteam.architecturetemplates"
    }
}