import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Image {
    File file;
    ImageIcon image;
    BufferedImage bufferedImage;
    ImageIcon sunkImage;

    public ImageIcon getShipImage() {
        return image;
    }

    public ImageIcon getSunkImage() {
        return sunkImage;
    }


    class DestroyerImage extends Image {
        DestroyerImage() {
        super();
        //Alive(Default) Image
        file = new File(null);  //Put path to destroyer default img here
        bufferedImage = ImageIO.read(file);
        image = new ImageIcon(bufferedImage);

        //Sunk Image
        File sunkFile = new File(null); //Put path to destroyer sunk img here
        BufferedImage bufferedSunkImage = ImageIO.read(sunkFile);
        ImageIcon sunkImage = new ImageIcon(bufferedSunkImage);

        }

    }

    class SubmarineImage extends Image {
        SubmarineImage() {
        super();
        //Alive(Default) Image
        file = new File(null);  //Put path to sub default img here
        bufferedImage = ImageIO.read(file);
        image = new ImageIcon(bufferedImage);

        //Sunk Image
        File sunkFile = new File(null); //Put path to sub sunk img here
        BufferedImage bufferedSunkImage = ImageIO.read(sunkFile);
        ImageIcon sunkImage = new ImageIcon(bufferedSunkImage);

        }

    }

    class CruiserImage extends Image {
        CruiserImage() {
        super();
        //Alive(Default) Image
        file = new File(null);  //Put path to cruiser default img here
        bufferedImage = ImageIO.read(file);
        image = new ImageIcon(bufferedImage);

        //Sunk Image
        File sunkFile = new File(null); //Put path to cruiser sunk img here
        BufferedImage bufferedSunkImage = ImageIO.read(sunkFile);
        ImageIcon sunkImage = new ImageIcon(bufferedSunkImage);
        }

    }

    class BattleshipImage extends Image {
        BattleshipImage() {
        super();
        //Alive(Default) Image
        file = new File(null);  //Put path to sub default img here
        bufferedImage = ImageIO.read(file);
        image = new ImageIcon(bufferedImage);

        //Sunk Image
        File sunkFile = new File(null); //Put path to sub sunk img here
        BufferedImage bufferedSunkImage = ImageIO.read(sunkFile);
        ImageIcon sunkImage = new ImageIcon(bufferedSunkImage);
        }
    }

    class CarrierImage extends Image {
        CarrierImage() {
        super();
        //Alive(Default) Image
        file = new File(null);  //Put path to sub default img here
        bufferedImage = ImageIO.read(file);
        image = new ImageIcon(bufferedImage);

        //Sunk Image
        File sunkFile = new File(null); //Put path to sub sunk img here
        BufferedImage bufferedSunkImage = ImageIO.read(sunkFile);
        ImageIcon sunkImage = new ImageIcon(bufferedSunkImage);
        }
    }

    	public static void main(String[] args) throws IOException {

		 
        File file = new File("C:\\Users\\Sophie Wang\\stamp-removebg-preview.png");
        BufferedImage bufferedImage = ImageIO.read(file);

        ImageIcon imageIcon = new ImageIcon(bufferedImage);
        
    }
}
