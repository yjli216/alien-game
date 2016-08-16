import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A spaceship is a GameObj that can only move up or down. It has the ability
 * to shoot little Boulder Stars at alien monsters. It has a health component of a 100.
 */
public class Spaceship extends GameObj {

	public static final String img_file = "spaceship.png";
	public static final int SIZE = 40;
	public static final int INIT_X = 20;
	public static final int INIT_Y = 250;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	private int health = 100;
	public static BufferedImage img;

	public Spaceship(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, SIZE, SIZE, courtWidth,
				courtHeight);
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, pos_x, pos_y, width, height, null);
	}
	public int getHealth() {
		return this.health;
	}
	//modifies the health of the spaceship if it hits an alien
	public void changeHealth(int x) {
		health = health + x;
	}
	/*
	* checks if click is within the Radius of the rocket. Click is within the rocket 
	* if the distance between the click and the center of the circle is shorter than 
	* the radii of the circle.
	* @param: coordinates of click
	* @return: whether the click is within the rocket
	*/
	public boolean withinRocket(int x, int y) {
		double distx = this.pos_x + SIZE/2;
		double disty = this.pos_y + SIZE/2;
		distx = Math.pow(x - distx, 2);
		disty = Math.pow(y - disty, 2);
		double radius = Math.pow(SIZE/2, 2);
		return (radius > distx + disty);
	}
}
