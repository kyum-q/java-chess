package chess.view;

import chess.domain.CheckState;
import chess.domain.Outcome;
import chess.domain.piece.character.Team;
import chess.domain.position.File;
import chess.domain.position.Rank;
import chess.dto.BoardDto;

import java.util.Map;

public class OutputView {
    public static void printChessBoard(BoardDto boardDto) {
        for (Rank rank : Rank.values()) {
            printChessRow(boardDto, rank);
        }
        System.out.println();
    }

    private static void printChessRow(BoardDto boardDto, Rank rank) {
        for (File file : File.values()) {
            System.out.print(boardDto.identifyPositionCharacter(file, rank));
        }
        System.out.println();
    }

    public static void printCheck(CheckState checkState) {
        String checkMessage = switch (checkState) {
            case CHECK -> "체크 !\n";
            case CHECK_MATE -> "체크 메이트 !\n";
            case SAFE -> "";
        };

        System.out.print(checkMessage);
    }

    public static void printScore(Outcome outcome, Map<Team, Double> mapTeamAndScore) {
        System.out.println("결과 출력");
        System.out.printf("결과 - %s%n", outcomeToString(outcome));
        mapTeamAndScore
                .forEach((key, value) -> System.out.println(teamToString(key) + " : " + value));
        System.out.println();
    }

    private static String outcomeToString(Outcome outcome) {
        return switch (outcome) {
            case BLACK_WIN -> "검은색 팀 승";
            case WHITE_WIN -> "흰색 팀 승";
            case DRAW -> "무승부";
        };
    }

    private static String teamToString(Team team) {
        return switch (team) {
            case BLACK -> "검은색 팀";
            case WHITE -> "흰색 팀";
        };
    }
}
