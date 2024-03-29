package chess.dao;

import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.piece.character.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatCode;

class PieceDaoTest {

    private Connection connection;

    @BeforeEach
    void connection() {
        connection = new ConnectionGenerator().getConnection("chess_test");
        final var query = "DELETE FROM piece";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("piece 정보를 데이터베이스에 추가할 수 있다.")
    @Test
    void addPiece() {
        Piece piece = new King(Team.WHITE);
        PieceDao pieceDao = new PieceDao(connection);
        assertThatCode(() -> pieceDao.addPiece(piece))
                .doesNotThrowAnyException();
    }
}
