# Étape 1 : Compilation Web avec GWT
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew :html:dist --no-daemon -x test

# Étape 2 : Serveur Web de production ultra-léger avec NGINX
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Nettoyer les fichiers par défaut de NGINX
RUN rm -rf ./*

# Copier les fichiers statiques générés par GWT
COPY --from=build /app/html/build/dist .

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
