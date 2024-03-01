public class Ship {
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
            System.out.println(this.getShipType() + "has been sunk.");        }
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
