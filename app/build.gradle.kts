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
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

android {
    namespace = "com.specialtech.diary"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.specialtech.diary"
        minSdk = 26
        targetSdk = 36
        versionCode = 28
        versionName = "0.3.2"
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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(type = "String", name = "WEATHER_API_KEY", value = localProperties.getProperty("WEATHER_API_KEY"))
            applicationIdSuffix = ".release"
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            buildConfigField(type = "String", name = "WEATHER_API_KEY", value = localProperties.getProperty("WEATHER_API_KEY"))
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
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
            packageName.set("com.specialtech.diary")
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

    // COIL
    implementation(libs.coil.compose)
    implementation(libs.coil.network.ktor3)

    // ICONS
    implementation(libs.erikflowers.weather.icons)
    implementation(libs.androidx.ui.text.google.fonts)

    // TESTS
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}