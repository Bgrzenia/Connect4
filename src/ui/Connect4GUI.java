package ui;

import core.Connect4;
import core.Connect4ComputerPlayer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/** Connect4GUI
 * 
 *  @author Brandon Grzenia
 *  @version 4/12/2020
 *  
 *  This class contains GUI for the Connect4 game.
 */

public class Connect4GUI extends Application {
	
	private Circle[][] spaces = new Circle[6][7]; //Array of circles to be drawn on the pane
	private EventHandler<MouseEvent> eventHandler;
	private Connect4 game = new Connect4();
	private boolean againstHuman;
	private final int CIRCLESIZE = 20; // The radius of the circles
	private final int INITALCIRCLELOCATION = 30; // Starting point for the first circle
	private final int CIRCLEPADDING = 50; // Padding between the circles
	private final int HEIGHT = 400; // Height for stage
	private final int WIDTH = 380; // Width for stage
	private final int PLAYERTEXT_X = 140; // X location for the text space
	private final int PLAYERTEXT_Y = 340; // Y location for the text space

	@Override
	public void start(Stage stage) throws Exception {
		
		// Set stage dimensions
		stage.setHeight(HEIGHT);
		stage.setWidth(WIDTH);
		
		// Local integers used as spacer variables
		int spacer;
		int spacer2 = 0;
		
		// Create the console
		Connect4TextConsole console = new Connect4TextConsole(game);
		
		// Create the Pane
		Pane board = new Pane();
		
		// Add the background color
		board.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		// Initialize text box
		Text playerText = new Text("Player X, your turn.");     
		playerText.setX(PLAYERTEXT_X); 
		playerText.setY(PLAYERTEXT_Y); 
		board.getChildren().add(playerText);
	    
		// Read in args from console to determine whether opponent is a computer or human
		for (String w : this.getParameters().getUnnamed()) {
			if (w.equals("human")) {
				againstHuman = true;
				break;
			} else {
				againstHuman = false;
				break;
			}
		}
		
		// Main loop
	    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
	    	@Override 
	    	public void handle(MouseEvent e) {  		
	    		if (againstHuman) {
		    		if (console.checkWinner() == 0) {
			    		Circle temp = (Circle)e.getSource();
			    		try {
			    			console.makeMove(Character.getNumericValue(temp.getId().charAt(1))+1);
				    		for (int i = 0; i < 6; i++) { 
				    			for (int j = 0; j < 7; j++) {
				    				if (game.getBoardArray()[i][j] == 1) {
				    					spaces[i][j].setFill(Color.RED);
				    				} else if (game.getBoardArray()[i][j] == -1) {
				    					spaces[i][j].setFill(Color.BLACK);
				    				}
				    			}
				    		}
							switch (console.checkWinner()) {
							case (0):
								break;
							case (3):
								playerText.setText("Tie!");
								break;
							default:
					    		if (game.getPlayerTurn() == 1) {
					    			playerText.setText("Player O won!");
					    		} else {
					    			playerText.setText("Player X won!");
					    		}
								return;
							}	    				    		
				    		if (console.checkWinner() == 0) {
					    		if (game.getPlayerTurn() == 1) {
					    			playerText.setText("Player X, your turn.");
					    		} else {
					    			playerText.setText("Player O, your turn.");
					    		}
				    		}
			    		} catch (IllegalArgumentException ex){
			    			playerText.setText("Invalid move. Try Again.");
			    		}	
		    		}
	    		} else {
		    		if (console.checkWinner() == 0) {
			    		Circle temp = (Circle)e.getSource();
			    		try {
			    			console.makeMove(Character.getNumericValue(temp.getId().charAt(1))+1);
				    		for (int i = 0; i < 6; i++) { 
				    			for (int j = 0; j < 7; j++) {
				    				if (game.getBoardArray()[i][j] == 1) {
				    					spaces[i][j].setFill(Color.RED);
				    				} else if (game.getBoardArray()[i][j] == -1) {
				    					spaces[i][j].setFill(Color.BLACK);
				    				}
				    			}
				    		}
							switch (console.checkWinner()) {
							case (0):
								break;
							case (3):
								playerText.setText("Tie!");
								break;
							default:
					    		if (game.getPlayerTurn() == 1) {
					    			playerText.setText("Computer won!");
					    		} else {
					    			playerText.setText("You won!");
					    		}
								return;
							}	    				    		
				    		if (console.checkWinner() == 0) {
								console.makeMove(Connect4ComputerPlayer.returnMove(console.getGame().getBoardArray()));
					    		for (int i = 0; i < 6; i++) { 
					    			for (int j = 0; j < 7; j++) {
					    				if (game.getBoardArray()[i][j] == 1) {
					    					spaces[i][j].setFill(Color.RED);
					    				} else if (game.getBoardArray()[i][j] == -1) {
					    					spaces[i][j].setFill(Color.BLACK);
					    				}
					    			}
					    		}
					    		playerText.setText("Computer moved, your turn!");
				    		}
							switch (console.checkWinner()) {
							case (0):
								break;
							case (3):
								playerText.setText("Tie!");
								break;
							default:
					    		if (game.getPlayerTurn() == 1) {
					    			playerText.setText("Computer won!");
					    		} else {
					    			playerText.setText("You won!");
					    		}
								return;
							}	    	
			    		} catch (IllegalArgumentException ex){
			    			playerText.setText("Invalid move. Try Again.");
			    		}	
		    		}
	    			
	    		}
	    	} 
	    };  
	    
	    // Initialize all of the spaces
		for (int i = 0; i < 6; i++) {
			spacer = 0;
			for (int j = 0; j < 7; j++) {
				spaces[i][j] = new Circle();
				spaces[i][j].setId(i + "" + j);
				spaces[i][j].setCenterX(INITALCIRCLELOCATION + spacer); 
				spaces[i][j].setCenterY(INITALCIRCLELOCATION + spacer2); 			      
				spaces[i][j].setRadius(CIRCLESIZE); 	  
				spaces[i][j].setFill(Color.WHITE); 	  
				spaces[i][j].addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
				board.getChildren().add(spaces[i][j]);
				spacer = spacer + CIRCLEPADDING; 		
			}
			spacer2 = spacer2 + CIRCLEPADDING;
		} 
	
		// Create the stage and show it
		Scene playArea = new Scene(board);
		stage.setScene(playArea);
		stage.show();
		
	}
	
}
