package chess.controller;

import chess.dao.*;
import chess.dao.converter.PieceConverter;
import chess.domain.Board;
import chess.domain.BoardFactory;
import chess.domain.ChessGame;
import chess.domain.piece.Piece;
import chess.domain.piece.character.Team;
import chess.domain.position.Position;
import chess.domain.position.Positions;
import chess.dto.BoardDto;
import chess.view.Command;
import chess.view.InputView;
import chess.view.OutputView;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ChessController {
    private Connection connection;

    public ChessController() {
        connection = ConnectionGenerator.getConnection("chess");
        new SettingDao(connection).settingTable();
        new PieceDao(connection).settingPiece();
        ConnectionGenerator.closeConnection(connection);
    }

    public void play() {
        connection = ConnectionGenerator.getConnection("chess");
        String gameId = InputView.inputGameId();
        ChessGame chessGame = makeGame(gameId);
        OutputView.printChessBoard(new BoardDto(chessGame.board()));
        Command command;
        while (!chessGame.findCheck().isCheckMate() && (command = InputView.inputCommand()) != Command.END) {
            gamePlayByCommand(chessGame, command, gameId);
        }
        printChessScore(chessGame);
        ConnectionGenerator.closeConnection(connection);
    }

    private ChessGame makeGame(String gameId) {
        ChessGameDao chessGameDao = new ChessGameDao(connection);
        boolean isPlayed = chessGameDao.checkById(gameId);
        if (!isPlayed) {
            chessGameDao.addChessGame(gameId);
        }
        return new ChessGame(makeBoard(gameId, isPlayed), Team.WHITE);
    }

    private Board makeBoard(String gameId, boolean isPlayed) {
        BoardDao boardDao = new BoardDao(connection);
        if (!isPlayed) {
            Map<Position, Piece> pieces = BoardFactory.generateStartBoard();
            pieces.forEach((key, value) -> boardDao.addPosition(key, gameId, PieceConverter.converterId(value)));
        }
        return new Board(makePieces(boardDao.findPiece(gameId)));
    }

    private Map<Position, Piece> makePieces(Map<Position, String> board) {
        Map<Position, Piece> pieces = new HashMap<>();
        PieceDao pieceDao = new PieceDao(connection);
        for (Map.Entry<Position, String> pieceId : board.entrySet()) {
            pieces.put(pieceId.getKey(), pieceDao.findById(pieceId.getValue()));
        }
        return pieces;
    }

    private void gamePlayByCommand(ChessGame chessGame, Command command, String gameId) {
        if (command == Command.STATUS) {
            printChessScore(chessGame);
        }
        if (command == Command.MOVE) {
            moveChess(chessGame, gameId);
        }
    }

    private void printChessScore(ChessGame chessGame) {
        OutputView.printScore(chessGame.findOutcome(), chessGame.mapTeamAndScore());
    }

    private void moveChess(ChessGame chessGame, String gameId) {
        Positions positions = InputView.inputPositions();
        chessGame.movePiece(positions);
        OutputView.printChessBoard(new BoardDto(chessGame.board()));
        OutputView.printCheck(chessGame.findCheck());
        turnUpdateDB(gameId, chessGame.currentTeam());
        Piece movedPiece = chessGame.board().pieces().get(positions.target());
        moveChessUpdateDB(gameId, positions, movedPiece);
    }

    private void turnUpdateDB(String gameId, Team team) {
        ChessGameDao chessGameDao = new ChessGameDao(connection);
        chessGameDao.updateTurn(gameId, team.opponent());
    }

    private void moveChessUpdateDB(String gameId, Positions positions, Piece piece) {
        BoardDao boardDao = new BoardDao(connection);
        boardDao.deletePosition(gameId, positions);
        boardDao.addPosition(positions.target(), gameId, PieceConverter.converterId(piece));
    }
}
