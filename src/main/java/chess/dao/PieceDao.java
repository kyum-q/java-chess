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

    public void settingPiece() {
        final var query = "INSERT INTO `piece` VALUES ('000', 'PAWN', 'BLACK', '0');" +
                "INSERT INTO `piece` VALUES ('001', 'PAWN', 'BLACK', '1');" +
                "INSERT INTO `piece` VALUES ('010', 'PAWN', 'WHITE', '0');" +
                "INSERT INTO `piece` VALUES ('011', 'PAWN', 'WHITE', '1');" +
                "INSERT INTO `piece` VALUES ('100', 'KNIGHT', 'BLACK', '0');" +
                "INSERT INTO `piece` VALUES ('101', 'KNIGHT', 'BLACK', '1');" +
                "INSERT INTO `piece` VALUES ('110', 'KNIGHT', 'WHITE', '0');" +
                "INSERT INTO `piece` VALUES ('111', 'KNIGHT', 'WHITE', '1');" +
                "INSERT INTO `piece` VALUES ('200', 'BISHOP', 'BLACK', '0');" +
                "INSERT INTO `piece` VALUES ('201', 'BISHOP', 'BLACK', '1');" +
                "INSERT INTO `piece` VALUES ('210', 'BISHOP', 'WHITE', '0');" +
                "INSERT INTO `piece` VALUES ('211', 'BISHOP', 'WHITE', '1');" +
                "INSERT INTO `piece` VALUES ('300', 'ROOK', 'BLACK', '0');" +
                "INSERT INTO `piece` VALUES ('301', 'ROOK', 'BLACK', '1');" +
                "INSERT INTO `piece` VALUES ('310', 'ROOK', 'WHITE', '0');" +
                "INSERT INTO `piece` VALUES ('311', 'ROOK', 'WHITE', '1');" +
                "INSERT INTO `piece` VALUES ('400', 'QUEEN', 'BLACK', '0');" +
                "INSERT INTO `piece` VALUES ('401', 'QUEEN', 'BLACK', '1');" +
                "INSERT INTO `piece` VALUES ('410', 'QUEEN', 'WHITE', '0');" +
                "INSERT INTO `piece` VALUES ('411', 'QUEEN', 'WHITE', '1');" +
                "INSERT INTO `piece` VALUES ('500', 'KING', 'BLACK', '0');" +
                "INSERT INTO `piece` VALUES ('501', 'KING', 'BLACK', '1');" +
                "INSERT INTO `piece` VALUES ('510', 'KING', 'WHITE', '0');" +
                "INSERT INTO `piece` VALUES ('511', 'KING', 'WHITE', '1');";

        try {
            final var statements = query.split(";");
            final var statement = connection.createStatement();

            for (final var singleQuery : statements) {
                String[] parts = singleQuery.split("'");
                if (!isContainPieceInDB(parts[1])) {
                    statement.executeUpdate(singleQuery);
                }
            }
            statement.close();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isContainPieceInDB(String id) {
        final var query = "SELECT * FROM piece WHERE piece_id = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            final var resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (final SQLException e) {
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
