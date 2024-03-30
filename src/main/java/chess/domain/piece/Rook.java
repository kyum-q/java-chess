package chess.domain.piece;

import chess.domain.Calculator;
import chess.domain.position.Position;
import chess.domain.position.Positions;
import chess.domain.piece.character.Kind;
import chess.domain.piece.character.Team;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(Team team) {
        this(team, false);
    }

    public Rook(Team team, boolean isMoved) {
        super(team, isMoved);
    }

    @Override
    public Piece move() {
        if (isMoved) {
            return this;
        }
        return new Rook(team, true);
    }

    @Override
    public boolean isAttackable(Positions positions) {
        return isMovable(positions);
    }

    @Override
    public boolean isMovable(Positions positions) {
        return positions.calculateRankDifference() == 0 || positions.calculateFileDifference() == 0;
    }

    @Override
    public List<Position> findBetweenPositionsWhenAttack(Positions positions) {
        return findBetweenPositions(positions);
    }

    @Override
    protected List<Position> findBetweenPositions(Position position, int fileDifference, int rankDifference) {
        int absoluteDifference = Math.max(Math.abs(rankDifference), Math.abs(fileDifference));
        int rankSign = Calculator.calculateMinMovement(rankDifference);
        int fileSign = Calculator.calculateMinMovement(fileDifference);

        List<Position> positions = new ArrayList<>();
        for (int i = MIN_MOVEMENT; i < absoluteDifference; i++) {
            positions.add(position.move(fileSign * i, rankSign * i));
        }
        return positions;
    }

    @Override
    public Kind findKind() {
        return Kind.ROOK;
    }

}
