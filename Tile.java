import javax.swing.JButton;

public class Tile {
    private boolean isOccupied;
    private boolean isShot;
    private Ship.ShipType shipType;
    private JButton buttonForTile;

    public Tile() {
        this.isOccupied = false;
        this.isShot = false;
        this.shipType = null;
    }

    public void occupyTile(Ship.ShipType shipType) {
        this.isOccupied = true;
        this.shipType = shipType;
    }

    public void shootTile() {
        this.isShot = true;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean isShot() {
        return isShot;
    }

    public Ship.ShipType getShipType() {
        return shipType;
    }

    public JButton getTileButton() {
        return buttonForTile;
    }
}
