package ch.uzh.group8.checkersv3.dom.board;

import static ch.uzh.group8.checkersv3.dom.BoardCoordinates.*;

import ch.uzh.group8.checkersv3.dom.BoardCoordinates;
import ch.uzh.group8.checkersv3.dom.Piece;
import ch.uzh.group8.checkersv3.dom.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class Store {
  final Map<Row, Map<Column, Piece>> boardMatrix = new HashMap<>();

  Store() {
    boardMatrix.put(
        Row.ROW_1,
        Map.of(
            Column.A,
            new Piece(Player.PLAYER_WHITE, false),
            Column.C,
            new Piece(Player.PLAYER_WHITE, false),
            Column.E,
            new Piece(Player.PLAYER_WHITE, false),
            Column.G,
            new Piece(Player.PLAYER_WHITE, false)));
    boardMatrix.put(
        Row.ROW_3,
        Map.of(
            Column.A,
            new Piece(Player.PLAYER_WHITE, false),
            Column.C,
            new Piece(Player.PLAYER_WHITE, false),
            Column.E,
            new Piece(Player.PLAYER_WHITE, false),
            Column.G,
            new Piece(Player.PLAYER_WHITE, false)));
    boardMatrix.put(
        Row.ROW_2,
        Map.of(
            Column.B,
            new Piece(Player.PLAYER_WHITE, false),
            Column.D,
            new Piece(Player.PLAYER_WHITE, false),
            Column.F,
            new Piece(Player.PLAYER_WHITE, false),
            Column.H,
            new Piece(Player.PLAYER_WHITE, false)));

    boardMatrix.put(
        Row.ROW_8,
        Map.of(
            Column.B,
            new Piece(Player.PLAYER_RED, false),
            Column.D,
            new Piece(Player.PLAYER_RED, false),
            Column.F,
            new Piece(Player.PLAYER_RED, false),
            Column.H,
            new Piece(Player.PLAYER_RED, false)));
    boardMatrix.put(
        Row.ROW_6,
        Map.of(
            Column.B,
            new Piece(Player.PLAYER_RED, false),
            Column.D,
            new Piece(Player.PLAYER_RED, false),
            Column.F,
            new Piece(Player.PLAYER_RED, false),
            Column.H,
            new Piece(Player.PLAYER_RED, false)));
    boardMatrix.put(
        Row.ROW_7,
        Map.of(
            Column.A,
            new Piece(Player.PLAYER_RED, false),
            Column.C,
            new Piece(Player.PLAYER_RED, false),
            Column.E,
            new Piece(Player.PLAYER_RED, false),
            Column.G,
            new Piece(Player.PLAYER_RED, false)));
  }

  Optional<Piece> getPieceAt(BoardCoordinates boardCoordinates) {
    Map<Column, Piece> columnOptionalMap = boardMatrix.get(boardCoordinates.row());
    if (columnOptionalMap == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(columnOptionalMap.get(boardCoordinates.column()));
  }

  void removePiece(BoardCoordinates start) {
    boardMatrix.compute(
        start.row(),
        (row, columnPieceMap) -> {
          if (columnPieceMap == null) {
            return new HashMap<>();
          } else {
            HashMap<Column, Piece> columnPieceHashMap = new HashMap<>(columnPieceMap);
            columnPieceHashMap.remove(start.column());
            return columnPieceHashMap;
          }
        });
  }

  void addPiece(BoardCoordinates boardCoordinates, Piece piece) {
    boardMatrix.compute(
        boardCoordinates.row(),
        (row, columnPieceMap) -> {
          if (columnPieceMap == null) {
            return Map.of(boardCoordinates.column(), piece);
          } else {
            HashMap<Column, Piece> columnPieceHashMap = new HashMap<>(columnPieceMap);
            columnPieceHashMap.put(boardCoordinates.column(), piece);
            return columnPieceHashMap;
          }
        });
  }
}
