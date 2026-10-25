package io.github.naval_mines.model;
import java.util.Random;

public class Grid {
    private final int width;
    private final int height;
    private final GameCell[][] cells;
    private final Random random = new Random();

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new GameCell[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new GameCell(x, y);
            }
        }
    }

    public GameCell getCell(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }
        return cells[x][y];
    }

    public boolean placeEntity(AbstractEntity entity, int x, int y) {
        GameCell cell = getCell(x, y);
        if (cell != null && !cell.hasEntity()) {
            cell.setEntity(entity);
            return true;
        }
        return false;
    }

    /**
     * Tente de placer un navire de taille donnée aléatoirement sur la grille.
     * @param ship Le navire à placer
     * @param maxAttempts Nombre d'essais max pour éviter une boucle infinie si la grille est saturée
     * @return true si le placement a réussi, false sinon
     */
    public boolean placeShipRandomly(Ship ship, int maxAttempts) {
        int attempts = 0;

        while (attempts < maxAttempts) {
            boolean horizontal = random.nextBoolean();
            int originX, originY;

            if (horizontal) {
                originX = random.nextInt(width - ship.getSize() + 1);
                originY = random.nextInt(height);
            } else {
                originX = random.nextInt(width);
                originY = random.nextInt(height - ship.getSize() + 1);
            }

            // 1. Vérifier la liberté absolue des cases et de leurs 8 voisins
            boolean canPlace = true;
            for (int i = 0; i < ship.getSize(); i++) {
                int checkX = horizontal ? originX + i : originX;
                int checkY = horizontal ? originY : originY + i;

                if (!isCellAndNeighborsFree(checkX, checkY)) {
                    canPlace = false;
                    break;
                }
            }

            // 2. Si l'emplacement et son périmètre sont dégagés, placer le navire
            if (canPlace) {
                for (int i = 0; i < ship.getSize(); i++) {
                    int placeX = horizontal ? originX + i : originX;
                    int placeY = horizontal ? originY : originY + i;
                    cells[placeX][placeY].setEntity(ship);
                }
                return true;
            }

            attempts++;
        }

        return false;
    }

    public void populateMines(int count, int damage) {
        int placed = 0;
        int maxAttempts = count * 50;
        int attempts = 0;

        while (placed < count && attempts < maxAttempts) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);

            // La mine ne peut être posée que si elle n'a aucun voisin direct ou en diagonale
            if (isCellAndNeighborsFree(x, y)) {
                getCell(x, y).setEntity(new Mine(damage));
                placed++;
            }
            attempts++;
        }
    }

    /**
     * Réinitialise et remplit le plateau de jeu avec des navires et des mines.
     */
    public void setupRandomFleetAndMines() {
        // Flotte standard : Porte-avions (5), Cuirassé (4), Croiseur (3), Sous-marin (3), Torpilleur (2)
        int[] shipSizes = {5, 4, 3, 3, 2};

        for (int i = 0; i < shipSizes.length; i++) {
            Ship ship = new Ship("Navire " + (i + 1), shipSizes[i]);
            boolean placed = placeShipRandomly(ship, 200);
            if (!placed) {
                System.out.println("Attention : Impossible de placer le navire " + ship.getName());
            }
        }

        // Sur une grille 20x20 (400 cases), 15 à 20 mines s'intègrent facilement avec l'écartement de sécurité
        populateMines(15, 1);
    }

    /**
     * Vérifie si une case et l'ensemble de ses 8 voisins immédiats sont libres.
     */
    private boolean isCellAndNeighborsFree(int x, int y) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int checkX = x + dx;
                int checkY = y + dy;

                // Si le voisin est dans la grille et contient déjà une entité, la zone n'est pas libre
                GameCell neighbor = getCell(checkX, checkY);
                if (neighbor != null && neighbor.hasEntity()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
