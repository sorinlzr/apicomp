import com.google.protobuf.gradle.id

plugins {
	id("java")
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	id("com.google.protobuf") version "0.9.4"
}

group = "net.pfsnc"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

extra["springGrpcVersion"] = "0.3.0-SNAPSHOT"

dependencies {
	implementation(platform("io.github.danielliu1123:grpc-starter-dependencies:3.4.1"))
	implementation("io.github.danielliu1123:grpc-boot-starter")
	implementation("io.grpc:grpc-testing-proto")
	implementation("com.google.protobuf:protobuf-java")
	implementation("com.google.protobuf:protobuf-java-util")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-graphql")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.liquibase:liquibase-core")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework:spring-webflux")
	testImplementation("org.springframework.graphql:spring-graphql-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.grpc:spring-grpc-dependencies:${property("springGrpcVersion")}")
	}
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc"
	}
	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java"
		}
	}
	generateProtoTasks {
		all().forEach { task ->
			task.plugins {
				id("grpc") {
					option("jakarta_omit")
					option("@generated=omit")
				}
			}
		}
	}
}

sourceSets {
	main {
		java {
			srcDirs("build/generated/source/proto/main/grpc", "build/generated/source/proto/main/java")
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}