# Étape 1 : Compilation Web avec GWT
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew :html:dist --no-daemon -x test

# Étape 2 : Serveur HTTP de production
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/html/build/dist /app/www

EXPOSE 8080

CMD ["jwebserver", "-d", "/app/www", "-p", "8080", "-b", "0.0.0.0"]
