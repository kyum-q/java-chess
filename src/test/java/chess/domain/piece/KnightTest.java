package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.Position;
import chess.domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KnightTest {
    @DisplayName("자신의 종류를 반환한다.")
    @Test
    void findKind() {
        assertThat(new Knight(Position.of(1, 1), Team.BLACK).findKind())
                .isEqualTo(Kind.KNIGHT);
    }
}