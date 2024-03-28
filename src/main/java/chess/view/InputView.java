package chess.view;

import chess.dao.converter.PositionConverter;
import chess.domain.position.Positions;

import java.util.Scanner;
import java.util.StringTokenizer;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void inputStartCommand() {
        System.out.println("> 체스 게임을 시작합니다.");
        System.out.printf("> 게임 시작 : %s%n", Command.START.getValue());
        System.out.printf("> 게임 종료 : %s%n", Command.END.getValue());
        System.out.printf("> 게임 이동 : %s source위치 target위치 - 예. %s b2 b3%n",
                Command.MOVE.getValue(), Command.MOVE.getValue());

        String commandValue = SCANNER.next();
        if (Command.START != Command.find(commandValue)) {
            throw new IllegalArgumentException("게임 시작 전, 다른 명령어를 입력할 수 없습니다.");
        }
    }

    public static Command inputCommand() {
        Command command = Command.find(SCANNER.next());
        if(Command.START == command) {
            throw new IllegalArgumentException("게임이 시작한 이후, 다시 게임을 시작할 수 없습니다.");
        }
        return command;
    }

    public static Positions inputPositions() {
        StringTokenizer inputTokenizer = new StringTokenizer(SCANNER.nextLine());
        if (inputTokenizer.countTokens() == 2) {
            return new Positions(
                    PositionConverter.convertPosition(inputTokenizer.nextToken()),
                    PositionConverter.convertPosition(inputTokenizer.nextToken()));
        }
        throw new IllegalArgumentException("체스 보드 내에 존재하지 않는 위치입니다.");
    }
}
