package chess.dao;

import chess.domain.piece.Piece;

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
            preparedStatement.setString(1, pieceToId(piece));
            preparedStatement.setString(2, piece.findKind().name());
            preparedStatement.setString(3, piece.team().name());
            preparedStatement.setString(4, booleanToTinyInt(piece.isMoved()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String pieceToId(Piece piece) {
        return String.valueOf(piece.findKind().ordinal())
                + piece.team().ordinal()
                + booleanToTinyInt(piece.isMoved());
    }

    private String booleanToTinyInt(boolean value) {
        if(value) {
            return "1";
        }
        return "0";
    }
}
