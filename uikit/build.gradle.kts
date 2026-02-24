plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
}

android {
    // ... твои android настройки

    publishing {
        singleVariant("release") {
            withSourcesJar()
            // withJavadocJar() // если надо, но для Android часто не используют
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                // В Kotlin DSL вот так:
                from(components["release"])

                groupId = "com.github.sonchouz"
                artifactId = "СomposeUiKit"
                version = "1.0.1" // JitPack подставит тег, но пусть будет
            }
        }
    }
}