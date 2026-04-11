package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Replay {
	private BufferedWriter writer;
	private BufferedReader reader;
	private int numMoves,currRow,currCol,boardSize;
	private String boardType;
	
	public Replay(Board board) {
		try {
			writer = new BufferedWriter(new FileWriter("replay.txt"));
			numMoves = 0;
			writer.write("boardType "+ board.getBoardType());
			writer.newLine();
			writer.write("boardSize "+ board.getBoardSize());
			writer.newLine();
		} catch (IOException e) {
			System.out.println("Replay class constructor failed");
		}
	}
	
	public void store(int row, int col) {
		//stores row/col into text file
		try {
			writer.write(row+" "+col);
			writer.newLine();
			numMoves++;
		} catch (IOException e) {
			System.out.println("store failed");
		}
	}
	public String processLine() {
		try {
			String rawLine = reader.readLine();
			if(rawLine.equals("randomize")) {
				rawLine = reader.readLine();
				updateRowCol(rawLine);
				return("randomize");
			}
			else {
				updateRowCol(rawLine);
				return("normal");
			}
		} catch (IOException e) {
			System.out.println("processLine failed");
			return("failed");
		}
		
	}
	public void updateRowCol(String rawLine) {
		String[] rowColArray = rawLine.split(" ");
		currRow = Integer.parseInt(rowColArray[0]);
		currCol = Integer.parseInt(rowColArray[1]);
	}
	public void storeRandomize(int row,int col) {
		try {
			writer.write("randomize");
			writer.newLine();
			writer.write(row +" "+col);
			writer.newLine();
			numMoves++;
			
		} catch (IOException e) {
			System.out.println("randomize failed");
		}
		
	}
	public void setup() {
		try {
			writer.flush();
			writer.close();
			//save file
			
			reader = new BufferedReader(new FileReader("replay.txt"));
			String currLine = reader.readLine();
			String[] split = currLine.split(" ");
			boardType = split[1];
			currLine = reader.readLine();
			split = currLine.split(" ");
			boardSize = Integer.parseInt(split[1]);
			
		} catch (IOException e) {
			System.out.println("setup failed");
		}
		
	}
	public int getRow() {
		return currRow;
	}
	public int getCol() {
		return currCol;
	}
	public int getBoardSize(){
		return boardSize;
	}
	public String getBoardType() {
		return boardType;
	}
	public int getNumMoves() {
		return numMoves;
	}
	public void save() {
		//TODO
		//saves text file to files
	}
	public void read() { //optional
		//TODO
		//read text file (provided by user)
	}
}
