import java.io.FileInputStream
import java.util.Properties

plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("maven-publish")
}

val libVersionsPropertiesFile = rootProject.file("libversions.properties")
val libVersionsProperties = Properties()
libVersionsProperties.load(FileInputStream(libVersionsPropertiesFile))

android {
  namespace = "com.tatsuki.purchasing.feature"
  compileSdk = 34

  defaultConfig {
    minSdk = 21
    targetSdk = 34

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  testOptions {
    unitTests {
      all {
        it.apply {
          jvmArgs("-noverify")
        }
      }
    }
  }
}

dependencies {
  implementation(project(":core"))
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
  testImplementation(project(":fake"))
  testImplementation("junit:junit:4.13.2")
  testImplementation("androidx.test.ext:junit-ktx:1.1.5")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.2")
  testImplementation("org.robolectric:robolectric:4.9")
}

publishing {
  publications {
    register<MavenPublication>("release") {
      version = libVersionsProperties["VERSION_NAME"] as String

      afterEvaluate {
        from(components["release"])
      }
    }
  }
}