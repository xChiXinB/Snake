package andy.com;

import andy.com.tools.FpsTicker;
import andy.com.key_listener_thread.KeyListener;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args ) {
        // 初始化
        var ticker = new FpsTicker(1);

        var keyListener = new KeyListener();
        var keyListenerThread = new Thread(keyListener);
        keyListenerThread.setDaemon(true);
        keyListenerThread.start();

        for (var i = 0; i < 10; i++) {
            System.out.println("Tick " + i);
            var keys = keyListener.getRawKeys();
            System.out.println("Raw keys: " + keys);
            ticker.tick();
        }
    }
}
