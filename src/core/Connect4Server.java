package core;

/** Connect4Server
 *
 *  @author Brandon Grzenia
 *  @version 4/19/2020
 *
 *  This class is the server-side for the Connect 4 game.
 */

import java.io.*;
import java.net.*;

import ui.Connect4TextConsole;


public class Connect4Server {

	private ServerSocket serverSocket;
	private Socket socket;
	private static int clientID = 0;
	
	/**
	 * Parameterized Constructor
	 * @param port
	 */
	public Connect4Server(int port) {

		System.out.println("Server started.");
		clientID++;

		try {

			serverSocket = new ServerSocket(port);

			while (true) {

				Socket socket = serverSocket.accept();
				InetAddress address = socket.getInetAddress();
				
				System.out.println("New client connected.");
				System.out.println("Client " + clientID + "'s IP address: " + address.getHostAddress());

				ClientHandler task = new ClientHandler(socket);

				new Thread(task).start();

			}

		} catch (IOException e) {

			System.out.println(e);

		}

	}
	
	/**
	 * ClientHandler
	 * @author bpgrz
	 */
	class ClientHandler implements Runnable {

		private Socket socket;
		private Connect4TextConsole console;

		public ClientHandler(Socket socket) {

			this.socket = socket;

		}
		
		/**
		 * stop
		 */
		public void stop() {
			
			try {
				
				socket.close();
				
			} catch (IOException e) {
				
				System.out.println("Unable to close connection!");
				
			}
			
		}
		
		/**
		 * convertGameStateToByteArray
		 * @return byte array consisting of the game state
		 */
		public byte[] convertGameStateToByteArray() {
			
			byte[] r = new byte[42];
			int index = 0;
			
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 7; j++) {
					r[index] = (byte) console.getGame().getBoardArray()[i][j];
					index++;
				}
			}
			
			return r;
			
		}
		
		/**
		 * These are the commands the server sends to the client (simple, but it works).
		 * 
		 * 0 = prompt for move with comp
		 * 1 = invalid move
		 * 2 = prepare to receive game state
		 * 3 = notify of tie
		 * 4 = notify player X won
		 * 5 = notify player O won
		 * 6 = notify computer won
		 * 7 = prompt to display comp move
		 * 8 = prompt player X to move
		 * 9 = prompt player O to move
		 */

		@Override
		/**
		 * run
		 */
		public void run() {

			try {

				DataInputStream inputFromClient= new DataInputStream(socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
				console = new Connect4TextConsole(new Connect4());
				
				while (true) {
		
					if (inputFromClient.readInt() == 2)	{
						
						System.out.println("Client " + clientID + ": Started a game against computer.");
						
						while (!console.isOver()) {
							while (true) {
								try {
									System.out.println("Client " + clientID + ": Requesting player move.");
									outputToClient.writeInt(0);
									console.makeMove(inputFromClient.readInt());
									break;
								} catch (IllegalArgumentException e){
									outputToClient.writeInt(1);
									System.out.println("Client " + clientID + ": Made an invalid move, trying again.");
								}
							}
							
							System.out.println("Client " + clientID + ": Sending game state.");
							outputToClient.writeInt(2);
							outputToClient.write(convertGameStateToByteArray());
							
							switch (console.checkWinner()) {
							case (0):
								break;
							case (3):
								System.out.println("Client " + clientID + ": Declaring tie.");
								outputToClient.writeInt(3);
								System.out.println("Client " + clientID + ": Closing connection.");
								socket.close();
								return;
							default:
								if (console.getGame().getPlayerTurn() == 2) {
									System.out.println("Client " + clientID + ": Declaring player X the winner.");
									outputToClient.writeInt(4);
								}
								else {
									System.out.println("Client " + clientID + ": Declaring computer the winner.");
									outputToClient.writeInt(6);
								}
								System.out.println("Client " + clientID + ": Closing connection.");
								socket.close();
								return;
							}
							
							if (console.checkWinner() == 0) {
								console.makeMove(Connect4ComputerPlayer.returnMove(console.getGame().getBoardArray()));
								System.out.println("Client " + clientID + ": Requesting to display computer move.");
								outputToClient.writeInt(7);
								System.out.println("Client " + clientID + ": Sending game state.");
								outputToClient.writeInt(2);
								outputToClient.write(convertGameStateToByteArray());
								switch (console.checkWinner()) {
								case (0):
									break;
								case (3):
									System.out.println("Client " + clientID + ": Declaring tie.");
									outputToClient.writeInt(3);
									System.out.println("Client " + clientID + ": Closing connection.");
									socket.close();
									return;
								default:
									if (console.getGame().getPlayerTurn() == 2) {
										System.out.println("Client " + clientID + ": Declaring player X the winner.");
										outputToClient.writeInt(4);
									}
									else {
										System.out.println("Client " + clientID + ": Declaring computer the winner.");
										outputToClient.writeInt(6);
									}
									System.out.println("Client " + clientID + ": Closing connection.");
									socket.close();
									return;
								}
							}
						}	
					} else {
						
						System.out.println("Client " + clientID + ": Started a game against a player.");
						
						while (!console.isOver()) {
							while (true) {
								try {
									System.out.println("Client " + clientID + ": Requesting player X move.");
									outputToClient.writeInt(8);
									console.makeMove(inputFromClient.readInt());
									break;
								} catch (IllegalArgumentException e){
									outputToClient.writeInt(1);
									System.out.println("Client " + clientID + ": Made an invalid move, trying again.");
								}
							}
							
							System.out.println("Client " + clientID + ": Sending game state.");
							outputToClient.writeInt(2);
							outputToClient.write(convertGameStateToByteArray());
							
							switch (console.checkWinner()) {
							case (0):
								break;
							case (3):
								System.out.println("Client " + clientID + ": Declaring tie.");
								outputToClient.writeInt(3);
								System.out.println("Client " + clientID + ": Closing connection.");
								socket.close();
								return;
							default:
								if (console.getGame().getPlayerTurn() == 2) {
									System.out.println("Client " + clientID + ": Declaring player X the winner.");
									outputToClient.writeInt(4);
								}
								else {
									System.out.println("Client " + clientID + ": Declaring player O the winner.");
									outputToClient.writeInt(5);
								}
								System.out.println("Client " + clientID + ": Closing connection.");
								socket.close();
								return;
							}
							
							if (console.checkWinner() == 0) {
								while (true) {
									try {
										System.out.println("Client " + clientID + ": Requesting player O move.");
										outputToClient.writeInt(9);
										console.makeMove(inputFromClient.readInt());
										break;
									} catch (IllegalArgumentException e){
										outputToClient.writeInt(1);
										System.out.println("Client " + clientID + ": Made an invalid move, trying again.");
									}
								}
								System.out.println("Client " + clientID + ": Sending game state.");
								outputToClient.writeInt(2);
								outputToClient.write(convertGameStateToByteArray());
								switch (console.checkWinner()) {
								case (0):
									break;
								case (3):
									System.out.println("Client " + clientID + ": Declaring tie.");
									outputToClient.writeInt(3);
									System.out.println("Client " + clientID + ": Closing connection.");
									socket.close();
									return;
								default:
									if (console.getGame().getPlayerTurn() == 2) {
										System.out.println("Client " + clientID + ": Declaring player X the winner.");
										outputToClient.writeInt(4);
									}
									else {
										System.out.println("Client " + clientID + ": Declaring player O the winner.");
										outputToClient.writeInt(5);
									}
									System.out.println("Client " + clientID + ": Closing connection.");
									socket.close();
									return;
								}
							
							}
						}
					
					}
					
					return;
					
				}
	
			} catch (IOException e) {

				System.out.println(e);

			}

		}

	}
	
	/**
	 * Main loop
	 * @param args
	 */
	public static void main(String[] args) {

		Connect4Server server = new Connect4Server(8000);

	}

}
