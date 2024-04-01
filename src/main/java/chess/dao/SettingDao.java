package chess.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class SettingDao {
    private final Connection connection;

    public SettingDao(Connection connection) {
        this.connection = connection;
    }
    public void settingTable() {
        final var query = "CREATE TABLE IF NOT EXISTS chess_game" +
                "(" +
                "  `game_id` varchar(255)," +
                "  `turn`    enum('WHITE', 'BLACK') DEFAULT 'WHITE'," +
                "  PRIMARY KEY (game_id)" +
                ");" +
                "CREATE TABLE IF NOT EXISTS piece" +
                "(" +
                "  `piece_id` char(3)," +
                "  `kind`     varchar(50) not null," +
                "  `team`     enum('WHITE', 'BLACK') not null," +
                "  `is_moved` boolean     not null," +
                "  PRIMARY KEY (piece_id)," +
                "  UNIQUE (kind, team, is_moved)" +
                ");" +
                "CREATE TABLE IF NOT EXISTS board" +
                "(" +
                "  `board_id` bigint auto_increment, " +
                "  `position` char(2)," +
                "  `game_id`  varchar(255) not null," +
                "  `piece_id` char(3) not null," +
                "  PRIMARY KEY (board_id)," +
                "  UNIQUE (position, game_id)," +
                "  FOREIGN KEY (game_id) REFERENCES chess_game (game_id) ON UPDATE CASCADE," +
                "  FOREIGN KEY (piece_id) REFERENCES piece (piece_id) ON UPDATE CASCADE" +
                ");";

        try {
            final var statements = query.split(";");
            final var statement = connection.createStatement();

            for (final var singleQuery : statements) {
                String[] parts = singleQuery.split("'");
                statement.executeUpdate(singleQuery);
            }
            statement.close();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
