import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.dokka.DokkaConfiguration.Visibility
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.dokka")
    //id("com.vanniktech.maven.publish")
    id("maven-publish")
}

android {
    compileSdk = rootProject.extra["compileSdkVersion"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdkVersion"] as Int
        targetSdk = rootProject.extra["targetSdkVersion"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFile("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            consumerProguardFiles("consumer-rules.pro")
        }

        getByName("debug") {
            isMinifyEnabled = false
            enableUnitTestCoverage = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(rootProject.extra["javaVersion"] as String)
        targetCompatibility = JavaVersion.valueOf(rootProject.extra["javaVersion"] as String)
    }

    kotlinOptions {
        jvmTarget = JavaVersion.valueOf(rootProject.extra["javaVersion"] as String).toString()
    }
    namespace = "${rootProject.extra["groupName"] as String}.android"

    lint {
        abortOnError = true
        warningsAsErrors = true
        disable += "GradleDependency"
    }

    detekt {
        config = files("$projectDir/config/detekt.yml")
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            unitTests.isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(libs.androidx.corektx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)

    debugImplementation(libs.square.leakcanary)
}

tasks.dokkaHtml.configure { configureDokka(this, "dokkaHtml") }
tasks.dokkaJavadoc.configure { configureDokka(this, "dokkaJavadoc") }

fun configureDokka(dokkaTask: DokkaTask, outputDir: String) = dokkaTask.apply {
    outputDirectory.set(buildDir.resolve(outputDir))

    dokkaSourceSets {
        configureEach {
            documentedVisibilities.set(
                setOf(
                    Visibility.PUBLIC,
                    Visibility.PRIVATE,
                    Visibility.PROTECTED,
                    Visibility.INTERNAL,
                    Visibility.PACKAGE
                )
            )

            skipEmptyPackages.set(true)
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["release"])

                groupId = rootProject.extra["groupName"] as String
                artifactId = "rasp-android"
                version = rootProject.extra["versionName"] as String
            }
        }
    }
}

/*mavenPublishing {
    group = rootProject.extra["groupName"] as String
    version = rootProject.extra["versionName"] as String
    coordinates("com.securevale", "rasp-android", rootProject.extra["versionName"] as String)

    publishToMavenCentral(SonatypeHost.S01, true)
    signAllPublications()

    pom {
        name.set("Android RASP")
        description.set("Runtime Application Self Protection library for Android.")
        inceptionYear.set("2023")
        url.set("https://github.com/securevale/android-rasp")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("bmaciejm")
                name.set("Bartosz Macięga")
                url.set("https://github.com/bmaciejm")
            }
        }

        scm {
            url.set("https://github.com/securevale/android-rasp")
            connection.set("scm:git:git://github.com/securevale/android-rasp.git")
            developerConnection.set("scm:git:ssh://git@github.com/securevale/android-rasp.git")
        }
    }
}*/
