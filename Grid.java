public class Grid {
    private Tile[][] gameBoard;

    public Grid() {
        gameBoard = new Tile[10][10];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gameBoard[i][j] = new Tile();
            }
        }
    }

    public Tile getTile(int row, int col) {
        return gameBoard[row][col];
    }

    public void setState(Grid g) {
        
    }

    public boolean isLegalPlacement(int row, int col) {
        // Implement better algorithm here
        return !gameBoard[row][col].isOccupied();
    }
    
    // Future Methods
}

public class SelfGrid extends Grid {
    
    private Ship[] ships; 

    public SelfGrid() {
        super();
        ships = new Ship[5];   
        initializeShips();
    }

    private void initializeShips() {

        for (Ship.ShipType type : Ship.ShipType.values()) {
            Ship ship = new Ship(type);
            placeShipRandomly(ship);
        }
    }

    private void placeShipRandomly(Ship ship) {
        // Placeholder Function
        int size = ship.getSize();
        int row = (int) (Math.random() * (10 - size + 1));
        int col = (int) (Math.random() * 10);

        while (!isLegalPlacement(row, col)) {
            row = (int) (Math.random() * (10 - size + 1));
            col = (int) (Math.random() * 10);
        }

        for (int i = 0; i < size; i++) {
            getTile(row + i, col).occupyTile(ship.getShipType());
        }
    }
}
