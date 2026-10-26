# Étape 1 : Compilation Web avec GWT
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY . .

# 1. Créer le dossier assets et un fichier bidon AVANT la compilation
# Cela force LibGDX à générer un 'assets.txt' avec le formatage interne exact attendu par GWT.
RUN mkdir -p assets && echo "keep" > assets/keep.txt

RUN chmod +x gradlew
# 2. La tâche :html:dist va lire assets/keep.txt et créer /app/html/build/dist/assets/assets.txt
RUN ./gradlew :html:dist --no-daemon -x test

# Étape 2 : Serveur Web de production NGINX
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Nettoyer les fichiers NGINX par défaut
RUN rm -rf ./*

# 3. Copier l'intégralité du build (index.html, js, et le dossier assets généré)
COPY --from=build /app/html/build/dist .

EXPOSE 80

CMD ["sh", "-c", "sed -i 's/listen  *80;/listen '\"${PORT:-8080}\"';/g' /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
