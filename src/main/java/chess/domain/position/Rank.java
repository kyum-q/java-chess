package chess.domain.position;

public enum Rank {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    ;

    public Rank move(int amount) {
        int newOrdinal = ordinal() + amount;
        if (newOrdinal >= values().length) {
            throw new IllegalArgumentException("Rank의 범위를 넘어갔습니다.");
        }
        return values()[newOrdinal];
    }

    public int calculateRankDifference(Rank targetRank) {
        return targetRank.ordinal() - ordinal();
    }
}
