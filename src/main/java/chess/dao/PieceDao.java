package chess.dao;

import chess.dao.converter.PieceConverter;
import chess.domain.piece.Piece;
import chess.domain.piece.character.Team;

import java.sql.Connection;
import java.sql.SQLException;

public class PieceDao {
    private final Connection connection;

    public PieceDao(Connection connection) {
        this.connection = connection;
    }

    public Piece findById(String id) {
        final var query = "SELECT * FROM piece WHERE piece_id = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return PieceConverter.converterPiece(
                        resultSet.getString("kind"),
                        Team.valueOf(resultSet.getString("team")),
                        resultSet.getString("is_moved").equals("1"));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("해당 id에 Piece가 없습니다.");
    }


}
