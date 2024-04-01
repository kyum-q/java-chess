package chess.domain.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositionsTest {
    @DisplayName("두 포지션이 동일하면 예외가 발생한다.")
    @Test
    void invalidPositions() {
        assertThatThrownBy(() -> new Positions(Position.of(File.A, Rank.TWO), Position.of(File.A, Rank.TWO)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작 위치와 목표 위치가 동일할 수 없습니다.");
    }
}
