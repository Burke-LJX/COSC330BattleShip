import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;


//import Drag.java;

        /*NOTES:
            Add playerGrid to gridPanel or someway to display grid. Fix this!
            Put listener on grid class that calls method to update/display grid?
            Need graphics for Ship, tiles to make icons out of.
            click grid, check if hit/miss, update imgIcon, disable that specific button
        */
public class MainScreen extends MouseAdapter{
        //Delcare and initialize panel to hold mainScreen
        JPanel mainScreen = new JPanel();

        //Declare and initialize a panel for player elements
        JPanel playerScreen = new JPanel(new BorderLayout());
        //Declare and initialize a panel for player elements
        JPanel enemyScreen = new JPanel(new BorderLayout());
        //Declare and initialize JPanels for the grid and ship register
        JPanel gridPanel = new JPanel(new GridLayout());
        JPanel shipPanel = new JPanel(new GridBagLayout());
        private final int GRIDSIZE = 10;
        JButton[][] eBoard = new JButton[GRIDSIZE][GRIDSIZE];
    
        MainScreen(SelfGrid playerGrid, EnemyGrid enemyGrid) {
            mainScreen.setLayout(new BoxLayout(mainScreen, BoxLayout.PAGE_AXIS));
            mainScreen.setBackground(Color.DARK_GRAY);
            //Set up vertical spacing for game screen
            mainScreen.add(Box.createRigidArea(new Dimension(0,5)));
            mainScreen.add(enemyScreen);
            mainScreen.add(Box.createRigidArea(new Dimension(0, 10)));
            mainScreen.add(playerScreen);
            mainScreen.add(Box.createRigidArea(new Dimension(0,5)));



            //Creating playerScreen

            //Creating/Displaying SelfGrid
            JPanel pGrid = new JPanel(new GridLayout());
            JButton[][] pBoard = new JButton[GRIDSIZE][GRIDSIZE];
            for(int row = 0; row < GRIDSIZE; row++) {
                for (int column = 0; column < GRIDSIZE; column++) {
                    Tile tempTile = playerGrid.getTile(row, column);
                    JButton buttonTile = tempTile.getTileButton();
                    pBoard[row][column] = buttonTile;
                    buttonTile.setBackground(Color.BLUE);

                    pGrid.add(pBoard[row][column]);
                }
            }   //REMEMBER: to set pGrid to inactive after ship placement

            //Drag and Drop Listener Assignment to playerScreen
            ClickListener clickListener = new ClickListener();
            playerScreen.addMouseListener(clickListener);
            DragListener dragListener = new DragListener();
            playerScreen.addMouseMotionListener(dragListener);


            //Create shipPanel in playerScreen
            Border shipPanelBorder = createShipPanelBorder();
            shipPanel.setBorder(shipPanelBorder);
            shipPanel.setBackground(Color.GRAY);
            //NEED TO: Add ship images to shipPanel, add click listeners? to ship image icons?


            //Adds pGrid and shipPanel to playerScreen
            playerScreen.add(pGrid, SwingConstants.LEFT);
            playerScreen.add(shipPanel, SwingConstants.RIGHT);



            //Create enemyScreen

            //Mouse click listeners on gridPanel to effect both enemy grid panel and fire button
            JPanel enemyDisplay = new JPanel(new GridBagLayout());
            enemyDisplay.setBackground(Color.GRAY);

            //Creating/Displaying enemyGrid
            for(int row = 0; row < GRIDSIZE; row++) {
                for(int column = 0; column < GRIDSIZE; column++) {
                    Tile tempTile = enemyGrid.getTile(row, column);
                    JButton buttonTile = tempTile.getTileButton();
                    eBoard[row][column] = buttonTile;
                    buttonTile.addMouseListener(this);

                    gridPanel.add(buttonTile);
                }
            }

            /*Create enemyDisplay in enemyScreen (enemy ship list and fire button)
            */
            GridBagConstraints eDisplayConstraints = new GridBagConstraints();
            eDisplayConstraints.insets = new Insets(10, 5, 5, 5);


            JButton fireButton = new JButton();
            fireButton.setBackground(Color.RED);
            fireButton.setForeground(Color.WHITE);
            fireButton.setText("Fire!");
            fireButton.setBorder(BorderFactory.createRaisedSoftBevelBorder());
            eDisplayConstraints.anchor = GridBagConstraints.PAGE_END;

            ClickListener eClickListener = new ClickListener();
            fireButton.addMouseListener(eClickListener);

            //Add fire button to enemyDisplay
            enemyDisplay.add(fireButton, eDisplayConstraints);

            //Adds gridPanel and enemyDisplay to enemyScreen
            enemyScreen.add(gridPanel, SwingConstants.LEFT);
            enemyScreen.add(enemyDisplay, SwingConstants.RIGHT);

        }   //End of MainScreen constructor
        
    private class ClickListener extends MouseAdapter{
	    public void mousePressed(MouseEvent event) {
			prevPoint = event.getPoint();
		}	
	}
    private class DragListener extends MouseMotionAdapter{
    	public void mouseDragged(MouseEvent event) {
    		Point currPoint = event.getPoint();
    		int dx = (int) (currPoint.getX() - prevPoint.getX());
    		int dy = (int) (currPoint.getY() - prevPoint.getY());
    		
    		imageUpperLeft.translate(dx, dy);
    		prevPoint = currPoint;
    		repaint();  		
    	}
	}


        //Function that creates border for shipPanel
        Border createShipPanelBorder() {
            Border raisedbevel = BorderFactory.createRaisedBevelBorder();
            Border loweredBevel = BorderFactory.createLoweredBevelBorder();
            Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredBevel);
            return compound;
        }

        public void mouseClicked(MouseEvent e) {
            //What happens when mouse is clicked
        }

        //Function io initialize enemyShipDisplay
        public void initializeEnemyShipDisplay(PlayerInfo player) {
            /*
             * run through enemy ships
             * set all ship imgs to alive(light) view
             */

        }

        //Function to update enemyShipDisplay
        public void updateEnemyShipDisplay(PlayerInfo player) {
            for(int i = 0; i < 5; i++) {
                /*run through enemy ships
                if ship is sunk, switch ship img to sunk(dark) view
                */

            }

            return;
        } 

        public static void main(String[] args) {
            // Create players and grids
            SelfGrid player1Grid = new SelfGrid();
            EnemyGrid player2Grid = new EnemyGrid();
            
            MainScreen game = new MainScreen(player1Grid, player2Grid);
        }
}
GRIDSIZE