plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("maven-publish")
}

android {
  namespace = "com.tatsuki.amazon"
  compileSdk = 33

  defaultConfig {
    minSdk = 21
    targetSdk = 33

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
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
  api("com.amazon.device:amazon-appstore-sdk:3.0.4")
  testImplementation("junit:junit:4.13.2")
}

publishing {
  publications {
    register<MavenPublication>("release") {
      groupId = "com.github.TatsukiIshijima"
      artifactId = "amazon-purchasing"
      version = "0.1.0-alpha"

      afterEvaluate {
        from(components["release"])
      }
    }
  }
}