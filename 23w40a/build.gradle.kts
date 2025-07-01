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
    version("23w40a")
    platform(MinecraftPlatform.SERVER)
}