package andy.com;

import andy.com.tools.FpsTicker;
import andy.com.key_listener_thread.KeyListener;

import java.util.Arrays;

import andy.com.display.DisplayMaster;
import andy.com.tools.Config;
import andy.com.games.snake.SnakePlayer;
import andy.com.games.apples.Apples;
import andy.com.games.background.Background;
import andy.com.display.UserInterface;

/**
 * 贪吃蛇游戏主程序
 */
public class App {
    public static boolean isGameLoopRunning = true;
    public static int ticksRemained = Config.GAME_LENGTH_TICKS;
    public static String exitMessage;

    public static void main(String[] args) {
        if (!Arrays.stream(args).anyMatch(a -> a.equals("--fast"))) {
            App.launch();
        } else {
            IO.println("强烈建议使用等宽中文字体，例如等距更纱黑体，体验此游戏！\033[2J\033[H\033[?25l"); // 清屏，并隐藏光标
        }

        // 初始化
        var ticker = new FpsTicker(Config.FPS);

        var keyListener = new KeyListener();
        var keyListenerThread = new Thread(keyListener);
        keyListenerThread.setDaemon(true);
        keyListenerThread.start();

        var displayMaster = new DisplayMaster(Config.DISPLAY_WIDTH, Config.DISPLAY_HEIGHT);
        var userInterface = new UserInterface();

        // 初始化贪吃蛇游戏对象
        int startX = 0;
        int startY = 0;
        var snakePlayer = new SnakePlayer(startX, startY, Config.DISPLAY_WIDTH, Config.DISPLAY_HEIGHT);
        var apples = new Apples(Config.DISPLAY_WIDTH, Config.DISPLAY_HEIGHT);
        var background = new Background(Config.DISPLAY_WIDTH, Config.DISPLAY_HEIGHT);

        // 游戏主循环
        while (App.isGameLoopRunning) {
            var keys = keyListener.getKeys();

            snakePlayer.setDirectionAndMove(keys, apples);
            if (!App.isGameLoopRunning) break;
            apples.tickForward(snakePlayer);

            displayMaster.setBackgroundDisplayer(background);
            displayMaster.addContentDisplayer(apples);
            displayMaster.addContentDisplayer(snakePlayer);

            displayMaster.flip();
            userInterface.flip(snakePlayer);

            App.ticksRemained--;
            if (App.ticksRemained <= 0) {
                App.isGameLoopRunning = false;
                App.exitMessage = "时间到！";
                // 额外渲染一帧，确保进度条为空
                displayMaster.flip();
                userInterface.flip(snakePlayer);
                continue;
            }
            ticker.tick();
        }
        IO.println(App.exitMessage + "\033[?25h"); // 显示光标
    }

    private static void launch() {
        try {
            IO.println("强烈建议使用等宽中文字体，例如等距更纱黑体，体验此游戏！");
            IO.print("启动倒计时 3...");
            Thread.sleep(1000);
            IO.print("2...");
            Thread.sleep(1000);
            IO.print("1...");
            Thread.sleep(1000);
            IO.print("\033[2J\033[H\033[?25l"); // 清屏，并隐藏光标
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
