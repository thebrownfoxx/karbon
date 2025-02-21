@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "com.thebrownfoxx"
version = properties["version"]!!

kotlin {
    explicitApi()

    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()
    macosX64()
    macosArm64()
    mingwX64()
    js {
        browser()
        nodejs()
    }
    wasmJs {
        browser()
        nodejs()
        d8()
    }
    wasmWasi {
        nodejs()
    }

    sourceSets {
        commonMain.dependencies {}
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

//mavenPublishing {
//    coordinates(
//        groupId = group.toString(),
//        artifactId = "karbon",
//        version = version.toString(),
//    )
//
//    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
//
//    signAllPublications()
//
//    pom {
//        name = "Karbon"
//        description = "A Kotlin DSL for composing Markdown"
//        inceptionYear = "2024"
//        url = "https://github.com/thebrownfoxx/karbon"
//
//        licenses {
//            license {
//                name = "The Apache License, Version 2.0"
//                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
//                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
//            }
//        }
//        developers {
//            developer {
//                id = "thebrownfoxx"
//                name = "Hamuel Agulto"
//                url = "https://github.com/thebrownfoxx"
//            }
//        }
//        scm {
//            url = "https://github.com/thebrownfoxx/karbon"
//            connection = "scm:git:git://github.com/thebrownfoxx/karbon.git"
//            developerConnection = "scm:git:ssh://git@github.com/thebrownfoxx/karbon.git"
//        }
//    }
//}
