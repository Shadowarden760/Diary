import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

var appProperties = Properties()
val appPropertiesFile = rootProject.file("app/app.properties")
if (appPropertiesFile.exists()) {
    appProperties.load(FileInputStream(appPropertiesFile))
} else {
    appProperties = System.getProperties()
}

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
        versionCode = 60
        versionName = "1.8.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            storeFile = appProperties.getProperty("DEBUG_STORE_FILE")?.let { file(it) }
            storePassword = appProperties.getProperty("DEBUG_STORE_PASSWORD")
            keyAlias = appProperties.getProperty("DEBUG_KEY_ALIAS")
            keyPassword = appProperties.getProperty("DEBUG_KEY_PASSWORD")
        }
        create("release") {
            storeFile = appProperties.getProperty("RELEASE_STORE_FILE")?.let { file(it) }
            storePassword = appProperties.getProperty("RELEASE_STORE_PASSWORD")
            keyAlias = appProperties.getProperty("RELEASE_KEY_ALIAS")
            keyPassword = appProperties.getProperty("RELEASE_KEY_PASSWORD")
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
            buildConfigField(type = "String", name = "WEATHER_API_URL", value = appProperties.getProperty("WEATHER_API_URL"))
            buildConfigField(type = "String", name = "WEATHER_API_KEY", value = appProperties.getProperty("WEATHER_API_KEY"))
            buildConfigField(type = "String", name = "IP_API_URL", value = appProperties.getProperty("IP_API_URL"))
            buildConfigField(type = "String", name = "MAP_API_URL", value = appProperties.getProperty("MAP_API_URL"))
            buildConfigField(type = "String", name = "MAPTILER_API_KEY", value = appProperties.getProperty("MAPTILER_API_KEY"))
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
            buildConfigField(type = "String", name = "WEATHER_API_URL", value = appProperties.getProperty("WEATHER_API_URL"))
            buildConfigField(type = "String", name = "WEATHER_API_KEY", value = appProperties.getProperty("WEATHER_API_KEY"))
            buildConfigField(type = "String", name = "IP_API_URL", value = appProperties.getProperty("IP_API_URL"))
            buildConfigField(type = "String", name = "MAP_API_URL", value = appProperties.getProperty("MAP_API_URL"))
            buildConfigField(type = "String", name = "MAPTILER_API_KEY", value = appProperties.getProperty("MAPTILER_API_KEY"))
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

    splits {
        abi {
            isEnable = true
            reset()
            include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
            isUniversalApk = true
        }
    }

    applicationVariants.configureEach {
        this.outputs.forEach { output ->
            output as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            val regex = Regex("-[\\w]+\\.apk$")
            val matchResult = regex.find(output.outputFileName)
            output.outputFileName = output.outputFileName.replace("app", "DiaryApp")
            matchResult?.value?.let { substring ->
                output.outputFileName = output.outputFileName.replace(substring, "-$versionName.apk")
            }
        }
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
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
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

    // MAPLIBRE
    implementation(libs.maplibre.compose)
    implementation(libs.maplibre.compose.material3)

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