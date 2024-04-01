package chess.domain.position;

import chess.domain.piece.*;
import chess.domain.piece.character.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class PositionTest {
    @DisplayName("원하는만큼 위쪽으로 이동할 수 있다.")
    @Test
    void moveUp() {
        Position position = Position.of(File.B, Rank.TWO)
                .move(1, 0);

        assertThat(position).isEqualTo(Position.of(File.C, Rank.TWO));
    }

    @DisplayName("원하는만큼 아래쪽로 이동할 수 있다.")
    @Test
    void moveDown() {
        Position position = Position.of(File.B, Rank.TWO)
                .move(-1, 0);

        assertThat(position).isEqualTo(Position.of(File.A, Rank.TWO));
    }

    @DisplayName("원하는만큼 왼쪽으로 이동할 수 있다.")
    @Test
    void moveLeft() {
        Position position = Position.of(File.B, Rank.TWO)
                .move(0, -1);

        assertThat(position).isEqualTo(Position.of(File.B, Rank.ONE));
    }

    @DisplayName("원하는만큼 오른쪽으로 이동할 수 있다.")
    @Test
    void moveRight() {
        Position position = Position.of(File.B, Rank.TWO)
                .move(0, 1);

        assertThat(position).isEqualTo(Position.of(File.B, Rank.THREE));
    }

    @DisplayName("킹이 움직일 수 있는 위치들을 알 수 있다.")
    @Test
    void findAllMovablePositionKing() {
        Position position = Position.of(File.B, Rank.TWO);
        Stream<Position> positions = position.findAllMovablePosition(new King(Team.WHITE));

        assertThat(positions).containsExactlyInAnyOrder(
                Position.of(File.A, Rank.ONE),
                Position.of(File.A, Rank.TWO),
                Position.of(File.A, Rank.THREE),
                Position.of(File.B, Rank.ONE),
                Position.of(File.B, Rank.THREE),
                Position.of(File.C, Rank.ONE),
                Position.of(File.C, Rank.TWO),
                Position.of(File.C, Rank.THREE));
    }

    @DisplayName("안 움직인 폰이 움직일 수 있는 위치들을 알 수 있다.")
    @Test
    void findAllMovablePositionMovedPawn() {
        Position position = Position.of(File.B, Rank.TWO);
        Stream<Position> positions = position.findAllMovablePosition(new Pawn(Team.WHITE));

        assertThat(positions).containsExactlyInAnyOrder(
                Position.of(File.B, Rank.THREE),
                Position.of(File.B, Rank.FOUR));
    }

    @DisplayName("움직인 폰이 움직일 수 있는 위치들을 알 수 있다.")
    @Test
    void findAllMovablePositionNotMovedPawn() {
        Position position = Position.of(File.B, Rank.TWO);
        Stream<Position> positions = position.findAllMovablePosition(new Pawn(Team.WHITE).move());

        assertThat(positions).containsExactlyInAnyOrder(
                Position.of(File.B, Rank.THREE));
    }

    @DisplayName("나이트가 움직일 수 있는 위치들을 알 수 있다.")
    @Test
    void findAllMovablePositionKnight() {
        Position position = Position.of(File.C, Rank.THREE);
        Stream<Position> positions = position.findAllMovablePosition(new Knight(Team.WHITE));

        assertThat(positions).containsExactlyInAnyOrder(
                Position.of(File.A, Rank.TWO),
                Position.of(File.A, Rank.FOUR),
                Position.of(File.B, Rank.ONE),
                Position.of(File.B, Rank.FIVE),
                Position.of(File.D, Rank.ONE),
                Position.of(File.D, Rank.FIVE),
                Position.of(File.E, Rank.TWO),
                Position.of(File.E, Rank.FOUR));
    }

    @DisplayName("룩이 움직일 수 있는 위치들을 알 수 있다.")
    @Test
    void findAllMovablePositionRook() {
        Position position = Position.of(File.B, Rank.TWO);
        Stream<Position> positions = position.findAllMovablePosition(new Rook(Team.WHITE));

        assertThat(positions).containsExactlyInAnyOrder(
                Position.of(File.B, Rank.ONE),
                Position.of(File.B, Rank.THREE),
                Position.of(File.B, Rank.FOUR),
                Position.of(File.B, Rank.FIVE),
                Position.of(File.B, Rank.SIX),
                Position.of(File.B, Rank.SEVEN),
                Position.of(File.B, Rank.EIGHT),
                Position.of(File.A, Rank.TWO),
                Position.of(File.C, Rank.TWO),
                Position.of(File.D, Rank.TWO),
                Position.of(File.E, Rank.TWO),
                Position.of(File.F, Rank.TWO),
                Position.of(File.G, Rank.TWO),
                Position.of(File.H, Rank.TWO));
    }

    @DisplayName("비숍이 움직일 수 있는 위치들을 알 수 있다.")
    @Test
    void findAllMovablePositionBishop() {
        Position position = Position.of(File.B, Rank.TWO);
        Stream<Position> positions = position.findAllMovablePosition(new Bishop(Team.WHITE));

        assertThat(positions).containsExactlyInAnyOrder(
                Position.of(File.A, Rank.ONE),
                Position.of(File.C, Rank.THREE),
                Position.of(File.D, Rank.FOUR),
                Position.of(File.E, Rank.FIVE),
                Position.of(File.F, Rank.SIX),
                Position.of(File.G, Rank.SEVEN),
                Position.of(File.H, Rank.EIGHT),
                Position.of(File.A, Rank.THREE),
                Position.of(File.C, Rank.ONE));
    }

    @DisplayName("퀸이 움직일 수 있는 위치들을 알 수 있다.")
    @Test
    void findAllMovablePositionQueen() {
        Position position = Position.of(File.B, Rank.TWO);
        Stream<Position> positions = position.findAllMovablePosition(new Queen(Team.WHITE));

        assertThat(positions).containsExactlyInAnyOrder(
                Position.of(File.B, Rank.ONE),
                Position.of(File.B, Rank.THREE),
                Position.of(File.B, Rank.FOUR),
                Position.of(File.B, Rank.FIVE),
                Position.of(File.B, Rank.SIX),
                Position.of(File.B, Rank.SEVEN),
                Position.of(File.B, Rank.EIGHT),
                Position.of(File.A, Rank.TWO),
                Position.of(File.C, Rank.TWO),
                Position.of(File.D, Rank.TWO),
                Position.of(File.E, Rank.TWO),
                Position.of(File.F, Rank.TWO),
                Position.of(File.G, Rank.TWO),
                Position.of(File.H, Rank.TWO),
                Position.of(File.A, Rank.ONE),
                Position.of(File.C, Rank.THREE),
                Position.of(File.D, Rank.FOUR),
                Position.of(File.E, Rank.FIVE),
                Position.of(File.F, Rank.SIX),
                Position.of(File.G, Rank.SEVEN),
                Position.of(File.H, Rank.EIGHT),
                Position.of(File.A, Rank.THREE),
                Position.of(File.C, Rank.ONE));
    }

    @DisplayName("두 위치의 File의 차이를 알 수 있다.")
    @Test
    void calculateFileDifference() {
        Position position = Position.of(File.B, Rank.TWO);
        assertThat(position.calculateFileDifference(Position.of(File.F, Rank.TWO)))
                .isEqualTo(4);
    }

    @DisplayName("두 위치의 File의 차이를 알 수 있다.")
    @Test
    void calculateRankDifference() {
        Position position = Position.of(File.B, Rank.TWO);
        assertThat(position.calculateRankDifference(Position.of(File.B, Rank.FOUR)))
                .isEqualTo(2);
    }

    @DisplayName("동일한 file일 경우, true를 반환한다.")
    @Test
    void checkFileTrue() {
        Position position = Position.of(File.B, Rank.TWO);
        assertThat(position.checkFile(File.B)).isTrue();
    }

    @DisplayName("동일한 file이 아닌 경우, false를 반환한다.")
    @Test
    void checkFile() {
        Position position = Position.of(File.B, Rank.TWO);
        assertThat(position.checkFile(File.C)).isFalse();
    }
}
