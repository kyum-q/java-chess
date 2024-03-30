package chess.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class ConnectionGeneratorTest {

    @DisplayName("DB와 연결된 Connection을 얻을 수 있다.")
    @Test
    void getConnection() {
        ConnectionGenerator generator = new ConnectionGenerator();
        try (final var connection = generator.getConnection("chess")) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
