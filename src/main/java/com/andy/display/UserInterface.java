package com.andy.display;

import com.andy.games.snake.SnakePlayer;
import com.andy.App;
import com.andy.tools.Config;

public class UserInterface {
    // 文字闪烁相关变量
    private int lastScore;
    private int scoreBlinkingTickNumber;
    private int lastLength;
    private int lengthBlinkingTickNumber;

    public UserInterface() {
        this.scoreBlinkingTickNumber = this.lengthBlinkingTickNumber = 0;
        this.lastScore = this.lastLength = -1;
    }

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
        int currentScore = snakePlayer.getScore();
        int currentBodyLength = snakePlayer.getWholeBodyLength();

        if (this.lastScore != -1 && this.lastScore != currentScore) {
            this.scoreBlinkingTickNumber = Config.UI_TEXT_BLINKING_TICK_NUMBER;
        }
        if (this.lastLength != -1 && this.lastLength != currentBodyLength) {
            this.lengthBlinkingTickNumber = Config.UI_TEXT_BLINKING_TICK_NUMBER;
        }

        this.lastScore = currentScore;
        this.lastLength = currentBodyLength;

        var scoreANSICode = "";
        var lengthANSICode = "";

        if (this.scoreBlinkingTickNumber > 0) {
            this.scoreBlinkingTickNumber--;
            if (this.scoreBlinkingTickNumber % 2 == 0) { scoreANSICode = "\033[4m"; } // 下划线
        }
        if (this.lengthBlinkingTickNumber > 0) {
            this.lengthBlinkingTickNumber--;
            if (this.lengthBlinkingTickNumber % 2 == 0) { lengthANSICode = "\033[4m"; }
        }

        return String.format("分数 - %s%d\033[0m | 长度 - %s%d\033[0m\n", scoreANSICode, currentScore, lengthANSICode, currentBodyLength);
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
