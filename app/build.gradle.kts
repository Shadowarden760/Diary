import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

android {
    namespace = "com.homeapps.diary"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.homeapps.diary"
        minSdk = 26
        targetSdk = 36
        versionCode = 49
        versionName = "1.5.7"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file(localProperties.getProperty("DEBUG_STORE_FILE"))
            storePassword = localProperties.getProperty("DEBUG_STORE_PASSWORD")
            keyAlias = localProperties.getProperty("DEBUG_KEY_ALIAS")
            keyPassword = localProperties.getProperty("DEBUG_KEY_PASSWORD")
        }
        create("release") {
            storeFile = file(localProperties.getProperty("RELEASE_STORE_FILE"))
            storePassword = localProperties.getProperty("RELEASE_STORE_PASSWORD")
            keyAlias = localProperties.getProperty("RELEASE_KEY_ALIAS")
            keyPassword = localProperties.getProperty("RELEASE_KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            applicationIdSuffix = ".release"
            versionNameSuffix = "-release"
            signingConfig = signingConfigs.getByName("release")
            buildConfigField(type = "String", name = "WEATHER_API_KEY", value = localProperties.getProperty("WEATHER_API_KEY"))
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField(type = "String", name = "WEATHER_API_KEY", value = localProperties.getProperty("WEATHER_API_KEY"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    androidResources {
        generateLocaleConfig = true
    }
}

sqldelight {
    databases {
        create("DiaryDB") {
            packageName.set("com.homeapps.diary")
            schemaOutputDirectory = file("src/main/sqldelight/com/homeapps/diary/schemas")
            migrationOutputDirectory = file("src/main/sqldelight/com/homeapps/diary/migrations")
            verifyMigrations = true
            generateAsync = true
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)

    // KOIN
    implementation(libs.koin.androidx.compose)

    // SQLDELIGHT
    implementation(libs.android.sqldelight.driver)
    implementation(libs.coroutines.extensions)

    // DATASTORE
    implementation(libs.androidx.datastore.preferences)

    // KTOR
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.android)

    // ICONS
    implementation(libs.erikflowers.weather.icons)

    // SERIALIZATION/DESERIALIZATION
    implementation(libs.gson)

    // LEAK CANARY
    debugImplementation(libs.leakcanary.android)

    //FIREBASE ANALYTICS, CRASHLYTICS
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics.ndk)
    implementation(libs.firebase.analytics)

    // TESTS
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}