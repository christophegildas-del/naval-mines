package io.github.naval_mines.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.badlogic.gdx.backends.gwt.preloader.Preloader.PreloaderCallback;
import com.badlogic.gdx.backends.gwt.preloader.Preloader.PreloaderState;
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
    public PreloaderCallback getPreloaderCallback() {
        return new PreloaderCallback() {
            @Override
            public void error(String file) {
                logToBrowser("[GWT Preloader] ERREUR Fichier : " + file);
            }

            @Override
            public void update(PreloaderState state) {
                logToBrowser("[GWT Preloader] Progression : "
                    + state.getLoaded() + " / " + state.hasEnded());
            }
        };
    }

    private static native void logToBrowser(String msg) /*-{
        console.log(msg);
    }-*/;
}
