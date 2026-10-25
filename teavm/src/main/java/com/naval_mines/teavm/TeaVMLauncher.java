package com.naval_mines.teavm;

import com.github.xpenatan.gdx.backends.teavm.TeaVMApplication;
import com.github.xpenatan.gdx.backends.teavm.TeaVMApplicationConfiguration;
import com.naval_mines.MainGame; // Ton package exact dans /core

public class TeaVMLauncher {
    public static void main(String[] args) {
        TeaVMApplicationConfiguration config = new TeaVMApplicationConfiguration();
        config.canvasId = "canvas";
        config.width = 800;
        config.height = 480;
        new TeaVMApplication(new MainGame(), config);
    }
}
