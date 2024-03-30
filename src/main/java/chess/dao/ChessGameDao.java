package chess.dao;

import chess.domain.piece.character.Team;

import java.sql.Connection;
import java.sql.SQLException;

public class ChessGameDao {
    private final Connection connection;

    public ChessGameDao(Connection connection) {
        this.connection = connection;
    }

    public void addChessGame(String id) {
        if(checkById(id)) {
            throw new IllegalStateException("해당 id에 게임이 이미 존재합니다.");
        }

        final var query = "INSERT INTO chess_game(game_id) VALUES(?)";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkById(String id) {
        final var query = "SELECT game_id FROM chess_game WHERE game_id = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            final var resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String findIdByLastGame() {
        final var query = "SELECT game_id FROM chess_game ORDER BY game_id DESC LIMIT 1";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("game_id");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("game이 없습니다.");
    }

    public Team findTeamById(String id) {
        final var query = "SELECT turn FROM chess_game WHERE game_id = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Team.valueOf(resultSet.getString("turn"));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("해당 id에 game이 없습니다.");
    }

    public void updateTurn(String id, Team team) {
        final var query = "UPDATE chess_game SET turn = ? WHERE game_id = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.name());
            preparedStatement.setString(2, id);

            if (preparedStatement.executeUpdate() != 1) {
                throw new IllegalStateException("game이 없습니다.");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
