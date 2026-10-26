# Étape 1 : Compilation Web avec GWT
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

# S'assurer qu'un fichier d'asset existe pour l'indexation GWT
RUN mkdir -p assets && echo "keep" > assets/keep.txt

RUN chmod +x gradlew
RUN ./gradlew :html:dist --no-daemon -x test

# Étape 2 : NGINX Production
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

RUN rm -rf ./*

# Copier le contenu distribué du build web GWT
COPY --from=build /app/html/build/dist .

EXPOSE 80

CMD ["sh", "-c", "sed -i 's/listen  *80;/listen '\"${PORT:-8080}\"';/g' /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
