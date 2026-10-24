# 1. Étape de compilation (Build)
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copie des fichiers du projet
COPY . .

# Donner les permissions d'exécution au script Gradle et compiler le serveur
RUN chmod +x gradlew
RUN ./gradlew server:build html:build --no-daemon

# 2. Étape d'exécution (Runtime)
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copie du fichier .jar compilé depuis l'étape précédente
COPY --from=build /app/server/build/libs/server-1.0.0.jar app.jar

# Configuration du port
EXPOSE 8080

# Lancement de l'application Java
CMD ["java", "-jar", "app.jar"]
