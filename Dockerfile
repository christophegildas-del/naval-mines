# Étape 1 : Build du serveur uniquement avec JDK 17
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

# Donner les permissions d'exécution
RUN chmod +x gradlew

# Exécuter le build UNIQUEMENT sur le sous-projet server (ignore Android)
RUN ./gradlew :html:build --no-daemon -x test

# Étape 2 : Image d'exécution légère (JRE 17)
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copier le JAR du serveur compilé
COPY --from=build /app/server/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]