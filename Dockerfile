FROM openjdk:22-jdk
WORKDIR /app
COPY Wallet_SecondDB /app/oracle_wallet/
COPY target/b1efta-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]