import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Create players and grids
        PlayerGrid player1Grid = new PlayerGrid();
        PlayerGrid player2Grid = new PlayerGrid();

        PlayerInfo player1Info = new PlayerInfo(1, 5);
        PlayerInfo player2Info = new PlayerInfo(2, 5);

        Scanner scanner = new Scanner(System.in);

        boolean gameIsRunning = true;

        while (gameIsRunning) {
            // Player 1's turn
            System.out.println("Player 1, it's your turn!");
            takeTurn(player1Grid, player2Grid, player1Info, player2Info, scanner);

            // Check if Player 2 has won
            if (player1Info.checkWin(player2Grid)) {
                System.out.println("Player 1 wins!");
                gameIsRunning = false;
                break;
            }

            // Player 2's turn
            System.out.println("Player 2, it's your turn!");
            takeTurn(player2Grid, player1Grid, player2Info, player1Info, scanner);

            // Check if Player 1 has won
            if (player2Info.checkWin(player1Grid)) {
                System.out.println("Player 2 wins!");
                gameIsRunning = false;
            }
        }

        scanner.close();
    }

    private static void takeTurn(PlayerGrid attackingGrid, PlayerGrid targetGrid,
                                  PlayerInfo attackerInfo, PlayerInfo targetInfo, Scanner scanner) {
        targetGrid.printGrid();
        System.out.println("Enter row and column to attack (e.g., A 4): ");
        char rowChar = scanner.next().charAt(0);
        int row = Character.toUpperCase(rowChar) - 'A';
        int col = scanner.nextInt() - 1;
        
        while (attackingGrid.attackTile(targetGrid, row, col) != true) {
            System.out.println("Enter another set of coordinates");
            System.out.println("Enter row and column to attack (e.g., A 4): ");
            rowChar = scanner.next().charAt(0);
            row = Character.toUpperCase(rowChar) - 'A';
            col = scanner.nextInt() - 1;
        }
        

        


        Tile attackedTile = targetGrid.getTile(row, col);
        if (attackedTile.isOccupied()) {
            Ship.ShipType hitShipType = attackedTile.getShipType();
            System.out.println("You hit a " + hitShipType + "!");
            attackerInfo.updateShipStatus(hitShipType,targetGrid);
        }
    }
}
