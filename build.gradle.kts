import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
//import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

val kotlinVersion = "1.5.20"
val kotlinJsVersion = "1.5.20"
val serializationVersion = "1.2.1"
val ktorVersion = "1.6.3"
val logbackVersion = "1.2.3"
val kmongoVersion = "4.2.7"
val reactWrappersVersion = "17.0.2-pre.214-kotlin-1.5.20"

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    //maven("https://dl.bintray.com/kotlin/kotlin-eap")
    //mavenLocal()
    mavenCentral()
    //jcenter()
    //maven("https://dl.bintray.com/kotlin-js-wrappers/") // react, styled, ...
    //maven { setUrl("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
}

plugins {
    kotlin("multiplatform") version "1.5.20-RC"
    application //to run JVM part
    kotlin("plugin.serialization") version "1.5.20"
}

//apply(plugin="kotlin-kapt")

kotlin {
    jvm {
        withJava()
    }
    js {
        browser {
            binaries.executable()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-serialization:$ktorVersion")
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("io.ktor:ktor-html-builder:$ktorVersion")
                implementation("ch.qos.logback:logback-classic:1.2.3")
                implementation("io.ktor:ktor-websockets:$ktorVersion")
                implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.2.7")
                implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.0")

                //TODO - upgrade to 5.0.1 to get rid of this warning:
                //    Illegal reflective access by com.google.inject.internal
                //This is described in more detail here:
                //    https://stackoverflow.com/questions/58165973/how-to-fix-guice-error-an-illegal-reflective-access-operation-has-occurred
                implementation("com.google.inject:guice:5.0.1")
                implementation("com.authzee.kotlinguice4:kotlin-guice:1.3.0")

                //TODO - investigate if this actually works
                implementation("io.mockk:mockk:1.10.4")

                //https://github.com/JetBrains/Exposed/wiki/Getting-Started
                //TODO - This belongs in gradle.properties
                val exposed_version = "0.31.1"
                implementation( "org.jetbrains.exposed:exposed-core:$exposed_version")
                implementation( "org.jetbrains.exposed:exposed-dao:$exposed_version")
                implementation( "org.jetbrains.exposed:exposed-jdbc:$exposed_version")
                implementation("com.h2database:h2:1.4.199")
                // for logging (StdOutSqlLogger), see
                // http://www.slf4j.org/codes.html#StaticLoggerBinder
                implementation("org.slf4j:slf4j-nop:1.7.30")
                //implementation("org.jetbrains.exposed:exposed:0.17.13")
                implementation("com.zaxxer:HikariCP:2.7.8")
                implementation("org.postgresql:postgresql:42.2.2")
                //implementation("org.flywaydb:flyway-core:5.2.4")

                implementation("org.jetbrains.kotlin:kotlin-reflect")
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
                implementation("com.squareup:kotlinpoet:1.5.0")

                implementation("com.github.doyaaaaaken:kotlin-csv-jvm:0.15.2")
                implementation("io.github.blackmo18:kotlin-grass-jvm:0.7.1")

                //Consider removing this since we already have a json library
                // https://mvnrepository.com/artifact/io.dropwizard/dropwizard-jackson
                implementation("io.dropwizard:dropwizard-jackson:1.1.2")

            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("io.mockk:mockk:1.10.4")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:$ktorVersion") //include http&websockets

                //ktor client js json
                implementation("io.ktor:ktor-client-json-js:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization-js:$ktorVersion")

                implementation(kotlin("stdlib-js", kotlinVersion))
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$reactWrappersVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$reactWrappersVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.0-pre.214-kotlin-1.5.20")
                //implementation(npm("@material-ui/core", "4.11.1"))
                //implementation(npm("@material-ui/lab", "4.0.0-alpha.56"))
                //implementation(npm("@material-ui/icons", "4.9.1"))
                implementation(npm("react-hot-loader", "^4.12.20"))

                //implementation(npm("react-hot-loader", "^4.12.20"))
                //implementation("org.jetbrains:kotlin-react:17.0.2-pre.204-kotlin-1.5.0")
                //implementation("org.jetbrains:kotlin-react-dom:17.0.2-pre.204-kotlin-1.5.0")
                //implementation(npm("react", "16.13.1"))
                //implementation(npm("react-dom", "16.13.1"))

                //implementation(npm("styled-components", "~5.1.1"))
                //implementation(npm("inline-style-prefixer", "~6.0.0"))

                /**
                 * Material UI support:
                 *     https://github.com/cfnz/muirwik
                 *     https://github.com/cfnz/muirwik-starterapp
                 */
                implementation("com.ccfraser.muirwik:muirwik-components:0.8.2")
                //implementation(npm("react-player", "~2.6.0"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

    }
}

application {
    mainClassName = "ServerKt"
}

tasks.register<ModelGenerator>("generate")
tasks.register<ModelGeneratorDsl>("generateDsl")

tasks.register<PlanPrioritize>("prioritize")

// include JS artifacts in any JAR we generate
tasks.getByName<Jar>("jvmJar") {
    val taskName = if (project.hasProperty("isProduction")) {
        "jsBrowserProductionWebpack"
    } else {
        "jsBrowserDevelopmentWebpack"
    }
    val webpackTask = tasks.getByName<KotlinWebpack>(taskName)
    dependsOn(webpackTask) // make sure JS gets compiled first
    from(File(webpackTask.destinationDirectory, webpackTask.outputFileName)) // bring output file along into the JAR
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

distributions {
    main {
        contents {
            from("$buildDir/libs") {
                rename("${rootProject.name}-jvm", rootProject.name)
                into("lib")
            }
        }
    }
}

// Alias "installDist" as "stage" (for cloud providers)
tasks.create("stage") {
    dependsOn(tasks.getByName("installDist"))
}

tasks.getByName<JavaExec>("run") {
    classpath(tasks.getByName<Jar>("jvmJar")) // so that the JS artifacts generated by `jvmJar` can be found and served
}