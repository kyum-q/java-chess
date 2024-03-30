package chess.domain.piece;

import chess.domain.piece.character.Team;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Positions;
import chess.domain.position.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.*;

class PawnTest {
    @DisplayName("흰색 폰은 시작 지점에 있는 경우, 2칸 초과시 예외가 발생한다.")
    @Test
    void startWhitePawnMoveOverTwo() {
        assertThatThrownBy(() -> new Pawn(Team.WHITE)
                .findBetweenPositions(new Positions(
                        Position.of(File.A, Rank.TWO),
                        Position.of(File.A, Rank.FIVE))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 움직일 수 없습니다.");
    }

    @DisplayName("검은색 폰은 시작 지점에 있는 경우, 2칸 초과시 예외가 발생한다.")
    @Test
    void startBlackPawnMoveOverTwo() {
        assertThatThrownBy(() -> new Pawn(Team.BLACK)
                .findBetweenPositions(new Positions(
                        Position.of(File.A, Rank.SEVEN),
                        Position.of(File.A, Rank.FOUR))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 움직일 수 없습니다.");
    }

    @DisplayName("흰색 폰은 1칸 초과시 예외가 발생한다.")
    @Test
    void whitePawnMoveOverTwo() {
        assertThatThrownBy(() -> new Pawn(Team.WHITE).move()
                .findBetweenPositions(new Positions(
                        Position.of(File.A, Rank.THREE),
                        Position.of(File.A, Rank.FIVE))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 움직일 수 없습니다.");
    }

    @DisplayName("검은색 폰은 1칸 초과시 예외가 발생한다.")
    @Test
    void blackPawnMoveOverTwo() {
        assertThatThrownBy(() -> new Pawn(Team.BLACK).move()
                .findBetweenPositions(new Positions(
                        Position.of(File.A, Rank.SIX),
                        Position.of(File.A, Rank.FOUR))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 움직일 수 없습니다.");
    }

    @DisplayName("흰색 폰은 옆으로 이동할 수 없다.")
    @ParameterizedTest
    @EnumSource
    void whitePawnMoveColumn(Team team) {
        assertThatThrownBy(() -> new Pawn(team)
                .findBetweenPositions(new Positions(
                        Position.of(File.A, Rank.SEVEN),
                        Position.of(File.B, Rank.SEVEN))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 움직일 수 없습니다.");
    }

    @DisplayName("두 위치 사이의 폰이 갈 수 있는 위치들을 반환한다.")
    @Test
    void betweenPosition() {
        assertThat(new Pawn(Team.WHITE)
                .findBetweenPositions(new Positions(
                        Position.of(File.C, Rank.TWO),
                        Position.of(File.C, Rank.FOUR))))
                .containsExactly(Position.of(File.C, Rank.THREE));
    }

    @DisplayName("두 위치 사이의 폰이 갈 수 있는 위치들을 반환한다.")
    @Test
    void betweenPositionHasMoved() {
        assertThat(new Pawn(Team.WHITE).move()
                .findBetweenPositions(new Positions(
                        Position.of(File.C, Rank.THREE),
                        Position.of(File.C, Rank.FOUR))))
                .isEmpty();
    }

    @DisplayName("두 위치 사이의 폰이 갈 수 있는 위치들을 반환한다.")
    @Test
    void betweenPositionOneWhenHasNotMoved() {
        assertThat(new Pawn(Team.WHITE)
                .findBetweenPositions(new Positions(
                        Position.of(File.C, Rank.TWO),
                        Position.of(File.C, Rank.THREE))))
                .isEmpty();
    }

    @DisplayName("공격 가능할 때, 대각으로 움직일 수 있다.")
    @Test
    void movableDiagonalWhenAttack() {
        assertThatCode(() -> new Pawn(Team.WHITE)
                .findBetweenPositionsWhenAttack(new Positions(
                        Position.of(File.B, Rank.TWO),
                        Position.of(File.C, Rank.THREE))))
                .doesNotThrowAnyException();
    }

    @DisplayName("공격 가능할 때, 두 위치 사이의 폰이 갈 수 있는 위치는 없다.")
    @Test
    void noneBetweenPositionWhenAttack() {
        assertThat(new Pawn(Team.WHITE)
                .findBetweenPositionsWhenAttack(new Positions(
                        Position.of(File.B, Rank.TWO),
                        Position.of(File.C, Rank.THREE))))
                .isEmpty();
    }

    @DisplayName("공격 가능할 때, 직선으로 움직이면 예외가 발생한다.")
    @Test
    void cannotMoveStraightWhenAttack() {
        assertThatThrownBy(() -> new Pawn(Team.WHITE)
                .findBetweenPositionsWhenAttack(new Positions(
                        Position.of(File.B, Rank.TWO),
                        Position.of(File.B, Rank.THREE))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 움직일 수 없습니다.");
    }
}
