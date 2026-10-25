# Étape 1 : Build de l'application Web avec Gradle et Java 17
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copie des fichiers de configuration Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle gradle.properties ./
COPY core core
COPY teavm teavm

# Rendre le script executable et compiler le module TeaVM
RUN chmod +x gradlew
RUN ./gradlew :teavm:build --no-daemon -x test

# Étape 2 : Servir les fichiers statiques HTML/JS
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copie des fichiers compilés depuis l'étape de build
COPY --from=build /app/teavm/build/dist/webapp /app/www

EXPOSE 8080

CMD ["jwebserver", "-d", "/app/www", "-p", "8080", "-b", "0.0.0.0"]
