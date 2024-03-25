package chess.domain;

import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.piece.character.Kind;
import chess.domain.piece.character.Team;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public void validateSameTeamByPosition(Position position, Team team) {
        validatePieceExistsOnPosition(position);
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
                = isNotBlockAttackingPiece(team, new Positions(attackingPiecePosition, kingPosition));
        return isNotSafePathAvailableForKing && isNotBlockAttackingPiece && isNotAttackAttackingPiece;
    }

    private boolean isNotSafePathAvailableForKing(Team team, Position kingPosition) {
        return kingPosition.findAllMovablePosition(new King(team))
                .stream()
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
        return findAttackingPiecePositions(team, position).size();
    }

    private Position findAttackingPiecePosition(Team team, Position position) {
        return findAttackingPiecePositions(team, position)
                .stream()
                .findAny()
                .orElseThrow(() -> new IllegalStateException("해당 위치를 공격하는 기물은 없습니다."));
    }

    private List<Position> findAttackingPiecePositions(Team team, Position position) {
        return pieces.entrySet()
                .stream()
                .filter(entry -> !entry.getKey().equals(position) && entry.getValue().isOppositeTeamWith(team))
                .filter(entry -> isAttacking(entry.getValue(), new Positions(entry.getKey(), position)))
                .map(Entry::getKey)
                .toList();
    }

    private boolean isNotAttackAttackingPiece(Team attackingTeam, Position attackingPosition) {
        return pieces.entrySet()
                .stream()
                .filter(entry -> !entry.getKey().equals(getKingPosition(attackingTeam.opponent()))
                        && !entry.getKey().equals(attackingPosition))
                .filter(entry -> entry.getValue().isOppositeTeamWith(attackingTeam))
                .noneMatch(entry -> isAttacking(entry.getValue(), new Positions(entry.getKey(), attackingPosition)));
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
        List<Position> teamPieceMovablePositions = findTeamPieceMovablePositions(team, positions);

        return teamPieceMovablePositions.stream()
                .noneMatch(attackRoutePositions::contains);
    }

    private List<Position> findTeamPieceMovablePositions(Team team, Positions positions) {
        return pieces.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isSameTeamWith(team) && !entry.getKey().equals(positions.target()))
                .flatMap(entry -> entry.getKey()
                        .findAllMovablePosition(entry.getValue())
                        .stream()
                        .filter(position -> !entry.getValue().equals(position) && isMovable(entry.getValue(), new Positions(entry.getKey(), position))))
                .toList();
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

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }
}
