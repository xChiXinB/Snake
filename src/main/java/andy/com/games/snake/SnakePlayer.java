package andy.com.games.snake;

import java.util.ArrayList;

import andy.com.key_listener_thread.Keys;
import andy.com.display.ContentDisplayer;
import andy.com.games.apples.Apples;

// 贪吃蛇玩家类
public class SnakePlayer implements ContentDisplayer {
    private int x;
    private int y;
    private ArrayList<int[]> bodies;
    private int tickNumberForGrowingTail;
    private SnakeDirections currentDirection;

    private int width;
    private int height;
    
    public SnakePlayer(int startX, int startY, int width, int height) {
        this.x = startX;
        this.y = startY;
        this.bodies = new ArrayList<int[]>();
        this.width = width;
        this.height = height;
        this.currentDirection = SnakeDirections.RIGHT;
        // 决定了开局尾巴长度
        this.tickNumberForGrowingTail = 1;
    }

    public void setDirectionAndMove(ArrayList<Keys> keys, Apples apples) {
        if (keys.isEmpty()) {
            this.moveInCurrentDirection(apples);
            return;
        }

        var key = keys.get(keys.size() - 1);
        this.currentDirection = switch (key) {
            case Keys.UP -> this.currentDirection == SnakeDirections.DOWN ? this.currentDirection : SnakeDirections.UP;
            case Keys.DOWN -> this.currentDirection == SnakeDirections.UP ? this.currentDirection : SnakeDirections.DOWN;
            case Keys.LEFT -> this.currentDirection == SnakeDirections.RIGHT ? this.currentDirection : SnakeDirections.LEFT;
            case Keys.RIGHT -> this.currentDirection == SnakeDirections.LEFT ? this.currentDirection : SnakeDirections.RIGHT;
        };

        this.moveInCurrentDirection(apples);
    }

    /**
     * 让蛇在接下来的 n 个 tick 中持续增长尾巴
     * 实际上，就是让蛇的尾巴变长 n 截
     * @param tickNumber
     */
    public void growTailForTickNumber(int tickNumber) {
        this.tickNumberForGrowingTail += tickNumber;
    }

    public boolean doesSnakeLieOn(int x, int y) {
        var isOnHead = this.x == x && this.y == y;
        var isOnBody = this.bodies.stream()
                                 .anyMatch((body) -> body[0] == x && body[1] == y);
        return isOnHead || isOnBody;
    }

    public int getWholeBodyLength() {
        return this.bodies.size() + 1;
    }

    private void moveInCurrentDirection(Apples apples) {
        this.bodies.add(new int[] { this.x, this.y });

        switch (this.currentDirection) {
            case SnakeDirections.UP -> this.y = this.y - 1;
            case SnakeDirections.DOWN -> this.y = this.y + 1;
            case SnakeDirections.LEFT -> this.x = this.x - 1;
            case SnakeDirections.RIGHT -> this.x = this.x + 1;
        }

        // 死亡检测
        var isDead = this.bodies.stream()
                                .anyMatch((body) -> body[0] == this.x && body[1] == this.y);
        if (!isDead) {
            isDead = this.x < 0 || this.x > this.width - 1 || this.y < 0 || this.y > this.height - 1;
        };
        // TODO: 处理死亡逻辑
        // 暂时用比较粗暴的方式
        if (isDead) {
            System.exit(0);
        }

        // 吃苹果
        var appleAvailability = apples.checkAppleAvailabilityAt(this.x, this.y);
        if (appleAvailability.isAvailable()) {
            apples.removeAppleIfAvailableForThisTick(appleAvailability);
            this.growTailForTickNumber(1);
        }

        // 尾巴增长
        if (this.tickNumberForGrowingTail > 0) {
            this.tickNumberForGrowingTail--;
        } else {
            this.bodies.remove(0);
        }
    }

    @Override
    public ArrayList<ArrayList<String>> getDisplay() {
        // TODO: 实现贪吃蛇的显示内容
        return new ArrayList<ArrayList<String>>();
    }
}
