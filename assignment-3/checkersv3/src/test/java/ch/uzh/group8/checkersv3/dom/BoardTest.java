package ch.uzh.group8.checkersv3.dom;

import static ch.uzh.group8.checkersv3.dom.BoardCoordinates.Column;
import static ch.uzh.group8.checkersv3.dom.BoardCoordinates.Row;
import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import ch.uzh.group8.checkersv3.dom.board.Board;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class BoardTest {
  @Test
  public void move_a_piece_from_a_to_b() {
    Board board = new Board();
    Move move =
        Move.of(
            Player.PLAYER_WHITE,
            new BoardCoordinates(Row.ROW_3, Column.A),
            new BoardCoordinates(Row.ROW_4, Column.B));

    board.executeMove(move);

    assertThat(board.getPieceAt(move.start()), is(empty()));
    assertThat(
        board.getPieceAt(move.end()), is(Optional.of(new Piece(Player.PLAYER_WHITE, false))));
  }

  @Test
  public void remove_piece_between_jump_move() {
    Board board = new Board();
    Move move1 =
        Move.of(
            Player.PLAYER_WHITE,
            new BoardCoordinates(Row.ROW_3, Column.A),
            new BoardCoordinates(Row.ROW_4, Column.B));
    Move move2 =
        Move.of(
            Player.PLAYER_RED,
            new BoardCoordinates(Row.ROW_6, Column.B),
            new BoardCoordinates(Row.ROW_5, Column.C));
    Move move3 =
        Move.of(
            Player.PLAYER_WHITE,
            new BoardCoordinates(Row.ROW_3, Column.C),
            new BoardCoordinates(Row.ROW_4, Column.D));
    Move jumpMove =
        Move.of(
            Player.PLAYER_RED,
            new BoardCoordinates(Row.ROW_5, Column.C),
            new BoardCoordinates(Row.ROW_3, Column.A));

    List.of(move1, move2, move3, jumpMove).forEach(board::executeMove);

    assertThat(
        board.getPieceAt(move1.start()), is(Optional.of(new Piece(Player.PLAYER_RED, false))));
    assertThat(board.getPieceAt(move1.end()), is(empty()));
  }

  @Test
  public void convert_piece_to_king_if_at_end_for_white() {
    Board board = new Board();
    // this move is not valid, but the board doesn't know that
    Move move =
        Move.of(
            Player.PLAYER_WHITE,
            new BoardCoordinates(Row.ROW_3, Column.A),
            new BoardCoordinates(Row.ROW_8, Column.B));

    board.executeMove(move);

    assertThat(board.getPieceAt(move.start()), is(empty()));
    assertThat(board.getPieceAt(move.end()), is(Optional.of(new Piece(Player.PLAYER_WHITE, true))));
  }

  @Test
  public void convert_piece_to_king_if_at_end_for_red() {
    Board board = new Board();
    // this move is not valid, but the board doesn't know that
    Move move =
        Move.of(
            Player.PLAYER_RED,
            new BoardCoordinates(Row.ROW_6, Column.B),
            new BoardCoordinates(Row.ROW_1, Column.B));

    board.executeMove(move);

    assertThat(board.getPieceAt(move.start()), is(empty()));
    assertThat(board.getPieceAt(move.end()), is(Optional.of(new Piece(Player.PLAYER_RED, true))));
  }

  @Test
  public void piece_stays_king_if_it_was_king() {
    Board board = new Board();
    // this move is not valid, but the board doesn't know that
    Move move1 =
        Move.of(
            Player.PLAYER_RED,
            new BoardCoordinates(Row.ROW_6, Column.B),
            new BoardCoordinates(Row.ROW_1, Column.B));
    Move move2 =
        Move.of(
            Player.PLAYER_RED,
            new BoardCoordinates(Row.ROW_1, Column.B),
            new BoardCoordinates(Row.ROW_2, Column.C));

    List.of(move1, move2).forEach(board::executeMove);

    assertThat(board.getPieceAt(move1.start()), is(empty()));
    assertThat(board.getPieceAt(move1.end()), is(empty()));
    assertThat(board.getPieceAt(move2.end()), is(Optional.of(new Piece(Player.PLAYER_RED, true))));
  }

  @Test
  public void remove_start_piece_when_JumpGambleResult_is_lost() {
    Board board = new Board();
    Move move1 =
        Move.of(
            Player.PLAYER_WHITE,
            new BoardCoordinates(Row.ROW_3, Column.A),
            new BoardCoordinates(Row.ROW_4, Column.B));
    Move move2 =
        Move.of(
            Player.PLAYER_RED,
            new BoardCoordinates(Row.ROW_6, Column.B),
            new BoardCoordinates(Row.ROW_5, Column.C));
    Move move3 =
        Move.of(
            Player.PLAYER_WHITE,
            new BoardCoordinates(Row.ROW_3, Column.C),
            new BoardCoordinates(Row.ROW_4, Column.D));
    Move jumpMove =
        Move.of(
                Player.PLAYER_RED,
                new BoardCoordinates(Row.ROW_5, Column.C),
                new BoardCoordinates(Row.ROW_3, Column.A))
            .withJumpGambleResult(JumpGambleResult.LOST);

    List.of(move1, move2, move3, jumpMove).forEach(board::executeMove);

    assertThat(board.getPieceAt(jumpMove.start()), is(Optional.empty()));
    assertThat(board.getPieceAt(jumpMove.getCoordinatesBetween().orElseThrow()), is(not(empty())));
  }

  @Test
  public void execute_normal_jump_move_if_JumpGambleResult_is_won() {
    Board board = new Board();
    Move move1 =
        Move.of(
            Player.PLAYER_WHITE,
            new BoardCoordinates(Row.ROW_3, Column.A),
            new BoardCoordinates(Row.ROW_4, Column.B));
    Move move2 =
        Move.of(
            Player.PLAYER_RED,
            new BoardCoordinates(Row.ROW_6, Column.B),
            new BoardCoordinates(Row.ROW_5, Column.C));
    Move move3 =
        Move.of(
            Player.PLAYER_WHITE,
            new BoardCoordinates(Row.ROW_3, Column.C),
            new BoardCoordinates(Row.ROW_4, Column.D));
    Move jumpMove =
        Move.of(
                Player.PLAYER_RED,
                new BoardCoordinates(Row.ROW_5, Column.C),
                new BoardCoordinates(Row.ROW_3, Column.A))
            .withJumpGambleResult(JumpGambleResult.WON);

    List.of(move1, move2, move3, jumpMove).forEach(board::executeMove);

    assertThat(
        board.getPieceAt(move1.start()), is(Optional.of(new Piece(Player.PLAYER_RED, false))));
    assertThat(board.getPieceAt(move1.end()), is(empty()));
  }
}
