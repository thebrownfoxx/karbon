import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        freeCompilerArgs.add("-Xmulti-dollar-interpolation")
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.karbon)
        }
    }
}