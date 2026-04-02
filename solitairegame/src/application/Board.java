package application;

import java.util.Random;

public class Board {
	private Peg[][] board;
	private int mid,boardSize,currRow,currCol;
	private boolean hasSelection;
	private String boardType;
	private Random rand = new Random();
	
	public Board(int boardSize, String boardType) {
		board = new Peg[boardSize][boardSize];
		mid = boardSize / 2;
		this.boardSize = boardSize;
		this.boardType = boardType;
		currRow = -1;
		currCol = -1;
		hasSelection = false;
		setBoard();
	}
	private void setBoard() {
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
	
	public boolean checkWin() {
		int count = 0;
		for(int row = 0;row<boardSize;row++) {
			for(int col = 0;col<boardSize;col++) {
				if(board[row][col].getIsAlive() == 1) {
					count++;
					if(count >= 2) {
						return false;//returns false if there are 2 or more alive pegs
					}
				}
			}
		}
		return true; //returns true if there are 1 or less alive pegs (0 shouldnt be possible)
	}
	public boolean isValidMove(int row, int col, int newRow, int newCol) {
	    // Bounds check
	    if (newRow < 0 || newRow >= boardSize || newCol < 0 || newCol >= boardSize) {
	        return false;
	    }
	    
	    // Destination must be empty
	    if (board[newRow][newCol].getIsAlive() != 0) {
	        return false;
	    }

	    int distanceRow = newRow - row;
	    int distanceCol = newCol - col;

	    // Must be exactly 2 steps orthogonally or diagonally
	    if (!((Math.abs(distanceRow) == 2 && distanceCol == 0) || (distanceRow == 0 && Math.abs(distanceCol) == 2) || (Math.abs(distanceRow) == 2 && Math.abs(distanceCol) == 2))) {
	        return false;
	    }

	    // The peg in between must be alive
	    int midRow = (row + newRow) / 2;
	    int midCol = (col + newCol) / 2;
	    return board[midRow][midCol].getIsAlive() == 1;
	}
	
	private void applyMove(int row, int col, int newRow, int newCol) {
	    int midRow = (row + newRow) / 2;
	    int midCol = (col + newCol) / 2;
	    board[row][col].setAlive(0); // source becomes empty
	    board[midRow][midCol].setAlive(0);   // jumped peg removed
	    board[newRow][newCol].setAlive(1);     // destination gets peg
	}
	
	public void randomizeBoard() {
		int rowDecider,colDecider; //decides which row/col will be randomized
		
		int numTimes = rand.nextInt(1,boardSize); //number of times a random peg will be randomized
		for(int i = 0;i<numTimes;i++) {
			do {
				rowDecider = rand.nextInt(0, boardSize);
				colDecider = rand.nextInt(0, boardSize);
			}while(board[rowDecider][colDecider].getIsAlive() == -1);
			
			board[rowDecider][colDecider].flip();
		}
	}
	
	public boolean isGameOver() {
	    // Check every live peg for any valid jump
	    for (int row = 0; row < boardSize; row++) {
	    	for (int col = 0; col < boardSize; col++) {
	    		if (board[row][col].getIsAlive() == 1) {
	    			for (int[] d : new int[][]{{0,2},{0,-2},{2,0},{-2,0}}) {
	                    int tr = row + d[0], tc = col + d[1];
	                    if (tr >= 0 && tr < boardSize && tc >= 0 && tc < boardSize) {
	                        if (isValidMove(row, col, tr, tc)) {
	                        	return false;	                    	
	                        }
	                    }
	                }
	    		}     
	    	}      
	    }
	        
	    return true;
	}
	public String clickHandler(int row, int col) {
		Peg clicked = board[row][col];
		
		if(clicked.getIsAlive() == -1) {
			return "invalid";
		}
		
		if (!hasSelection) { //first move
	        if (clicked.getIsAlive() == 1) {
	            clicked.setIsSelected(true);
	            currRow = row;
	            currCol = col;
	            hasSelection = true;
	            return "selected";
	        }
	        return "no_peg";
	    }
		
		//second move options
	    if (row == currRow && col == currCol) { //same peg
	        clicked.setIsSelected(false);
	        hasSelection = false;
	        currRow = -1; currCol = -1;
	        return "deselected";
	    }

	    if (clicked.getIsAlive() == 1) {//change which peg is selected
	        board[currRow][currCol].setIsSelected(false);
	        clicked.setIsSelected(true);
	        currRow = row;
	        currCol = col;
	        return "selected";
	    }

	    // Clicked an empty hole — attempt the jump
	    if (isValidMove(currRow, currCol, row, col)) {
	        applyMove(currRow, currCol, row, col);
	        hasSelection = false;
	        currRow = -1; currCol = -1;
	        return "moved";
	    }

	    return "invalid_move";
		
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
