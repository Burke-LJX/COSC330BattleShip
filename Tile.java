import javax.swing.JButton;

public class Tile {
    private boolean isOccupied;
    private boolean isShot;
    private Ship.ShipType shipType;
    private JButton tileButton;

    public Tile() {
        this.isOccupied = false;
        this.isShot = false;
        this.shipType = null;
        tileButton = null;
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
    public void setIsOcupied(boolean occ) {
        isOccupied = occ;
    }
    public Ship.ShipType getShipType() {
        return shipType;
    }

    public void setTileButton(JButton button) {
        tileButton = button;
    }
    public JButton getTileButton() {
        return tileButton;
    }
}