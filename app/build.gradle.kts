plugins {
    id ("com.android.application")
    id ("kotlin-android")
    id ("kotlin-kapt")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.jfma75.composeform"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.appcompat:appcompat:1.4.0")
    implementation ("com.google.android.material:material:1.4.0")

    // Compose
    implementation (Libs.AndroidX.Compose.activity)
    implementation (Libs.AndroidX.Compose.animation)
    implementation (Libs.AndroidX.Compose.foundation)
    implementation (Libs.AndroidX.Compose.iconsExtended)
    implementation (Libs.AndroidX.Compose.layout)
    implementation (Libs.AndroidX.Compose.material)
    implementation (Libs.AndroidX.Compose.navigation)
    implementation (Libs.AndroidX.Compose.ui)
    implementation (Libs.AndroidX.Compose.runtime)
    implementation (Libs.AndroidX.Compose.runtimeLiveData)
    implementation (Libs.AndroidX.Compose.tooling)

    // Compose Paging
    implementation(Libs.AndroidX.Compose.paging)

    // Accompanist
    implementation (Libs.Google.Accompanist.swiperefresh)
    implementation (Libs.Google.Accompanist.navigationAnimation)
    implementation (Libs.Google.Accompanist.pager)
    implementation (Libs.Google.Accompanist.pagerIndicators)

    // Hilt
    implementation (Libs.Google.Hilt.core)
    implementation (Libs.AndroidX.Hilt.lifeCycle)
    implementation (Libs.AndroidX.Hilt.navigation)
    kapt (Libs.Google.Hilt.compiler)
    kapt (Libs.AndroidX.Hilt.compiler)

    // Livedata + Viewmodel
    implementation (Libs.AndroidX.Lifecycle.livedata)
    implementation (Libs.AndroidX.Lifecycle.runtime)
    implementation (Libs.AndroidX.Lifecycle.viewmodel)

    // Tests

    // jUnit
    testImplementation (Libs.JUnit.junit)

    // Core library
    androidTestImplementation (Libs.AndroidX.Test.core)

    // Assertions
    androidTestImplementation  (Libs.AndroidX.Test.Ext.junit)

    // AndroidJUnitRunner and JUnit Rules
    androidTestImplementation (Libs.AndroidX.Test.runner)
    androidTestImplementation (Libs.AndroidX.Test.rules)
    androidTestImplementation (Libs.AndroidX.Test.espressoCore)
}