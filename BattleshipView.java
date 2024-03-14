import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

import Ship.ShipType;


public class BattleshipView extends Grid{
    JFrame gameWindow = new JFrame("Battleship");
    private final int GRIDSIZE = 10;
    private BattleshipModel model;

    BattleshipView() {
        //Set game frame attributes
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setLocation(0, 0);
        gameWindow.setSize(1500, 1000);
        createAndShowMainScreen();

        gameWindow.setVisible(true);
    }


    public void createAndShowMainScreen() {
        JPanel mainScreen = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        JPanel playerGridPanel;
        JPanel playerShipPanel;
        JPanel enemyGridPanel;
        JPanel enemyShipPanel;
        PlayerGrid gameboard = new PlayerGrid();
        PlayerGrid enemyboard = new PlayerGrid();
        String blankTileImgPath = "/images/blankTile.png";
        ImageIcon blankTile = createImageIcon(blankTileImgPath, "Empty blue water tile");


        mainScreen.setBackground(Color.DARK_GRAY);
        Box topBox = new Box(BoxLayout.LINE_AXIS);
        Box centerBox = new Box(BoxLayout.LINE_AXIS);
        Box bottomBox = new Box(BoxLayout.PAGE_AXIS);
    
        topPanel = initializeTopPanel();
        topBox.add(topPanel);
        mainScreen.add(topBox, BorderLayout.NORTH);
    
        // Create player panel
        Box playerBox = new Box(BoxLayout.PAGE_AXIS);
        playerGridPanel = initializePlayerGridPanel(gameboard, blankTile);
        playerShipPanel = initializePlayerShipPanel();
        playerBox.add(playerGridPanel);
        playerBox.add(playerShipPanel);
    
        // Create center panel with buttonPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.GRAY);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));  // Set the layout to PAGE_AXIS
        JPanel buttonPanel = initializeButtonPanel(mainScreen.getHeight());  // Use mainScreen.getHeight()
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalGlue());
    
        // Create right panel with enemyGridPanel and enemyShipPanel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        enemyShipPanel = initializeEnemyDisplay();
        enemyGridPanel = initializeEnemyGridPanel(enemyboard, blankTile);
        rightPanel.add(enemyGridPanel);
        rightPanel.add(enemyShipPanel);
    
        // Add player, center, and right panels to centerBox
        centerBox.add(playerBox);
        centerBox.add(Box.createHorizontalGlue());
        centerBox.add(centerPanel);
        centerBox.add(rightPanel);
    
        mainScreen.add(centerBox, BorderLayout.CENTER);
    
        // Rest of the code remains unchanged
        bottomBox.add(Box.createVerticalGlue());
        mainScreen.add(bottomBox, BorderLayout.SOUTH);
        gameWindow.add(mainScreen);
    }
    
    //Function to initialize top box of game window
    private JPanel initializeTopPanel() {
        JPanel topPanel = new JPanel();
        JLabel gameTitle = new JLabel();
        JLabel pLabel = new JLabel("Player Side");
        JLabel eLabel = new JLabel("Enemy Side");
        topPanel.setBackground(Color.BLACK);
        gameTitle.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        gameTitle.setForeground(Color.GREEN);
        gameTitle.setText("Battleship");

        pLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        eLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        pLabel.setForeground(Color.GREEN);
        eLabel.setForeground(Color.GREEN);
        //Add labels for player side and enemy side, switch with turns?
        topPanel.add(gameTitle);

        return topPanel;
    }

    // Function to initialize enemy ship display
    private JPanel initializeEnemyDisplay() {
        JPanel enemyDisplayPanel = new JPanel();
        enemyDisplayPanel.setLayout(new BoxLayout(enemyDisplayPanel, BoxLayout.PAGE_AXIS));
        enemyDisplayPanel.setBackground(Color.DARK_GRAY);
    
        // Setting up enemy ship images
        String[] shipPaths = {
            "/images/eCarrierImg.png", "/images/eBattleshipImg.png",
            "/images/eCruiserImg.png", "/images/eSubImg.png", "/images/eDestroyerImg.png"
        };
    
        for (String path : shipPaths) {
            ImageIcon originalIcon = createImageIcon(path, "Enemy Ship Image");
    
            // Scale the image while maintaining the aspect ratio
            ImageIcon scaledIcon = scaleImageIcon(originalIcon, 500, 100);
    
            JLabel shipLabel = new JLabel(scaledIcon);
    
            shipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            enemyDisplayPanel.add(shipLabel);
            enemyDisplayPanel.add(Box.createVerticalStrut(5)); // Add spacing between ships
        }
    
        return enemyDisplayPanel;
    }
    
    //Function to initialize enemygrid
    private JPanel initializeEnemyGridPanel(PlayerGrid gameboard, ImageIcon blankTile) {
        //Function to initialize playerGridPanel
        JPanel ePanel = new JPanel(new GridLayout(GRIDSIZE, GRIDSIZE));
        JButton[][] eGrid = new JButton[GRIDSIZE][GRIDSIZE];
            
        //Creating Buttons for playerGrid and gameboard
        for(int row = 0; row < GRIDSIZE; row++) {
            for(int column = 0; column < GRIDSIZE; column++) {
                Tile temp = gameboard.getTile(row, column);
                JButton buttonTile = new JButton();
                buttonTile.setIcon(blankTile);
                // Increase the preferred size of the buttons
                buttonTile.setPreferredSize(new Dimension(40, 40));
                    
    
                eGrid[row][column] = buttonTile;
                temp.setTileButton(buttonTile);
                buttonTile.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        enemyButtonTileClicked(evt, gameboard);
                    }
                });
    
    
                ePanel.add(buttonTile);
            }
        }
    
        return ePanel;
    }    




// Function to initialize player grid
private JPanel initializePlayerGridPanel(PlayerGrid gameboard, ImageIcon blankTile) {
    JPanel playerPanel = new JPanel(new GridLayout(GRIDSIZE, GRIDSIZE));
    JButton[][] playerGrid = new JButton[GRIDSIZE][GRIDSIZE];
   

    // Creating Buttons for playerGrid and gameboard
    for (int row = 0; row < GRIDSIZE; row++) {
        for (int column = 0; column < GRIDSIZE; column++) {
            Tile temp = gameboard.getTile(row, column);
            JButton buttonTile = new JButton();
            buttonTile.setIcon(blankTile);
            
            // Increase the preferred size of the buttons
            buttonTile.setPreferredSize(new Dimension(40, 40));

            playerGrid[row][column] = buttonTile;
            temp.setTileButton(buttonTile);

            playerPanel.add(buttonTile);
        }
    }

    return playerPanel;
}


// Function to initialize player ship panel
//Need to redo function to individually size ships so that they dont stretch
private JPanel initializePlayerShipPanel() {
    JPanel shipPanel = new JPanel();
    shipPanel.setLayout(new BoxLayout(shipPanel, BoxLayout.PAGE_AXIS));
    shipPanel.setBackground(Color.DARK_GRAY);
    Border shipPanelBorder = createShipPanelBorder();
    shipPanel.setBorder(shipPanelBorder);

    // Setting up player ship images
    String[] shipPaths = {
        "/images/pCarrierImg.png", "/images/pBattleshipImg.png",
        "/images/pCruiserImg.png", "/images/pSubImg.png", "/images/pDestroyerImg.png"
    };

    shipPanel.add(Box.createVerticalGlue());

    for (String path : shipPaths) {
        ImageIcon originalIcon = createImageIcon(path, "Player Ship Icon");

        // Scale the image while maintaining the aspect ratio
        ImageIcon scaledIcon = scaleImageIcon(originalIcon, 500, 100);

        JLabel shipLabel = new JLabel(scaledIcon);

        shipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        shipPanel.add(shipLabel);
        shipPanel.add(Box.createVerticalGlue());
    }

    shipPanel.add(Box.createVerticalGlue());

    return shipPanel;
}

// Function to scale ImageIcon while maintaining aspect ratio
private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
    java.awt.Image image = icon.getImage();
    java.awt.Image scaledImage = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
    return new ImageIcon(scaledImage);
}



    //Function that creates border for shipPanel
    Border createShipPanelBorder() {
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredBevel = BorderFactory.createLoweredBevelBorder();
        Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredBevel);
        return compound;
    }


// Function that creates and initializes ButtonPanel
JPanel initializeButtonPanel(int windowWidth) {
    JPanel buttonPanel = new JPanel();
    JButton hostButton = new JButton("Host Game");
    JButton joinButton = new JButton("Join Game");
    JButton randomizeShipButton = new JButton("Randomize Ship Placement");

    // Increase the font size for all buttons
    Font buttonFont = new Font("Arial", Font.BOLD, 24);
    hostButton.setFont(buttonFont);
    joinButton.setFont(buttonFont);
    randomizeShipButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

    // Set button alignment to center
    hostButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    joinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    randomizeShipButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
    buttonPanel.setBackground(Color.GRAY);
    buttonPanel.setPreferredSize(new Dimension(750, 1500));  // Use a fixed height or another suitable value

    // Add rigid area to center buttons vertically
    buttonPanel.add(Box.createVerticalGlue());
    
    // Add buttons to the panel
    buttonPanel.add(hostButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    buttonPanel.add(joinButton);

    
    // Add rigid area to center buttons vertically
    buttonPanel.add(Box.createVerticalGlue());

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


    return buttonPanel;
}


    //Function to randomize Ship placement for player
    protected void randomizeButtonMouseClick(MouseEvent e, PlayerGrid playerGrid) {
       for(int i = 0; i < playerGrid.ships.length; i++ ) {
        placeShipRandomly(playerGrid.ships[i], playerGrid);
       }
    }

    protected void placeShipRandomly(Ship ship, PlayerGrid playerGrid) {
        int size = ship.getSize();
        int row, col;
        boolean isHorizontal;

        // Find a random position and orientation that avoids overlapping with existing ships
        do {
            row = (int) (Math.random() * 10);
            col = (int) (Math.random() * 10);
            isHorizontal = Math.random() < 0.5;
        } while (!isLegalPlacement(row, col) || !isLegalPlacement(row, col, size, isHorizontal));

        // Place the ship incrementally in the array
        for (int i = 0; i < size; i++) {
            ImageIcon[] shipImgArray = getChoppedShipImgs(ship.getShipType());
            if (isHorizontal) {
                getTile(row, col + i).occupyTile(ship.getShipType());
                getTile(row, col + i).getTileButton().setIcon(shipImgArray[i]);
            } else {
                getTile(row + i, col).occupyTile(ship.getShipType());
                getTile(row + i, col).getTileButton().setIcon(shipImgArray[i]);
            }
        }

    }

    private boolean isLegalPlacement(int startRow, int startCol, int size, boolean isHorizontal) {
        if (isHorizontal) {
            for (int i = 0; i < size; i++) {
                if (startCol + i >= 10 || getTile(startRow, startCol + i).isOccupied()) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (startRow + i >= 10 || getTile(startRow + i, startCol).isOccupied()) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void joinButtonMouseClick(MouseEvent e) {
        JButton joinButton = (JButton) e.getSource();
        joinButton.setText("Enter IP Address:");
    
        // Get the parent panel of the joinButton
        Container buttonPanel = joinButton.getParent();
    
        // Create a JTextField for entering the IP address
        JTextField ipAddressField = new JTextField();
        ipAddressField.setMaximumSize(new Dimension(200, 30));
    
        // Create a JButton for submitting the entered IP address
        JButton submitButton = new JButton("Submit");
        submitButton.setMaximumSize(new Dimension(80, 30));
    
        // Set up a BoxLayout for the button panel
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    
        // Add the text field to the button panel
        buttonPanel.add(Box.createVerticalGlue()); // Add glue for centering
        buttonPanel.add(ipAddressField);
        buttonPanel.add(Box.createVerticalGlue()); // Add glue for centering
    
        // Add the submit button to the button panel
        buttonPanel.add(submitButton);
        buttonPanel.add(Box.createVerticalGlue()); // Add glue for centering
    
        // Disable other buttons in the button panel
        for (Component component : buttonPanel.getComponents()) {
            if (component != joinButton && component != ipAddressField && component != submitButton) {
                component.setVisible(false);
            }
        }
    
        // Disable the joinButton
        joinButton.setEnabled(false);
    
        // Add an ActionListener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the entered IP address using ipAddressField.getText()
                String ipAddress = ipAddressField.getText();
                // Perform actions related to joining the game with the specified IP address
    
                // For example, you might want to initiate a connection to the specified IP
                // and perform necessary actions based on the success of the connection.
    
                // After handling the connection, you can enable the joinButton and remove the text field and submit button:
                joinButton.setEnabled(true);
                ipAddressField.setVisible(false);
                submitButton.setVisible(false);
    
                // Enable other buttons in the button panel
                for (Component component : buttonPanel.getComponents()) {
                    if (component != joinButton && component != ipAddressField && component != submitButton) {
                        component.setVisible(true);
                    }
                }
            }
        });
    }
    
    
    

    protected void hostButtonMouseClick(MouseEvent e) {
        JButton hostButton = (JButton) e.getSource();
        hostButton.setText("Waiting for opponent...");
    
        // Get the parent panel of the hostButton
        Container buttonPanel = hostButton.getParent();
    
        // Iterate through the components of the panel
        for (Component component : buttonPanel.getComponents()) {
            // Check if the component is a button and not the hostButton itself
            if (component instanceof JButton && component != hostButton) {
                component.setVisible(false);
            }
        }
    
        Timer timer = new Timer(500, new ActionListener() {
            private int count = 0;
    
            @Override
            public void actionPerformed(ActionEvent e) {
                count = (count + 1) % 3;
                String dots = ".".repeat(count + 1);
                hostButton.setText("Waiting for opponent" + dots);
            }
        });
    
        timer.start();
        // Here you might want to perform any necessary actions related to hosting the game
        // For example, start a server or initiate the connection process.
    
        // To stop the timer and show the other buttons when needed (e.g., when the opponent is found), use:
        // timer.stop();
        // for (Component component : buttonPanel.getComponents()) {
        //     if (component instanceof JButton && component != hostButton) {
        //         component.setVisible(true);
        //     }
        // }
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

    protected void enemyButtonTileClicked(MouseEvent evt, PlayerGrid enemyGrid) {
        Object clickedTile = evt.getSource();
        for(int r = 0; r < GRIDSIZE; r++) {
            for(int c = 0; c < GRIDSIZE; c++) {
                if (enemyGrid.getTile(r, c).getTileButton() == evt.getSource()) {
                    attackTile(enemyGrid, r, c);
                }
            }
        }

    }

    public void attackTile(PlayerGrid targetGrid, int row, int col) {
        Tile targetTile = targetGrid.getTile(row, col);
        String hitTilePath = "images/hitTileImg.png";
        String missTilePath = "images/missTileImg.png";
        ImageIcon hitTile = createImageIcon(hitTilePath, "Player hit a ship on this tile");
        ImageIcon missTile = createImageIcon(missTilePath, "Player missed on this tile");

        if (targetTile.isShot()) {
            System.out.println("Already shot at this location!");
            return;
        }

        targetTile.shootTile();

        if (targetTile.isOccupied()) {
            System.out.println("Hit!");
            targetTile.getTileButton().setIcon(hitTile);
            return;
        } else {
            System.out.println("Miss!");
            targetTile.getTileButton().setIcon(missTile);
            return;
        }
    }

    protected ImageIcon[] getChoppedShipImgs(Ship.ShipType shiptype) {
        String[] destroyerImgPaths = {"/images/pDestroyerImg_01.png", "/images/pDestroyerImg_02.png"};
        String[] subImgPaths = {"/images/pSubImg_01.png", "/images/pSubImg_02.png", "/images/pSubImg_03.png"};
        String[] cruiserImgPaths = {"/images/pCruiserImg_01.png", "/images/pCruiserImg_02.png", "/images/pCruiserImg_03.png"};
        String[] battleshipImgPaths = {"/images/pBattleshipImg_01.png", "/images/pBattleshipImg_02.png", "/images/pBattleshipImg_03.png", "/images/pBattleshipImg_04.png"};
        String[] carrierImgPaths = {"/images/pCarriershipImg_01.png", "/images/pCarriershipImg_02.png", "/images/pCarriershipImg_03.png", "/images/pCarriershipImg_04.png", "/images/pCarriershipImg_05.png"};
    
        ImageIcon destroyerImg01 = createImageIcon(destroyerImgPaths[0], "Part 1 of 2 of Player Destroyer");
        ImageIcon destroyerImg02 = createImageIcon(destroyerImgPaths[1], "Part 2 of 2 of Player Destroyer");

        ImageIcon subImg01 = createImageIcon(subImgPaths[0], "Part 1 of 3 of Player Submarine");
        ImageIcon subImg02 = createImageIcon(subImgPaths[1], "Part 2 of 3 of Player Submarine");
        ImageIcon subImg03 = createImageIcon(subImgPaths[2], "Part 3 of 3 of Player Submarine");

        ImageIcon cruiserImg01 = createImageIcon(cruiserImgPaths[0], "Part 1 of 3 of Player Cruiser");
        ImageIcon cruiserImg02 = createImageIcon(cruiserImgPaths[1], "Part 2 of 3 of Player Cruiser");
        ImageIcon cruiserImg03 = createImageIcon(cruiserImgPaths[2], "Part 3 of 3 of Player Cruiser");

        ImageIcon battleshipImg01 = createImageIcon(battleshipImgPaths[0], "Part 1 of 4 of Player Battleship");
        ImageIcon battleshipImg02 = createImageIcon(battleshipImgPaths[1], "Part 2 of 4 of Player Battleship");
        ImageIcon battleshipImg03 = createImageIcon(battleshipImgPaths[2], "Part 3 of 4 of Player Battleship");
        ImageIcon battleshipImg04 = createImageIcon(battleshipImgPaths[3], "Part 4 of 4 of Player Battleship");

        ImageIcon carrierImg01 = createImageIcon(carrierImgPaths[0], "Part 1 of 5 of Player Carrier");
        ImageIcon carrierImg02 = createImageIcon(carrierImgPaths[1], "Part 2 of 5 of Player Carrier");
        ImageIcon carrierImg03 = createImageIcon(carrierImgPaths[2], "Part 3 of 5 of Player Carrier");
        ImageIcon carrierImg04 = createImageIcon(carrierImgPaths[3], "Part 4 of 5 of Player Carrier");
        ImageIcon carrierImg05 = createImageIcon(carrierImgPaths[4], "Part 5 of 5 of Player Carrier");

        ImageIcon[] destroyerImgArray = {destroyerImg01, destroyerImg02};
        ImageIcon[] subImgArray = {subImg01, subImg02, subImg03};
        ImageIcon[] cruiserImgArray = {cruiserImg01, cruiserImg02, cruiserImg03};
        ImageIcon[] battleshipImgArray = {battleshipImg01, battleshipImg02, battleshipImg03, battleshipImg04};
        ImageIcon[] carrierImgArray = {carrierImg01, carrierImg02, carrierImg03, carrierImg04, carrierImg05};
        if (shiptype == Ship.ShipType.DESTROYER)
            return destroyerImgArray;
        else if (shiptype == Ship.ShipType.SUBMARINE)
            return subImgArray;
        else if (shiptype == Ship.ShipType.CRUISER)
            return cruiserImgArray;
        else if (shiptype == Ship.ShipType.BATTLESHIP)
            return battleshipImgArray;
        else if (shiptype == Ship.ShipType.CARRIER)
            return carrierImgArray;
    }


    public static void main(String[] args) {
        BattleshipView game = new BattleshipView();
    }
}