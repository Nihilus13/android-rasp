// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.4.0" apply false
    id("com.android.library") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.22" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.22" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.0" apply false
    id("org.jetbrains.dokka") version "1.8.20" apply false
    id("com.vanniktech.maven.publish") version "0.25.2"
}

val groupName by extra { "com.securevale.rasp" }
val versionName by extra { "0.7.0" }
val minSdkVersion by extra { 24 }
val compileSdkVersion by extra { 34 }
val targetSdkVersion by extra { 34 }
val javaVersion by extra { JavaVersion.VERSION_1_8.name }
val versionCode by extra { 7 }

task("clean", type = Delete::class) {
    delete(rootProject.buildDir)
}
