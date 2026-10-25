package io.github.naval_mines.model;

public class Mine extends AbstractEntity {
    private final int damage;

    public Mine(int damage) {
        super("Naval Mine");
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
