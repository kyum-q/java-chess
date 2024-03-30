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

class KingTest {
    @DisplayName("킹은 한칸 초과하여 움직인 경우, 예외가 발생한다.")
    @Test
    void kingMoveOverOne() {
        assertThatThrownBy(() -> new King(Team.WHITE)
                .findBetweenPositions(new Positions(
                        Position.of(File.A, Rank.ONE),
                        Position.of(File.C, Rank.THREE))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 움직일 수 없습니다.");
    }

    @DisplayName("두 위치 사이의 킹이 갈 수 있는 위치들을 반환한다.")
    @Test
    void betweenPosition() {
        assertThat(new King(Team.WHITE)
                .findBetweenPositions(new Positions(
                        Position.of(File.C, Rank.THREE),
                        Position.of(File.B, Rank.TWO))))
                .isEmpty();
    }
}
