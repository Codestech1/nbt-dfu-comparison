rootProject.name = "nbt-dfu-comparison"

pluginManagement {
    repositories {
        maven("https://repo.spongepowered.org/repository/maven-public/")
    }
}

sequenceOf("23w40a").forEach {
    include("nbt-dfu-comparison-$it")
    project(":nbt-dfu-comparison-$it").projectDir = file(it)
}