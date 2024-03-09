import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.BoxLayout;
import MainScreen;

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
        titleScreen.setBackground(Color.BLACK);  //Set value to dark blue or dark green

        //Sets up Title Text
        textLabel.setForeground(Color.GREEN);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);   //Set middle of screen
        textLabel.setVerticalAlignment(SwingConstants.CENTER);
        //textLabel.setFont(new Font("Arial", , 32));
        textLabel.setText("Battleship");

        //Sets up Buttons
        hostGameButton.setText("Host Game");
        hostGameButton.setHorizontalTextPosition(SwingConstants.CENTER);
        hostGameButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                hostGameButtonClick(e);
            }
        });
        joinGameButton.setText("Join Game");
        joinGameButton.setHorizontalTextPosition(SwingConstants.CENTER);
        joinGameButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                joinGameButtonClick(e);
            }
        });

        return titleScreen;
    }

    protected void joinGameButtonClick(MouseEvent e) {
        // Call method to join game (client side)
        String ipAddress;
        throw new UnsupportedOperationException("Unimplemented method 'joinGameButtonClick'");
    }

    protected void hostGameButtonClick(MouseEvent e) {
        // call method to create server and find client
        throw new UnsupportedOperationException("Unimplemented method 'hostGameButtonClick'");
    }


}
