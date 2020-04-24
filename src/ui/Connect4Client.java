package ui;

/** Connect4Client
 * 
 *  @author Brandon Grzenia
 *  @version 4/19/2020
 *  
 *  This class is the client-side for the Connect 4 game.
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Connect4Client {
	
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	private static Socket socket;
	
	/** 
	 * Parameterized Constructor
	 * @param host - name of the host
	 * @param port - port number
	 * @throws IOException - if server connection is lost
	 */
	public Connect4Client(String host, int port) throws IOException {
		
		try {
			
			socket = new Socket(host, port);
			
			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());
			 
		} catch (IOException e) {		
			
			throw new IOException();
			
		}
		
	}
	
	/**
	 * readFromServer
	 * @return the int value from the server
	 * @throws IOException - if server connection is lost
	 */
	public int readFromServer() throws IOException {
		
        try {
        	
			return fromServer.readInt();
			
		} catch (IOException e) {
			
			System.out.println("Lost connection to server.");
			throw new IOException();
			
		}

	}
	
	/**
	 * promptPlayerWithComp
	 * @throws IOException - if server connection is lost
	 */
	public void promptPlayerWithComp() throws IOException {
		
		int choice;
		
		Scanner input = new Scanner(System.in);
		System.out.println("It's your turn. Choose a column number from 1-7");
		choice = input.nextInt();
		
		while(choice < 1 || choice > 7) {
			
			System.out.println("Invalid choice. Choose a column number from 1-7");
			choice = input.nextInt();
			
		} 
		
        try {
        	
			toServer.writeInt(choice);
			
		} catch (IOException e) {
			
			System.out.println("Lost connection to server.");
			throw new IOException();
			
		}
		
	}
	
	/**
	 * setup
	 * @throws IOException - if server connection is lost
	 */
	public void setup() throws IOException {
		
		Scanner input = new Scanner(System.in);
		int choice;
		
		System.out.println("1 - Play against a human");
		System.out.println("2 - Play against the computer");
		choice = input.nextInt();
		
		while ((choice !=1 ) && (choice != 2)) {
			
			System.out.println("Invalid choice. Enter 1 for a human or 2 for a computer.");
			choice = input.nextInt();
			
		}
		
        try {
        	
			toServer.writeInt(choice);
			toServer.flush();
			
		} catch (IOException e) {
			
			System.out.println("Lost connection to server.");
			throw new IOException();
			
		}

	}
	
	/**
	 * displayInvalidMove
	 */
	public void displayInvalidMove() {
		
		System.out.println("Invalid move, trying again.");
		
	}
	
	/**
	 * readGameStateFromServer
	 * @return an int[][] representing the game status
	 * @throws IOException - if server connection is lost
	 */
	public int[][] readGameStateFromServer() throws IOException {
		
		int index = 0;
		int[][] gameBoard = new int[6][7];
		byte[] r = new byte[42];
        try {
        	
			fromServer.read(r);
			
		} catch (IOException e) {
			
			System.out.println("Lost connection to server.");
			throw new IOException();
			
		}
        for (int i = 0; i < 6; i++) {
        	
        	for (int j = 0; j < 7; j++) {
       
        		gameBoard[i][j] = r[index];
        		index++;
        				
        	}
        	
        }	
        
        return gameBoard;
        
	}
	
	/**
	 * displayGameState
	 * @param board - the game board represented as an int[][]
	 */
	public void displayGameState(int[][] board) {
		
		for (int i = 0; i < board.length; i++) {
			
			System.out.print("|");
			
			for (int j = 0; j < board[i].length; j++) {
				
				switch (board[i][j]) {
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
	
	/**
	 * displayTie
	 */
	public void displayTie() {
		
		System.out.println("Tie!\n");
		
	}
	
	/**
	 * displayPlayerXWin
	 */
	public void displayPlayerXWin() {
		
		System.out.println("Player X Won!\n");
		
	}
	
	/**
	 * displayPlayerOWin
	 */
	public void displayPlayerOWin() {
		
		System.out.println("Player O Won!\n");
		
	}
	
	/**
	 * displayComputerWin
	 */
	public void displayComputerWin() {
		
		System.out.println("The Computer Won!\n");
		
	}
	
	/**
	 * displayCompterTurn
	 */
	public void displayComputerTurn() {
		
		System.out.println("Computer Move:");
		
	}
	
	/**
	 * promptPlayerXMove
	 * @throws IOException - if server connection is lost
	 */
	public void promptPlayerXMove() throws IOException {
		
		int choice;
		
		Scanner input = new Scanner(System.in);
		System.out.println("Player X, it's your turn. Choose a column number from 1-7");
		choice = input.nextInt();
		
		while(choice < 1 || choice > 7) {
			
			System.out.println("Invalid choice. Choose a column number from 1-7");
			choice = input.nextInt();
			
		} 
		
        try {
        	
			toServer.writeInt(choice);
			
		} catch (IOException e) {
			
			System.out.println("Lost connection to server.");
			throw new IOException();
			
		}
		
	}
	
	/**
	 * promptPlayerOMove
	 * @throws IOException - if server connection is lost
	 */
	public void promptPlayerOMove() throws IOException {
		
		int choice;
		
		Scanner input = new Scanner(System.in);
		System.out.println("Player O, it's your turn. Choose a column number from 1-7");
		choice = input.nextInt();
		
		while(choice < 1 || choice > 7) {
			
			System.out.println("Invalid choice. Choose a column number from 1-7");
			choice = input.nextInt();
			
		} 
		
        try {
        	
			toServer.writeInt(choice);
			
		} catch (IOException e) {
			
			System.out.println("Lost connection to server.");
			throw new IOException();
			
		}
		
	}
	
	/**
	 * quit
	 */
	public void quit() {
		
		System.out.println("Closing connection to server.");
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("Failed to close connection to server.");
		}
		
	}
	
	/**
	 * Main loop
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		
		try {
			
			System.out.println("Establishing connection to server...");
			Connect4Client client = new Connect4Client("localhost", 8000);
			System.out.println("Connected!\n");
			
			client.setup();
			
			while (true) {
				
				switch(client.readFromServer()) {
					case(0):
						client.promptPlayerWithComp();
						break;
					case(1):
						client.displayInvalidMove();
						break;
					case(2):
						client.displayGameState(client.readGameStateFromServer());
						break;
					case(3):
						client.displayTie();
						client.quit();
						return;
					case(4):
						client.displayPlayerXWin();
						client.quit();
						return;
					case(6):
						client.displayComputerWin();
						client.quit();
						return;
					case(7):
						client.displayComputerTurn();
						break;	
					case(8):
						client.promptPlayerXMove();
						break;	
					case(5):
						client.displayPlayerOWin();
						client.quit();
						return;
					case(9):
						client.promptPlayerOMove();
						break;
						
				}
				
			}
			
		} catch (IOException e) {
		
			System.out.println("Unable to establish connection to server.");
			
		}
		
	}
	
}
