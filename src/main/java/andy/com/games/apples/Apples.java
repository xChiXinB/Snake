package andy.com.games.apples;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.UUID;

import andy.com.display.ContentDisplayer;

// 贪吃蛇果子类
public class Apples implements ContentDisplayer {
    private int width;
    private int height;

    private ArrayList<int[]> apples;
    private ArrayList<Integer> applesSpawningSchedule;
    private int maxAppleNumber = 1;
    private int allApplesSpawnRate = 5; // ticks

    private String tickIdentifier;

    public Apples(int width, int height) {
        this.width = width;
        this.height = height;
        this.apples = new ArrayList<int[]>();
        this.applesSpawningSchedule = new ArrayList<Integer>();
        this.tickIdentifier = UUID.randomUUID().toString();
    }

    public void tickForward() {
        this.tickIdentifier = UUID.randomUUID().toString();

        var random = new Random();

        this.updateAppleSpawningSchedule(random);

        // 生成新苹果
        // NOTE - 该算法性能较低，但是考虑到苹果数量通常较少，影响不大
        int expectedGeneratedApplesNumber = (int) this.applesSpawningSchedule.stream()
                                                                       .filter((s) -> s == 0)
                                                                       .count();
        this.applesSpawningSchedule.removeIf(s -> s == 0);

        int remainedEmptySlots = (this.width * this.height) - this.apples.size();
        expectedGeneratedApplesNumber = Math.min(expectedGeneratedApplesNumber, remainedEmptySlots);

        for (int i = 0; i < expectedGeneratedApplesNumber; i++) {
            int appleX = random.nextInt(this.width);
            int appleY = random.nextInt(this.height);
            
            // 检查是否已有苹果在该位置
            if (this.apples.stream().anyMatch((apple) -> apple[0] == appleX && apple[1] == appleY)) {
                // 已有苹果在该位置，重新生成
                i--;
                continue;
            }

            this.apples.add(new int[] { appleX, appleY });
        }
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

    private void updateAppleSpawningSchedule(Random random) {
        // 检查苹果数量
        if (this.apples.size() < this.maxAppleNumber) {
            var missingAppleNumber = this.maxAppleNumber - this.apples.size();
            ArrayList<Integer> newApplesSpawningSchedule = random.ints(missingAppleNumber, 1, this.allApplesSpawnRate + 1)
                                                                       .boxed()
                                                                       .collect(Collectors.toCollection(ArrayList::new));
            this.applesSpawningSchedule.addAll(newApplesSpawningSchedule);
        }

        // 更新 allApplesSpawningSchedule
        this.applesSpawningSchedule.replaceAll((s) -> s - 1);
    }

    @Override
    public ArrayList<ArrayList<String>> getDisplay() {
        // TODO: 实现苹果的显示内容
        return new ArrayList<ArrayList<String>>();
    }
}
