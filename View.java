import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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
        
        gameWindow.add(mainScreen);
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
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        return shipPanel;
    }

    protected void enemyButtonTileClicked(MouseEvent evt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enemyButtonTileClicked'");
    }

    public static void main(String[] args) {
        View game = new View();
    }
}

