package io.github.naval_mines.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
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

    // LE CORRECTIF ABSOLU EST ICI
    // On remplace le préchargeur par défaut pour empêcher la lecture de assets.txt
    @Override
    public Preloader createPreloader() {
        return new Preloader(getPreloaderBaseURL()) {
            @Override
            public void preload(String assetFileUrl) {
                // En ne faisant RIEN ici, on annule la requête HTTP vers assets.txt.
                // LibGDX verra 0 asset à charger, passera instantanément à 100%,
                // et démarrera le jeu sans aucune erreur !
            }
        };
    }

    private static native void logToBrowser(String msg) /*-{
        console.log(msg);
    }-*/;
}
