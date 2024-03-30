package chess.domain.position;

public enum File {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    ;

    public File move(int amount) {
        int newOrdinal = ordinal() + amount;
        if (newOrdinal >= values().length) {
            throw new IllegalArgumentException("File의 범위를 넘어갔습니다.");
        }
        return values()[newOrdinal];
    }

    public int calculateFileDifference(File targetFile) {
        return targetFile.ordinal() - ordinal();
    }
}
