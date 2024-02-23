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


    public boolean isLegalPlacement(int row, int col, int size, int Orientation) {
        // Implement better algorithm here
        // Check that all tiles below are clear
        boolean Clear = true;
        if (Orientation == 1) {
            for (int i = 0; i < size; i++) {
                if (gameBoard[row][col+i].isOccupied()) {
                    Clear = false;
                }

            }
        } else {
            for (int i = 0; i < size; i++) {
                if (gameBoard[row+i][col].isOccupied()) {
                    Clear = false;
                }

            }
        }
        return Clear;
    }
    
    // Future Methods
}

class SelfGrid extends Grid {
    
    private Ship[] ships; 

    public SelfGrid() {
        super();
        ships = new Ship[5];   
        initializeShips();
    }

    private void initializeShips() {
        int counter = 0;
        for (Ship.ShipType type : Ship.ShipType.values()) {
            ships[counter] = new Ship(type);
            placeShipRandomly(ships[counter]);
            counter++;
        }
    }

    private void placeShipRandomly(Ship ship) {
        // Placeholder Function
        int size = ship.getSize();
        int Orientation = (int) (Math.random() * 2);
        int row = (int) (Math.random() * (10 - size + 1));
        int col = (int) (Math.random() * 10);

        while (!isLegalPlacement(row, col, size, Orientation)) {
            row = (int) (Math.random() * (10 - size + 1));
            col = (int) (Math.random() * 10);
        }

        for (int i = 0; i < size; i++) {
            getTile(row + i, col).occupyTile(ship.getShipType());
        }
    }
}
