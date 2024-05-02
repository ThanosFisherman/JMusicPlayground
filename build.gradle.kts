import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    kotlin("jvm") version "1.9.23"
    java
}

group = "io.github.thanosfisherman"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(files("libs/jMusic1.6.5.jar"))
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.getByName<Jar>("jar") {
    dependsOn(configurations.getByName("runtimeClasspath"))
    // sets the name of the .jar file this produces to the name of the game or app.
    archiveFileName.set(project.name)
    val destDir = file(project.layout.buildDirectory.asFile.get().absolutePath + File.separator + "lib")
    // using 'lib' instead of the default 'libs' appears to be needed by jpackageimage.
    destinationDirectory.set(destDir)
    // the duplicatesStrategy matters starting in Gradle 7.0; this setting works.
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    println(configurations)

    from(configurations.get("runtimeClasspath").map { if (it.isDirectory) it else zipTree(it) })
    // these "exclude" lines remove some unnecessary duplicate files in the output JAR.
    excludes.apply {
        add("META-INF/INDEX.LIST")
        add("META-INF/*.SF")
        add("META-INF/*.DSA")
        add("META-INF/*.RSA")
    }
    dependencies {
        exclude("META-INF/INDEX.LIST", "META-INF/maven/**")
    }
    // setting the manifest makes the JAR runnable.
    manifest {
        attributes["Main-Class"] = "io.github.thanosfisherman.fractaloid.MainKt"
    }

    // this last step may help on some OSes that need extra instruction to make runnable JARs.
    doLast {
        file(archiveFile).setExecutable(true, false)
    }
}
