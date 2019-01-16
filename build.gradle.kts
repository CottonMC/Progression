plugins {
    kotlin("jvm") version "1.3.10"
}
group = "io.github.cottonmc.advancements"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    testCompile("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testCompile("org.junit.jupiter:junit-jupiter-params:5.3.1")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testCompile("org.mockito:mockito-core:2.+")
}
