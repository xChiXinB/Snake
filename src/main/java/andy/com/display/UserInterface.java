package andy.com.display;

import andy.com.games.snake.SnakePlayer;
import andy.com.App;
import andy.com.tools.Config;

public class UserInterface {

    /**
     * 渲染用户界面相关内容
     */
    public void flip(SnakePlayer snakePlayer) {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(this.generateScoreAndLength(snakePlayer));
        stringBuilder.append(this.generateTimeRemained());
        IO.println(stringBuilder.toString());
    }

    public String generateScoreAndLength(SnakePlayer snakePlayer) {
        return String.format("分数 - %d | 长度 - %d\n", snakePlayer.getScore(), snakePlayer.getWholeBodyLength());
    }

    public String generateTimeRemained() {
        var timeBarLength = 30;
        var timeRemainedPercentage = (double) App.ticksRemained / Config.GAME_LENGTH_TICKS;
        var timeBarFilledLength = (int) Math.ceil(timeBarLength * timeRemainedPercentage);

        var stringBuilder = new StringBuilder();
        stringBuilder.append("剩余时间 - [");

        if (timeRemainedPercentage < 0.1) {
            stringBuilder.append("\033[38;2;255;0;0m"); // 红色背景
        } else if (timeRemainedPercentage < 0.3) {
            stringBuilder.append("\033[38;2;255;255;0m"); // 黄色背景 
        }

        stringBuilder.append("#".repeat(timeBarFilledLength));
        stringBuilder.append(" ".repeat(timeBarLength - timeBarFilledLength));
        stringBuilder.append("\033[0m]\n"); // 重置颜色
        return stringBuilder.toString();
    }
}
