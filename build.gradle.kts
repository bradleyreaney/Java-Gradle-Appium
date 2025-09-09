plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Test framework
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    
    // Appium dependencies
    implementation("io.appium:java-client:9.0.0")
    
    // Selenium WebDriver
    implementation("org.seleniumhq.selenium:selenium-java:4.15.0")
    
    // JSON processing for configuration
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.15.2")
    
    // Logging
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    
    // Assertions
    testImplementation("org.assertj:assertj-core:3.24.2")
    
    // TestNG (alternative to JUnit, commonly used with Appium)
    testImplementation("org.testng:testng:7.8.0")
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}