# Étape 1 : Build de l'application Web avec Gradle et Java 17
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copie de l'intégralité du projet
COPY . .

# Rendre le script exécutable et compiler le module TeaVM
RUN chmod +x gradlew
RUN ./gradlew :teavm:build --no-daemon -x test

# Étape 2 : Servir les fichiers statiques HTML/JS
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copie des fichiers HTML/JS/WASM générés dans build/dist
COPY --from=build /app/teavm/build/dist /app/www

EXPOSE 8080

CMD ["jwebserver", "-d", "/app/www", "-p", "8080", "-b", "0.0.0.0"]
