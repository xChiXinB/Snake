package andy.com.games.background;

import java.util.ArrayList;

import andy.com.display.BackgroundDisplayer;

public class Background implements BackgroundDisplayer {
    private final int width;
    private final int height;

    public Background(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public ArrayList<ArrayList<String>> getDisplay() {
        var display = new ArrayList<ArrayList<String>>();
        for (int h = 0; h < height; h++) {
            var row = new ArrayList<String>();
            for (int w = 0; w < width; w++) {
                this.fillGridShapeBackground(h, row, w);
            }
            display.add(row);
        }
        return display;
    }

    private void fillGridShapeBackground(int h, ArrayList<String> row, int w) {
        if (h % 2 == 0) {
            if (w % 2 == 0) {
                row.add("\033[48;2;0;0;0m \033[0m");
            } else {
                row.add("\033[48;2;30;30;30m \033[0m");
            }
        } else {
            if (w % 2 == 0) {
                row.add("\033[48;2;30;30;30m \033[0m");
            } else {
                row.add("\033[48;2;0;0;0m \033[0m");
            }
        }
    }
    
}
