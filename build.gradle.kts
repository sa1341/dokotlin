plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa") apply false
    kotlin("kapt")
    id("io.spring.dependency-management")
    id("org.springframework.boot")
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "maven")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        kapt("org.springframework.boot:spring-boot-configuration-processor")
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
    }

    tasks.compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.compileTestKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.bootJar {
        enabled = false
    }

    tasks.jar {
        enabled = true
    }

    group = "com.yolo.jean"    // 그룹명을 명시, 빌드시에 생략되도 영향이 없음.
    version = "0.0.1-SNAPSHOT" // bootJar Task 단계에서 생성된 jar 버전명을 명시함.
    java.sourceCompatibility = JavaVersion.VERSION_11
}

tasks.bootJar {
    enabled = false
}


