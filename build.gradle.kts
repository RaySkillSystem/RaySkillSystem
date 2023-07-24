plugins {
    `java-library`
    `maven-publish`
    id("io.izzel.taboolib") version "1.56"
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
}

taboolib {
    install("common")
    install("common-5")
    install("module-ui")
    install("module-nms")
    install("module-nms-util")
    install("module-effect")
    install("module-ai")
    install("module-chat")
    install("module-configuration")
    install("platform-bukkit")
    install("expansion-command-helper")
    relocate("ink.ptms.um","top.maplex.rayskillsystem.um")
    classifier = null
    version = "6.0.11-27"
}

repositories {
    mavenCentral()
}

dependencies {
    taboo("ink.ptms:um:1.0.0-beta-30")
    compileOnly("ink.ptms:nms-all:1.0.0")
    compileOnly("ink.ptms.core:v11902:11902-minimize:mapped")
    compileOnly("ink.ptms.core:v11902:11902-minimize:universal")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
