package application;

public class Logic {
	private Board board;
	private int currRow,currCol;
	
	public Logic(int boardSize,String boardType) {
		board = new Board(boardSize,boardType);
		currRow = -1;
		currCol = -1;
	}
	
	public void newGame() {
		//TODO
		
	}
	private boolean checkWin() {
		//TODO
		return false;
	}
	public void makeMove(int row,int col) {
		//TODO
		currRow = row;
		currCol = col;
	}
	
	public int getCurrRow() {
		return currRow;
	}
	public int getCurrCol() {
		return currCol;
	}
	public Board getBoard() {
		return board;
	}
}
