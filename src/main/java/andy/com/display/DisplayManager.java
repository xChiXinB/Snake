package andy.com.display;

public class DisplayManager {
    private int width;
    private int height;

    public DisplayManager(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public String generateDisplayString(char fillChar) {
        var sb = new StringBuilder();
        sb.append("\n".repeat(100));
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                sb.append(fillChar);
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
