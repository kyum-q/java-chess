package chess.domain.position;

import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
}
