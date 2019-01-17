plugins {
    java
    id("com.jfrog.artifactory") version "4.9.0"
    `maven-publish`
}
group = "io.github.cottonmc.advancements"
version = "1.0.0"

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

tasks.create<Zip>("sourcesJar") {
    extension = "jar"
    classifier = "sources"
    from("src/main/java")
    from("src/main/resources")
}

//the artifactory block is written in the groovy dsl
apply(from = "artifactory.gradle")

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
        }
    }
}