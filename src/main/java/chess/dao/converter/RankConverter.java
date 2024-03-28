package chess.dao.converter;

import chess.domain.position.Rank;

import java.util.Arrays;

public class RankConverter {
    public static Rank convertRank(char value) {
        String stringRank = String.valueOf(value);
        return Arrays.stream(Rank.values())
                .filter(rank -> convertChar(rank).equals(stringRank))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Rank의 범위는 1-8까지 입니다. : %c".formatted(value)));
    }

    public static String convertChar(Rank rank) {
        return switch (rank) {
            case EIGHT -> "8";
            case SEVEN -> "7";
            case SIX -> "6";
            case FIVE -> "5";
            case FOUR -> "4";
            case THREE -> "3";
            case TWO -> "2";
            case ONE -> "1";
        };
    }
}
