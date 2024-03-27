package chess.domain.piece;

import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Positions;
import chess.domain.piece.character.Team;
import chess.domain.position.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class KnightTest {
    @DisplayName("나이트는 날 일 자가 아닌 경우, 예외가 발생한다.")
    @Test
    void knightMoveOverDayHieroglyph() {
        assertThatThrownBy(() -> new Knight(Team.WHITE)
                .findBetweenPositions(new Positions(
                        Position.of(File.A, Rank.ONE),
                        Position.of(File.C, Rank.THREE))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 움직일 수 없습니다.");
    }

    @DisplayName("두 위치 사이의 나이트가 갈 수 있는 위치들을 반환한다.")
    @Test
    void betweenPosition() {
        assertThat(new Knight(Team.WHITE)
                .findBetweenPositions(new Positions(
                        Position.of(File.C, Rank.THREE),
                        Position.of(File.B, Rank.ONE))))
                .isEmpty();
    }
}
