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
        // Logic to copy the state from one grid to another if needed
    }

    public boolean isLegalPlacement(int row, int col) {
        // Implement your logic here
        return !gameBoard[row][col].isOccupied();
    }
}

class SelfGrid extends Grid {
    private Ship[] ships;

    public SelfGrid() {
        super();
        ships = new Ship[5];  // Assuming there are 5 ships in the game
        initializeShips();
    }

    private void initializeShips() {
        for (Ship.ShipType type : Ship.ShipType.values()) {
            Ship ship = new Ship(type);
            placeShipRandomly(ship);
        }
    }

    private void placeShipRandomly(Ship ship) {
        int size = ship.getSize();
        int row, col;
        boolean isHorizontal;
    
        // Find a random position and orientation that avoids overlapping with existing ships
        do {
            row = (int) (Math.random() * 10);
            col = (int) (Math.random() * 10);
            isHorizontal = Math.random() < 0.5;
        } while (!isLegalPlacement(row, col, size, isHorizontal));
    
        // Place the ship incrementally in the array
        for (int i = 0; i < size; i++) {
            if (isHorizontal) {
                getTile(row, col + i).occupyTile(ship.getShipType());
            } else {
                getTile(row + i, col).occupyTile(ship.getShipType());
            }
        }
    
        // Store the ship in the ships array at the first available position
        for (int i = 0; i < ships.length; i++) {
            if (ships[i] == null) {
                ships[i] = ship;
                break;
            }
        }
    }
    

    public void printShipLocations() {
        System.out.println("Player's Ship Locations:");
        for (Ship ship : ships) {
            if (ship != null) {
                System.out.println(ship.getShipType() + " - Size: " + ship.getSize() + ", Location: " + getShipLocation(ship));
            }
        }
    }

    private String getShipLocation(Ship ship) {
        StringBuilder location = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (getTile(i, j).getShipType() == ship.getShipType()) {
                    location.append("(").append(i).append(",").append(j).append(") ");
                }
            }
        }
        return location.toString().trim();
    }

    private boolean isLegalPlacement(int startRow, int startCol, int size, boolean isHorizontal) {
        if (isHorizontal) {
            for (int i = 0; i < size; i++) {
                if (startCol + i >= 10 || getTile(startRow, startCol + i).isOccupied()) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (startRow + i >= 10 || getTile(startRow + i, startCol).isOccupied()) {
                    return false;
                }
            }
        }
        return true;
    }
}

class EnemyGrid extends Grid {
    public EnemyGrid() {
        super();
    }

    public boolean attackTile(int row, int col) {
        Tile targetTile = getTile(row, col);

        if (targetTile.isShot()) {
            return false;
        }

        targetTile.shootTile();

        if (targetTile.isOccupied()) {
            System.out.println("Hit!");
            Ship.ShipType hitShipType = targetTile.getShipType();
            return true;
        } else {
            System.out.println("Miss!");
            return false;
        }
    }
}