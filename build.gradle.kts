import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val kotlin_version: String by project
val logback_version: String by project
val kotlinx_html_version: String by project

plugins {
    kotlin("jvm") version "2.0.20"
    id("io.ktor.plugin") version "2.3.12"
}

group = "the.oronco"
version = "0.0.1"

application {
    mainClass.set("the.oronco.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

kotlin{
    compilerOptions{
        jvmTarget = JvmTarget.JVM_21
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
}

dependencies {
    // ktor core
    // runner
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-html-builder-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    // status
    implementation("io.ktor:ktor-server-status-pages")
    // data handling
    implementation("io.ktor:ktor-server-request-validation")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    // perf
    implementation("io.ktor:ktor-server-compression")
    // headers
    implementation("io.ktor:ktor-server-caching-headers")
    implementation("io.ktor:ktor-server-auto-head-response")
    implementation("io.ktor:ktor-server-conditional-headers")
    implementation("io.ktor:ktor-server-forwarded-header")
    implementation("io.ktor:ktor-server-hsts")
    // content handling
    // TODO use "io.ktor:ktor-server-i18n" as soon as it is officially released
    // html
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinx_html_version")
    implementation("org.jetbrains:kotlin-css-jvm:1.0.0-pre.129-kotlin-1.4.20")

    // webjars
    // TODO get rid of java dependencies so that I can run this server completely natively (see https://ktor.io/docs/server-native.html#native-target)
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
