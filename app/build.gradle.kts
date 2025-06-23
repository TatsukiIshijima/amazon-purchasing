plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.dagger.hilt.android)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.ksp)
}

// If release build, please disable comment out.
//val keystorePropertiesFile = rootProject.file("local.properties")
//val keystoreProperties = Properties()
//keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
  namespace = "com.tatsuki.amazonpurchasingsample"
  compileSdk = 35

  // If release build, please disable comment out.
//  signingConfigs {
//    create("release") {
//      keyAlias = keystoreProperties["keyAlias"] as String
//      keyPassword = keystoreProperties["keyPassword"] as String
//      storeFile = file(keystoreProperties["storeFile"] as String)
//      storePassword = keystoreProperties["storePassword"] as String
//    }
//  }

  defaultConfig {
    applicationId = "com.tatsuki.amazonpurchasingsample"
    minSdk = 21
    versionCode = 5
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }

    setProperty("archivesBaseName", "${applicationId}-${versionName}(${versionCode})")
  }

  buildTypes {
    debug {
      isDebuggable = true
      applicationIdSuffix = ".debug"
    }
    release {
      isDebuggable = false
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

      // If release build, please disable comment out.
//      signingConfig = signingConfigs.getByName("release")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.3.2"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(platform(libs.kotlin.bom))
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.hilt.android)
  implementation(project(":core"))
//  implementation("com.github.TatsukiIshijima.amazon-purchasing:core:develop-SNAPSHOT")
  implementation(project(":feature"))
//  implementation("com.github.TatsukiIshijima.amazon-purchasing:feature:develop-SNAPSHOT")
  ksp(libs.hilt.android.compiler)
  testImplementation(project(":fake"))
//  implementation("com.github.TatsukiIshijima.amazon-purchasing:fake:develop-SNAPSHOT")
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}