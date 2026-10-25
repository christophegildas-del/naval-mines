package com.naval_mines.teavm;

import com.github.xpenatan.gdx.backends.teavm.TeaApplication;
import com.github.xpenatan.gdx.backends.teavm.TeaApplicationConfiguration;
import io.github.naval_mines.Main;

public class TeaVMLauncher {
    public static void main(String[] args) {
        // "canvas" correspond à l'ID de la balise <canvas id="canvas"></canvas> dans votre index.html
        TeaApplicationConfiguration config = new TeaApplicationConfiguration("canvas");
        config.width = 800;
        config.height = 480;

        new TeaApplication(new io.github.naval_mines.Main(), config);
    }
}
