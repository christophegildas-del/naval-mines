# Étape 1 : Compilation Web avec GWT
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew :html:dist --no-daemon -x test

# Créer un asset placeholder et générer le fichier assets.txt requis par LibGDX GWT
RUN mkdir -p /app/assets && echo "keep" > /app/assets/keep.txt
RUN cd /app/assets && find . -type f ! -name "assets.txt" | sed 's|^\./||' > assets.txt

# Copier le dossier assets indexé dans le dossier dist avant l'export Nginx
RUN mkdir -p /app/html/build/dist/assets
RUN cp -r /app/assets/* /app/html/build/dist/assets/

# Étape 2 : Serveur Web de production NGINX
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Nettoyer les fichiers NGINX par défaut
RUN rm -rf ./*

# Copier le dossier dist complet (contenant index.html, JS et assets/assets.txt)
COPY --from=build /app/html/build/dist .

EXPOSE 80

CMD ["sh", "-c", "sed -i 's/listen  *80;/listen '\"${PORT:-8080}\"';/g' /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
