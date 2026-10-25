package io.github.naval_mines.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import io.github.naval_mines.Main;

public class GwtLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        // Configuration du Canvas : 800x600 px
        GwtApplicationConfiguration config = new GwtApplicationConfiguration(800, 600);
        config.padHorizontal = 0;
        config.padVertical = 0;
        return config;
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return new Main();
    }
}
