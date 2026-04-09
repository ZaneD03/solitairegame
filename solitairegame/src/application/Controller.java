package application;

import java.net.URL;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Controller implements Initializable{
	
	@FXML
	private RadioButton eng, hex, dia, manual, automatic;
	@FXML
    private GridPane boardGrid;
	@FXML
	private Spinner<Integer> boardSizeSpinner;
	@FXML
	private Text winLose;
	@FXML
	private Text invalidMove;
	@FXML
	private MouseEvent defaultMousePosition;
	@FXML 
	private Slider replaySpeedSlider;
	
	private Button[][] buttons; //grid for buttons
	private Board logicBoard;
	private int boardSize,replaySpeed;
	private String boardType; //"english","hexagon","Diamond"
	private String gameType; // "manual" or "automatic"
	private boolean gameActive,replayActive = false;
	private Random rand = new Random();
	private Replay replay;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//DEFAULT CONFIGURATION FOR FIRST LAUNCH
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(5,11,7,2);
		boardSizeSpinner.setValueFactory(valueFactory);
		replaySpeedSlider = new Slider(1,1000,500);
		boardType ="english";
		gameType = "manual";
		winLose.setOpacity(0);
		invalidMove.setOpacity(0);
		winLose.setText("winLose");
	}
	
	@FXML
	public void newGame(ActionEvent e) {
		//When the New Game button is selected, a new game is started based on boardType,boardSize, and gameType
		winLose.setOpacity(0);
		boardSize = boardSizeSpinner.getValue();
		gameActive = true;
		replayActive = false;
		initBoard();
		setBoard();
		if(gameType.equals("automatic")) {
			automate();
		}
	}
	
	@FXML
	public void replay(ActionEvent e) {
	    if (!gameActive) {
	        replayActive = true;
	        gameActive = true;
	        winLose.setOpacity(0);
	        winLose.setText("winLose");
	        
	        // setup replay data
	        replay.setup();
	        boardType = replay.getBoardType();
	        boardSize = replay.getBoardSize();
	        replaySpeed = ((int) replaySpeedSlider.getValue()); //NOT WORKING
	        initBoard();
	        setBoard();

	        Timeline timeline = new Timeline();

	        for (int i = 0; i < replay.getNumMoves(); i++) {

	            KeyFrame keyFrame = new KeyFrame(
	                Duration.millis(replaySpeed * i),
	                event -> {
	                    replay.processLine();
	                    buttonClick(replay.getRow(),replay.getCol(),defaultMousePosition);
	                }
	            );

	            timeline.getKeyFrames().add(keyFrame);
	        }

	        timeline.play();
	    }
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
	
	@FXML
	public void gameType(ActionEvent e) {
		if(manual.isSelected()) {
			gameType = "manual";
		}
		else if(automatic.isSelected()) {
			gameType = "automatic";
		}
	}
	
	@FXML
	public void randomize(ActionEvent e) {
		if(gameActive) {
			logicBoard.randomizeBoard();
			setBoard();
		}
		
	}
	@FXML
	public void newGameMouseEvent(MouseEvent e) {
		defaultMousePosition = e;
	}
	
	private void initBoard() {
		boardGrid.getChildren().clear();
	    boardGrid.getColumnConstraints().clear();
	    boardGrid.getRowConstraints().clear();
	    
	    boardSize = boardSizeSpinner.getValue();
	    logicBoard = new Board(boardSize,boardType);
	    buttons = new Button[boardSize][boardSize];
	    
	    if(!replayActive) {
	    	replay = new Replay(logicBoard);
	    }
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
	            btn.setOnMouseClicked(e -> buttonClick(r, c, e));
	        	buttons[row][col] = btn;
	        	buttons[row][col].setOpacity(0);
	            boardGrid.add(btn, col, row);
	        }
	    }
	}
	private void setBoard() {
		//change visual layout/button layout to match 
		
		for(int row = 0;row<boardSize;row++) {
			for(int col = 0;col<boardSize;col++) {
				Button currBtn = buttons[row][col];
				if(logicBoard.getPegState(row, col) == -1) {
					currBtn.setOpacity(0);
					currBtn.setDisable(true);
					currBtn.setText("");
				}
				else if(logicBoard.getPegState(row, col) == 0) {
					currBtn.setOpacity(1);
					currBtn.setDisable(false);
					currBtn.setText("0");
					currBtn.setStyle("");
				}
				else {
					currBtn.setOpacity(1);
					currBtn.setDisable(false);
					currBtn.setText("●");
					if (logicBoard.isPegSelected(row, col)) {
	                    currBtn.setStyle("-fx-background-color: blue;");
	                } 
					else {
	                    currBtn.setStyle("");
	                }
				}
			}
		}
	}
	private void buttonClick(int row,int col, MouseEvent e) {
		if(gameActive) { //only allows input when the game is not over
			String result = logicBoard.clickHandler(row, col);
			if(!replayActive) {
				replay.store(row, col);
			}
			
			// Refresh the entire board display after every click
			setBoard();
		    
		    // React to the result
		    switch (result) {
		        case "moved" -> {
		        	if (logicBoard.checkWin()) {
		            	endGame(true);
		            }
		        	else if (logicBoard.isGameOver()) {
		                endGame(false);
		            }
		        }
		        case "invalid_move" -> invalidMove(e); //replace with visual
		    }
		}
	}
	
	private void endGame(boolean condition) {
		//shows user when they have won/lose the game
		
		if(condition == true) {//game was won
			winLose.setText("You Win!");
			winLose.setFill(Color.GREEN);
			
			winLose.setOpacity(1);
		}
		else if(condition == false) { //game was lost
			winLose.setText("You Lose!");
			winLose.setFill(Color.RED);
			
			winLose.setOpacity(1);
		}
		gameActive = false;
		replayActive = false;
	}
	private void invalidMove(MouseEvent e) {
		//Quickly displays a message telling the user their move was invalid at users current mouse position
		invalidMove.setLayoutX(e.getSceneX()-80);
		invalidMove.setLayoutY(e.getSceneY());
		invalidMove.setOpacity(1);
		
		FadeTransition fade = new FadeTransition();
	    fade.setDuration(Duration.millis(500));
	    fade.setNode(invalidMove);
	    fade.setFromValue(1.0);
	    fade.setToValue(0.0);
	    fade.play();
	}
	
	private void automate() {
		//Completely random automated game that runs until game is over or won
		int randRow,randCol;
		do {
			do { //finds valid space for first click
				randRow = rand.nextInt(boardSize);
				randCol = rand.nextInt(boardSize);
			}while(logicBoard.getPegState(randRow, randCol) != 1);
			
			//Finds first valid move for jump (second click) (orthogonal)
			if(logicBoard.isValidMove(randRow, randCol, randRow-2, randCol)) {
				buttonClick(randRow,randCol,defaultMousePosition);
				buttonClick(randRow-2,randCol,defaultMousePosition);
			}
			else if(logicBoard.isValidMove(randRow, randCol, randRow+2, randCol)) {
				buttonClick(randRow,randCol,defaultMousePosition);
				buttonClick(randRow+2,randCol,defaultMousePosition);
			}
			else if(logicBoard.isValidMove(randRow, randCol, randRow, randCol-2)) {
				buttonClick(randRow,randCol,defaultMousePosition);
				buttonClick(randRow,randCol-2,defaultMousePosition);
			}
			else if(logicBoard.isValidMove(randRow, randCol, randRow, randCol+2)) {
				buttonClick(randRow,randCol,defaultMousePosition);
				buttonClick(randRow,randCol+2,defaultMousePosition);
			}
		}while(gameActive); //runs until game is over
		
	}
	
}