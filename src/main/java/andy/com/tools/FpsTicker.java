package andy.com.tools;

public class FpsTicker {
    private int fps;
    private long lastTickTime = 0;

    public FpsTicker(int fps) {
        this.fps = fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    /**
     * 阻塞进程，直到当前时间达到this.lastTickTime + 1000 / this.fps
     */
    public void tick() {
        // 处理初次调用
        if (this.lastTickTime == 0) {
            try {
                Thread.sleep(1000 / this.fps);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.lastTickTime = System.currentTimeMillis();
            return;
        }

        long currentTimeMillis = System.currentTimeMillis();
        long targetTimeMillis = this.lastTickTime + (1000 / this.fps);
        // 已经超时
        if (currentTimeMillis >= targetTimeMillis) {
            this.lastTickTime = currentTimeMillis;
            return;
        }
        
        try {
            Thread.sleep(targetTimeMillis - currentTimeMillis);
            this.lastTickTime = targetTimeMillis;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
