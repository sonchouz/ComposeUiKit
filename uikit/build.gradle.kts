plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
    id("maven-publish")
}

android {


    publishing {
        singleVariant("release") {
            withSourcesJar()

        }
        namespace = "com.example.uikit"
        compileSdk = 35

        defaultConfig {
            minSdk = 28
        }
        buildFeatures {
            compose = true
        }

    }
}
dependencies {
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.ui.text)
    implementation(libs.androidx.material3)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.ui.tooling)
}


afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.sonchouz"
                artifactId = "ComposeUiKit"
                version = "1.0.4"
            }
        }
    }
}