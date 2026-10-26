# Étape 1 : Compilation Web avec GWT
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew :html:dist --no-daemon -x test

# Étape 2 : Serveur Web de production NGINX
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

RUN rm -rf ./*

# Copier la distribution GWT générée
COPY --from=build /app/html/build/dist .

# LA CORRECTION : Détruire le dossier assets potentiellement corrompu par Gradle,
# et recréer un fichier de 0 octet avec les bonnes permissions
RUN rm -rf assets && \
    mkdir assets && \
    touch assets/assets.txt && \
    chmod -R 755 assets

EXPOSE 80

CMD ["sh", "-c", "sed -i 's/listen  *80;/listen '\"${PORT:-8080}\"';/g' /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
