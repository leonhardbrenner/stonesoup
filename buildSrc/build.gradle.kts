plugins{
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.squareup:kotlinpoet:1.5.0")
    implementation(kotlin("test-junit"))
}
