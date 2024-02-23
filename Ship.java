public class Ship {
    private boolean isSunk;
    private ShipType shipType;
    private int size;

    public Ship(ShipType shipType) {
        this.shipType = shipType;
        this.size = shipType.getSize();
        this.isSunk = false;
    }

    public void setIsSunk(boolean isSunk) {
        this.isSunk = isSunk;
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

public enum ShipType {
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
