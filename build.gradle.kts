plugins {
    kotlin("jvm") version "2.4.0"
    kotlin("plugin.serialization") version "2.4.0"
    id("dev.iurysouza.modulegraph") version "0.13.0"
    id("io.github.euledge.code-atlas") version "1.2.0" apply false
}

group = "io.cuttlefish"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "io.github.euledge.code-atlas")

    extensions.configure<com.euledge.codeatlas.CodeAtlasExtension> {
        formats.set(listOf("mermaid"))

        // Use relative pathing to step up out of the submodule directory
        outputDir.set("../docs/diagrams/${project.name}")

        groupByPackage.set(true)
        showDetails.set(true)
    }

    tasks.named("generateDiagrams") {
        tasks.findByName("compileKotlin")?.let { compileTask ->
            dependsOn(compileTask)
        }

        doFirst {
            val kotlinDir = layout.buildDirectory.dir("classes/kotlin/main").get().asFile
            val javaDir = layout.buildDirectory.dir("classes/java/main").get().asFile

            if (kotlinDir.exists()) {
                javaDir.mkdirs()
                kotlinDir.copyRecursively(javaDir, overwrite = true)
            }
        }
    }
}

//moduleGraphConfig {
//    linkText.set(dev.iurysouza.modulegraph.LinkText.CONFIGURATION)
//    setStyleByModuleType.set(true)
//}
//codeAtlas {
//    formats.set(listOf("mermaid"))  // Strictly outputs .mmd files
//    outputDir.set("docs/diagrams")   // Where the diagram will be saved
//    groupByPackage.set(true)         // Groups your classes inside boxes by package
//    showDetails.set(true)           // Set to true to see fields/methods; false for just the class boxes
//
//    // Optional: Only scan your specific root package to keep the diagram clean
//    // rootPackages.set(listOf("io.cuttlefish"))
//}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
    testImplementation(kotlin("test"))
}

sourceSets {
    main {
        kotlin {
            exclude("**/C Assist/**")
        }
    }
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}