package chess.domain.piece.character;

public enum Kind {
    PAWN(1.0, 0.5),
    KNIGHT(2.5, 2.5),
    BISHOP(3.0, 3.0),
    ROOK(5.0, 5.0),
    QUEEN(9.0, 9.0),
    KING(0.0, 0.0),
    ;

    private final double maxScore;
    private final double minScore;

    Kind(double maxScore, double minScore) {
        this.maxScore = maxScore;
        this.minScore = minScore;
    }

    public double maxScore() {
        return maxScore;
    }

    public double minScore() {
        return minScore;
    }
}
