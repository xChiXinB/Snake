package andy.com.games.apples;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.UUID;

import andy.com.display.ContentDisplayer;
import andy.com.games.snake.SnakePlayer;

// 贪吃蛇果子类
public class Apples implements ContentDisplayer {
    private int width;
    private int height;

    private ArrayList<int[]> apples;
    private ArrayList<Integer> applesSpawningSchedule;
    private int maxAppleNumber = 1;
    private int maximumApplesSpawnTime = 5; // ticks

    private String tickIdentifier;

    public Apples(int width, int height) {
        this.width = width;
        this.height = height;
        this.apples = new ArrayList<int[]>();
        this.applesSpawningSchedule = new ArrayList<Integer>();
        this.tickIdentifier = UUID.randomUUID().toString();
    }

    public void tickForward(SnakePlayer snakePlayer) {
        this.tickIdentifier = UUID.randomUUID().toString();
        var random = new Random();

        // 检查苹果数量
        var doNeedMoreApple = (this.apples.size() + this.applesSpawningSchedule.size()) < this.maxAppleNumber;
        if (doNeedMoreApple) {
            var missingAppleNumber = this.maxAppleNumber - this.apples.size();
            ArrayList<Integer> newApplesSpawningSchedule = random.ints(missingAppleNumber, 1, this.maximumApplesSpawnTime + 1)
                                                                       .boxed()
                                                                       .collect(Collectors.toCollection(ArrayList::new));
            this.applesSpawningSchedule.addAll(newApplesSpawningSchedule);
        }

        // 生成新苹果
        // NOTE - 该算法性能较低，但是考虑到苹果数量通常较少，影响不大
        int expectedGeneratedApplesNumber = (int) this.applesSpawningSchedule.stream()
                                                                       .filter((s) -> s == 0)
                                                                       .count();
        int remainedEmptySlots = (this.width * this.height) - this.apples.size() - snakePlayer.getWholeBodyLength();
        expectedGeneratedApplesNumber = Math.min(expectedGeneratedApplesNumber, remainedEmptySlots);

        for (int i = 0; i < expectedGeneratedApplesNumber; i++) {
            int appleX = random.nextInt(this.width);
            int appleY = random.nextInt(this.height);

            if (!this.checkSpaceAvailabilityAt(appleX, appleY, snakePlayer)) {
                // 已有苹果在该位置，重新生成
                i--;
                continue;
            }

            this.apples.add(new int[] { appleX, appleY });
        }

        this.applesSpawningSchedule.removeIf(s -> s == 0);
        this.applesSpawningSchedule.replaceAll((s) -> s - 1);
    }

    public AppleAvailability checkAppleAvailabilityAt(int x, int y) {
        for (var i = 0; i < this.apples.size(); i++) {
            var apple = this.apples.get(i);
            if (apple[0] == x && apple[1] == y) {
                return new AppleAvailability(true, i, this.tickIdentifier);
            }
        }
        return new AppleAvailability(false, -1, this.tickIdentifier);
    }

    /**
     * 检查指定位置是否有可用空间生成苹果
     * @param x
     * @param y
     * @return true - 有可用空间；false - 无可用空间
     */
    private boolean checkSpaceAvailabilityAt(int x, int y, SnakePlayer snakePlayer) {
        var isAppleHere = this.apples.stream()
                              .anyMatch((apple) -> apple[0] == x && apple[1] == y);
        var isSnakeHere = snakePlayer.doesSnakeLieOn(x,  y);
        return !(isAppleHere || isSnakeHere);
    }

    /**
     * 移除可用的苹果
     * 为了使调用生效，请确保传入的 AppleAvailability 的获取时机和调用该方法的时机在同一个 tickForward 调用后，下一个 tickForward 调用前
     * @param appleAvailability
     */
    public void removeAppleIfAvailableForThisTick(AppleAvailability appleAvailability) {
        if (!appleAvailability.isAvailable()) return;
        if (!appleAvailability.tickIdentifier().equals(this.tickIdentifier)) return;
        int appleIndex = appleAvailability.appleIndexAtThisTick();
        this.apples.remove(appleIndex);
    }

    @Override
    public ArrayList<ArrayList<String>> getDisplay() {
        // 创建空白显示数组
        ArrayList<ArrayList<String>> display = new ArrayList<>();
        for (int h = 0; h < this.height; h++) {
            ArrayList<String> row = new ArrayList<>();
            for (int w = 0; w < this.width; w++) {
                row.add(" ");
            }
            display.add(row);
        }

        // 在苹果位置填充红色空格
        // ANSI 红色背景：\033[41m 空格 \033[0m（重置）
        String appleChar = "\033[41m \033[0m";
        for (int[] apple : this.apples) {
            int x = apple[0];
            int y = apple[1];
            display.get(y).set(x, appleChar);
        }

        return display;
    }
}
