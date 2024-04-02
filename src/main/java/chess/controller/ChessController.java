package chess.controller;

import chess.dao.BoardDao;
import chess.dao.ChessGameDao;
import chess.dao.SettingDB;
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

import java.util.HashMap;
import java.util.Map;

public class ChessController {
    private static final String DATABASE_NAME = "chess";

    public ChessController() {
        SettingDB.settingTable(DATABASE_NAME);
    }

    public void play() {
        String gameId = InputView.inputGameId();
        ChessGame chessGame = makeGame(gameId);
        OutputView.printChessBoard(new BoardDto(chessGame.board()));

        while (!chessGame.findCheck().isCheckMate()) {
            Command command = InputView.inputCommand();
            if(command.isEnd()) {
                break;
            }
            playGame(chessGame, command, gameId);
        }
        printChessScore(chessGame);
    }

    private ChessGame makeGame(String gameId) {
        ChessGameDao chessGameDao = new ChessGameDao(DATABASE_NAME);
        boolean isPlayed = chessGameDao.checkById(gameId);
        if (!isPlayed) {
            chessGameDao.addChessGame(gameId);
        }
        return new ChessGame(makeBoard(gameId, isPlayed), chessGameDao.findTeamById(gameId));
    }

    private Board makeBoard(String gameId, boolean isPlayed) {
        BoardDao boardDao = new BoardDao(DATABASE_NAME);
        if (!isPlayed) {
            Map<Position, Piece> pieces = BoardFactory.generateStartBoard();
            pieces.forEach((key, value) -> boardDao.addPosition(key, gameId, PieceConverter.converterId(value)));
        }
        return new Board(makePieces(boardDao.findPiece(gameId)));
    }

    private Map<Position, Piece> makePieces(Map<Position, String> board) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (Map.Entry<Position, String> pieceId : board.entrySet()) {
            pieces.put(pieceId.getKey(), PieceConverter.converterPiece(pieceId.getValue()));
        }
        return pieces;
    }

    private void playGame(ChessGame chessGame, Command command, String gameId) {
        if (command.isMove()) {
            moveChess(chessGame, gameId);
        }
        if (command.isStart()) {
            printChessScore(chessGame);
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
        Piece movedPiece = chessGame.findPiece(positions.target());
        moveChessUpdateDB(gameId, positions, movedPiece);
    }

    private void turnUpdateDB(String gameId, Team team) {
        ChessGameDao chessGameDao = new ChessGameDao(DATABASE_NAME);
        chessGameDao.updateTurn(gameId, team.opponent());
    }

    private void moveChessUpdateDB(String gameId, Positions positions, Piece piece) {
        BoardDao boardDao = new BoardDao(DATABASE_NAME);
        boardDao.deletePosition(gameId, positions);
        boardDao.addPosition(positions.target(), gameId, PieceConverter.converterId(piece));
    }
}
