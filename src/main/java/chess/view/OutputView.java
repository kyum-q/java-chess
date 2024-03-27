package chess.view;

import chess.domain.CheckState;
import chess.domain.position.File;
import chess.domain.position.Rank;
import chess.dto.BoardDto;

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
}
