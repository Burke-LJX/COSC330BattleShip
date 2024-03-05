public class PlayerInfo {
    private int playerNumber;
    private int numShips;
    private boolean gameStatus;
    private boolean turnActive;
    

    public PlayerInfo(int playerNumber, int numShips) {
        this.playerNumber = playerNumber;
        this.numShips = numShips;
        this.gameStatus = false;
        this.turnActive = false;
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

    public void updateShipStatus(Ship.ShipType shipName, PlayerGrid grid) {
        // Update ship status when a hit occurs
        for (int i = 0; i < numShips; i++) {
            Ship currentShip = grid.ships[i];
            if (currentShip.getShipType().equals(shipName)) {
                currentShip.registerHit();
                break;  // Exit the loop since we found the matching ship
            }
        }
    }

    public boolean checkWin(PlayerGrid grid) {
        if (grid.checkShips() == false) {
            return true;
        }
        return false;
    }

}
