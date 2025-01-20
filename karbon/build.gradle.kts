import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "com.thebrownfoxx"
version = "0.1.0-alpha01"

kotlin {
    explicitApi()

    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

mavenPublishing {
    coordinates(
        groupId = group.toString(),
        artifactId = "karbon",
        version = version.toString(),
    )

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    pom {
        name = "Karbon"
        description = "A Kotlin DSL for composing Markdown"
        inceptionYear = "2024"
        url = "https://github.com/thebrownfoxx/karbon"
    }
}
