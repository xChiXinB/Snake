package andy.com.key_listener_thread;

import java.io.IOException;
import java.util.ArrayList;

import org.jline.terminal.TerminalBuilder;

// import andy.com.key_listener_thread.Keys;

public class KeyListener implements Runnable {
    // private ArrayList<Keys> keys;
    private ArrayList<Character> rawKeys;

    public KeyListener() {
        // this.keys = new ArrayList<Keys>();
        this.rawKeys = new ArrayList<Character>();
    }

    @Override
    public void run() {
        try {

            // 初始化
            var terminal = TerminalBuilder.builder()
                    .jna(true)
                    .system(true)
                    .build();
            var reader = terminal.reader();

            while (true) {
                if (reader.available() <= 0) {
                    continue;
                }
                int ch = reader.read();

                // 将按键添加到keys
                this.rawKeys.add((char) ch);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * （debug）获取自从上次调用以来按下的所有原始按键
     * @return
     */
    @Deprecated
    public ArrayList<Character> getRawKeys() {
        var res = this.rawKeys;
        this.rawKeys = new ArrayList<Character>();
        // this.keys = new ArrayList<Keys>();
        return res;
    }
}
