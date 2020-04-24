package ui;

/** Connect4TextConsole
 * 
 *  @author Brandon Grzenia
 *  @version 4/12/2020
 *  
 *  This class is the text console for the Connect4 Game.
 */

import java.io.IOException;

/** Connect4TextConsole
 * 
 *  @author Brandon Grzenia
 *  @version 4/5/2020
 *  
 *  This class contains the UI and the driver code for the Connect4 game.
 *  
 */

import java.util.*;
import core.Connect4;
import core.Connect4ComputerPlayer;
import javafx.application.Application;

public class Connect4TextConsole {
	
	private Connect4 game;
	
	/**Default Constructor 
	 * 
	 * @param game - initialize the game
	 * 
	 * **/
	public Connect4TextConsole(Connect4 game) {
		this.game = game;
	}
	
	/** getGame
	 * 
	 * @return The current game instance
	 * 
	 */
	public Connect4 getGame() {
		return this.game;
	}
	
	/** displayBoard **/
	public void displayBoard() {
		for (int i = 0; i < this.game.getBoardArray().length; i++) {
			System.out.print("|");
			for (int j = 0; j < this.game.getBoardArray()[i].length; j++) {
				switch (this.game.getBoardArray()[i][j]) {
				case (0):
					System.out.print(" ");
					break;
				case (1):
					System.out.print("X");
					break;
				case (-1):
					System.out.print("O");
					break;
				default:
					break;
				}
				System.out.print("|");
			}	
			System.out.println();
		}
		System.out.println();
	}
	
	/** makeMove 
	 * 
	 * @param col - The column to place the piece in
	 * @throws IllegalArgumentException if the column chosen is invalid
	 * 
	 * **/
	public void makeMove(int col) {
		col--;
		if (!game.placePiece(col)) {
			throw new IllegalArgumentException();
		}
	}
	
	/** promptPlayer 
	 * 
	 * @return the column chosen by the player
	 * 
	 * **/
	public int promptPlayer() {
		char player;
		int choice;
		Scanner input = new Scanner(System.in);
		if (game.getPlayerTurn() == 1) player = 'X';
		else player = 'O';
		System.out.println("Player " + player + " - your turn. Choose a column number from 1-7");
		choice = input.nextInt();
		while(choice < 1 || choice > 7) {
			System.out.println("Invalid choice. Choose a column number from 1-7");
			choice = input.nextInt();
		} 
		return choice;
	}

	/** promptPlayerWithComp 
	 * 
	 * @return the column chosen by the player
	 * 
	 * **/
	public int promptPlayerWithComp() {
		int choice;
		Scanner input = new Scanner(System.in);
		System.out.println("It's your turn. Choose a column number from 1-7");
		choice = input.nextInt();
		while(choice < 1 || choice > 7) {
			System.out.println("Invalid choice. Choose a column number from 1-7");
			choice = input.nextInt();
		} 
		return choice;
	}
	
	/** promptForComputer
	 * 
	 * @return true for playing against computer, false otherwise
	 * 
	 */
	public boolean promptForComputer() {
		String choice = "";
		Scanner input = new Scanner(System.in);
		System.out.println("Begin game. Enter ‘P’ if you want to play against another player; enter ‘C’ to play against a computer.");
		choice = input.next();
		while (!choice.equals("P") && !choice.equals("C")) {
			System.out.println("Invalid choice. Enter ‘P’ if you want to play against another player; enter ‘C’ to play against a computer.");
			choice = input.next();
		}
		if (choice.equals("C")) game.setCompTrue();
		return choice.equals("C");
	}
	
	/** displayWinner **/
	public void displayWinner() { 
		char player;
		
		if (game.getPlayerTurn() == 2) player = 'X';
		else player = 'O';
		if (game.getCompStatus()) {
			if (player == 'X') {
				System.out.println("You win!");
			} else {
				System.out.println("The computer won!");
			}
		} else {
			System.out.println("Player " + player + " won the game!");
		}
	}
	
	/** displayTie **/
	public void displayTie() {
		System.out.println("Tie!");
	}
	
	/** isOver 
	 * 
	 * @return true if the game is over
	 * 
	 * **/
	public boolean isOver() {
		return game.isGameOver();
	}
	
	/** checkWinner 
	 * 
	 * @return 3 for a tie, 0 for no winner, 1 for player X, and -1 for player O
	 * 
	 * **/
	public int checkWinner() {
		return game.playerWon();
	}
	
	/** Driver loop *
	 * 
	 * @param args - command line arguments
	 * @throws Exception 
	 * 
	 * */
	public static void main(String[] args) throws Exception {
		
		Connect4TextConsole console = new Connect4TextConsole(new Connect4());
		Scanner input = new Scanner(System.in);
		String choice;
		
		System.out.println("Would you like to use the GUI? (Y/N)");
		choice = input.next();
		
		while ((!choice.equals("Y")) && (!choice.equals("N"))) {
			System.out.println("Invalid choice. Enter Y for the text console or N to use the GUI.");
			choice = input.next();
		}
		
		if (choice.equals("Y")) {
			
			System.out.println("1 - Play against a human");
			System.out.println("2 - Play against the computer");
			choice = input.next();	
			
			while ((!choice.equals("1")) && (!choice.equals("2"))) {
				System.out.println("Invalid choice. Enter 1 for a human or 2 for a computer.");
				choice = input.next();
			}
			
			if (choice.equals("1")) Application.launch(Connect4GUI.class, "human");
			if (choice.equals("2")) Application.launch(Connect4GUI.class, "computer");
			
		} else {	
			if (console.promptForComputer()) {
				while (!console.isOver()) {
					while (true) {
						try {
							console.makeMove(console.promptPlayerWithComp());
							break;
						} catch (IllegalArgumentException e){
							System.out.println("Invalid move. Try Again.");
						}
					}
					console.displayBoard();
					switch (console.checkWinner()) {
					case (0):
						break;
					case (3):
						console.displayTie();
						break;
					default:
						console.displayWinner();
						break;
					}
					if (console.checkWinner() == 0) {
						console.makeMove(Connect4ComputerPlayer.returnMove(console.game.getBoardArray()));
						System.out.println("Computer Move:");
						console.displayBoard();
						switch (console.checkWinner()) {
						case (0):
							break;
						case (3):
							console.displayTie();
							break;
						default:
							console.displayWinner();
							break;
						}
					}
				}	
			}
			else {	
				while (!console.isOver()) {
					while (true) {
						try {
							console.makeMove(console.promptPlayer());
							break;
						} catch (IllegalArgumentException e){
							System.out.println("Invalid move. Try Again.");
						}
					}
					console.displayBoard();
					switch (console.checkWinner()) {
					case (0):
						break;
					case (3):
						console.displayTie();
						break;
					default:
						console.displayWinner();
						break;
					}
				}
			}
		}
	}
}
