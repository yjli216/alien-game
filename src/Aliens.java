import java.awt.*;
import java.awt.image.BufferedImage;

public interface Aliens {
	public static final int INIT_VEL_X = -2;
	public static final int INIT_VEL_Y = 0;
	public static final int SIZE = 100;
	//draws the Alien
	void draw(Graphics g);
	//get the X coordinate of the upper left corner
	int getX();
	//get the Y coordinate of the upper left corner
	int getY();
	//updates the position of the alien based on its velocity
	void move();
	//gets damage done by alien
	int getDamage();
	//returns if the specific alien is dead
	boolean isDead();
	//decrements the health of the alien when it's hit
	void hit();
	//gets the score of the alien
	int getScore();
	//get image
	BufferedImage getImg();
}