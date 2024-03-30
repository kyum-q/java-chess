package chess.domain;

import chess.domain.piece.character.Team;
import chess.domain.position.Positions;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ChessGame {
    private final Board board;
    private Team currentTeam;

    public ChessGame(Board board) {
        this.board = board;
        this.currentTeam = Team.BLACK;
    }

    public void movePiece(Positions positions) {
        currentTeam = currentTeam.opponent();
        board.validateSameTeamByPosition(positions.source(), currentTeam);
        board.move(positions);
    }

    public CheckState findCheck() {
        if (board.findCheckState(currentTeam) != CheckState.SAFE) {
            throw new IllegalArgumentException("체크 상태를 벗어나지 않았습니다.");
        }
        return board.findCheckState(currentTeam.opponent());
    }

    public Outcome findOutcome() {
        if(findCheck().isCheckMate()) {
            return Outcome.of(currentTeam);
        }
        if(findScore(Team.WHITE) == findScore(Team.BLACK)) {
            return Outcome.DRAW;
        }
        return Outcome.of(findWinner());
    }

    private Team findWinner() {
        if(findScore(Team.WHITE) > findScore(Team.BLACK)) {
            return Team.WHITE;
        }
        return Team.BLACK;
    }

    public Map<Team, Double> mapTeamAndScore() {
        return Arrays.stream(Team.values())
                .collect(Collectors.toMap(
                        team -> team,
                        this::findScore
                ));
    }

    private double findScore(Team team) {
        return board.calculateScore(team);
    }

    public Board board() {
        return board;
    }

    public Team currentTeam() {
        return currentTeam;
    }
}
