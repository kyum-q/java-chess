package chess.domain.piece;

import chess.domain.Calculator;
import chess.domain.piece.character.Kind;
import chess.domain.piece.character.Team;
import chess.domain.position.Position;
import chess.domain.position.Positions;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public static final int NORMAL_MOVEMENT = 1;
    public static final int START_MOVEMENT = 2;
    public static final int ATTACK_COLUMN_MOVEMENT = 1;

    public Pawn(Team team) {
        this(team, false);
    }

    public Pawn(Team team, boolean isMoved) {
        super(team, isMoved);
    }

    @Override
    public Piece move() {
        if (isMoved) {
            return this;
        }
        return new Pawn(team, true);
    }

    @Override
    public boolean isAttackable(Positions positions) {
        return isPossibleOneStepMovement(positions.calculateRankDifference())
                && Math.abs(positions.calculateFileDifference()) == ATTACK_COLUMN_MOVEMENT;
    }

    @Override
    public boolean isMovable(Positions positions) {
        if (positions.calculateFileDifference() != 0) {
            return false;
        }
        int rankDifference = positions.calculateRankDifference();
        return isPossibleOneStepMovement(rankDifference) || isPossibleTowStepMovement(rankDifference);
    }

    private boolean isPossibleOneStepMovement(int rankDifference) {
        return rankDifference == NORMAL_MOVEMENT * team.attackDirection();
    }

    private boolean isPossibleTowStepMovement(int rankDifference) {
        return !isMoved && rankDifference == START_MOVEMENT * team.attackDirection();
    }

    @Override
    public List<Position> findBetweenPositionsWhenAttack(Positions positions) {
        int rankDifference = positions.calculateRankDifference();
        int fileDifference = positions.calculateFileDifference();

        validateAttackable(positions);
        return findBetweenPositions(positions.source(), rankDifference, fileDifference);
    }

    private void validateAttackable(Positions positions) {
        if (isAttackable(positions)) {
            return;
        }
        throw new IllegalArgumentException("해당 위치로 움직일 수 없습니다.");
    }

    @Override
    protected List<Position> findBetweenPositions(Position position, int fileDifference, int rankDifference) {
        List<Position> positions = new ArrayList<>();
        if (Math.abs(rankDifference) == START_MOVEMENT) {
            positions.add(position.move(0, Calculator.calculateMinMovement(rankDifference)));
            return positions;
        }
        return new ArrayList<>();
    }

    @Override
    public Kind findKind() {
        return Kind.PAWN;
    }
}
