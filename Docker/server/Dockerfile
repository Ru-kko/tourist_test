FROM maven:3.8.5-openjdk-17

WORKDIR /app

COPY Server /app

ENV MSSQL_USER=sa
ENV MSSQL_SA_PASSWORD=Password1
ENV TOKEN_SECRET=thisisnotsecure-tokenpassword-pleasechange-it
ENV MSSQL_HOST=localhost

RUN mvn clean package -DskipTests

EXPOSE 90 

CMD ["java", "-jar", "/app/target/app-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]