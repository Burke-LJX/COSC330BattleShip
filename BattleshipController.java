import java.util.Scanner;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class BattleshipController {
    private ObjectOutputStream output; // output stream to server
    private ObjectInputStream input; // input stream from server
    private String message = ""; // message from server
    private static String chatServer; // host server for this application
	private ServerSocket server; // server socket
	private Socket connection; // connection to client
	private boolean isServer = true;

    private BattleshipModel model;
    private Scanner scanner;

    public BattleshipController(BattleshipModel model) {
        this.model = model;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        boolean turn = false;
        // Initialize the game
        model.initializeGame();
        if (GetIsServer()) {
            turn = true;
        }
        // Main game loop
        while (true) {
            if (turn) {
            // Display the player's grid
            System.out.println("Your Grid:");
            model.getPlayerGrid().printGrid();

            // Display opponent's grid (for testing purposes)
            System.out.println("Opponent's Grid:");
            model.getOpponentGrid().printGrid();

            // Ask the player for their move
            System.out.println("Enter your move (e.g., A3): ");
            String move = scanner.nextLine().toUpperCase();
            System.out.println(SendMessage(move));
            turn = false;
            } else {
                System.out.println("Opponent Shot Result: "); 
                WaitForMessage();
                turn = true;
            }
        }
    }

    private boolean isValidMoveFormat(String move) {
        // Check if the move has a valid format (e.g., A3)
        return move.matches("[A-J][0-9]|10");
    }

    public void RunServer() {
		try {
			System.out.println("Creating server");
			server = new ServerSocket(12345, 100);
			System.out.println("Waiting For Connection");
			WaitForConnection();
			System.out.println("Connected");
			GetStreams();
		}
		catch (EOFException eofException) {
			System.out.println("Server Terminated Connection");
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	private void WaitForConnection() {
		try {
			connection = server.accept();
		}
		catch (IOException ioException){
			System.out.println("IO Error WaitForConnection");
		}
	}
	
	//starts the client
	public void RunClient() {
		try {
			ConnectToServer(); //will stay here until a connection is made
			GetStreams(); //recieves the streams from the server
		}
		catch (EOFException eofException) {
			System.out.println("Client Terminated Connection");
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	private void ConnectToServer() throws IOException {
		connection = new Socket( InetAddress.getByName( chatServer ), 12345 ); //connect to the server socket
	}
	
	private void GetStreams() throws IOException {
		output = new ObjectOutputStream( connection.getOutputStream() );      
	    output.flush();
	    input = new ObjectInputStream( connection.getInputStream() );
	}
	
	public String SendMessage(String msg) {
        try {
            output.writeObject(msg);
        } catch (IOException ioException) {
            System.out.println("Error Sending Message");
        }
    
        try {
            message = (String) input.readObject();
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("Unknown Object Type Received");
        } catch (IOException ioException) {
            System.out.println("IOException in ProcessConnection");
        }
    
        if (message.equals("Congratulations! You have sunk all opponent's ships. You win!")) {
            System.out.println(message);
            return " ";
        } else if (message.equals("Hit!")) {
            int row = msg.charAt(0) - 'A';
            int col = Integer.parseInt(msg.substring(1));
            model.getOpponentGrid().getTile(row, col).shootTile();
            model.getOpponentGrid().getTile(row, col).setIsOcupied(true);
            System.out.println("Shot Result: ");
            return (String) message;
        } else {
            int row = msg.charAt(0) - 'A';
            int col = Integer.parseInt(msg.substring(1));
            model.getOpponentGrid().getTile(row, col).shootTile();
            System.out.println("Shot Result: ");
            return (String) message;
        }
    }
    
	
    public void WaitForMessage() {
		//program will start by waiting for a message
		try {
			message = (String)input.readObject();
		}
		catch (ClassNotFoundException classNotFoundException){
			System.out.print("Unknown Object Type Recieved");
		}
		catch (IOException ioException) {
			System.out.print("IOException in ProcessConnection");
		}
		
		// Process the player's move
        if (isValidMoveFormat(message)) {
            int row = message.charAt(0) - 'A';
            int col = Integer.parseInt(message.substring(1));
        
            // Perform the attack
            boolean hit = model.getPlayerGrid().attackTile(model.getPlayerGrid(), row, col);
            while (!hit) {
                System.out.println("Space has already been shot. Please enter a valid move (e.g., A3).");
                message = scanner.nextLine().toUpperCase();
                hit = model.getPlayerGrid().attackTile(model.getOpponentGrid(), row, col);
            }
            if (model.getPlayerGrid().getTile(row,col).isOccupied()) {
                message = "Hit!";
            } else {
                message = "Miss!";
            }
            // Check if all opponent's ships are sunk
                        if (!model.getPlayerGrid().checkShips()) {
                            message = ("Congratulations! You have sunk all opponent's ships. You win!");
                            
                        }
        } else {
            System.out.println("Invalid move format. Please enter a valid move (e.g., A3).");
        }
        
		
		//send the data back to the shooter of if it was a hit, miss, or they won
		try {
			output.writeObject(message);
		}
		catch (IOException ioException) {
			System.out.print("Error Sending Message");
		}
		
		//display loss message
		if (message.equals("Congratulations! You have sunk all opponent's ships. You win!")) {
			System.out.print("YOU LOST");
		}
		
		//now it is this user's turn to shoot
	}


    
	public void SetIsServer(boolean serv) {
		isServer = serv;
	}
	
	public boolean GetIsServer() {
		return isServer;
	}


    public static void main(String[] args) {

        String host;
	    if (args.length == 0) {
	    	host = "127.0.0.1";
	    }
	    else {
	    	host = args[0];
	    }

        // Create an instance of BattleshipModel
        BattleshipModel model = new BattleshipModel();

        // Create an instance of BattleshipController
        BattleshipController controller = new BattleshipController(model);


        if (args.length == 0) {
			controller.SetIsServer(true);
			controller.RunServer();
		}
		else {
			controller.SetIsServer(false);
			controller.RunClient();
		}

        // Start the game
        controller.startGame();
    }
}
