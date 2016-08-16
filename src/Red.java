import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This is the scary red alien! He might look cute and cuddly, but cuddle with him
 * and you will lose 10 healths! It takes 3 shots to kill the red alien.
 */
public class Red extends GameObj implements Aliens {

	 public static final String img_file = "alien.png";
	 private int health = 3;
	 private int score = 5;

	 private BufferedImage img;
	 private int damage = -10;
	 public static final int SCORE = 3;

	public Red(int xpos, int ypos, int courtWidth, int courtHeight) {
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

	/* Draws the alien and his health bar*/
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, pos_x, pos_y, width, width, null);
		g.setColor(Color.GREEN);
		g.fillRect(pos_x, pos_y, SIZE/3 * health, 4);
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
