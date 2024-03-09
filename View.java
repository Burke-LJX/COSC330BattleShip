import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;


public class View extends Grid{
    JFrame gameWindow = new JFrame("Battleship");
    private final int GRIDSIZE = 10;

    View() {
        //Set game frame attributes
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setSize(1000, 800);
        createAndShowMainScreen();

        gameWindow.setVisible(true);
    }

    public void createAndShowMainScreen() {
        JPanel mainScreen = new JPanel();
        JPanel playerGridPanel;
        JPanel playerShipPanel;
        JPanel enemyDisplayPanel;
        Grid gameboard = new Grid();

        mainScreen.setLayout(new BoxLayout(mainScreen, BoxLayout.PAGE_AXIS));
        mainScreen.setBackground(Color.DARK_GRAY);

        //Create mainScreen layout

        /*Create enemyDisplay
        Bar horizontal across top that holds enemy ship displays, height up to 300px
        */
        Box enemyBox = new Box(BoxLayout.LINE_AXIS);
        enemyBox.setMinimumSize(new Dimension(Short.MAX_VALUE, 200));
        enemyBox.setMaximumSize(new Dimension(Short.MAX_VALUE, 300));
        enemyDisplayPanel = initializeEnemyDisplay();

        enemyBox.add(enemyDisplayPanel);
        mainScreen.add(enemyBox);
        Box playerBox = new Box(BoxLayout.LINE_AXIS);

        //Create and customize playerGridPanel
        playerGridPanel = initializePlayerGridPanel(gameboard);
        //Create and customize playerShipPanel
        playerShipPanel = initializePlayerShipPanel();

        //Add player display to playerBox and then mainScreen
        //Frame buffer 25-50 px, ButtonPanel up to 50*100 px
        playerGridPanel.setMinimumSize(new Dimension(500,500));

        playerBox.add(playerGridPanel);
        playerBox.add(playerShipPanel);
        mainScreen.add(playerBox);

        //Creating button menu at bottom of frame: randomize ships, maybe host and join?
        JPanel buttonPanel = initializeButtonPanel();
        mainScreen.add(buttonPanel);

        
        gameWindow.add(mainScreen);
    }


    private JPanel initializeEnemyDisplay() {
        JPanel enemyDisplayPanel = new JPanel(new GridBagLayout());
        String eCarrierPath = "\\images\\eCruiserImg.png";
        String eBattleshipPath = "\\images\\eCruiserImg.png";
        String eCruiserPath = "\\images\\eCruiserImg.png";
        String eSubPath = "\\images\\eCruiserImg.png";
        String eDestroyerPath = "\\images\\eCruiserImg.png";

        ImageIcon eCarrier = createImageIcon(eCarrierPath, "Enemy Carrier Image");
        ImageIcon eBattlehsip = createImageIcon(eBattleshipPath, "Enemy Battleship Image");
        ImageIcon eCruiser = createImageIcon(eCruiserPath, "Enemy Cruiser Image");
        ImageIcon eSub = createImageIcon(eSubPath, "Enemy Submarine Image");
        ImageIcon eDestroyer = createImageIcon(eDestroyerPath, "Enemy Destroyer Image");

        JLabel eCarrierPic = new JLabel(eCarrier);

        //eCarrierPic.setIcon(createEnemyShipIcon(Ship.ShipType.CARRIER, false));
        eCarrierPic.setFocusable(false);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        eShipPanel.add(eCarrierPic, gridBagConstraints);
    }

    private JPanel initializePlayerGridPanel(Grid gameboard) {
        //Function to initialize playerGridPanel
        JPanel playerPanel = new JPanel(new GridLayout(GRIDSIZE, GRIDSIZE));
        JButton[][] playerGrid = new JButton[GRIDSIZE][GRIDSIZE];

        //Creating Buttons for playerGrid and gameboard
        for(int row = 0; row < GRIDSIZE; row++) {
            for(int column = 0; column < GRIDSIZE; column++) {
                Tile temp = gameboard.getTile(row, column);
                JButton buttonTile = new JButton();
                buttonTile.setBackground(Color.BLUE);
                

                playerGrid[row][column] = buttonTile;
                temp.setTileButton(buttonTile);
                buttonTile.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        enemyButtonTileClicked(evt);
                    }
                });


                playerPanel.add(buttonTile);
            }
        }

        return playerPanel;
    }

    private JPanel initializePlayerShipPanel() {
        JPanel shipPanel = new JPanel(new GridBagLayout());
        shipPanel.setMinimumSize(new Dimension(200, 400));
        shipPanel.setMaximumSize(new Dimension(300, 400));
        shipPanel.setBackground(Color.GRAY);
        Border shipPanelBorder = createShipPanelBorder();
        shipPanel.setBorder(shipPanelBorder);


        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        //Destroyer:
            //destroyerPic.setIcon(createPlayerShipIcon(Ship.ShipType.DESTROYER, false));
            destroyerPic.setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            shipPanel.add(destroyerPic, gridBagConstraints);

            //Submarine:
            //subPic.setIcon(createPlayerShipIcon(Ship.ShipType.SUBMARINE, false));
            subPic.setText("Submarine");
            subPic.setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 2;
            shipPanel.add(subPic, gridBagConstraints);

            //Cruiser:
            cruiserPic.setText("Cruiser");
            //cruiserPic.setIcon(createPlayerShipIcon(Ship.ShipType.CRUISER, false));
            cruiserPic.setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 3;
            pShipPanel.add(cruiserPic, gridBagConstraints);

            //Battleship:
            battleshipPic.setText("Battleship");
            //battleshipPic.setIcon(createPlayerShipIcon(Ship.ShipType.BATTLESHIP, false));
            battleshipPic.setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 4;
            pShipPanel.add(battleshipPic, gridBagConstraints);

            //Carrier:
            //carrierPic.setIcon(createPlayerShipIcon(Ship.ShipType.CARRIER, false));
            carrierPic.setFocusable(false);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 5;
            pShipPanel.add(carrierPic, gridBagConstraints);        

        return shipPanel;
    }

    //Function that creates border for shipPanel
    Border createShipPanelBorder() {
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredBevel = BorderFactory.createLoweredBevelBorder();
        Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredBevel);
        return compound;
    }

    //Function that creates and initializes ButtonPanel
    JPanel initializeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton hostButton = new JButton("Host Game");
        JButton joinButton = new JButton("Join Game");
        JButton randomizeShipButton = new JButton("Randomize Ship Placement");
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.setMinimumSize(new Dimension(Short.MAX_VALUE, 150));
        buttonPanel.add(Box.createRigidArea(new Dimension(25, 0)));

        //Customize Buttons
        hostButton.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        joinButton.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        hostButton.setFont(new Font("Arial", 1, 20));
        joinButton.setFont(new Font("Arial", 1, 20));
        randomizeShipButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        hostButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                hostButtonMouseClick(e);
                hostButton.setEnabled(false);
            }
        });
        joinButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                joinButtonMouseClick(e);
                joinButton.setEnabled(false);
            }
        });
        randomizeShipButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                randomizeButtonMouseClick(e);
                randomizeShipButton.setEnabled(false);
            }
        });

        buttonPanel.add(hostButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(joinButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(randomizeShipButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(25, 0)));

        return buttonPanel;
    }

    //Function to randomize Ship placement for player
    protected void randomizeButtonMouseClick(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'randomizeButtonMouseClick'");
    }

    //Function to join game
    protected void joinButtonMouseClick(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'randomizeButtonMouseClick'");
    }

    //Function to host game
    protected void hostButtonMouseClick(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hostButtonMouseClick'");
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
        throw new UnsupportedOperationException("Unimplemented method 'enemyButtonTileClicked'");
    }

    public static void main(String[] args) {
        View game = new View();
    }
}

