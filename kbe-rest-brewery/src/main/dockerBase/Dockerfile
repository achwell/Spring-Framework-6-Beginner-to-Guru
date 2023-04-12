FROM openjdk:17-jdk-slim

ENV JAVA_OPTS " -Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

WORKDIR application

COPY ../../../target/${project.build.finalName}.jar ./

ENTRYPOINT ["java", "-jar", "${project.build.finalName}.jar"]
