package io.github.naval_mines;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.naval_mines.model.GameCell;
import io.github.naval_mines.model.Grid;
import io.github.naval_mines.model.Mine;
import io.github.naval_mines.model.Ship;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private Grid grid;

    // Dimensions de la grille
    private static final int GRID_WIDTH = 20;
    private static final int GRID_HEIGHT = 20;
    private static final float CELL_SIZE = 25f;

    // Décalage pour centrer la grille à l'écran (800x600)
    private float offsetX;
    private float offsetY;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();

        // 1. Initialisation du modèle et placement aléatoire
        grid = new Grid(GRID_WIDTH, GRID_HEIGHT);
        grid.setupRandomFleetAndMines();

        // 2. Calcul du centrage
        offsetX = (Gdx.graphics.getWidth() - (GRID_WIDTH * CELL_SIZE)) / 2f;
        offsetY = (Gdx.graphics.getHeight() - (GRID_HEIGHT * CELL_SIZE)) / 2f;
    }

    @Override
    public void render() {
        // Effacer l'écran (fond bleu marine très foncé)
        Gdx.gl.glClearColor(0.05f, 0.1f, 0.15f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Rendu de chaque cellule
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                GameCell cell = grid.getCell(x, y);
                float drawX = offsetX + (x * CELL_SIZE);
                float drawY = offsetY + (y * CELL_SIZE);

                // 1. Dessiner l'arrière-plan de la case
                if (cell.isRevealed()) {
                    shapeRenderer.setColor(Color.LIGHT_GRAY);
                } else {
                    shapeRenderer.setColor(Color.ROYAL);
                }
                shapeRenderer.rect(drawX + 1, drawY + 1, CELL_SIZE - 2, CELL_SIZE - 2);

                // 2. Si la case est révélée, afficher son contenu
                if (cell.isRevealed() && cell.hasEntity()) {
                    if (cell.getEntity() instanceof Ship) {
                        // Navire : carré rouge
                        shapeRenderer.setColor(Color.RED);
                        shapeRenderer.rect(drawX + 8, drawY + 8, CELL_SIZE - 16, CELL_SIZE - 16);
                    } else if (cell.getEntity() instanceof Mine) {
                        // Mine : cercle noir
                        shapeRenderer.setColor(Color.BLACK);
                        shapeRenderer.circle(drawX + (CELL_SIZE / 2f), drawY + (CELL_SIZE / 2f), CELL_SIZE / 4f);
                    }
                }
            }
        }

        shapeRenderer.end();

        // Contour de la grille en lignes blanches
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                float drawX = offsetX + (x * CELL_SIZE);
                float drawY = offsetY + (y * CELL_SIZE);
                shapeRenderer.rect(drawX, drawY, CELL_SIZE, CELL_SIZE);
            }
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
