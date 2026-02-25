plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
    id("maven-publish")
}

android {
    // ... твои android настройки

    publishing {
        singleVariant("release") {
            withSourcesJar()
            // withJavadocJar() // если надо, но для Android часто не используют
        }
        namespace = "com.example.uikit" // любое твоё
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
    debugImplementation(libs.ui.tooling)
}


afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                // В Kotlin DSL вот так:
                from(components["release"])

                groupId = "com.github.sonchouz"
                artifactId = "СomposeUiKit"
                version = "1.0.2" // JitPack подставит тег, но пусть будет
            }
        }
    }
}