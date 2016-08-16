import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This cute little orange alien tries. He tried to be just like the big
 * bad red alien, but he just isn't strong enough. It takes 2 shots to kill this
 * alien, and he only does 3 damage.
 */
public class Orange extends GameObj implements Aliens {

	 public static final String img_file = "orange2.png";
	 private BufferedImage img;
	 private int damage = -3;
	 private int score = 3;
	 private int health = 2;

	public Orange(int xpos, int ypos, int courtWidth, int courtHeight) {
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

	/* draws the orange alien and his health bar*/
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, pos_x, pos_y, width, width, null);
		g.setColor(Color.GREEN);
		g.fillRect(pos_x, pos_y, SIZE/2 * health, 4);
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
