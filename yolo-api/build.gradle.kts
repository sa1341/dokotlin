dependencies {
    //api("org.springframework.boot:spring-boot-starter-data-redis")
    //api("org.springframework.boot:spring-boot-starter-webflux")
    api(project(":yolo-domain"))
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.ehcache:ehcache:3.8.0")
    implementation("javax.cache:cache-api:1.0.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    api("org.springframework.boot:spring-boot-starter-validation")
    implementation ("org.springframework.retry:spring-retry")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

//val snippetsDir = file("build/generated-snippets").also { extra["snippetsDir"] = it }

tasks.jar {
    enabled = false
}


tasks.bootJar {
    enabled = true
}


