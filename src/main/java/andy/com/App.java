package andy.com;

import andy.com.tools.FpsTicker;
import andy.com.key_listener_thread.KeyListener;
import andy.com.display.DisplayMaster;
import andy.com.key_listener_thread.Keys;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        App.launch();

        // 初始化
        var ticker = new FpsTicker(24);

        var keyListener = new KeyListener();
        var keyListenerThread = new Thread(keyListener);
        keyListenerThread.setDaemon(true);
        keyListenerThread.start();

        var displayManager = new DisplayMaster(20, 10);

        while (true) {
            var keys = keyListener.getKeys();

            if (keys.isEmpty()) {
                IO.print(displayManager.getDisplayString('0'));
            } else {
                IO.print(displayManager.getDisplayString(Keys.toChar(keys.get(0))));
            }
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
