package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Controller implements Initializable{
	
	private Button[][] buttons; //grid for buttons
	private Board logicBoard;
	@FXML
	private RadioButton eng, hex, dia;
	@FXML
    private GridPane boardGrid;
	@FXML
	private Spinner<Integer> boardSizeSpinner;
	private int boardSize; //int value to store board size for logic implementation
	private String boardType; //"english","hexagon","Diamond"
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {//for board size
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 11, 7, 2);
		valueFactory.setValue(7);
		boardSizeSpinner.setValueFactory(valueFactory);
		boardSize = boardSizeSpinner.getValue();
		boardType ="english";
		buttons = new Button[boardSize][boardSize];
		initBoard();
	}
	
	@FXML
	public void newGame(ActionEvent e) {
		//When the New Game button is selected, a new game is started based on boardType and boardSize
		logicBoard = new Board(boardSize,boardType);
		//boardSize = boardSizeSpinner.getValue(); //BREAKS THE PROGRAM: FIX FOR SPRINT3
		setBoard();
		
	}
	
	@FXML
	public void boardType(ActionEvent e) {
		if(eng.isSelected()) {
			System.out.println("boardType(english): TODO");
			boardType = "english";
		}
		else if(hex.isSelected()) {
			System.out.println("boardType(hexagon): TODO");
			boardType = "hexagon";
		}
		else if(dia.isSelected()) {
			System.out.println("boardType(diamond): TODO");
			boardType = "diamond";
		}
		
	}
	
	
	private void initBoard() {
		boardGrid.getChildren().clear();
	    boardGrid.getColumnConstraints().clear();
	    boardGrid.getRowConstraints().clear();
	    for (int i = 0; i < boardSize; i++) {
	        ColumnConstraints col = new ColumnConstraints();
	        col.setPercentWidth(100.0 / boardSize);
	        boardGrid.getColumnConstraints().add(col);

	        RowConstraints row = new RowConstraints();
	        row.setPercentHeight(100.0 / boardSize);
	        boardGrid.getRowConstraints().add(row);
	    }
	    for (int row = 0; row < boardSize; row++) {
	        for (int col = 0; col < boardSize; col++) {
	            Button btn = new Button();
	            btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	            btn.setOnAction(e -> buttonClick());
	        	buttons[row][col] = btn;
	            boardGrid.add(btn, col, row);
	        }
	    }
	}
	private void setBoard() {
		//change visual layout/button layout to match logicBoard
		for(int row = 0;row<boardSize;row++) {
			for(int col = 0;col<boardSize;col++) {
				if(logicBoard.getBoardAt(row,col).getIsAlive() == -1) {
					buttons[row][col].setOpacity(0);
					buttons[row][col].setDisable(true);
					buttons[row][col].setText("");
				}
				else if(logicBoard.getBoardAt(row,col).getIsAlive() == 0) {
					buttons[row][col].setOpacity(1);
					buttons[row][col].setDisable(false);
					buttons[row][col].setText("0");
				}
				else {
					buttons[row][col].setOpacity(1);
					buttons[row][col].setDisable(false);
					buttons[row][col].setText("1");
				}
			}
		}
	}
	private void buttonClick() {
		//button logic
		System.out.println("TODO: buttonCick");
	}
}
