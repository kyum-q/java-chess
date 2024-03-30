package chess.dao;

import chess.dao.converter.PieceConverter;
import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.piece.character.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

class PieceDaoTest {

    private PieceDao pieceDao;

    @BeforeEach
    void connection() {
        Connection connection = new ConnectionGenerator().getConnection("chess_test");
        pieceDao = new PieceDao(connection);
    }

    @DisplayName("piece 정보를 데이터베이스에 찾을 수 있다.")
    @Test
    void findPiece() {
        Piece piece = new King(Team.WHITE);
        String id = PieceConverter.converterId(piece);
        assertThat(pieceDao.findById(id)).isEqualTo(piece);
    }
}
