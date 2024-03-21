plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.credit_risk_eval_badri_v01"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.credit_risk_eval_badri_v01"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//    implementation("com.google.firebase:firebase-auth-ktx:22.1.2")
//    implementation("com.google.firebase:firebase-database-ktx:20.2.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))
    implementation("com.google.firebase:firebase-analytics-ktx")

    //auth
    implementation("com.google.firebase:firebase-auth-ktx")

    //realtime db
    implementation("com.google.firebase:firebase-database-ktx")

    //storage
    implementation("com.google.firebase:firebase-storage-ktx")

    //glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    //circle imageview
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //safety net
    implementation ("com.google.android.gms:play-services-safetynet:18.0.1")

    //integrity
    implementation ("com.google.android.play:integrity:1.2.0")

    //api handling
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //pdf reader
//    implementation ("com.github.barteksc:android-pdf-viewer:3.2.0-beta.1")
//    implementation ("com.github.barteksc:android-pdf-viewer:2.8.2")

    //firebase ui
    implementation ("com.google.firebase:firebase-database:20.0.2")
    implementation ("com.firebaseui:firebase-ui-database:7.1.1")

}