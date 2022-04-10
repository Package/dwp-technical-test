FROM adoptopenjdk/openjdk11:alpine

RUN apk add --no-cache maven

WORKDIR /app

COPY pom.xml ./

RUN mvn dependency:go-offline

COPY src ./src

CMD ["mvn", "spring-boot:run"]