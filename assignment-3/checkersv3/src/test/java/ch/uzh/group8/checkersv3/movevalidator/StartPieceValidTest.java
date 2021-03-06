package ch.uzh.group8.checkersv3.movevalidator;

import static ch.uzh.group8.checkersv3.dom.BoardCoordinates.Column;
import static ch.uzh.group8.checkersv3.dom.BoardCoordinates.Row;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ch.uzh.group8.checkersv3.dom.BoardCoordinates;
import ch.uzh.group8.checkersv3.dom.Move;
import ch.uzh.group8.checkersv3.dom.Piece;
import ch.uzh.group8.checkersv3.dom.Player;
import ch.uzh.group8.checkersv3.dom.board.Board;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StartPieceValidTest {
  public static final Piece WHITE_PAWN = new Piece(Player.PLAYER_WHITE, false);
  public static final Piece RED_PAWN = new Piece(Player.PLAYER_RED, false);

  private StartPieceValid startPieceValid;
  private Board board;

  @BeforeEach
  public void setup() {
    startPieceValid = new StartPieceValid();
    board = mock(Board.class);
  }

  @Test
  public void return_false_if_no_piece() {
    when(board.getPieceAt(notNull())).thenReturn(Optional.empty());

    Move move =
        Move.of(
            Player.PLAYER_RED,
            new BoardCoordinates(Row.ROW_1, Column.A),
            new BoardCoordinates(Row.ROW_2, Column.B));

    assertThat(startPieceValid.validate(move, board), is(false));
  }

  @Test
  public void return_true_if_piece_belongs_to_player_red() {
    when(board.getPieceAt(notNull())).thenReturn(Optional.of(RED_PAWN));

    Move move =
        Move.of(
            Player.PLAYER_RED,
            new BoardCoordinates(Row.ROW_1, Column.A),
            new BoardCoordinates(Row.ROW_2, Column.B));

    assertThat(startPieceValid.validate(move, board), is(true));
  }

  @Test
  public void return_true_if_piece_belongs_to_player_white() {
    when(board.getPieceAt(notNull())).thenReturn(Optional.of(WHITE_PAWN));

    Move move =
        Move.of(
            Player.PLAYER_WHITE,
            new BoardCoordinates(Row.ROW_1, Column.A),
            new BoardCoordinates(Row.ROW_2, Column.B));

    assertThat(startPieceValid.validate(move, board), is(true));
  }

  @Test
  public void return_false_if_piece_belongs_to_opponent_player_white() {
    when(board.getPieceAt(notNull())).thenReturn(Optional.of(RED_PAWN));

    Move move =
        Move.of(
            Player.PLAYER_WHITE,
            new BoardCoordinates(Row.ROW_1, Column.A),
            new BoardCoordinates(Row.ROW_2, Column.B));

    assertThat(startPieceValid.validate(move, board), is(false));
  }

  @Test
  public void return_false_if_piece_belongs_to_opponent_player_red() {
    when(board.getPieceAt(notNull())).thenReturn(Optional.of(WHITE_PAWN));

    Move move =
        Move.of(
            Player.PLAYER_RED,
            new BoardCoordinates(Row.ROW_1, Column.A),
            new BoardCoordinates(Row.ROW_2, Column.B));

    assertThat(startPieceValid.validate(move, board), is(false));
  }
}
