package chess.domain.position;

import java.util.Arrays;

public enum Rank {
    EIGHT(8),
    SEVEN(7),
    SIX(6),
    FIVE(5),
    FOUR(4),
    THREE(3),
    TWO(2),
    ONE(1),
    ;

    private static final String REGEX = "^[1-8]$";

    private final int value;

    Rank(int value) {
        this.value = value;
    }

    public static Rank of(char s) {
        return of(s - '0');
    }

    private static Rank of(Integer value) {
        return Arrays.stream(values())
                .filter(rank -> rank.value == value)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Rank의 범위는 1-8까지 입니다. : %d".formatted(value)));
    }

    public Rank move(int amount) {
        return of(value + amount);
    }

    public int calculateRankDifference(Rank targetRank) {
        return targetRank.value - value;
    }
}
