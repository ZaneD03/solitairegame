//This classes code was generated with ChatGPT

package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import application.Board;

public class BoardSize {

    @Test
    public void boardInitSeven() { //changed ChatGPT generated name
        Board board = new Board(7, "english");

        assertEquals(7, board.getBoard().length);
        assertEquals(7, board.getBoard()[0].length);
    }

    @Test
    public void boardContainPegs() { //changed ChatGPT generated name
        Board board = new Board(7, "english");

        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 7; c++) {
                assertNotNull(board.getBoardAt(r, c));
            }
        }
    }
}