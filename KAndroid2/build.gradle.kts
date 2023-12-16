plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("maven-publish")
}

group = "com.github.KORuL"
version = "1.0.0"

android {
    namespace = "ru.korul.kandroid2"
    compileSdk = 34

    defaultConfig {
        minSdk = 16

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        multiDexEnabled = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
        freeCompilerArgs += "-Xcontext-receivers"
        freeCompilerArgs += "-opt-in=kotlin.Experimental"
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
}

dependencies {
    // Kotlin coroutines
    implementation(libs.bundles.kotlin.coroutines)
    // Material
    implementation(libs.androidx.material)
    // Timber
    implementation(libs.timber)
    // Annotation
    implementation(libs.androidx.annotation)
    // preference
    implementation(libs.androidx.preference.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

afterEvaluate {
    publishing {
        publications {
            register("mavenJava", MavenPublication::class) {
                from(components["release"])
                groupId = "com.github.KORuL"
                artifactId = "KAndroid2"
                version = "1.0.0"
            }
        }
    }
}