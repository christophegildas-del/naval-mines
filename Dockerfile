# Étape 1 : Compilation Web avec GWT
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew :html:dist --no-daemon -x test

# Étape 2 : Serveur Web de production NGINX
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# 1. Nettoyer les fichiers par défaut
RUN rm -rf ./*

# 2. Copier le build web généré par GWT
COPY --from=build /app/html/build/dist .

# 3. LE CORRECTIF ABSOLU : Créer un faux asset parfaitement formaté pour LibGDX
RUN mkdir -p assets && \
    echo -n "keep" > assets/keep.txt && \
    echo "t:4:text/plain:keep.txt" > assets/assets.txt

EXPOSE 80

CMD ["sh", "-c", "sed -i 's/listen  *80;/listen '\"${PORT:-8080}\"';/g' /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
