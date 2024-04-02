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
    public static final String DATABASE_NAME = "chess_test";
    private PieceDao pieceDao;

    @BeforeEach
    void connection() {
        pieceDao = new PieceDao(DATABASE_NAME);
        pieceDao.settingPiece();
    }

    @DisplayName("piece 정보를 데이터베이스에 찾을 수 있다.")
    @Test
    void findPiece() {
        Piece piece = new King(Team.WHITE);
        String id = PieceConverter.converterId(piece);
        assertThat(pieceDao.findById(id)).isEqualTo(piece);
    }
}
