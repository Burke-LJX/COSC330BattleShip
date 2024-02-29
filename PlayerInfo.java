public class PlayerInfo {
    private int playerNumber;
    private int numShips;
    private boolean gameStatus;
    private boolean turnActive;
    private boolean[][] shipStatus;  // 2D array to represent ship status

    public PlayerInfo(int playerNumber, int numShips) {
        this.playerNumber = playerNumber;
        this.numShips = numShips;
        this.gameStatus = false;
        this.turnActive = false;
        this.shipStatus = new boolean[numShips][2];  // Assuming each ship has two statuses: alive or sunk
        initializeShipStatus();
    }

    private void initializeShipStatus() {
        // Initialize ship status for each ship as alive
        for (int i = 0; i < numShips; i++) {
            shipStatus[i][0] = true;  // Alive
            shipStatus[i][1] = false;  // Not sunk
        }
    }

    public void setShipStatus(int shipIndex, boolean isAlive, boolean isSunk) {
        shipStatus[shipIndex][0] = isAlive;
        shipStatus[shipIndex][1] = isSunk;
    }

    public boolean[][] getShipStatus() {
        return shipStatus;
    }

    public void setTurnStatus(boolean turnActive) {
        this.turnActive = turnActive;
    }

    public boolean getTurnStatus() {
        return turnActive;
    }

    public void setWinStatus(boolean gameStatus) {
        this.gameStatus = gameStatus;
    }

    public boolean getWinStatus() {
        return gameStatus;
    }

    public void updateShipStatus(int shipIndex) {
        // Update ship status when a hit occurs
        if (shipIndex >= 0 && shipIndex < numShips) {
            Ship hitShip = ships[shipIndex];
            hitShip.registerHit();
            
            boolean isAlive = !hitShip.getIsSunk();
            boolean isSunk = hitShip.getIsSunk();
            
            shipStatus[shipIndex][0] = isAlive;
            shipStatus[shipIndex][1] = isSunk;
    
            System.out.println("Player " + playerNumber + " hit a " + hitShip.getShipType() +
                    "! Ship Status - Alive: " + isAlive + ", Sunk: " + isSunk);
    
            // Check if all ships are sunk
            boolean allShipsSunk = true;
            for (int i = 0; i < numShips; i++) {
                if (!ships[i].getIsSunk()) {
                    allShipsSunk = false;
                    break;
                }
            }
    
            // Update game status if all ships are sunk
            if (allShipsSunk) {
                gameStatus = true;
                System.out.println("Player " + playerNumber + " has lost all ships. Game Over!");
            }
        }
    }
}
