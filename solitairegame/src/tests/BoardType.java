//This classes code was generated with ChatGPT

package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import application.Board;

public class BoardType {

    @Test
    public void engBoardInvalids() { //changed ChatGPT generated name
        Board board = new Board(7, "english");

        assertEquals(-1, board.getBoardAt(0,0).getIsAlive());
        assertEquals(-1, board.getBoardAt(0,1).getIsAlive());
        assertEquals(-1, board.getBoardAt(1,0).getIsAlive());

        assertEquals(-1, board.getBoardAt(0,5).getIsAlive());
        assertEquals(-1, board.getBoardAt(0,6).getIsAlive());
        assertEquals(-1, board.getBoardAt(1,6).getIsAlive());

        assertEquals(-1, board.getBoardAt(5,0).getIsAlive());
        assertEquals(-1, board.getBoardAt(6,0).getIsAlive());

        assertEquals(-1, board.getBoardAt(6,5).getIsAlive());
        assertEquals(-1, board.getBoardAt(6,6).getIsAlive());
    }

    @Test
    public void engBoardValids() {//changed ChatGPT generated name
        Board board = new Board(7, "english");

        assertEquals(1, board.getBoardAt(3,2).getIsAlive());
        assertEquals(1, board.getBoardAt(2,3).getIsAlive());
        assertEquals(1, board.getBoardAt(4,3).getIsAlive());
    }
}