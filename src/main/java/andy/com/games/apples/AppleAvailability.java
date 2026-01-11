package andy.com.games.apples;

/**
 * 苹果可用性记录
 * @param isAvailable 苹果在当前 tick 是否可用
 * @param appleIndexAtThisTick 苹果在当前 tick 中的索引，-1 表示不可用
 * @param tickIdentifier 当前 tick 的唯一标识符
 */
public record AppleAvailability(boolean isAvailable, int appleIndexAtThisTick, String tickIdentifier) {}
