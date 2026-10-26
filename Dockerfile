# Étape 1 : Compilation Web avec GWT
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew :html:dist --no-daemon -x test

# Étape 2 : Serveur Web NGINX
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

RUN rm -rf ./*

# Copie de la distribution GWT
COPY --from=build /app/html/build/dist .

# Ingestion garantie d'un asset valide de 5 octets
RUN mkdir -p assets && \
    echo "keep" > assets/keep.txt && \
    echo "t:5:text/plain:keep.txt" > assets/assets.txt

EXPOSE 80

CMD ["sh", "-c", "sed -i 's/listen  *80;/listen '\"${PORT:-8080}\"';/g' /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
