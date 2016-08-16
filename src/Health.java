/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Sometimes, when an alien gets dumped, he drops his heart in the vast galaxy. These hearts are 
 * hard to come by, but when collected, gives one a health boost of 3! If shot, however, the
 * universe will recognise the heart breakers and deduct 5 points from them.
 */
public class Health extends GameObj implements Aliens {
	 public static final String img_file = "health.png";
	 private BufferedImage img;
	 private int damage = 3;
	 private int score = -5;
	 private int health = 1;

	public Health(int xpos, int ypos, int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, xpos, ypos, SIZE, SIZE, courtWidth,
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
		g.drawImage(img, pos_x, pos_y, width, width, null);
	}

	public int getX() {
		return pos_x;
	}

	public int getY() {
		return pos_y;
	}
	public int getDamage() {
		return this.damage;
	}
	public boolean isDead() {
		return (health == 0);
	}
	public void hit() {
		health--;
	}
	public int getScore() {
		return this.score;
	}
	public BufferedImage getImg() {
		return this.img;
	}
}
