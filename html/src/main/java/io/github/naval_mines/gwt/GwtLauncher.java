package io.github.naval_mines.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader.PreloaderCallback;
import com.badlogic.gdx.backends.gwt.preloader.Preloader.PreloaderState;
import com.google.gwt.core.client.GWT;
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
        GWT.setUncaughtExceptionHandler(e ->
            logToBrowser("GWT Exception: " + e.getMessage())
        );
        return new Main();
    }

    @Override
    public PreloaderCallback getPreloaderCallback() {
        return new PreloaderCallback() {
            @Override
            public void error(String file) {
                logToBrowser("Asset ignore: " + file);
            }

            @Override
            public void update(PreloaderState state) {
                // Pas de traitement spécifique requis
            }
        };
    }

    private static native void logToBrowser(String msg) /*-{
        console.log(msg);
    }-*/;
}
