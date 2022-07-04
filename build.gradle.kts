plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa") apply false
    kotlin("kapt")
    id("io.spring.dependency-management")
    id("org.springframework.boot")
    jacoco
}

allprojects {
    repositories {
        mavenCentral()
    }

    group = "com.yolo.jean"    // 그룹명을 명시, 빌드시에 생략되도 영향이 없음.
    version = "0.0.1-SNAPSHOT" // bootJar Task 단계에서 생성된 jar 버전명을 명시함.
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "maven")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "jacoco")

    dependencies {
        kapt("org.springframework.boot:spring-boot-configuration-processor")
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
    }

    java.sourceCompatibility = JavaVersion.VERSION_11

    jacoco {
        toolVersion = "0.8.6"
    }

    tasks.jacocoTestReport {
        dependsOn(tasks.test)
        reports {
            html.isEnabled = true
            html.destination = file("$buildDir/reports/myReport.html")
            csv.isEnabled = true
            xml.isEnabled = false
        }

        var excludes = mutableListOf<String>()
        excludes.add("com/yolo/jean/config")
        excludes.add("com/yolo/jean/global")
        excludes.add("com/yolo/jean/kafka")
        excludes.add("com/yolo/jean/board/dto")
        excludes.add("com/yolo/jean/reply/dto")

        classDirectories.setFrom(
            sourceSets.main.get().output.asFileTree.matching {
                exclude(excludes)
            }
        )
        finalizedBy(tasks.jacocoTestCoverageVerification)
    }

    tasks.jacocoTestCoverageVerification {

        var Qdomains = mutableListOf<String>()

        for (qPattern in 'A' .. 'Z') {
            Qdomains.add("*.Q${qPattern}*")
        }

        violationRules {
            rule {
                enabled = true
                element = "CLASS"

                limit {
                    counter = "BRANCH"
                    value = "COVEREDRATIO"
                    minimum = "0.80".toBigDecimal()
                }

                limit {
                    counter = "LINE"
                    value = "TOTALCOUNT"
                    maximum = "200".toBigDecimal()
                }

                excludes = Qdomains
            }
        }

        var excludes = mutableListOf<String>()
        excludes.add("com/yolo/jean/config")
        excludes.add("com/yolo/jean/global")
        excludes.add("com/yolo/jean/reply/service/ReplyService.class")
        excludes.add("com/yolo/jean/board/service/BoardSearchService.class")

        classDirectories.setFrom(
            sourceSets.main.get().output.asFileTree.matching {
                exclude(excludes)
            }
        )
    }

    tasks.test {
        useJUnitPlatform()
        finalizedBy(tasks.jacocoTestReport)
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
}

tasks.bootJar {
    enabled = false
}




