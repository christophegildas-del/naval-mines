package io.github.naval_mines.model;

public class Ship extends AbstractEntity {
    private final int size;
    private int hits;

    public Ship(String name, int size) {
        super(name);
        this.size = size;
        this.hits = 0;
    }

    public void hit() {
        if (hits < size) {
            hits++;
        }
    }

    public boolean isSunk() {
        return hits >= size;
    }

    public int getSize() { return size; }
    public int getHits() { return hits; }
}
