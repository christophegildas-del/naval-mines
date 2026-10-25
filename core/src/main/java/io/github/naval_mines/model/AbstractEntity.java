package io.github.naval_mines.model;

public abstract class AbstractEntity {
    private final String name;

    public AbstractEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
