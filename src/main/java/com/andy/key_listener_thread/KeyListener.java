package com.andy.key_listener_thread;

import java.io.IOException;
import java.util.ArrayList;

import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;

public class KeyListener implements Runnable {
    private ArrayList<Keys> keys;

    public KeyListener() {
        this.keys = new ArrayList<Keys>();
    }

    @Override
    public void run() {
        try {

            // 初始化
            var terminal = TerminalBuilder.builder()
                    .jna(true)
                    .system(true)
                    .build();
            terminal.enterRawMode();
            
            var reader = terminal.reader();
            var bindingReader = new BindingReader(reader);

            var keyMap = new KeyMap<Keys>();
            var upKey = KeyMap.key(terminal, Capability.key_up);
            var downKey = KeyMap.key(terminal, Capability.key_down);
            var rightKey = KeyMap.key(terminal, Capability.key_right);
            var leftKey = KeyMap.key(terminal, Capability.key_left);
            if (upKey != null) keyMap.bind(Keys.UP, upKey);
            if (downKey != null) keyMap.bind(Keys.DOWN, downKey);
            if (rightKey != null) keyMap.bind(Keys.RIGHT, rightKey);
            if (leftKey != null) keyMap.bind(Keys.LEFT, leftKey);
            
            // 循环监听按键
            while (true) {
                var key = bindingReader.readBinding(keyMap);
                if (key == null) continue;
                if (this.keys.contains(key)) continue;
                this.keys.add(key);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取自从上次调用以来按下的所有按键
     * @return 按键列表
     */
    public ArrayList<Keys> getKeys() {
        var pressedKeys = new ArrayList<Keys>(this.keys);
        this.keys.clear();
        return pressedKeys;
    }

    /**
     * （debug）获取自从上次调用以来按下的所有原始按键
     * @return
     */
    @Deprecated
    public ArrayList<Character> getRawKeys() {
        throw new UnsupportedOperationException("getRawKeys 已弃用，请使用 getKeys 方法");
    }
}
