package andy.com.display;

import java.util.ArrayList;

public class DisplayMaster {
    // 显示区域的宽度和高度
    private int width;
    private int height;

    private BackgroundDisplayer backgroundDisplayer; // 背景器
    private ArrayList<ContentDisplayer> contentDisplayers; // 内容器列表
    private ArrayList<ArrayList<String>> displayArray; // 显示数组

    private boolean isDisplayAltered;

    public DisplayMaster(int width, int height) {
        this.width = width;
        this.height = height;
        this.backgroundDisplayer = null;
        this.contentDisplayers = new ArrayList<ContentDisplayer>();
        this.isDisplayAltered = false;
    }

    /**
     * 设置背景器。
     * 注意：本方法假设传入的背景器的getDisplay()方法返回数组大小符合显示区域大小。
     * @param backgroundDisplayer
     */
    public void setBackgroundDisplayer(BackgroundDisplayer backgroundDisplayer) {
        this.backgroundDisplayer = backgroundDisplayer;
    }

    /**
     * 添加内容器。
     * 注意：本方法假设传入的内容器的getDisplay()方法返回数组大小符合显示区域大小。
     * @param contentDisplayer
     */
    public void addContentDisplayer(ContentDisplayer contentDisplayer) {
        this.contentDisplayers.add(contentDisplayer);
    }

    /**
     * 渲染并输出显示内容，然后重置显示器状态。
     */
    public void flip() {
        this.isDisplayAltered = false;
        this.renderBackground();
        this.renderContents();
        if (!this.isDisplayAltered) return;
        var displayString = this.toString();
        IO.print(displayString);
        this.reset();
    }

    /**
     * debug - 在内部渲染显示内容。
     */
    @Deprecated
    public void apply() {
        this.renderBackground();
        this.renderContents();
    }

    private void renderBackground() {
        if (this.backgroundDisplayer == null) return;

        this.isDisplayAltered = true;
        var backgroundArray = this.backgroundDisplayer.getDisplay();
        // 将 backgroundArray 覆盖到 displayArray 上
        this.displayArray = backgroundArray;
    }

    private void renderContents() {
        if (this.contentDisplayers.isEmpty()) return;

        this.isDisplayAltered = true;
        for (var contentDisplayer : this.contentDisplayers) {
            var contentArray = contentDisplayer.getDisplay();
            // 将 contentArray 叠加到 displayArray 上
            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    var contentString = contentArray.get(h).get(w);
                    if (contentString.equals(" ")) continue;
                    this.displayArray.get(h).set(w, contentString);
                }
            }
        }
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("\033[2J\033[H");
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                // 为了视觉体验，将每个字符重复两遍以达到近似正方形的显示效果
                stringBuilder.append(this.displayArray.get(h).get(w).repeat(2));
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    private void reset() {
        this.backgroundDisplayer = null;
        this.contentDisplayers.clear();
    }

    @Deprecated
    public String getDisplayString(char fillChar) {
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
