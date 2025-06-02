plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.udb.login"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.udb.login"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.compose.icons)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.compose.v277)
    implementation(libs.material3)


    implementation("androidx.compose.ui:ui:1.4.0") // Cambia a la versión más reciente
    implementation("androidx.compose.material3:material3:1.0.0") // Cambia a la versión más reciente
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0")
    implementation("androidx.navigation:navigation-compose:2.5.3")

    // Dependencias Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

        // Compose Navigation
        implementation(libs.androidx.navigation.compose.v277)

        // Material 3
        implementation(libs.material3)

        // Animations
        implementation(libs.androidx.animation)

        // Activity Compose
        implementation(libs.androidx.activity.compose.v181)

        // Compose BOM para versiones consistentes (opcional)
        implementation(libs.androidx.compose.bom.v20240400)

        implementation(libs.ui)
        implementation(libs.material3)
        implementation(libs.androidx.navigation.compose.v277)
        implementation(libs.androidx.activity.compose.v182)

        // Opcional pero recomendado
        implementation(libs.ui.tooling.preview)
        debugImplementation(libs.ui.tooling)
    }





