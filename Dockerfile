# Étape 1 : Build de l'application
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x gradlew

# On compile le module exécutable (lwjgl3 ou teavm) en ignorant android
RUN ./gradlew :lwjgl3:jar --no-daemon -x test -x :android:build

# Étape 2 : Image d'exécution légère JRE 17
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copie du JAR exécutable généré par lwjgl3
COPY --from=build /app/lwjgl3/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
