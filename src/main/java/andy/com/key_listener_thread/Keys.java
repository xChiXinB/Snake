package andy.com.key_listener_thread;

public enum Keys {
    // 小方向键
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static char toChar(Keys key) {
        return switch (key) {
            case UP -> '↑';
            case DOWN -> '↓';
            case LEFT -> '←';
            case RIGHT -> '→';
        };
    }
}
