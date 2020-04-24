package core;

/** Connect4
 * 
 *  @author Brandon Grzenia
 *  @version 4/4/2020
 *  
 *  This class contains the methods used to create an instance of a Connect 4
 *  game, i.e. all of the logic and score-keeping is done here.
 */

public class Connect4 {
	
	private int[][] gameBoard; // [row][col], uses 1 for Player One, -1 for Player Two, and 0 for empty
	private int playerTurn; // 1 for Player One, 2 for Player Two
	private boolean gameOver; // true if game is done, false otherwise
	private boolean vsComputer; // true if player is playing against a computer opponent
	
	/** Default Constructor **/
	public Connect4() {
		gameBoard = new int[6][7]; // 6 rows, 7 columns
		playerTurn = 1;
		gameOver = false;
		vsComputer = false;
	}
	
	/** getBoardArray 
	 * 
	 *  @return the game state as an int[][]
	 *  
	 **/
	public int[][] getBoardArray() {
		return gameBoard;	
	}
	
	/** placePiece 
	 * 
	 * @param col - the array defined column to insert the piece
	 * @return true if piece placed correctly
	 * 
	 * **/
	public boolean placePiece(int col) {	
		if (isColFull(col)) return false;
		for (int i = gameBoard.length-1; i >= 0; i--) {
			if (gameBoard[i][col] == 0) {
				switch (playerTurn) {
				case (1):
					gameBoard[i][col] = 1;
					swapPlayers();
					return true;
				case (2):
					gameBoard[i][col] = -1;
					swapPlayers();
					return true;
				default:
					break;
				}	
			}
		}
		return false;	
	}
	
	/** playerWon
	 * 
	 * @return 1 if Player One has won the game, -1 if Player Two has won,
	 * 			0 if nobody has won, and 3 if its a tie
	 * 
	 */
	public int playerWon() {
		
		int counter = 0;
		int winningSpace = 0;
		for (int i = 0; i < gameBoard[0].length; i++) {
			if (isColFull(i)) counter++;
		}	
		if (counter == 7) {
			gameOver = true;
			return 3; // Tie
		}
		counter = 0;
		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard[i].length; j++) {
				if (gameBoard[i][j] != 0) {
					winningSpace = gameBoard[i][j];			
					// Check Vertical
					if (i >= 3) {
						counter++;
						while ((counter < 4) && (gameBoard[i-counter][j] == winningSpace)) {
							counter++;
						}
						if (counter == 4) {
							gameOver = true;
							return winningSpace;
						}
						counter = 0;
					}
					// Check Horizontal
					if (j <= 3) {
						counter++;
						while ((counter < 4) && (gameBoard[i][j+counter] == winningSpace)) {
							counter++;
						}
						if (counter == 4) {
							gameOver = true;
							return winningSpace;
						}
						counter = 0;						
					}		
					// Check Positive Diagonal
					if ((i >= 3) && (j <=3)) {
						counter++;
						while ((counter < 4) && (gameBoard[i-counter][j+counter] == winningSpace)) {
							counter++;
						}
						if (counter == 4) {
							gameOver = true;
							return winningSpace;
						}
						counter = 0;
					}
					// Check Negative Diagonal
					if ((i < 3) && (j < 4)) {
						counter++;
						while ((counter < 4) && (gameBoard[i+counter][j+counter] == winningSpace)) {
							counter++;
						}
						if (counter == 4) {
							gameOver = true;
							return winningSpace;
						}
						counter = 0;
					}				
				}
			}
		}	
		// Return no winner yet
		return 0;	
	}
	
	/** isGameOver 
	 * 
	 * @return true if game is over
	 * 
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	
	/** getPlayerTurn 
	 * 
	 * @return which players turn it is
	 * 
	 * **/
	public int getPlayerTurn() {
		return playerTurn;
	}
	
	/** isColFull 
	 * 
	 * @param col - the column to be checked for being full
	 * @return true if the column is full
	 * 
	 **/
	public boolean isColFull(int col) {
		for (int i = 0; i < gameBoard.length; i++) {
			if (gameBoard[i][col] == 0) {
				return false;
			}
		}
		return true;
	}
	
	/** setCompTrue **/
	
	public void setCompTrue() {
		this.vsComputer = true;
	}
	
	/** getCompStatus 
	 * 
	 * @return true if game is against a computer
	 * 
	 * **/
	
	public boolean getCompStatus() {
		return vsComputer;
	}
	
	/** swapPlayers **/
	public void swapPlayers() {
		if (playerTurn == 1) playerTurn = 2;
		else playerTurn = 1;
	}
}
