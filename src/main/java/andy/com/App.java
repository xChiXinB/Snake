package andy.com;

import andy.com.tools.FpsTicker;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args ) {
        FpsTicker ticker = new FpsTicker(1);
        for (int i = 0; i < 10; i++) {
            System.out.println("Tick " + i);
            ticker.tick();
        }
    }
}
