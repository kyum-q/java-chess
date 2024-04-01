package chess.domain.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RankTest {
    @DisplayName("rank를 원하는 만큼 움직일 수 있다.")
    @Test
    void move() {
        assertThat(Rank.ONE.move(3)).isEqualTo(Rank.FOUR);
    }

    @DisplayName("두 rank의 차이 값을 알 수 있다.")
    @Test
    void calculateRankDifference() {
        assertThat(Rank.ONE.calculateRankDifference(Rank.FOUR)).isEqualTo(3);
    }
}
