plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = rootProject.extra["compileSdkVersion"] as Int

    defaultConfig {
        applicationId = "com.securevale.rasp.android"
        minSdk = rootProject.extra["minSdkVersion"] as Int
        targetSdk = rootProject.extra["targetSdkVersion"] as Int
        versionCode = rootProject.extra["versionCode"] as Int
        versionName = rootProject.extra["versionName"] as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            applicationIdSuffix = ".release"
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(rootProject.extra["javaVersion"] as String)
        targetCompatibility = JavaVersion.valueOf(rootProject.extra["javaVersion"] as String)
    }
    kotlinOptions {
        jvmTarget = JavaVersion.valueOf(rootProject.extra["javaVersion"] as String).toString()
    }
    namespace = "com.securevale.rasp.android.sample"

    packagingOptions.jniLibs.keepDebugSymbols += "**/*.so"
}

dependencies {
    implementation(project(":rasp"))
    implementation(libs.androidx.corektx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)
}
