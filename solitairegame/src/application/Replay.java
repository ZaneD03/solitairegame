package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Replay {
	BufferedWriter writer;
	BufferedReader reader;
	int numMoves,currMove,currRow,currCol,boardSize;
	String boardType;
	
	public Replay(Board board) {
		try {
			writer = new BufferedWriter(new FileWriter("replay.txt"));
			numMoves = 0;
			currMove = 0;
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
	public void processLine() {
		try {
			String rawLine = reader.readLine();
			String[] rowColArray = rawLine.split(" ");
			currRow = Integer.parseInt(rowColArray[0]);
			currCol = Integer.parseInt(rowColArray[1]);
		} catch (IOException e) {
			System.out.println("processLine failed");
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
