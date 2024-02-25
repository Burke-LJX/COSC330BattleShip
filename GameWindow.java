import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.BoxLayout;

public class GameWindow {
    private final int GRIDSIZE = 10;
    JFrame gameWindow = new JFrame("Battleship");
    JPanel titleScreen = new JPanel();
    JPanel mainScreen;

    GameWindow() {
        //Set game frame attributes
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLocationRelativeTo(null);
        //gameWindow.setLayout(new BorderLayout());

        titleScreen = callTitleScreen();
        gameWindow.setContentPane(titleScreen);
    }


    //Function that sets up and displays the title screen
    JPanel callTitleScreen() {
        //Declarations and Initializations:
        JPanel titleScreen = new JPanel(new BoxLayout(this.titleScreen, BoxLayout.PAGE_AXIS));
        JLabel textLabel = new JLabel();
        JButton hostGameButton = new JButton();
        JButton joinGameButton = new JButton();
        //JButton creditButton = new JButton();

        //Sets up background color of title screen
        titleScreen.setBackground(Color.BLUE);  //Set value to dark blue or dark green

        //Sets up Title Text
        textLabel.setForeground(Color.GREEN);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);   //Set middle of screen
        textLabel.setVerticalAlignment();   //Set 1/3 way down. specific px amounts?

        //Sets up Buttons
        hostGameButton.setText("Host Game");
        hostGameButton.setHorizontalTextPosition(SwingConstants.CENTER);
        joinGameButton.setText("Join Game");
        joinGameButton.setHorizontalTextPosition(SwingConstants.CENTER);

        return titleScreen;
    }

    //Function to set up main gameplay screen
    JPanel createMainScreen() {
        //Declare and initialize mainScreen
        JPanel playerScreen = new JPanel(new BorderLayout());
        //Declare and initialize JPanels for the grid and ship register
        JPanel gridPanel = new JPanel(new GridLayout());
        JPanel shipPanel = new JPanel(new GridBagLayout());

        //GridPanel section. Holds the grid display for the game
        SelfGrid playerGrid = new SelfGrid();
        //Add playerGrid to gridPanel or someway to display grid. Fix this!
        for(int row = 0; row < GRIDSIZE; row++) {
            for(int column = 0; column < GRIDSIZE; column++) {
                gridPanel.add(playerGrid.getTile(row, column));
            }
        }
        playerScreen.add(gridPanel, SwingConstants.LEFT);

        //ShipPanel section. Holds ship display for the game

        playerScreen.add(shipPanel, SwingConstants.RIGHT);



        return mainScreen;
    }
    /*Create actionListener functions
     * one for clicking grid
     * one for draggin ships
    */
}
