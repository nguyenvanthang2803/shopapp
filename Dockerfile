
FROM openjdk:17-oracle
WORKDIR /app
COPY ./target/ShopApp-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "ShopApp-0.0.1-SNAPSHOT.jar"]