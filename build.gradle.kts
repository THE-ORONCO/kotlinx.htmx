val kotlin_version: String by project
val logback_version: String by project
val kotlinx_html_version: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.11"
}

group = "the.oronco"
version = "0.0.1"

application {
    mainClass.set("the.oronco.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
}

dependencies {
    // ktor core
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-html-builder-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-compression")

    // html
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinx_html_version")
    implementation("org.jetbrains:kotlin-css-jvm:1.0.0-pre.129-kotlin-1.4.20")
    // webjars
    implementation("io.ktor:ktor-server-webjars-jvm")
    implementation("org.webjars.npm:htmx.org:2.0.2")
    implementation("org.webjars:bootstrap:5.2.3")
    // hints
    compileOnly("org.jetbrains:annotations:24.1.0")
    // logging
    implementation("ch.qos.logback:logback-classic:$logback_version")
    // testing
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
