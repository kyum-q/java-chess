package chess.domain.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileTest {
    @DisplayName("a-h까지 문자로 주면 해당 File이 제작된다.")
    @ParameterizedTest
    @CsvSource(value = {"a, A", "b, B", "c, C", "d, D", "e, E", "f, F", "g, G", "h, H"})
    void createFile(char value, File file) {
        assertThat(File.of(value)).isEqualTo(file);
    }

    @DisplayName("File 범위 밖을 벗어나면 예외가 발생한다.")
    @Test
    void invalidColumnThrowsException() {
        assertThatThrownBy(() -> File.of('i'))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("File 범위는 1-8까지 입니다. : i");
    }
}
