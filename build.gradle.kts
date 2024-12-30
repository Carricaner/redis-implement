// TODO: Remove once KTIJ-19369 is fixed
// Ref: https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    application
    alias(libs.plugins.springBootPlugin)
    alias(libs.plugins.springDependencyManagementPlugin)
    id("io.freefair.lombok") version "8.10.2"
}

group = "org.example"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Entity Mapping
    implementation(libs.mapStruct)
    annotationProcessor(libs.mapStructProcessor)
    testAnnotationProcessor(libs.mapStructProcessor)

    // Guava
    implementation(libs.guava)

    // Functional programming
    implementation(libs.vavr)
    implementation(libs.vavrTest)

    // Test
    testImplementation(libs.junit)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.11.1")
    implementation(platform("org.testcontainers:testcontainers:1.20.4"))
    testImplementation("org.testcontainers:junit-jupiter:1.20.4")


    // Redis
    implementation("redis.clients:jedis:4.4.0")
    implementation("org.redisson:redisson:3.23.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileJava {
    options.compilerArgs.addAll(
            listOf(
                    "-Amapstruct.defaultComponentModel=spring",
                    "-Amapstruct.defaultInjectionStrategy=constructor"
            )
    )
}