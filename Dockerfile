# 1. Image pour la compilation avec JDK 17
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copie de tout le code du projet
COPY . .

# Rendre le script Gradle exécutable
RUN chmod +x gradlew

# On compile UNIQUEMENT le module core (la logique métier du jeu)
# et on exclut explicitement le module Android (-x :android:build)
RUN ./gradlew :core:build --no-daemon -x test -x :android:build

# 2. Image légère pour exécuter le serveur
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copie du binaire compilé
COPY --from=build /app/core/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
