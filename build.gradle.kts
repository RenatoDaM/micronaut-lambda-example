plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.20"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.20"
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.2.0"
    id("io.micronaut.aot") version "4.2.0"
}

version = "0.1"
group = "com.example"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {
    ksp("io.micronaut:micronaut-http-validation")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.aws:micronaut-function-aws-custom-runtime:4.1.1")
    implementation("io.micronaut.aws:micronaut-aws-lambda-events-serde")
    implementation("io.micronaut.crac:micronaut-crac")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("io.micronaut:micronaut-http-client")
}


application {
    mainClass.set("com.example.FunctionLambdaRuntime")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}


micronaut {
    runtime("lambda_provided")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.example.*")
    }
    nativeLambda {
        lambdaRuntimeClassName = "com.example.FunctionLambdaRuntime"
    }
}

tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    baseImage.set("amazonlinux:2")
    args(
        "-XX:MaximumHeapSizePercent=80",
        "-Dio.netty.allocator.numDirectArenas=0",
        "-Dio.netty.noPreferDirect=true"
    )
}



