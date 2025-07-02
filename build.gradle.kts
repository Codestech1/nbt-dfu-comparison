subprojects {
    apply<JavaPlugin>()

    group = "net.hypejet"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenLocal()
        mavenCentral()
    }

    extensions.getByType<JavaPluginExtension>().apply {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        systemProperty("junit.jupiter.extensions.autodetection.enabled", true)
    }
}