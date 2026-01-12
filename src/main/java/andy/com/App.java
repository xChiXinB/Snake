package andy.com;

import andy.com.tools.FpsTicker;
import andy.com.key_listener_thread.KeyListener;
import andy.com.display.DisplayMaster;
import andy.com.tools.Config;
import andy.com.games.snake.SnakePlayer;
import andy.com.games.apples.Apples;
import andy.com.games.background.Background;

/**
 * 贪吃蛇游戏主程序
 */
public class App {
    public static void main(String[] args) {
        App.launch();

        // 初始化
        var ticker = new FpsTicker(10);

        var keyListener = new KeyListener();
        var keyListenerThread = new Thread(keyListener);
        keyListenerThread.setDaemon(true);
        keyListenerThread.start();

        var displayMaster = new DisplayMaster(Config.DISPLAY_WIDTH, Config.DISPLAY_HEIGHT);

        // 初始化贪吃蛇游戏对象
        int startX = 0;
        int startY = 0;
        var snakePlayer = new SnakePlayer(startX, startY, Config.DISPLAY_WIDTH, Config.DISPLAY_HEIGHT);
        var apples = new Apples(Config.DISPLAY_WIDTH, Config.DISPLAY_HEIGHT);
        var background = new Background(Config.DISPLAY_WIDTH, Config.DISPLAY_HEIGHT);

        // 游戏主循环
        while (true) {
            var keys = keyListener.getKeys();

            snakePlayer.setDirectionAndMove(keys, apples);
            apples.tickForward(snakePlayer);

            displayMaster.setBackgroundDisplayer(background);
            displayMaster.addContentDisplayer(apples);
            displayMaster.addContentDisplayer(snakePlayer);

            displayMaster.flip();

            ticker.tick();
        }
    }

    private static void launch() {
        try {
            IO.print("The program will launch in 3...");
            Thread.sleep(1000);
            IO.print("2...");
            Thread.sleep(1000);
            IO.print("1...");
            Thread.sleep(1000);
            IO.print("\033[2J\033[H"); // 清屏
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
