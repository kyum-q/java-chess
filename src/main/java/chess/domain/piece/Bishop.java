package chess.domain.piece;

import chess.domain.piece.character.Kind;
import chess.domain.piece.character.Team;
import chess.domain.position.Position;
import chess.domain.position.Positions;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(Team team) {
        this(team, false);
    }

    public Bishop(Team team, boolean isMoved) {
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
        return Math.abs(positions.calculateRankDifference()) == Math.abs(positions.calculateFileDifference());
    }

    @Override
    public List<Position> findBetweenPositionsWhenAttack(Positions positions) {
        return findBetweenPositions(positions);
    }

    @Override
    protected List<Position> findBetweenPositions(Position position, int fileDifference, int rankDifference) {
        int absoluteDifference = Math.abs(rankDifference);
        int rankSign = rankDifference / absoluteDifference;
        int fileSign = fileDifference / absoluteDifference;

        List<Position> positions = new ArrayList<>();
        for (int i = MIN_MOVEMENT; i < absoluteDifference; i++) {
            positions.add(position.move(fileSign * i, rankSign * i));
        }
        return positions;
    }

    @Override
    public Kind findKind() {
        return Kind.BISHOP;
    }
}
