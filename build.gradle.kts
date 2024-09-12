plugins {
    kotlin("jvm") version "2.0.0"
}

group = "dev.issam.bandeauxtvrecorder"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.1.4")
    implementation("com.natpryce:konfig:1.6.10.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
