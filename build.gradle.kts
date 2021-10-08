import org.jetbrains.kotlin.contracts.model.structure.UNKNOWN_COMPUTATION.type
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.10.RELEASE"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.asciidoctor.convert") version "1.5.10"
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.spring") version "1.4.10"
    kotlin("plugin.jpa") version "1.4.10"
    kotlin("kapt") version "1.4.10"
}

repositories {
    mavenCentral()
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

group = "com.yolo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val snippetsDir = file("build/generated-snippets").also { extra["snippetsDir"] = it }

dependencies {
    implementation("com.querydsl:querydsl-jpa")
    kapt("com.querydsl:querydsl-apt:4.2.2:jpa")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-cache")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly ("mysql:mysql-connector-java")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("org.springframework.boot:spring-boot-starter-webflux")
    //api("org.springframework.boot:spring-boot-starter-data-redis")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

sourceSets["main"].withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
    kotlin.srcDir("$buildDir/generated/source/kapt/main")
}


tasks.register<Copy>("copyDocument") {
    from("build/asciidoc/html5")
    into("BOOT-INF/classes/static/docs")
}

tasks {

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    test {
        outputs.dir(snippetsDir)
    }

    asciidoctor {
        inputs.dir(snippetsDir)
        dependsOn(test)
    }

    asciidoctor {
        outputDir = file("$build/asciidoc")
        doLast {
            copy {
                from("$build/asciidoc/html5") // asciidoctor로 변환된 html의 위치
                into("src/main/resources/static/docs") // 변환된 api 문서 html을 이동시켜 서빙할 위치
            }
        }
    }

    bootJar {
        dependsOn(asciidoctor)
        doLast {
            copy {
                from("${asciidoctor.get().outputDir}/html5")
                    .into("BOOT-INF/classes/static/docs")
            }
        }
    }
}

