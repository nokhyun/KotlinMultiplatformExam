import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.ksp)
    kotlin("plugin.serialization").version("1.9.20")
    id(libs.plugins.sqldelight.get().pluginId)
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
//            isStatic = true

            isStatic = false
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(libs.compose.ui)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.compose.navigation)
                implementation(libs.androidx.runtime.saveable)

                // ktor
                implementation(libs.ktor.client.android)

                // 확인필요.
                implementation(libs.koin.android)

                //
                implementation("com.squareup.sqldelight:android-driver:1.5.5")
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation("com.squareup.sqldelight:native-driver:1.5.5")
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                // Navigator
                implementation(libs.voyager.navigator)
                // BottomSheetNavigator
                implementation(libs.voyager.bottom.sheet.navigator)
                // TabNavigator
                implementation(libs.voyager.tab.navigator)
                // Transitions
                implementation(libs.voyager.transitions)
                implementation(libs.koin.core)
                implementation(libs.koin.test)

                implementation(libs.paging.compose.common)
                implementation(libs.paging.common)

                // db
                implementation(libs.sqldelight)

                // log
                implementation(libs.napier)
            }
        }

        commonMain {
            dependencies {
                implementation(libs.kamel.image)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.serialization)

                // ktor
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlinx.datetime)
                implementation(libs.ktor.client.logging)
            }
        }
    }
}

android {
    namespace = "com.nokhyun.kmmexam"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.nokhyun.kmmexam"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        // ktor(or kotlinx-datetime) 사용 시 발생하는 에러
        resources.excludes.apply {
            resources.excludes.add("META-INF/versions/**")
        }

        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.nokhyun.kmmexam"
            packageVersion = "1.0.0"
        }
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.nokhyun.kmmexam.common.cache"
    }

    linkSqlite = true

}