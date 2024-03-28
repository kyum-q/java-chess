package chess.dao;

import chess.dao.converter.FileConverter;
import chess.domain.position.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileConverterTest {
    @DisplayName("a-h까지 문자로 주면 해당 File이 제작된다.")
    @ParameterizedTest
    @CsvSource(value = {"a, A", "b, B", "c, C", "d, D", "e, E", "f, F", "g, G", "h, H"})
    void createFile(char value, File file) {
        assertThat(FileConverter.convertFile(value)).isEqualTo(file);
    }

    @DisplayName("File 범위 밖을 벗어나면 예외가 발생한다.")
    @Test
    void invalidColumnThrowsException() {
        assertThatThrownBy(() -> FileConverter.convertFile('i'))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("File 범위는 1-8까지 입니다. : i");
    }
}
