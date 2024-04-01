package chess.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @DisplayName("양수 값이 입력되면, 가장 작은 움직임으로 1을 알 수 있다.")
    @Test
    void calculateMinMovementPlus() {
        assertThat(Calculator.calculateMinMovement(2)).isEqualTo(1);
    }

    @DisplayName("음수 값이 입력되면, 가장 작은 움직임으로 -1을 알 수 있다.")
    @Test
    void calculateMinMovementMinus() {
        assertThat(Calculator.calculateMinMovement(-2)).isEqualTo(-1);
    }
}
