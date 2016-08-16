import java.awt.Graphics;
import java.awt.image.BufferedImage;

/** An object in the game. 
 *
 *  Game objects exist in the game court. They have a position, 
 *  velocity, size and bounds. Their velocity controls how they 
 *  move; their position should always be within their bounds.
 */
public class GameObj {

	/** Current position of the object (in terms of graphics coordinates)
	 *  
	 * Coordinates are given by the upper-left hand corner of the object.
	 * This position should always be within bounds.
	 *  0 <= pos_x <= max_x 
	 *  0 <= pos_y <= max_y 
	 */
	public int pos_x; 
	public int pos_y;

	/** Size of object, in pixels */
	public int width;
	public int height;
	
	/** Velocity: number of pixels to move every time move() is called */
	public int v_x;
	public int v_y;

	public BufferedImage img;

	/** Upper bounds of the area in which the object can be positioned.  
	 *    Maximum permissible x, y positions for the upper-left 
	 *    hand corner of the object
	 */
	public int max_x;
	public int max_y;

	/**
	 * Constructor
	 */
	public GameObj(int v_x, int v_y, int pos_x, int pos_y, 
		int width, int height, int court_width, int court_height){
		this.v_x = v_x;
		this.v_y = v_y;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.width = width;
		this.height = height;
		// take the width and height into account when setting the 
		// bounds for the upper left corner of the object.
		this.max_x = court_width - width;
		this.max_y = court_height - height;
	}


	/**
	 * Moves the object by its velocity.  Ensures that the object does
	 * not go outside its bounds by clipping.
	 */
	public void move(){
		pos_x += v_x;
		pos_y += v_y;
		clip();
	}

	/**
	 * Prevents the object from going outside of the bounds of the area 
	 * designated for the object. (i.e. Object cannot go outside of the 
	 * active area the user defines for it).
	 */ 
	public void clip(){
		pos_y = Math.min(max_y, Math.max(0, pos_y));
		pos_x = Math.min(max_x, Math.max(0, pos_x));
	}
	/**
	 * Determines if this game object collides with another
	 * 
	 * Firstly, a bounding box method is used to check if they are within a close
	 * range of each other. Afterwards, the alpha value of the images are
	 * checked to see if the opaque parts of the objects collide.
	 * 
	 * @param obj : other object
	 * @param bi  : image of the other object
	 * @return whether this object intersects the other object.
	 */
	public boolean collides(Aliens a, BufferedImage bi) {	

		//get the coordinates of corners of the rectangle (alien):
		int x_a = a.getX();
		int y_a = a.getY();
		int x_b = x_a + Aliens.SIZE;
		int y_b = y_a + Aliens.SIZE;
		//get coordinates of corners of current object
		int x_1 = pos_x;
		int y_1 = pos_y;
		int x_2 = x_1 + width;
		int y_2 = y_1 + height;
		//get the coordinates of the area of intersection
		int xl = Math.max(x_a, x_1);
		int xr = Math.min(x_b, x_2);
		int yt = Math.max(y_1, y_a);
		int yb = Math.min(y_2, y_b);
    	if (yt < yb && xl < xr) {
    		//check if the area of intersection actually exists and is valid
    		BufferedImage alien = a.getImg();
    		//iterating through all the common pixels
    		for (int i = xl; i < xr; i++) {
    			for (int j = yt; j < yb; j++) {
    				//get the position relative to the images
    				int al = alien.getRGB(i - x_a, j - y_a); //get RGB
    				int b = bi.getRGB(i - x_1, j - y_1); //get RGB
    				//check if they're transparent
					if (((al>>24) != 0) && ((b>>24) != 0)) {
						return true;
					}
    			}
    		}
    	}
    	return false; 
	}
	/**
	 * Default draw method that provides how the object should be drawn 
	 * in the GUI. This method does not draw anything. Subclass should 
	 * override this method based on how their object should appear.
	 * 
	 * @param g 
	 *	The <code>Graphics</code> context used for drawing the object.
	 * 	Remember graphics contexts that we used in OCaml, it gives the 
	 *  context in which the object should be drawn (a canvas, a frame, 
	 *  etc.)
	 */
	public void draw(Graphics g) {
	}
	
}
