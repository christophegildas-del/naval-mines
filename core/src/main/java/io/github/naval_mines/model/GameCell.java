package io.github.naval_mines.model;

public class GameCell {
    private final int x;
    private final int y;
    private boolean revealed;
    private AbstractEntity entity;

    public GameCell(int x, int y) {
        this.x = x;
        this.y = y;
        this.revealed = false;
        this.entity = null;
    }

    public boolean reveal() {
        this.revealed = true;
        if (entity instanceof Ship) {
            ((Ship) entity).hit();
        }
        return entity != null;
    }

    public boolean hasEntity() { return entity != null; }
    public AbstractEntity getEntity() { return entity; }
    public void setEntity(AbstractEntity entity) { this.entity = entity; }
    public boolean isRevealed() { return revealed; }
    public int getX() { return x; }
    public int getY() { return y; }
}
