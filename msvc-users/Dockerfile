FROM amazoncorretto:21-alpine-jdk as builder

WORKDIR /app/msvc-users

COPY ./pom.xml /app
COPY ./msvc-users/.mvn ./.mvn
COPY ./msvc-users/mvnw .
COPY ./msvc-users/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

COPY ./msvc-users/src ./src

RUN ./mvnw clean package -DskipTests

FROM amazoncorretto:21-alpine-jdk

WORKDIR /app
RUN mkdir ./logs

COPY --from=builder /app/msvc-users/target/msvc-users-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

CMD ["java", "-jar", "msvc-users-0.0.1-SNAPSHOT.jar"]