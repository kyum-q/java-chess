package chess.dao;

import chess.dao.converter.PositionConverter;
import chess.domain.position.Position;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BoardDao {
    private final Connection connection;

    public BoardDao(Connection connection) {
        this.connection = connection;
    }

    public void addPosition(Position position, String gameId, String pieceId) {
        final var query = "INSERT INTO board VALUES(?, ?, ?)";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, PositionConverter.convertString(position));
            preparedStatement.setString(2, gameId);
            preparedStatement.setString(3, pieceId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePosition(Position position) {
        final var source = PositionConverter.convertString(position);
        final var query = "DELETE FROM `board` WHERE (`position` = %s)".formatted(source);
        try (final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Position, String> findPiece(String gameId) {
        Map<Position, String> board = new HashMap<>();

        final var query = "SELECT * FROM board WHERE game_id = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameId);
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                board.put(
                        PositionConverter.convertPosition(resultSet.getString("position")),
                        resultSet.getString("piece_id")
                );
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return board;
    }
}
