package ch.uzh.group8.checkersv2.movevalidator;

import ch.uzh.group8.checkersv2.dom.Board;
import ch.uzh.group8.checkersv2.dom.BoardCoordinates;
import ch.uzh.group8.checkersv2.dom.Move;

public class MoveIsDiagonal implements MoveValidator {
  @Override
  public boolean validate(Move move, Board board) {
    BoardCoordinates start = move.start();
    BoardCoordinates end = move.end();
    boolean bothDirectionsChange = start.row() != end.row() && start.column() != end.column();

    int rowDiff = Math.abs(start.row().ordinal() - end.row().ordinal());
    int colDiff = Math.abs(start.column().ordinal() - end.column().ordinal());
    return bothDirectionsChange && rowDiff == colDiff;
  }
}
