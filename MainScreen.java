import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

import Image.BattleshipImage;
import Image.CarrierImage;
import Image.SubmarineImage;


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
    
        MainScreen() {
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
                    JButton buttonTile = new JButton();
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
            GridBagConstraints pShipPanelConstraints = new GridBagConstraints(); 
            Border shipPanelBorder = createShipPanelBorder();
            shipPanel.setBorder(shipPanelBorder);
            shipPanel.setBackground(Color.GRAY);

            //Set ship imageIcons to JLabels that are then added to shipPanel//
            JLabel destroyerPic;
            JLabel subPic;
            JLabel cruiserPic;
            JLabel battleshipPic;
            JLabel carrierPic;

            //Set imageIcons to specified JLabels
            destroyerPic.setIcon(DestroyerImage.getShipImage());
            subPic.setIcon(SubmarineImage.getShipImage());
            cruiserPic.setIcon(CruiserImage.getShipImage());
            battleshipPic.setIcon(BattleshipImage.getShipImage());
            carrierPic.setIcon(CarrierImage.getShipImage());

            //Add drag listeners to said JLabels
            destroyerPic.addMouseListener(clickListener);
            destroyerPic.addMouseMotionListener(dragListener);
            subPic.addMouseListener(clickListener);
            subPic.addMouseMotionListener(dragListener);
            cruiserPic.addMouseListener(clickListener);
            cruiserPic.addMouseMotionListener(dragListener);
            battleshipPic.addMouseListener(clickListener);
            battleshipPic.addMouseMotionListener(dragListener);
            carrierPic.addMouseListener(clickListener);
            carrierPic.addMouseMotionListener(dragListener);

            //Add JLabels to ship panel in certain positions
            pShipPanelConstraints.insets = new Insets(10, 5, 5, 5);
            pShipPanelConstraints.anchor = GridBagConstraints.CENTER;
            pShipPanelConstraints.weightx = .1;
            shipPanel.add(destroyerPic, pShipPanelConstraints);

            pShipPanelConstraints.insets = new Insets(0, 5, 5, 5);
            pShipPanelConstraints.anchor = GridBagConstraints.CENTER;
            pShipPanelConstraints.weightx = .2;
            shipPanel.add(subPic, pShipPanelConstraints);

            pShipPanelConstraints.insets = new Insets(0, 5, 5, 5);
            pShipPanelConstraints.anchor = GridBagConstraints.CENTER;
            pShipPanelConstraints.weightx = .2;
            shipPanel.add(cruiserPic, pShipPanelConstraints);

            pShipPanelConstraints.insets = new Insets(0, 5, 5, 5);
            pShipPanelConstraints.anchor = GridBagConstraints.CENTER;
            pShipPanelConstraints.weightx = .3;
            shipPanel.add(battleshipPic, pShipPanelConstraints);

            pShipPanelConstraints.insets = new Insets(0, 5, 5, 10);
            pShipPanelConstraints.anchor = GridBagConstraints.CENTER;
            pShipPanelConstraints.weightx = .4;
            shipPanel.add(carrierPic, pShipPanelConstraints);


            //Adds pGrid and shipPanel to playerScreen
            playerScreen.add(pGrid, SwingConstants.LEFT);
            playerScreen.add(shipPanel, SwingConstants.RIGHT);



            //Create enemyScreen

            //Mouse click listeners on enemyDisplay to effect both enemy grid panel and fire button
            JPanel enemyDisplay = new JPanel(new GridBagLayout());
            enemyDisplay.setBackground(Color.GRAY);

            //Creating/Displaying enemyGrid
            for(int row = 0; row < GRIDSIZE; row++) {
                for(int column = 0; column < GRIDSIZE; column++) {
                    JButton buttonTile = new JButton();
                    eBoard[row][column] = buttonTile;
                    buttonTile.addMouseListener(this);

                    gridPanel.add(buttonTile);
                }
            }

            /*Create enemyDisplay in enemyScreen (enemy ship list and fire button)
            */

            //Set ship imageIcons to JLabels that are then added to shipPanel//
            JLabel eDestroyerPic;
            JLabel eSubPic;
            JLabel eCruiserPic;
            JLabel eBattleshipPic;
            JLabel eCarrierPic;
 
            //Set imageIcons to specified JLabels
            eDestroyerPic.setIcon(DestroyerImage.getShipImage());
            eSubPic.setIcon(SubmarineImage.getShipImage());
            eCruiserPic.setIcon(CruiserImage.getShipImage());
            eBattleshipPic.setIcon(BattleshipImage.getShipImage());
            eCarrierPic.setIcon(CarrierImage.getShipImage());

            //Add JLabels to ship panel in certain positions
            GridBagConstraints eDisplayConstraints = new GridBagConstraints();
            eDisplayConstraints.insets = new Insets(10, 5, 5, 5);
            eDisplayConstraints.anchor = GridBagConstraints.PAGE_START;
            eDisplayConstraints.weightx = .4;
            enemyDisplay.add(eCarrierPic, eDisplayConstraints);

            eDisplayConstraints.insets = new Insets(0, 5, 5, 5);
            eDisplayConstraints.weightx = .3;
            enemyDisplay.add(eBattleshipPic, eDisplayConstraints);

            eDisplayConstraints.insets = new Insets(0, 5, 5, 5);
            eDisplayConstraints.weightx = .2;
            enemyDisplay.add(eCruiserPic, eDisplayConstraints);

            eDisplayConstraints.insets = new Insets(0, 5, 5, 5);
            eDisplayConstraints.weightx = .2;
            enemyDisplay.add(eSubPic, eDisplayConstraints);

            eDisplayConstraints.insets = new Insets(0, 5, 5, 5);
            eDisplayConstraints.weightx = .1;
            enemyDisplay.add(eDestroyerPic, eDisplayConstraints);


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
    
        
    //eavesdropper thing in action listener?
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
