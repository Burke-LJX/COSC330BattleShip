public class Main {
    public static void main(String[] args) {
        // Create players and grids
        SelfGrid player1Grid = new SelfGrid();
        EnemyGrid player2Grid = new EnemyGrid();

        PlayerInfo player1Info = new PlayerInfo(1, 5);
        PlayerInfo player2Info = new PlayerInfo(2, 5);

        

        // Example: Player 2 attacks Player 1's grid
        System.out.println("Player 2 attacks Player 1's grid!");
        player2Grid.attackTile(3, 4);

        // Example: Check if the attacked tile contains a ship on Player 1's grid
        Tile attackedTile = player1Grid.getTile(3, 4);
        if (attackedTile.isOccupied()) {
            System.out.println("Player 2 hit a ship on Player 1's grid!");
            Ship.ShipType hitShipType = attackedTile.getShipType();
            // Additional logic for handling hits can be added here
        } else {
            System.out.println("Player 2 missed on Player 1's grid!");
            // Additional logic for handling misses can be added here
        }

        // Example: Player 1 checks the status of their ships
        boolean[][] player1ShipStatus = player1Info.getShipStatus();
        System.out.println("Player 1's Ship Status:");
        printShipStatus(player1ShipStatus);

        // Example: Player 1 sets turn status
        player1Info.setTurnStatus(false);

        // Example: Player 2 sets win status
        player2Info.setWinStatus(true);

        // Example: Player 2 checks if they won
        boolean player2WinStatus = player2Info.getWinStatus();
        if (player2WinStatus) {
            System.out.println("Player 2 wins!");
        }
        player1Grid.printShipLocations();

    }

    private static void printShipStatus(boolean[][] shipStatus) {
        // Helper method to print ship status
        for (int i = 0; i < shipStatus.length; i++) {
            System.out.println("Ship " + (i + 1) + ": Alive - " + shipStatus[i][0] + ", Sunk - " + shipStatus[i][1]);
        }
    }

    
}
