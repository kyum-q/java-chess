package chess.domain;

public enum CheckState {
    CHECK,
    CHECK_MATE,
    SAFE,
    ;

    public boolean isCheckMate() {
        return this == CHECK_MATE;
    }
}
