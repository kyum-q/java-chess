package chess.domain.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileTest {
    @DisplayName("file을 원하는 만큼 움직일 수 있다.")
    @Test
    void move() {
        assertThat(File.A.move(3)).isEqualTo(File.D);
    }

    @DisplayName("두 file의 차이 값을 알 수 있다.")
    @Test
    void calculateFileDifference() {
        assertThat(File.A.calculateFileDifference(File.D)).isEqualTo(3);
    }
}
