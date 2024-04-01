package chess.domain;

import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.piece.character.Kind;
import chess.domain.piece.character.Team;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Positions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public record Board(Map<Position, Piece> pieces) {
    public void validateSameTeamByPosition(Position position, Team team) {
        if (pieces.get(position).isOppositeTeamWith(team)) {
            throw new IllegalArgumentException("%s 팀이 움직일 차례입니다".formatted(team.name()));
        }
    }

    public void move(Positions positions) {
        validatePieceExistsOnPosition(positions.source());

        Piece piece = pieces.get(positions.source());
        validateSameTeamPieceExistsOnTargetPosition(positions.target(), piece);
        validateBlockingPieceExists(piece, positions);

        pieces.put(positions.target(), piece.move());
        pieces.remove(positions.source());
    }

    private void validatePieceExistsOnPosition(Position position) {
        if (!pieces.containsKey(position)) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }
    }

    private void validateSameTeamPieceExistsOnTargetPosition(Position targetPosition, Piece piece) {
        if (pieces.containsKey(targetPosition) && piece.isSameTeamWith(pieces.get(targetPosition))) {
            throw new IllegalArgumentException("해당 위치에 아군 기물이 존재합니다.");
        }
    }

    private void validateBlockingPieceExists(Piece piece, Positions positions) {
        List<Position> betweenPositions = findBetweenPositions(piece, positions);

        if (betweenPositions.stream()
                .anyMatch(pieces::containsKey)) {
            throw new IllegalArgumentException("이동을 가로막는 기물이 존재합니다.");
        }
    }

    public CheckState findCheckState(Team team) {
        Position kingPosition = getKingPosition(team);
        if (!isChecked(team, kingPosition)) {
            return CheckState.SAFE;
        }
        if (isCheckmate(team, kingPosition)) {
            return CheckState.CHECK_MATE;
        }
        return CheckState.CHECK;
    }

    private Position getKingPosition(Team team) {
        return pieces.entrySet()
                .stream()
                .filter(entry -> entry.getValue().checkKind(Kind.KING)
                        && entry.getValue().isSameTeamWith(team))
                .findAny()
                .orElseThrow(() -> new IllegalStateException(
                        "%s 왕이 체스판 위에 존재하기 않습니다.".formatted(team.name())))
                .getKey();
    }

    private boolean isChecked(Team team, Position kingPosition) {
        return calculateAttackedPositionCount(team, kingPosition) > 0;
    }

    private boolean isCheckmate(Team team, Position kingPosition) {
        boolean isNotSafePathAvailableForKing = isNotSafePathAvailableForKing(team, kingPosition);
        if (isDoubleCheck(team, kingPosition) && isNotSafePathAvailableForKing) {
            return true;
        }

        Position attackingPiecePosition = findAttackingPiecePosition(team, kingPosition);
        boolean isNotAttackAttackingPiece = isNotAttackAttackingPiece(team.opponent(), attackingPiecePosition);
        boolean isNotBlockAttackingPiece
                = isNotBlockAttackingPiece(team.opponent(), new Positions(attackingPiecePosition, kingPosition));
        return isNotSafePathAvailableForKing && isNotBlockAttackingPiece && isNotAttackAttackingPiece;
    }

    private boolean isNotSafePathAvailableForKing(Team team, Position kingPosition) {
        return kingPosition.findAllMovablePosition(new King(team))
                .filter(position -> isMovablePosition(team, position))
                .allMatch(position -> calculateAttackedPositionCount(team, position) != 0);
    }

    private boolean isMovablePosition(Team team, Position position) {
        if (pieces.containsKey(position)) {
            return pieces.get(position)
                    .isOppositeTeamWith(team);
        }
        return true;
    }

    private boolean isDoubleCheck(Team team, Position kingPosition) {
        return calculateAttackedPositionCount(team, kingPosition) >= 2;
    }

    private int calculateAttackedPositionCount(Team team, Position position) {
        return (int) findAttackingPiecePositions(team, position).count();
    }

    private Position findAttackingPiecePosition(Team team, Position position) {
        return findAttackingPiecePositions(team, position)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("해당 위치를 공격하는 기물은 없습니다."));
    }

    private boolean isNotAttackAttackingPiece(Team attackingTeam, Position attackingPosition) {
        return findAttackingPiecePositions(attackingTeam, attackingPosition)
                .allMatch(position -> position.equals(getKingPosition(attackingTeam.opponent())));
    }

    private Stream<Position> findAttackingPiecePositions(Team team, Position position) {
        return pieces.entrySet()
                .stream()
                .filter(entry -> isDifferentPiece(entry.getKey(), position, team))
                .filter(entry -> isAttacking(entry.getValue(), new Positions(entry.getKey(), position)))
                .map(Entry::getKey);
    }

    private boolean isDifferentPiece(Position source, Position target, Team team) {
        return !source.equals(target) && pieces.get(source).isOppositeTeamWith(team);
    }

    private boolean isAttacking(Piece piece, Positions positions) {
        if (piece.isAttackable(positions)) {
            List<Position> betweenPositions = piece.findBetweenPositionsWhenAttack(positions);
            return betweenPositions.stream()
                    .noneMatch(pieces::containsKey);
        }
        return false;
    }

    private boolean isNotBlockAttackingPiece(Team team, Positions positions) {
        List<Position> attackRoutePositions = pieces.get(positions.source())
                .findBetweenPositionsWhenAttack(positions);
        Stream<Position> teamPieceMovablePositions = findTeamPieceMovablePositions(team, positions);

        return teamPieceMovablePositions
                .noneMatch(attackRoutePositions::contains);
    }

    private Stream<Position> findTeamPieceMovablePositions(Team team, Positions positions) {
        return pieces.entrySet()
                .stream()
                .filter(entry -> isDifferentPiece(entry.getKey(), positions.target(), team))
                .flatMap(entry -> entry.getKey()
                        .findAllMovablePosition(entry.getValue())
                        .filter(position -> !entry.getKey().equals(position)
                                && isMovable(entry.getValue(), new Positions(entry.getKey(), position))));
    }

    private boolean isMovable(Piece piece, Positions positions) {
        if (piece.isMovable(positions)) {
            List<Position> betweenPositions = piece.findBetweenPositions(positions);
            return betweenPositions.stream()
                    .noneMatch(pieces::containsKey);
        }
        return false;
    }

    private List<Position> findBetweenPositions(Piece piece, Positions positions) {
        if (pieces.containsKey(positions.target())) {
            return piece.findBetweenPositionsWhenAttack(positions);
        }
        return piece.findBetweenPositions(positions);
    }


    public double calculateScore(Team team) {
        return calculateNotPawnScore(team) + calculatePawnScore(team);
    }

    private double calculateNotPawnScore(Team team) {
        return pieces.values()
                .stream()
                .filter(piece -> piece.isSameTeamWith(team) && !piece.checkKind(Kind.PAWN))
                .mapToDouble(Piece::findMaxScore)
                .sum();
    }

    private double calculatePawnScore(Team team) {
        List<Position> pawnPositions = pieces.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isSameTeamWith(team) && entry.getValue().checkKind(Kind.PAWN))
                .map(Entry::getKey)
                .toList();

        return Arrays.stream(File.values())
                .mapToDouble(file -> calculateSamePieceScoreByFile(pawnPositions, file))
                .sum();
    }

    private double calculateSamePieceScoreByFile(List<Position> positions, File file) {
        long count = positions.stream()
                .filter(position -> position.checkFile(file))
                .count();

        if(count == 0) {
            return 0;
        }
        if (count > 1) {
            return findAnyPiece(positions).findMinScore() * count;
        }
        return findAnyPiece(positions).findMaxScore() * count;
    }

    private Piece findAnyPiece(List<Position> pawnPositions) {
        return pieces.get(pawnPositions.stream()
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("기물이 존재하지 않습니다.")));
    }

    @Override
    public Map<Position, Piece> pieces() {
        return Collections.unmodifiableMap(pieces);
    }
}
