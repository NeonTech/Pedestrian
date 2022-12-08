import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.ALL

plugins {
    java

    id("io.spring.dependency-management") version "1.1.0"
    id("org.springframework.boot") version "3.0.0"

    id("com.google.cloud.tools.jib") version "3.3.1"
}

val bouncyCastleVersion: String = "1.72"
val gradleVersion: String = "7.6"
val gradleWrapperVersion: String = gradleVersion
val immutablesVersion: String = "2.9.2"
val javaVersion: String = "19"
val pedestrianVersion: String = "0.0.1.0"

group = "dev.neontech"
version = pedestrianVersion

java {
    sourceCompatibility = JavaVersion.toVersion(javaVersion)
    targetCompatibility = sourceCompatibility
}

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.immutables:value:$immutablesVersion")
    compileOnly("org.immutables:value:$immutablesVersion")

    implementation("org.bouncycastle:bcprov-jdk18on:$bouncyCastleVersion")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

jib {
    to {
        image = "rg.nl-ams.scw.cloud/dreamexposure/pedestrian"
        tags = mutableSetOf("latest", pedestrianVersion)
    }
    from.image = "eclipse-temurin:19-jre-alpine"
}

tasks {
    wrapper {
        distributionType = ALL
        gradleVersion = gradleWrapperVersion
    }

    withType<Test> {
        useJUnitPlatform()
    }
}
