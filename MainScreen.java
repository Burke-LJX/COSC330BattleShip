import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.JComponent;


//import Drag.java;

        /*NOTES:
            Fill out methods for listeners
            Fix image icon paths
            implement drag and drop on playerScreen
            TEST
            click grid, check if hit/miss, update imgIcon, disable that specific button
        */
public class MainScreen {
        //Delcare and initialize panel to hold mainScreen
        JPanel mainScreen = new JPanel();
        //Declare and initialize a panel for player elements
        JSplitPane playerScreen = new JSplitPane();
        //Declare and initialize a panel for player elements
        JSplitPane enemyScreen = new JSplitPane();
        private final int GRIDSIZE = 10;
    	Point imageUpperLeft, prevPoint;
        
    
        MainScreen() {
            mainScreen.setLayout(new BoxLayout(mainScreen, BoxLayout.PAGE_AXIS));
            mainScreen.setBackground(Color.DARK_GRAY);
            //Set up vertical spacing for game screen
            mainScreen.add(Box.createRigidArea(new Dimension(0,5)));
            mainScreen.add(enemyScreen);
            mainScreen.add(Box.createRigidArea(new Dimension(0, 10)));
            mainScreen.add(playerScreen);
            mainScreen.add(Box.createRigidArea(new Dimension(0,5)));

            mainScreenComponents();
        }   //End of MainScreen constructor

        public void mainScreenComponents() {
           //Declaring and Initializing needed components and variables

           //Declare and initialize JPanels for the grid and ship register
           JPanel eGridPanel = new JPanel(new GridLayout(GRIDSIZE, GRIDSIZE));
           JPanel eShipPanel = new JPanel(new GridBagLayout());
           JPanel pGridPanel = new JPanel(new GridLayout(GRIDSIZE, GRIDSIZE));
           JPanel pShipPanel = new JPanel(new GridBagLayout());

           JButton[][] eBoard = new JButton[GRIDSIZE][GRIDSIZE];
           JButton[][] pBoard = new JButton[GRIDSIZE][GRIDSIZE];

           JLabel eDestroyerPic = new JLabel();
           JLabel eSubPic = new JLabel();
           JLabel eCruiserPic = new JLabel();
           JLabel eBattleshipPic = new JLabel();
           JLabel eCarrierPic = new JLabel();
           JButton fireButton = new JButton();

           JLabel destroyerPic = new JLabel();
           JLabel subPic = new JLabel();
           JLabel cruiserPic = new JLabel();
           JLabel battleshipPic = new JLabel();
           JLabel carrierPic = new JLabel();



           //Create enemyScreen

           enemyScreen.setDividerLocation(400);

            //Creating/Displaying enemyGrid
            for(int row = 0; row < GRIDSIZE; row++) {
                for(int column = 0; column < GRIDSIZE; column++) {
                    JButton buttonTile = new JButton();
                    buttonTile.setBackground(Color.BLUE);
                    eBoard[row][column] = buttonTile;
                    buttonTile.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent evt) {
                            enemyButtonTileClicked(evt);
                        }
                    });

                    eGridPanel.add(buttonTile);
                }
            }

            //Add eGridPanel to enemyScreen left side
            enemyScreen.setLeftComponent(eGridPanel);


            //Creating/Displaying eShipPanel
            eShipPanel.setBackground(Color.GRAY);
            eShipPanel.setBorder(createShipPanelBorder());
 


            //Customize JPanels as ship icons and add to eShipPanel
            //Carrier:
            eCarrierPic.setIcon(createEnemyShipIcon(Ship.ShipType.CARRIER, false));
            eCarrierPic.setFocusable(false);
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            eShipPanel.add(eCarrierPic, gridBagConstraints);

            //Battleship: 
            eBattleshipPic.setIcon(createEnemyShipIcon(Ship.ShipType.BATTLESHIP, false));
            eBattleshipPic.setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            eShipPanel.add(eBattleshipPic, gridBagConstraints);

            //Cruiser:
            eCruiserPic.setIcon(createEnemyShipIcon(Ship.ShipType.CRUISER, false));
            eCruiserPic.setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 2;
            eShipPanel.add(eCruiserPic, gridBagConstraints);

            //Submarine:
            eSubPic.setIcon(createEnemyShipIcon(Ship.ShipType.SUBMARINE, false));
            eSubPic.setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 3;
            eShipPanel.add(eSubPic, gridBagConstraints);

            //Destroyer:
            eDestroyerPic.setIcon(createEnemyShipIcon(Ship.ShipType.DESTROYER, false));
            eDestroyerPic.setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 4;
            eShipPanel.add(eDestroyerPic, gridBagConstraints);


            //Fire Button:
            fireButton.setBorder(BorderFactory.createRaisedSoftBevelBorder());
            fireButton.setBackground(Color.RED);
            fireButton.setForeground(Color.WHITE);
            fireButton.setFont(new Font("Arial", 1, 18));
            fireButton.setText("Fire!");
            fireButton.setOpaque(true);
            fireButton.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    fireButtonMouseClick(e);
                }
            });

            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 5;
            eShipPanel.add(fireButton, gridBagConstraints);

            //Add eShipPanel to enemyScreen right side
            enemyScreen.setRightComponent(eShipPanel);


            //Creating playerScreen

            //Drag and Drop Listener Assignment to playerScreen
            ClickListener clickListener = new ClickListener();
            playerScreen.addMouseListener(clickListener);
            DragListener dragListener = new DragListener();
            playerScreen.addMouseMotionListener(dragListener);
            
            playerScreen.setDividerLocation(400);

            //Creating/Displaying SelfGrid
            for(int row = 0; row < GRIDSIZE; row++) {
                for (int column = 0; column < GRIDSIZE; column++) {
                    JButton buttonTile = new JButton();
                    buttonTile.setBackground(Color.BLUE);
                    //buttonTile.addDropTarget(new DropTargetListener dropTarget);
                    pBoard[row][column] = buttonTile;

                    pGridPanel.add(pBoard[row][column]);
                }
            }
            //REMEMBER: to set pGrid to inactive after ship placement

            //Add pGridPanel to playerScreen left side
            playerScreen.setLeftComponent(pGridPanel);


            //Create shipPanel in playerScreen
            pShipPanel.setBorder(createShipPanelBorder());
            pShipPanel.setBackground(Color.GRAY);

            //Destroyer:
            destroyerPic.setIcon(createPlayerShipIcon(Ship.ShipType.DESTROYER, false));
            destroyerPic.setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            pShipPanel.add(destroyerPic, gridBagConstraints);

            //Submarine:
            subPic.setIcon(createPlayerShipIcon(Ship.ShipType.SUBMARINE, false));
            subPic.setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 2;
            pShipPanel.add(subPic, gridBagConstraints);

            //Cruiser:
            cruiserPic.setIcon(createPlayerShipIcon(Ship.ShipType.CRUISER, false));
            cruiserPic.setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 3;
            pShipPanel.add(cruiserPic, gridBagConstraints);

            //Battleship:
            battleshipPic.setIcon(createPlayerShipIcon(Ship.ShipType.BATTLESHIP, false));
            battleshipPic.setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 4;
            pShipPanel.add(battleshipPic, gridBagConstraints);

            //Carrier:
            carrierPic.setIcon(createPlayerShipIcon(Ship.ShipType.CARRIER, false));
            carrierPic.setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 5;
            pShipPanel.add(carrierPic, gridBagConstraints);

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
   
            
            //Adds pShipPanel to playerScreen right side
            playerScreen.setRightComponent(pShipPanel);


            
        }   //End of mainScreenComponents
    

    //Function that creates border for shipPanel
    Border createShipPanelBorder() {
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredBevel = BorderFactory.createLoweredBevelBorder();
        Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredBevel);
        return compound;
    }

    protected ImageIcon createPlayerShipIcon(Ship.ShipType ship, boolean isSunk) {
        //imgs 100x50 px?
        //Initialization of path variables
        //Player Ships:
        String destroyerImgPath = "images\\pDestroyerImg.png";
        String destroyerSunkImgPath = "images\\pDestroyerImgSunk.png";
        String subImgPath = "images\\pSubImg.png";
        String subSunkImgPath = "images\\pSubImgSunk.png";
        String cruiserImgPath = "images\\pCruiserImg.png";
        String cruiserSunkImgPath = "images\\pCruiserImgSunk.png";
        String battleshipImgPath = "images\\pBattleshipImg.png";
        String battleshipSunkImgPath = "images\\pBattleshipImgSunk.png";
        String carrierImgPath = "images\\pCarrierImg.png";
        String carrierSunkImgPath = "images\\pCarrierImgSunk.png";


        //Create ImageIcons
        ImageIcon destroyerImg = createImageIcon(destroyerImgPath, "Player's Destroyer");
        ImageIcon destroyerSunkImg = createImageIcon(destroyerSunkImgPath, "Player's Sunk Destroyer");
        ImageIcon subImg = createImageIcon(subImgPath, "Player's Submarine");
        ImageIcon subSunkImg = createImageIcon(subSunkImgPath, "Player's Sunk Submarine");
        ImageIcon cruiserImg = createImageIcon(cruiserImgPath, "Player's Cruiser");
        ImageIcon cruiserSunkImg = createImageIcon(cruiserSunkImgPath, "Player's Sunk Cruiser");
        ImageIcon battleshipImg = createImageIcon(battleshipImgPath, "Player's Battleship");
        ImageIcon battleshipSunkImg = createImageIcon(battleshipSunkImgPath, "Player's Sunk Battleship");
        ImageIcon carrierImg = createImageIcon(carrierImgPath, "Player's Carrier");
        ImageIcon carrierSunkImg = createImageIcon(carrierSunkImgPath, "Player's Sunk Carrier");

        switch(ship) {
            case DESTROYER:
                if (isSunk == false) {
                    return destroyerImg;
                }
                else {
                    return destroyerSunkImg;
                }
            case SUBMARINE:
                if (isSunk == false) {
                    return subImg;
                }
                else {
                    return subSunkImg;
                }
            case CRUISER:
                if (isSunk == false) {
                    return cruiserImg;
                }
                else {
                    return cruiserSunkImg;
                }
            case BATTLESHIP:
                if (isSunk == false) {
                    return battleshipImg;
                }
                else {
                    return battleshipSunkImg;
                }
            case CARRIER:
                if (isSunk == false) {
                    return carrierImg;
                }
                else {
                    return carrierSunkImg;
                }
            default:
            System.out.println("Failure to create ImageIcon");
        }
    }

    protected ImageIcon createEnemyShipIcon(Ship.ShipType ship, boolean isSunk) {
        //Initialization of path variables
        //Enemy Ships:
        String eDestroyerImgPath = "images\\eDestroyerImg.png";
        String eDestroyerSunkImgPath = "images\\eDestroyerImgSunk.png";
        String eSubImgPath = "images\\eSubImg.png";
        String eSubSunkImgPath = "images\\eSubImgSunk.png";
        String eCruiserImgPath = "images\\eCruiserImg.png";
        String eCruiserSunkImgPath = "images\\eCruiserImgSunk.png";
        String eBattleshipImgPath = "images\\eBattleshipImg.png";
        String eBattleshipSunkImgPath = "images\\eBattleshipImgSunk.png";
        String eCarrierImgPath = "images\\eCarrierImg.png";
        String eCarrierSunkImgPath = "images\\eCarrierImgSunk.png";


        //Create ImageIcons
        ImageIcon eDestroyerImg = createImageIcon(eDestroyerImgPath, "Enemy's Destroyer");
        ImageIcon eDestroyerSunkImg = createImageIcon(eDestroyerSunkImgPath, "Enemy's Sunk Destroyer");
        ImageIcon eSubImg = createImageIcon(eSubImgPath, "Enemy's Submarine");
        ImageIcon eSubSunkImg = createImageIcon(eSubSunkImgPath, "Enemy's Sunk Submarine");
        ImageIcon eCruiserImg = createImageIcon(eCruiserImgPath, "Enemy's Cruiser");
        ImageIcon eCruiserSunkImg = createImageIcon(eCruiserSunkImgPath, "Enemy's Sunk Cruiser");
        ImageIcon eBattleshipImg = createImageIcon(eBattleshipImgPath, "Enemy's Battleship");
        ImageIcon eBattleshipSunkImg = createImageIcon(eBattleshipSunkImgPath, "Enemy's Sunk Battleship");
        ImageIcon eCarrierImg = createImageIcon(eCarrierImgPath, "Enemy's Carrier");
        ImageIcon eCarrierSunkImg = createImageIcon(eCarrierSunkImgPath, "Enemy's Sunk Carrier");

        switch(ship) {
            case DESTROYER:
                if (isSunk == false) {
                    return eDestroyerImg;
                }
                else {
                    return eDestroyerSunkImg;
                }
            case SUBMARINE:
                if (isSunk == false) {
                    return eSubImg;
                }
                else {
                    return eSubSunkImg;
                }
            case CRUISER:
                if (isSunk == false) {
                    return eCruiserImg;
                }
                else {
                    return eCruiserSunkImg;
                }
            case BATTLESHIP:
                if (isSunk == false) {
                    return eBattleshipImg;
                }
                else {
                    return eBattleshipSunkImg;
                }
            case CARRIER:
                if (isSunk == false) {
                    return eCarrierImg;
                }
                else {
                    return eCarrierSunkImg;
                }
            default:
            System.out.println("Failure to create ImageIcon");
        }
    }

    public ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        }
        else {
        System.err.println("Couldn't find file: " + path);
        return null;
        }
    }

    protected void enemyButtonTileClicked(MouseEvent evt) {
        // TODO Auto-generated method stub
        //click grid, check if hit/miss, update imgIcon, disable that specific button
        
        throw new UnsupportedOperationException("Unimplemented method 'enemyButtonTileClicked'");
    }

    protected void fireButtonMouseClick(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fireButtonMouseClick'");
    }
        
    
    protected class ClickListener extends MouseAdapter{
	    public void mousePressed(MouseEvent event) {
			prevPoint = event.getPoint();
		}	
	}
    protected class DragListener extends MouseMotionAdapter{
    	public void mouseDragged(MouseEvent event) {
    		Point currPoint = event.getPoint();
    		int dx = (int) (currPoint.getX() - prevPoint.getX());
    		int dy = (int) (currPoint.getY() - prevPoint.getY());
    		
    		imageUpperLeft.translate(dx, dy);
    		prevPoint = currPoint;
    		repaint();  		
    	}
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


}
