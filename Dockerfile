# Étape 1 : Build de l'application avec JDK 17
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copie de l'ensemble du projet
COPY . .

# Donner les permissions d'exécution et compiler tout le projet
RUN chmod +x gradlew
RUN ./gradlew build --no-daemon -x test

# Étape 2 : Image d'exécution légère (JRE 17)
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copie du JAR compilé (ajuste le dossier si ton module s'appelle différemment)
COPY --from=build /app/server/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]