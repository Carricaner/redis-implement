plugins {
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("redis.clients:jedis:4.3.1")
    // Data mapping
    val mapstructVersion = "1.5.2.Final"
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
    implementation("org.redisson:redisson:3.23.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register("writeProjectInfoToEnvFile") {
    group = "deploy"
    doLast {
        val version = project.version.toString()
        val projectName = project.name

        val envFile = file(".env")
        if (!envFile.exists()) {
            envFile.createNewFile()
            envFile.setExecutable(true)
        }
        envFile.appendText("PROJECT_NAME=$projectName")
        envFile.appendText("\nVERSION=$version")
    }
}