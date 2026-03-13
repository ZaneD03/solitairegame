//This classes code was generated with ChatGPT

package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import application.Board;

public class NewGame {

    @Test
    public void centerEmpty() {//changed ChatGPT generated name
        Board board = new Board(7, "english");

        int mid = 7 / 2;

        assertEquals(0, board.getBoardAt(mid, mid).getIsAlive());
    }

    @Test
    public void allValidsStartAlive() {//changed ChatGPT generated name
        Board board = new Board(7, "english");

        int mid = 7 / 2;

        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 7; c++) {

                int state = board.getBoardAt(r, c).getIsAlive();

                if (state == -1) {
                    continue;
                }

                if (r == mid && c == mid) {
                    assertEquals(0, state);
                } else {
                    assertEquals(1, state);
                }
            }
        }
    }
}