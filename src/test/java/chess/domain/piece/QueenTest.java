package chess.domain.piece;

import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Positions;
import chess.domain.piece.character.Team;
import chess.domain.position.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueenTest {
    @DisplayName("두 위치 사이의 퀸이 갈 수 있는 위치들을 반환한다.")
    @Test
    void betweenPositionDiagonal() {
        assertThat(new Queen(Team.WHITE)
                .findBetweenPositions(new Positions(
                        Position.of(File.D, Rank.FOUR),
                        Position.of(File.G, Rank.SEVEN))))
                .containsExactly(Position.of(File.E, Rank.FIVE), Position.of(File.F, Rank.SIX));
    }

    @DisplayName("두 위치 사이의 퀸이 갈 수 있는 위치들을 반환한다.")
    @Test
    void betweenPositionDiagonalMinus() {
        assertThat(new Queen(Team.WHITE)
                .findBetweenPositions(new Positions(
                        Position.of(File.D, Rank.FOUR),
                        Position.of(File.A, Rank.ONE))))
                .containsExactly(Position.of(File.C, Rank.THREE), Position.of(File.B, Rank.TWO));
    }

    @DisplayName("두 위치 사이의 퀸이 갈 수 있는 위치들을 반환한다.")
    @Test
    void betweenPositionLine() {
        assertThat(new Queen(Team.WHITE)
                .findBetweenPositions(new Positions(
                        Position.of(File.D, Rank.FOUR),
                        Position.of(File.G, Rank.FOUR))))
                .containsExactly(Position.of(File.E, Rank.FOUR), Position.of(File.F, Rank.FOUR));
    }

    @DisplayName("두 위치 사이의 퀸이 갈 수 있는 위치들을 반환한다.")
    @Test
    void betweenPositionLineMinus() {
        assertThat(new Queen(Team.WHITE)
                .findBetweenPositions(new Positions(
                        Position.of(File.D, Rank.FOUR),
                        Position.of(File.D, Rank.ONE))))
                .containsExactly(Position.of(File.D, Rank.THREE), Position.of(File.D, Rank.TWO));
    }
}
