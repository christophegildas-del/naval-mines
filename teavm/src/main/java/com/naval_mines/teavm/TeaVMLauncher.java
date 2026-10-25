package com.naval_mines.teavm;

import com.github.mgsx.gdx.backends.teavm.TeaVMApplication;
import com.github.mgsx.gdx.backends.teavm.TeaVMApplicationConfiguration;
import io.github.naval_mines.Main;

public class TeaVMLauncher {
    public static void main(String[] args) {
        TeaVMApplicationConfiguration config = new TeaVMApplicationConfiguration();
        config.canvasId = "canvas";
        config.width = 800;
        config.height = 480;
        new TeaVMApplication(new Main(), config);
    }
}
