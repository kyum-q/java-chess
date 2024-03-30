package chess.domain.position;

import chess.domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Position {
    private static final List<Position> positions;

    static {
        positions = new ArrayList<>();

        for (File file : File.values()) {
            for (Rank rank : Rank.values()) {
                positions.add(new Position(file, rank));
            }
        }
    }

    private final File file;
    private final Rank rank;

    public Position(File file, Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    public static Position of(File file, Rank rank) {
        return positions.stream()
                .filter(position -> position.file == file && position.rank == rank)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("캐시에 해당 값이 존재하지 않습니다."));
    }

    public Position move(int fileMoveAmount, int rankMoveAmount) {
        return Position.of(file.move(fileMoveAmount), rank.move(rankMoveAmount));
    }

    public Stream<Position> findAllMovablePosition(Piece piece) {
        return positions.stream()
                .filter(position -> position != this)
                .filter(position -> piece.isMovable(new Positions(this, position)));
    }

    public int calculateFileDifference(Position targetPosition) {
        return file.calculateFileDifference(targetPosition.file);
    }

    public int calculateRankDifference(Position targetPosition) {
        return rank.calculateRankDifference(targetPosition.rank);
    }

    public boolean checkFile(File file) {
        return this.file == file;
    }

    public File file() {
        return file;
    }

    public Rank rank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return file == position.file && rank == position.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }
}
