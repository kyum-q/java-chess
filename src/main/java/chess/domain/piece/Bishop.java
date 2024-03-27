package chess.domain.piece;

import chess.domain.Position;
import chess.domain.Positions;
import chess.domain.piece.character.Kind;
import chess.domain.piece.character.Team;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(Team team) {
        this(team, false);
    }

    private Bishop(Team team, boolean isMoved) {
        super(team, isMoved);
    }

    @Override
    public Piece move() {
        if (isMoved) {
            return this;
        }
        return new Bishop(team, true);
    }

    @Override
    public boolean isAttackable(Positions positions) {
        return isMovable(positions);
    }

    @Override
    public boolean isMovable(Positions positions) {
        return Math.abs(positions.calculateRowDifference()) == Math.abs(positions.calculateColumnDifference());
    }

    @Override
    public List<Position> findBetweenPositionsWhenAttack(Positions positions) {
        return findBetweenPositions(positions);
    }

    @Override
    protected List<Position> findBetweenPositions(Position position, int rowDifference, int columnDifference) {
        int absoluteDifference = Math.abs(rowDifference);
        int rowSign = rowDifference / absoluteDifference;
        int columnSign = columnDifference / absoluteDifference;

        List<Position> positions = new ArrayList<>();
        for (int i = MIN_MOVEMENT; i < absoluteDifference; i++) {
            positions.add(position.move(rowSign * i, columnSign * i));
        }
        return positions;
    }

    @Override
    protected Kind findKind() {
        return Kind.BISHOP;
    }
}
