package chess.domain.piece;

import chess.domain.piece.character.Kind;
import chess.domain.piece.character.Team;
import chess.domain.position.Position;
import chess.domain.position.Positions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Knight extends Piece {
    private static final Set<Integer> MOVE_DIFFERENCES = Set.of(1, 2);

    public Knight(Team team) {
        this(team, false);
    }

    public Knight(Team team, boolean isMoved) {
        super(team, isMoved);
    }

    @Override
    public Piece move() {
        if (isMoved) {
            return this;
        }
        return new Knight(team, true);
    }

    @Override
    public boolean isAttackable(Positions positions) {
        return isMovable(positions);
    }

    @Override
    public boolean isMovable(Positions positions) {
        List<Integer> differenceList = List.of(
                Math.abs(positions.calculateRankDifference()),
                Math.abs(positions.calculateFileDifference()));
        return differenceList.containsAll(MOVE_DIFFERENCES);
    }

    @Override
    public List<Position> findBetweenPositionsWhenAttack(Positions positions) {
        return findBetweenPositions(positions);
    }

    @Override
    protected List<Position> findBetweenPositions(Position position, int fileDifference, int rankDifference) {
        return new ArrayList<>();
    }

    @Override
    public Kind findKind() {
        return Kind.KNIGHT;
    }
}
