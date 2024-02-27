import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.border.Border;
//import Drag.java;

        /*NOTES:
            Add playerGrid to gridPanel or someway to display grid. Fix this!
            Put listener on grid class that calls method to update/display grid?
            Need graphics for Ship, tiles to make icons out of.
        */
public class MainScreen {
        //Declare and initialize a panel for main screen
        playerScreen = new JPanel(new BorderLayout());
        //Declare and initialize JPanels for the grid and ship register
        gridPanel = new JPanel(new GridLayout());
        shipPanel = new JPanel(new GridBagLayout());
        private final int GRIDSIZE = 10;
        JButton[][] playerBoard = new JButton[GRIDSIZE][GRIDSIZE];
    
        MainScreen() {
            Border shipPanelBorder = createShipPanelBorder();
            shipPanel.setBorder(shipPanelBorder);
            shipPanel.setBackground(Color.DARK_GRAY);
            

            for(int row = 0; row < GRIDSIZE; row++) {
                for(int column = 0; column < GRIDSIZE; column++) {
                    JButton buttonTile = new JButton();
                    gridPanel.add(buttonTile);
                }
            }
            //Set up mouse listeners and Drag.java implementation on playerscreen
            //Mouse click listeners on gridPanel and Drag.java to effect both grid panel and ship panel
            playerScreen.add(gridPanel, SwingConstants.LEFT);
            playerScreen.add(shipPanel, SwingConstants.RIGHT);
    
        }
        
            
        //Function that creates border for shipPanel
        Border createShipPanelBorder() {
            Border raisedbevel = BorderFactory.createRaisedBevelBorder();
            Border loweredBevel = BorderFactory.createLoweredBevelBorder();
            Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredBevel);
            return compound;
        }



}
