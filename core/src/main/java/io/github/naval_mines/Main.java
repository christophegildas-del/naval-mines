package io.github.naval_mines;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private boolean hasError = false;
    private String errorMessage = "";

    @Override
    public void create() {
        try {
            shapeRenderer = new ShapeRenderer();
            // Initialisation de ta grille / jeu ici
        } catch (Throwable t) {
            hasError = true;
            errorMessage = t.getMessage();
            Gdx.app.error("Main", "Erreur dans create(): ", t);
        }
    }

    @Override
    public void render() {
        if (hasError) {
            // Affiche un fond rouge vif si une erreur est survenue
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            return;
        }

        try {
            // Effacer l'écran (fond bleu foncé)
            Gdx.gl.glClearColor(0.05f, 0.1f, 0.16f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // Ton code de rendu avec ShapeRenderer ici
        } catch (Throwable t) {
            hasError = true;
            Gdx.app.error("Main", "Erreur dans render(): ", t);
        }
    }

    @Override
    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}
