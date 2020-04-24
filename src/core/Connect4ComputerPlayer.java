package core;
import java.util.Random;

/** Connect4ComputerPlayer
 * 
 *  @author Brandon Grzenia
 *  @version 4/4/2020
 *  
 *  This abstract class contains the logic for a computer player in Connect4.
 *  
 */

public abstract class Connect4ComputerPlayer {
	
	/** returnMove
	 * 
	 * @param board - game state (returned from Connect4.getBoardArray)
	 * @return int representing the column it has chosen to play it's piece in
	 */
	public static int returnMove(int[][] board) {
		Random rng = new Random();
		int move = rng.nextInt(7);
		while (isColFull(board, move)) {
			move = rng.nextInt(7);
		}
		return move+1; // Plus one because isCollFull uses array indexing
	}
	
	/** isColFull 
	 * 
	 * @param board - board being checked
	 * @param col - the column to be checked for being full
	 * @return true if the column is full
	 * 
	 **/
	public static boolean isColFull(int[][] board, int col) {
		for (int i = 0; i < board.length; i++) {
			if (board[i][col] == 0) {
				return false;
			}
		}
		return true;
	}

}
