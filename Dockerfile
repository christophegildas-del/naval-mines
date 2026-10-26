# Étape 1 : Compilation Web avec GWT
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew :html:dist --no-daemon -x test

# Étape 2 : NGINX Production
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

RUN rm -rf ./*

COPY --from=build /app/html/build/dist .

# Créer un dossier assets basique pour éviter toute erreur 404
RUN mkdir -p assets && \
    echo "keep" > assets/keep.txt && \
    echo "t:4:text/plain:keep.txt" > assets/assets.txt

EXPOSE 80

CMD ["sh", "-c", "sed -i 's/listen  *80;/listen '\"${PORT:-8080}\"';/g' /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
