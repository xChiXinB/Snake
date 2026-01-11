package andy.com.games.apples;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import andy.com.display.ContentDisplayer;

// 贪吃蛇果子类
public class Apples implements ContentDisplayer {
    private int width;
    private int height;

    private ArrayList<int[]> apples;
    private ArrayList<Integer> applesSpawningSchedule;
    private int maxAppleNumber = 1;
    private int allApplesSpawnRate = 5; // ticks

    public Apples(int width, int height) {
        this.width = width;
        this.height = height;
        this.apples = new ArrayList<int[]>();
        this.applesSpawningSchedule = new ArrayList<Integer>();
    }

    public void tickForward() {
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
