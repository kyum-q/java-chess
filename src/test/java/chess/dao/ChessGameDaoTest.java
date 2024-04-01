package chess.dao;

import chess.domain.piece.character.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ChessGameDaoTest {
    private Connection connection;
    private ChessGameDao chessGameDao;

    @BeforeEach
    void connection() {
        connection = new ConnectionGenerator().getConnection("chess_test");
        new SettingDao(connection).settingTable();
        new PieceDao(connection).settingPiece();

        deleteTable();
        chessGameDao = new ChessGameDao(connection);
    }

    @AfterEach
    void deleteTable() {
        final var query = "DELETE FROM chess_game";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("체스 게임을 추가할 수 있다.")
    @Test
    void addChessGame() {
        assertThatCode(() -> chessGameDao.addChessGame("newGame"))
                .doesNotThrowAnyException();
        ;
    }

    @DisplayName("마지막에 추가된 게임 아이디를 알 수 있다.")
    @Test
    void findIdByLastGame() {
        chessGameDao.addChessGame("newGame");
        assertThat(chessGameDao.findIdByLastGame())
                .isEqualTo("newGame");
    }

    @DisplayName("해당 게임에 turn(팀)을 알 수 있다.")
    @Test
    void findTeamById() {
        chessGameDao.addChessGame("newGame");
        assertThat(chessGameDao.findTeamById("newGame"))
                .isEqualTo(Team.WHITE);
    }

    @DisplayName("해당 게임의 turn(팀)을 업데이트 할 수 있다.")
    @Test
    void updateTurn() {
        chessGameDao.addChessGame("newGame");
        chessGameDao.updateTurn("newGame", Team.BLACK);
        assertThat(chessGameDao.findTeamById("newGame"))
                .isEqualTo(Team.BLACK);
    }
}
