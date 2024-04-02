package chess.dao;

import java.sql.SQLException;

public class SettingDB {
    public static void settingTable(String databaseName) {
        final var query = "CREATE TABLE IF NOT EXISTS chess_game" +
                "(" +
                "  `game_id` varchar(255)," +
                "  `turn`    enum('WHITE', 'BLACK') DEFAULT 'WHITE'," +
                "  PRIMARY KEY (game_id)" +
                ");" +
                "CREATE TABLE IF NOT EXISTS board" +
                "(" +
                "  `board_id` bigint auto_increment, " +
                "  `position` char(2)," +
                "  `game_id`  varchar(255) not null," +
                "  `piece_id` char(3) not null," +
                "  PRIMARY KEY (board_id)," +
                "  UNIQUE (position, game_id)," +
                "  FOREIGN KEY (game_id) REFERENCES chess_game (game_id) ON UPDATE CASCADE" +
                ");";

        try (final var connection = ConnectionGenerator.getConnection(databaseName)) {
            final var statements = query.split(";");
            final var statement = connection.createStatement();

            for (final var singleQuery : statements) {
                statement.executeUpdate(singleQuery);
            }
            statement.close();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
