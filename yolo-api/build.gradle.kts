plugins {
    id("org.asciidoctor.convert") version "1.5.8"
}

extra["springCloudVersion"] = "Hoxton.SR5"

dependencies {
    // api("org.springframework.boot:spring-boot-starter-webflux")
    api(project(":yolo-domain"))
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    //  implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.ehcache:ehcache:3.8.0")
    implementation("javax.cache:cache-api:1.0.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    api("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.retry:spring-retry")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation(project(":yolo-domain"))
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

val asciidocDir = "build/asciidoc"
val snippetsDir = file("build/generated-snippets").also { extra["snippetsDir"] = it }
val resourceDir = "src/main/resources"

tasks.test {
    outputs.dir(snippetsDir)
}

tasks.jar {
    enabled = false
}

tasks.bootJar {
    enabled = true

    val asciidoctor by tasks
    dependsOn(asciidoctor)

    copy {
        from("$asciidocDir/html5")
        into("$resourceDir/static/docs")
    }
}

tasks.register<Copy>("copyDocument") {
    from("$asciidocDir/html5")
    into("BOOT-INF/classes/static/docs")
}

tasks.asciidoctor {
    val test by tasks
    dependsOn(test)
}
