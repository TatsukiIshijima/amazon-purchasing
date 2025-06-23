import java.io.FileInputStream
import java.util.Properties

plugins {
  id("maven-publish")
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.ksp)
}

val libVersionsPropertiesFile = rootProject.file("libversions.properties")
val libVersionsProperties = Properties()
libVersionsProperties.load(FileInputStream(libVersionsPropertiesFile))

android {
  namespace = "com.tatsuki.purchasing.feature"
  compileSdk = 35

  defaultConfig {
    minSdk = 21

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
  implementation(libs.kotlinx.coroutines.core)
  testImplementation(project(":fake"))
  testImplementation(libs.junit)
  testImplementation(libs.androidx.junit.ktx)
  testImplementation(libs.kotlinx.coroutines.test)
  testImplementation(libs.robolectric)
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