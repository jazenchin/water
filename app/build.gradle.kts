plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-android")
    id ("kotlin-kapt")
}

android {
    namespace = "com.lhr.water"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.lhr.water"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // LiveData
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.2.0")
    //room
    implementation ("androidx.room:room-runtime:2.2.1")
    annotationProcessor ("androidx.room:room-compiler:2.2.1")
    testImplementation ("androidx.room:room-testing:2.2.1")
    kapt ("androidx.room:room-compiler:2.2.1")
    implementation ("androidx.room:room-ktx:2.2.1")
    //CircleImageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")
}