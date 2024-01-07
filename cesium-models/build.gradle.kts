plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "com.thebrownfoxx"
version = "0.0.1"

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.3.7")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}