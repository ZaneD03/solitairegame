package application;

public class Board {
	private Peg[][] board;//2D array that holds Peg object as variables to represent the board
	private int mid,boardSize;
	
	public Board(int boardSize, String boardType) {
		board = new Peg[boardSize][boardSize];
		mid = boardSize / 2;
		this.boardSize = boardSize;
		
		if(boardType.equals("english")) {
			englishBoard();
		}
		else if(boardType.equals("diamond")) {
			diamondBoard();
		}
		else if(boardType.equals("hexagon")) {
			hexagonBoard();
		}
		else {
			System.out.println("Board.java: invalid boardType when constructing");//shouldnt be possible, replace with exception being thrown later
		}
	}
	
	private void englishBoard() {
		int invalidBand = boardSize / 2 - 1;
		for(int row = 0;row<boardSize;row++) {
			for(int col = 0;col<boardSize;col++) {
				
				// invalid pegs
		        if ((row < invalidBand || row >= boardSize - invalidBand) &&
		            (col < invalidBand || col >= boardSize - invalidBand)) {
		        	board[row][col] = new Peg(-1,row,col);
		        }
		        // valid peg
		        else {
		        	board[row][col] = new Peg(1,row,col);
		        }
			}
		}
		//middle empty peg
        board[mid][mid] = new Peg(0,mid,mid);
	}
	
	private void diamondBoard() {
		for(int row = 0;row<boardSize;row++) {
			for(int col = 0;col<boardSize;col++) {
				//valid pegs 
				if (Math.abs(row - mid) + Math.abs(col - mid) <= mid) {
					board[row][col] = new Peg(1,row,col);
					} 
				//invalid pegs
				else {
					board[row][col] = new Peg(-1,row,col);
					 }
			}
		}
		//middle empty peg
        board[mid][mid] = new Peg(0,mid,mid);
	}
	
	private void hexagonBoard() {
		for(int row = 0;row<boardSize;row++) {
			int offset = Math.abs(row - mid) / 2;
			for(int col = 0;col<boardSize;col++) {
				//valid pegs
				if (col >= offset && col < boardSize - offset) {
					board[row][col] = new Peg(1,row,col);
					} 
				//invalid pegs
				else {
					board[row][col] = new Peg(-1,row,col);
					}
			}
		}
		//middle empty peg
        board[mid][mid] = new Peg(0,mid,mid);
	}
	
	public Peg[][] getBoard() {
		return board;
	}
	public Peg getBoardAt(int row, int col) {
		return board[row][col];
	}
	public void printBoard(){
		for(int row = 0;row<boardSize;row++) {
			for(int col = 0;col<boardSize;col++) {
				System.out.println(board[row][col].getIsAlive());
			}
		}
	}
}
