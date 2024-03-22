package chess.domain.piece;

import chess.domain.Position;
import chess.domain.piece.character.Character;
import chess.domain.piece.character.Team;
import java.util.List;

public abstract class Piece {
    protected static final int MIN_MOVEMENT = 1;

    protected final Team team;
    protected final boolean hasMoved;

    public Piece(Team team) {
        this(team, false);
    }

    protected Piece(Team team, boolean hasMoved) {
        this.team = team;
        this.hasMoved = hasMoved;
    }

    public abstract Piece move();

    public abstract Character findCharacter();

    protected abstract List<Position> findBetweenPositions(Position position, int rowDifference, int columnDifference);

    protected abstract boolean isMovable(int rowDifference, int columnDifference);

    protected boolean isAttackable(int rowDifference, int columnDifference) {
        return isMovable(rowDifference, columnDifference);
    }

    public boolean isMovable(Position oldPosition, Position newPosition) {
        int rowDifference = oldPosition.calculateRowDifference(newPosition);
        int columnDifference = oldPosition.calculateColumnDifference(newPosition);

        return isMovable(rowDifference, columnDifference);
    }

    public List<Position> findBetweenPositionsWhenAttack(Position oldPosition, Position newPosition) {
        return findBetweenPositions(oldPosition, newPosition);
    }

    public List<Position> findBetweenPositions(Position oldPosition, Position newPosition) {
        validateMovable(oldPosition, newPosition);
        int rowDifference = oldPosition.calculateRowDifference(newPosition);
        int columnDifference = oldPosition.calculateColumnDifference(newPosition);

        return findBetweenPositions(oldPosition, rowDifference, columnDifference);
    }

    private void validateMovable(Position oldPosition, Position newPosition) {
        if (!oldPosition.equals(newPosition) && isMovable(oldPosition, newPosition)) {
            return;
        }
        throw new IllegalArgumentException("해당 위치로 움직일 수 없습니다.");
    }

    public boolean isAttacking(Position oldPosition, Position newPosition) {
        int rowDifference = oldPosition.calculateRowDifference(newPosition);
        int columnDifference = oldPosition.calculateColumnDifference(newPosition);

        return !oldPosition.equals(newPosition) && isAttackable(rowDifference, columnDifference);
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
}
