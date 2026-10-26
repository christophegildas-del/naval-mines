package io.github.naval_mines.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import io.github.naval_mines.Main;

public class GwtLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        GwtApplicationConfiguration config = new GwtApplicationConfiguration(800, 600);
        config.padHorizontal = 0;
        config.padVertical = 0;
        return config;
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return new Main();
    }

    @Override
    public Preloader.PreloaderCallback getPreloaderCallback() {
        return new Preloader.PreloaderCallback() {
            @Override
            public void error(String file) {
                consoleLog("[GWT Preloader] ERREUR Fichier : " + file);
            }

            @Override
            public void update(Preloader.PreloaderState state) {
                consoleLog("[GWT Preloader] Progression : "
                    + state.getDownloaded() + " / " + state.getKnownTotal());
            }
        };
    }

    private static native void consoleLog(String msg) /*-{
        console.log(msg);
    }-*/;
}
