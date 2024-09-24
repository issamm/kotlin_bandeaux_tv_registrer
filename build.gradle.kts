plugins {
    kotlin("jvm") version "2.0.20"
    id("application")
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
    implementation("com.github.pgreze:kotlin-process:1.5")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
application {
    mainClass = "dev.issam.bandeauxtv.registrer.MainKt"
}
