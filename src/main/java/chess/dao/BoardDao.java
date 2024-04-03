package chess.dao;

import chess.dao.converter.PositionConverter;
import chess.domain.position.Position;
import chess.domain.position.Positions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BoardDao {
    private final String databaseName;

    public BoardDao(String databaseName) {
        this.databaseName = databaseName;
    }

    public void deleteAndAddPositions(String gameId, Positions positions, String pieceId) {
        deletePosition(gameId, positions);
        addPosition(gameId, positions.target(), pieceId);
    }

    public void addPosition(String gameId, Position position, String pieceId) {
        final var query = "INSERT INTO board(position, game_id, piece_id) VALUES(?, ?, ?)";
        try (final var connection = ConnectionGenerator.getConnection(databaseName)) {
            final var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, PositionConverter.convertString(position));
            preparedStatement.setString(2, gameId);
            preparedStatement.setString(3, pieceId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deletePosition(String gameId, Positions positions) {
        final var source = PositionConverter.convertString(positions.source());
        final var target = PositionConverter.convertString(positions.target());
        final var query = "DELETE FROM board WHERE game_id = '%s' AND (position = '%s' OR position = '%s')"
                .formatted(gameId, source, target);
        try (final var connection = ConnectionGenerator.getConnection(databaseName)) {
            final var preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Position, String> findPiece(String gameId) {
        Map<Position, String> board = new HashMap<>();

        final var query = "SELECT * FROM board WHERE game_id = ?";
        try (final var connection = ConnectionGenerator.getConnection(databaseName)) {
            final var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, gameId);
            final var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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
