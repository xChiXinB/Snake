package andy.com;

import andy.com.tools.FpsTicker;
import andy.com.key_listener_thread.KeyListener;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
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
}
