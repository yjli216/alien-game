import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
* Creates a class for the Boulder Star object. The Boulder Star shoots out of the space
* ship to attack incoming aliens. 
*/
public class Boulder extends GameObj {
	public static final String img_file = "star.png";
	public static final int SIZE = 20;
	public static final double ACCELERATION = 1;
	public static BufferedImage img;

	public Boulder(int xpos, int ypos, int xvel, int yvel, int courtWidth, int courtHeight) {
		super(xvel, yvel, xpos, ypos, SIZE, SIZE, courtWidth,
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

	@Override
	/**
	 * Moves the boulder by its velocity.  Ensures that the object does
	 * not go outside its bounds by clipping.
	 */
	public void move() {
		v_y += ACCELERATION; //change y velocity by acceleration
		pos_x += v_x;
		pos_y += v_y;
		clip();
	}


	/**
	 * Override the normal object clipping method, in this case, if the star
	 * goes above the stop of the screen, it'll come back down if it's within the screen
	 */ 
	@Override
	public void clip(){
		pos_x = Math.min(max_x, Math.max(0, pos_x));
		pos_y = Math.min(max_y, pos_y);
	}

	public int getY() {
		return pos_y;
	}

	public int getX() {
		return pos_x;
	}
}
