package chess.domain;

import chess.domain.piece.character.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class OutcomeTest {

    @DisplayName("팀에 따라, 해당 팀이 우승인 결과를 알 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"BLACK, BLACK_WIN", "WHITE, WHITE_WIN"})
    void of(Team team, Outcome outcome) {
        assertThat(Outcome.of(team)).isEqualTo(outcome);
    }
}
