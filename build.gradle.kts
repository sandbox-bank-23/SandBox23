import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.detekt)
}

tasks.withType<Detekt>().configureEach {
    parallel = true
    autoCorrect = false
    buildUponDefaultConfig = true
    disableDefaultRuleSets = false

    jvmTarget = "17"

    setSource(files("app/src"))
    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**")

    config.setFrom(files(project.rootDir.resolve("conf/custom-detekt.yml")))

    reports {
        xml.required.set(false)
        html.required.set(true)
        txt.required.set(true)
    }
}

tasks.register<Detekt>("detektAll") {
    description = "Runs Detekt on the app module"
    group = "verification"
    parallel = true
    buildUponDefaultConfig = true
    jvmTarget = "17"

    setSource(files("app/src"))
    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**")

    config.setFrom(files(project.rootDir.resolve("conf/custom-detekt.yml")))

    reports {
        html.required.set(true)
        txt.required.set(true)
    }
}

tasks.register<Detekt>("detektFormat") {
    description = "Runs auto formatting accoring to detekt"
    group = "clean-up"

    parallel = true
    autoCorrect = true
    disableDefaultRuleSets = false
    buildUponDefaultConfig = false
    jvmTarget = "17"

    setSource(files("app/src"))
    include("**/*.kt")
    exclude("**/resources/**", "**/build/**")

    config.setFrom(files(project.rootDir.resolve("conf/custom-detekt.yml")))

    reports {
        html.required.set(true)
        txt.required.set(true)
    }
}

dependencies {
    detektPlugins(libs.staticAnalysis.detektFormatting)
    detektPlugins(libs.staticAnalysis.detektLibraries)
}
