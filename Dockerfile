# Étape 1 : Compilation Web avec GWT
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew :html:dist --no-daemon -x test

# Étape 2 : Serveur Web de production NGINX
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Nettoyer les fichiers NGINX par défaut
RUN rm -rf ./*

# Copier les fichiers statiques générés (index.html, JS et assets/assets.txt)
COPY --from=build /app/html/build/dist .

EXPOSE 80

CMD ["sh", "-c", "sed -i 's/listen  *80;/listen '\"${PORT:-8080}\"';/g' /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
