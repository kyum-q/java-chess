package chess.domain.piece;

import chess.domain.position.Position;
import chess.domain.position.Positions;
import chess.domain.piece.character.Kind;
import chess.domain.piece.character.Team;

import java.util.List;
import java.util.Objects;

public abstract class Piece {
    protected static final int MIN_MOVEMENT = 1;

    protected final Team team;
    protected final boolean isMoved;

    protected Piece(Team team, boolean isMoved) {
        this.team = team;
        this.isMoved = isMoved;
    }

    public abstract Piece move();

    public abstract boolean isAttackable(Positions positions);

    public abstract boolean isMovable(Positions positions);

    public abstract List<Position> findBetweenPositionsWhenAttack(Positions positions);

    protected abstract List<Position> findBetweenPositions(Position position, int fileDifference, int rankDifference);

    protected abstract Kind findKind();

    public List<Position> findBetweenPositions(Positions positions) {
        validateMovable(positions);
        int rankDifference = positions.calculateRankDifference();
        int fileDifference = positions.calculateFileDifference();

        return findBetweenPositions(positions.source(), fileDifference, rankDifference);
    }

    private void validateMovable(Positions positions) {
        if (isMovable(positions)) {
            return;
        }
        throw new IllegalArgumentException("해당 위치로 움직일 수 없습니다.");
    }

    public boolean isOppositeTeamWith(Team team) {
        return !isSameTeamWith(team);
    }

    public boolean isSameTeamWith(Piece piece) {
        return isSameTeamWith(piece.team);
    }

    public boolean isSameTeamWith(Team team) {
        return this.team == team;
    }

    public boolean checkKind(Kind kind) {
        return findKind() == kind;
    }

    public double findMinScore() {
       return findKind().minScore();
    }

    public double findMaxScore() {
        return findKind().maxScore();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return isMoved == piece.isMoved && team == piece.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, isMoved);
    }
}
