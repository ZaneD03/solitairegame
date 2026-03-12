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
	
	@FXML
	private RadioButton eng, hex, dia;
	@FXML
    private GridPane boardGrid;
	@FXML
	private Spinner<Integer> boardSizeSpinner;
	private Button[][] buttons; //grid for buttons
	private Board logicBoard;
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
			boardType = "english";
		}
		else if(hex.isSelected()) {
			boardType = "hexagon";
		}
		else if(dia.isSelected()) {
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
	            final int r = row;
	            final int c = col;
	            btn.setOnAction(e -> buttonClick(r,c));
	        	buttons[row][col] = btn;
	        	buttons[row][col].setOpacity(0);
	            boardGrid.add(btn, col, row);
	        }
	    }
	}
	private void setBoard() {
		//change visual layout/button layout to match logicBoard
		for(int row = 0;row<boardSize;row++) {
			for(int col = 0;col<boardSize;col++) {
				Peg currPeg = logicBoard.getBoardAt(row,col);
				Button currBtn = buttons[row][col];
				if(currPeg.getIsAlive() == -1) {
					currBtn.setOpacity(0);
					currBtn.setDisable(true);
					currBtn.setText("");
				}
				else if(currPeg.getIsAlive() == 0) {
					currBtn.setOpacity(1);
					currBtn.setDisable(false);
					currBtn.setText("0");
					currBtn.setStyle("");
				}
				else {
					currBtn.setOpacity(1);
					currBtn.setDisable(false);
					currBtn.setText("●");
					
					if (currPeg.getIsSelected()) {
	                    currBtn.setStyle("-fx-background-color: blue;");
	                } else {
	                    currBtn.setStyle("");
	                }
				}
			}
		}
	}
	private void buttonClick(int row,int col) {
		String result = logicBoard.clickHandler(row, col);

	    // Refresh the entire board display after every click
	    setBoard();

	    // React to the result
	    switch (result) {
	        case "moved" -> {
	            if (logicBoard.isGameOver()) {
	                System.out.println("Game Over");
	            }
	        }
	        case "invalid_move" -> System.out.println("Invalid move!");
	        // selected/deselected are handled visually by refreshBoard()
	    }
	}
}
