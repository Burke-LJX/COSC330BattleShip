import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;


public class BattleshipView extends Grid{
    JFrame gameWindow = new JFrame("Battleship");
    private final int GRIDSIZE = 10;

    BattleshipView() {
        //Set game frame attributes
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setSize(1500, 1500);
        createAndShowMainScreen();

        gameWindow.setVisible(true);
    }

    public void createAndShowMainScreen() {
        JPanel mainScreen = new JPanel();  
        JPanel playerGridPanel;
        JPanel playerShipPanel;
        JPanel enemyGridPanel;
        JPanel enemyShipPanel;
        Grid gameboard = new Grid();
        Grid eboard = new Grid();
    
        mainScreen.setLayout(new BoxLayout(mainScreen, BoxLayout.PAGE_AXIS));
        mainScreen.setBackground(Color.DARK_GRAY);
        Box topBox = new Box(BoxLayout.LINE_AXIS);
        Box mainBox = new Box(BoxLayout.LINE_AXIS);
        Box bottomBox = new Box(BoxLayout.PAGE_AXIS);

        //use topBox to hold titles for player v enemy side?
        mainScreen.add(topBox);

        Box playerBox = new Box(BoxLayout.PAGE_AXIS);
        // Create and customize playerGridPanel
        playerGridPanel = initializePlayerGridPanel(gameboard);
        // Create and customize playerShipPanel
        playerShipPanel = initializePlayerShipPanel();
        playerBox.add(playerGridPanel);
        playerBox.add(playerShipPanel);
        mainBox.add(playerBox);

        mainBox.add(Box.createHorizontalGlue());

        //Create enemyDisplay
        Box enemyBox = new Box(BoxLayout.PAGE_AXIS);
        enemyShipPanel = initializeEnemyDisplay();
        enemyGridPanel = initializeEnemyGridPanel(eboard);
        enemyBox.add(enemyGridPanel);
        enemyBox.add(enemyShipPanel);
        mainBox.add(enemyBox);

        mainScreen.add(mainBox);
 
    
        //Creating button menu at the bottom left of the frame: randomize ships, maybe host and join?
        JPanel buttonPanel = initializeButtonPanel(mainScreen.getWidth());
        bottomBox.add(Box.createVerticalGlue());
        bottomBox.add(buttonPanel);
        mainScreen.add(bottomBox);
        
        gameWindow.add(mainScreen);
    }
    
    
    
    
    

    
    // Function to initialize enemy display
private JPanel initializeEnemyDisplay() {
    JPanel enemyDisplayPanel = new JPanel();
    enemyDisplayPanel.setLayout(new BoxLayout(enemyDisplayPanel, BoxLayout.PAGE_AXIS));
    enemyDisplayPanel.setBackground(Color.DARK_GRAY);

/*     Box panelTitleBox = new Box(BoxLayout.LINE_AXIS);
    JLabel panelTitle = new JLabel();
    panelTitle.setForeground(Color.WHITE);
    panelTitle.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
    panelTitle.setText("Enemy Ships Left");
    panelTitle.setPreferredSize(new Dimension(20, 30));
    panelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    panelTitleBox.add(panelTitle);

    enemyDisplayPanel.add(panelTitleBox); */

    Box enemyShipImgBox = new Box(BoxLayout.LINE_AXIS);

    // Setting up enemy ship images
    String[] shipPaths = {
        "/images/eCarrierImg.png", "/images/eBattleshipImg.png",
        "/images/eCruiserImg.png", "/images/eSubImg.png", "/images/eDestroyerImg.png"
    };

    enemyShipImgBox.add(Box.createHorizontalGlue());

    for (String path : shipPaths) {
        ImageIcon shipIcon = createImageIcon(path, "Enemy Ship Image");
        JLabel shipLabel = new JLabel(shipIcon);

        // Set preferred size for the enemy ship images
        shipLabel.setPreferredSize(new Dimension(20, 20));

        shipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        enemyShipImgBox.add(shipLabel);
        enemyShipImgBox.add(Box.createHorizontalGlue());
    }

    enemyShipImgBox.add(Box.createHorizontalGlue());

    enemyDisplayPanel.add(enemyShipImgBox);

    return enemyDisplayPanel;
}




// Function to initialize player grid
private JPanel initializePlayerGridPanel(Grid gameboard) {
    JPanel playerPanel = new JPanel(new GridLayout(GRIDSIZE, GRIDSIZE));
    JButton[][] playerGrid = new JButton[GRIDSIZE][GRIDSIZE];

    // Creating Buttons for playerGrid and gameboard
    for (int row = 0; row < GRIDSIZE; row++) {
        for (int column = 0; column < GRIDSIZE; column++) {
            Tile temp = gameboard.getTile(row, column);
            JButton buttonTile = new JButton();
            buttonTile.setBackground(Color.BLUE);
            
            // Increase the preferred size of the buttons
            buttonTile.setPreferredSize(new Dimension(40, 40));

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


//Function to initialize player ship panel
private JPanel initializePlayerShipPanel() {
    JPanel shipPanel = new JPanel();
    shipPanel.setLayout(new BoxLayout(shipPanel, BoxLayout.PAGE_AXIS));
    shipPanel.setBackground(Color.GRAY);
    Border shipPanelBorder = createShipPanelBorder();
    shipPanel.setBorder(shipPanelBorder);

    // Setting up player ship images
    String[] shipPaths = {
        "/images/pCarrierImg.png", "/images/pBattleshipImg.png",
        "/images/pCruiserImg.png", "/images/pSubImg.png", "/images/pDestroyerImg.png"
    };

    shipPanel.add(Box.createVerticalGlue());
    shipPanel.add(Box.createHorizontalGlue());

    for (String path : shipPaths) {
        ImageIcon shipIcon = createImageIcon(path, "Player Ship Icon");
        JLabel shipLabel = new JLabel(shipIcon);

        // Set preferred size for the ship images
        shipLabel.setPreferredSize(new Dimension(40, 40));

        shipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        shipLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        shipPanel.add(shipLabel);
        shipPanel.add(Box.createVerticalGlue());
    }

    shipPanel.add(Box.createVerticalGlue());

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
    JPanel initializeButtonPanel(int windowWidth) {
        JPanel buttonPanel = new JPanel();
        JButton hostButton = new JButton("Host Game");
        JButton joinButton = new JButton("Join Game");
        JButton randomizeShipButton = new JButton("Randomize Ship Placement");
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.setMinimumSize(new Dimension(windowWidth, 150));
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
        buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
        buttonPanel.add(joinButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(randomizeShipButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(25, 0)));

        return buttonPanel;
    }

    //Function to initialize enemygrid
    private JPanel initializeEnemyGridPanel(Grid gameboard) {
        //Function to initialize playerGridPanel
        JPanel ePanel = new JPanel(new GridLayout(GRIDSIZE, GRIDSIZE));
        JButton[][] eGrid = new JButton[GRIDSIZE][GRIDSIZE];
    
        //Creating Buttons for playerGrid and gameboard
        for(int row = 0; row < GRIDSIZE; row++) {
            for(int column = 0; column < GRIDSIZE; column++) {
                Tile temp = gameboard.getTile(row, column);
                JButton buttonTile = new JButton();
                buttonTile.setBackground(Color.BLUE);
                // Increase the preferred size of the buttons
                buttonTile.setPreferredSize(new Dimension(40, 40));
                    
    
                eGrid[row][column] = buttonTile;
                temp.setTileButton(buttonTile);
                buttonTile.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        enemyButtonTileClicked(evt);
                    }
                });
    
    
                ePanel.add(buttonTile);
            }
        }
    
        return ePanel;
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
        BattleshipView game = new BattleshipView();
    }
}
