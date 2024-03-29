package chess.dao.converter;

import chess.dao.converter.RankConverter;
import chess.domain.position.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RankConverterTest {
    @DisplayName("1-8까지 숫자를 문자로 주면 해당 Rank가 제작된다.")
    @ParameterizedTest
    @CsvSource(value = {"1, ONE", "2, TWO", "3, THREE", "4, FOUR", "5, FIVE", "6, SIX", "7, SEVEN", "8, EIGHT"})
    void createRank(char value, Rank rank) {
        assertThat(RankConverter.convertRank(value)).isEqualTo(rank);
    }

    @DisplayName("Rank 범위 밖을 벗어나면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(chars = {'0', '9'})
    void invalidRowThrowsException(char rank) {
        assertThatThrownBy(() -> RankConverter.convertRank(rank))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Rank의 범위는 1-8까지 입니다. : " + rank);
    }
}
