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
        gameWindow.setLocation(0, 0);
        gameWindow.setSize(1500, 1000);
        createAndShowMainScreen();

        gameWindow.setVisible(true);
    }

    public void createAndShowMainScreen() {
        JPanel mainScreen = new JPanel(new BorderLayout());
        JPanel playerGridPanel;
        JPanel playerShipPanel;
        JPanel enemyGridPanel;
        JPanel enemyShipPanel;
        Grid gameboard = new Grid();
        Grid eboard = new Grid();
    
        mainScreen.setBackground(Color.DARK_GRAY);
        Box topBox = new Box(BoxLayout.LINE_AXIS);
        Box centerBox = new Box(BoxLayout.LINE_AXIS);
        Box bottomBox = new Box(BoxLayout.PAGE_AXIS);
    
        mainScreen.add(topBox, BorderLayout.NORTH);
    
        // Create player panel
        Box playerBox = new Box(BoxLayout.PAGE_AXIS);
        playerGridPanel = initializePlayerGridPanel(gameboard);
        playerShipPanel = initializePlayerShipPanel();
        playerBox.add(playerGridPanel);
        playerBox.add(playerShipPanel);
    
        // Create center panel with buttonPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));  // Set the layout to PAGE_AXIS
        JPanel buttonPanel = initializeButtonPanel(mainScreen.getHeight());  // Use mainScreen.getHeight()
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalGlue());
    
        // Create right panel with enemyGridPanel and enemyShipPanel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        enemyShipPanel = initializeEnemyDisplay();
        enemyGridPanel = initializeEnemyGridPanel(eboard);
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
    
    // Function to initialize enemy display
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


// Function to initialize player ship panel
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
    buttonPanel.setBackground(Color.DARK_GRAY);
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

    protected void enemyButtonTileClicked(MouseEvent evt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enemyButtonTileClicked'");
    }

    public static void main(String[] args) {
        BattleshipView game = new BattleshipView();
    }
}