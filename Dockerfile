# Étape 1 : Build avec JDK 17
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x gradlew

# Compilation avec TeaVM en Java 17
RUN ./gradlew :teavm:build --no-daemon -x test

# Étape 2 : Serveur de fichiers statiques JRE 17
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copie des fichiers statiques générés
COPY --from=build /app/teavm/build/dist/webapp /app/www

EXPOSE 8080

CMD ["jwebserver", "-d", "/app/www", "-p", "8080", "-b", "0.0.0.0"]
