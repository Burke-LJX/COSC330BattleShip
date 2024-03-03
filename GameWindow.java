import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.border.Border;

//import Drag.java;

public class GameWindow {
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
        //textLabel.setVerticalAlignment();   //Set 1/3 way down. specific px amounts?

        //Sets up Buttons
        hostGameButton.setText("Host Game");
        hostGameButton.setHorizontalTextPosition(SwingConstants.CENTER);
        joinGameButton.setText("Join Game");
        joinGameButton.setHorizontalTextPosition(SwingConstants.CENTER);

        return titleScreen;
    }


}
