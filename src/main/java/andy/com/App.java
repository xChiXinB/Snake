package andy.com;

import andy.com.tools.FpsTicker;
import andy.com.key_listener_thread.KeyListener;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        App.launch();

        // 初始化
        var ticker = new FpsTicker(30);

        var keyListener = new KeyListener();
        var keyListenerThread = new Thread(keyListener);
        keyListenerThread.setDaemon(true);
        keyListenerThread.start();

        while (true) {
            var keys = keyListener.getKeys();
            IO.println("Keys: " + keys);
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
