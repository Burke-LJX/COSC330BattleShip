public class BattleshipModel {
    private PlayerGrid playerGrid;
    private PlayerGrid opponentGrid;

    public BattleshipModel() {
        // Initialize the game in the constructor
        initializeGame();
    }

    public void initializeGame() {
        // Create player and opponent grids
        playerGrid = new PlayerGrid();
        opponentGrid = new PlayerGrid();

        // Place ships on player and opponent grids
        playerGrid.initializeShips();
        
    }

    public PlayerGrid getPlayerGrid() {
        return playerGrid;
    }

    public PlayerGrid getOpponentGrid() {
        return opponentGrid;
    }

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
    
        public void printGrid() {
            System.out.println("  0 1 2 3 4 5 6 7 8 9");
            for (int i = 0; i < 10; i++) {
                char yAxisLabel = (char) ('A' + i); 
                System.out.print(yAxisLabel + " ");
                for (int j = 0; j < 10; j++) {
                    char status = gameBoard[i][j].isShot() ? (gameBoard[i][j].isOccupied() ? 'X' : 'O') : ' ';
                    System.out.print(status + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        
    
        public boolean isLegalPlacement(int row, int col) {
            
            return !gameBoard[row][col].isOccupied();
        }
    }
    
    class PlayerGrid extends Grid {
            public Ship[] ships;
        
            public PlayerGrid() {
                super();
                ships = new Ship[5];  // Assuming there are 5 ships in the game
                initializeShips();
            }
        
            private void initializeShips() {
                for (Ship.ShipType type : Ship.ShipType.values()) {
                    Ship ship = new Ship(type);
                }
            }
    
            public boolean checkShips() {
                for (int i = 0; i < 5; i++) {
                    if (ships[i].getIsSunk() == false) {
                        return true;
                    }
                }
                return false;
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
                } while (!isLegalPlacement(row, col) || !isLegalPlacement(row, col, size, isHorizontal));
        
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
        
            public boolean attackTile(PlayerGrid targetGrid, int row, int col) {
                Tile targetTile = targetGrid.getTile(row, col);
        
                if (targetTile.isShot()) {
                    System.out.println("Already shot at this location!");
                    return false;
                }
        
                targetTile.shootTile();
        
                if (targetTile.isOccupied()) {
                    System.out.println("Hit!");
                    return true;
                } else {
                    System.out.println("Miss!");
                    return true;
                }
            }
        }
    
}

class Ship {
    private boolean isSunk;
    private ShipType shipType;
    private int size;
    private int hitCount; 

    enum ShipType {
        DESTROYER(2),
        SUBMARINE(3),
        CRUISER(3),
        BATTLESHIP(4),
        CARRIER(5);

        private final int size;

        ShipType(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    public Ship(ShipType shipType) {
        this.shipType = shipType;
        this.size = shipType.getSize();
        this.isSunk = false;
        this.hitCount = 0;
    }

    public void registerHit() {
        hitCount++;
        if (hitCount == size) {
            isSunk = true;
            System.out.println(this.getShipType() + " has been sunk.");        }
    }

    public boolean getIsSunk() {
        return isSunk;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public int getSize() {
        return size;
    }
}
