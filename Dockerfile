FROM openjdk:8-alpine
WORKDIR /app
COPY . .
RUN ./gradlew clean build -xcheck

CMD java -jar ./build/libs/*.jar