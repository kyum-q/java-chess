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
    public void addPiece(Piece piece) {
        final var query = "INSERT INTO piece VALUES(?, ?, ?, ?)";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, PieceConverter.converterId(piece));
            preparedStatement.setString(2, piece.findKind().name());
            preparedStatement.setString(3, piece.team().name());
            preparedStatement.setString(4, PieceConverter.booleanToTinyInt(piece.isMoved()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
