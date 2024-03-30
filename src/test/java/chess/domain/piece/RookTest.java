package chess.domain.piece;

import chess.domain.piece.character.Team;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Positions;
import chess.domain.position.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RookTest {
    @DisplayName("룩은 직선이 아닌 경우, 예외가 발생한다.")
    @Test
    void rookMoveOverLine() {
        assertThatThrownBy(() -> new Rook(Team.WHITE)
                .findBetweenPositions(new Positions(
                        Position.of(File.A, Rank.ONE),
                        Position.of(File.B, Rank.TWO))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 움직일 수 없습니다.");
    }

    @DisplayName("두 위치 사이의 룩이 갈 수 있는 위치들을 반환한다.")
    @Test
    void betweenPosition() {
        assertThat(new Rook(Team.WHITE)
                .findBetweenPositions(new Positions(
                        Position.of(File.D, Rank.FOUR),
                        Position.of(File.G, Rank.FOUR))))
                .containsExactly(Position.of(File.E, Rank.FOUR), Position.of(File.F, Rank.FOUR));
    }

    @DisplayName("두 위치 사이의 룩이 갈 수 있는 위치들을 반환한다.")
    @Test
    void betweenPositionMinus() {
        assertThat(new Rook(Team.WHITE)
                .findBetweenPositions(new Positions(
                        Position.of(File.D, Rank.FOUR),
                        Position.of(File.D, Rank.ONE))))
                .containsExactly(Position.of(File.D, Rank.THREE), Position.of(File.D, Rank.TWO));
    }

}
