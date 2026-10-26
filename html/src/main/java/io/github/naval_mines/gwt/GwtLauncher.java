package io.github.naval_mines.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
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
        // Redirige immédiatement le préchargeur sans écran d'attente
        return new Preloader.PreloaderCallback() {
            @Override
            public void error(String file) {
                System.out.println("Asset introuvable : " + file);
            }

            @Override
            public void update(Preloader.PreloaderState state) {
                // Ne bloque pas la boucle d'exécution
            }
        };
    }

    @Override
    protected Panel createPreloaderPanel() {
        // Renvoie un panneau vide pour supprimer le logo LibGDX
        return new FlowPanel();
    }
}
