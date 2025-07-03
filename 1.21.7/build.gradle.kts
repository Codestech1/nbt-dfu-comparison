import org.spongepowered.gradle.vanilla.repository.MinecraftPlatform

plugins {
    alias(libs.plugins.vanillaGradle)
}

dependencies {
    testImplementation(libs.adventureNbtSerializer)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform)
}

minecraft {
    version("1.21.7")
    platform(MinecraftPlatform.SERVER)
}