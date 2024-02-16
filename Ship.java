public class Ship {
    boolean IsSunk;
    enum ShipType {
        Destroyer,
        Submarine,
        Cruiser,
        Battleship,
        Carrier
    }
    public void setIsSunk(boolean isSunk) {
        IsSunk = isSunk;
    }
    public boolean getIsSunk() {
        return IsSunk;
    }
}


