import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;

/**
 * GameCourt
 * 
 * Contains the primary logic for how objects interact. Also updates the state of the court
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	private int xposition; //track the xposition of the first row of aliens
	private int score = 0;
	private Spaceship spaceship; // the spaceship, moves up and down
	private Aliens[][] aliens = new Aliens[4][3]; //an array of aliens
	private List <Boulder> boulders;
	public boolean playing = false; // whether the game is running
	private JLabel status; // Current status text (i.e. Running...)

	// Game constants
	public static final int COURT_WIDTH = 900;
	public static final int COURT_HEIGHT = 500;
	public static final int SPACESHIP_VELOCITY = 4;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;

	public GameCourt(JLabel status) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// This key listener allows the spaceship to move as long
		// as an arrow key is pressed, by changing the spaceship's
		// velocity accordingly. (The tick method below actually
		// moves the spaceship.)
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_S)
					spaceship.v_y = SPACESHIP_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_W)
					spaceship.v_y = -SPACESHIP_VELOCITY;
			}
			public void keyReleased(KeyEvent e) {
				spaceship.v_x = 0;
				spaceship.v_y = 0;
			}
		});
		// MouseListener check if someone clicks the spaceship. If so,
		// then create a new boulder star at position of click, with
		// velocity dependent on how far back the mouse is dragged
		addMouseListener(new MouseAdapter() {
				int mp_x;
				int mp_y;
				int mr_x;
				int mr_y;
			public void mousePressed(MouseEvent e) {
				mp_x = e.getX();
				mp_y = e.getY();
			}
			public void mouseReleased(MouseEvent e) {
				mr_x = e.getX();
				mr_y = e.getY();
				shootBoulder();
			}
			// creates a Boulder Star and shoots it
			public void shootBoulder() {
				if (spaceship.withinRocket(mp_x, mp_y)) {
					int xvel = mp_x - mr_x;
					int yvel = mp_y - mr_y;
					xvel = xvel/7;
					yvel = yvel/10;
					createBoulder(mp_x, mp_y, xvel, yvel);
				}
			}
		});
		this.status = status;
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
		spaceship = new Spaceship(COURT_WIDTH, COURT_HEIGHT);
		boulders = new LinkedList<Boulder>();
		createAliens();
		score = 0;
		playing = true;
		status.setText("Running...");
		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	// This method generates random y coordinates for the alien to ensures that 
	// they don't overlap.
	private int[] generateYPos() {
		int[] pos = new int[3];
		int[] nums = new int[5]; //numbers for alien to choose from
		for (int i = 0; i < nums.length; i++) {
			nums[i] = i * 100 + 1;
		}
		for (int i = 0; i < 3; i++) {
			int temp = (int) (Math.random() * 5); //random select one of the blocks 
			while (nums[temp] == 0) {
				temp = (int) (Math.random() * 5); // select another number if previously chosen
			}
			pos[i] = nums[temp]; //update x position
			nums[temp] = 0; //set temp as 0
		}
		return pos;
	}
	//create all the Aliens
	private void createAliens() {
		aliens = new Aliens[4][3];
		for (int i = 0; i < 4; i++) {
			int xpos = i * 200 + 200;
			int ypos[] = generateYPos();
			for (int j = 0; j < 3; j++) {
				double rand = Math.random();
				//randomly generates aliens
				if (rand < 0.05) {
					aliens[i][j] = new Health(xpos, ypos[j], COURT_WIDTH, COURT_HEIGHT);
				} else if (rand < 0.4) {
					aliens[i][j] = new Red(xpos, ypos[j], COURT_WIDTH, COURT_HEIGHT);
				} else {
					aliens[i][j] = new Orange(xpos, ypos[j], COURT_WIDTH, COURT_HEIGHT);
				}
			}
			this.xposition = 200; //set as 150
		}
	}

	//called to move the Aliens
	private void moveAliens() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				if (aliens[i][j] != null)
					aliens[i][j].move();
			}
		}
		xposition+=-2;
	}

	private void updateAliens() {
		//update Aliens if necessary
		if (this.xposition == 0) {
			//if the first row of aliens reached the end
			//copy all the aliens forward by one row
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					aliens[i][j] = aliens[i + 1][j];
				}
			}
			//create new wave of aliens
			aliens[3] = new Aliens[3];
			int xpos = 800;
			int ypos[] = generateYPos();
			for (int j = 0; j < 3; j++) {
				double rand = Math.random();
				if (rand < 0.05) {
					aliens[3][j] = new Health(xpos, ypos[j], COURT_WIDTH, COURT_HEIGHT);
				} else if (rand < 0.4) {
					aliens[3][j] = new Red(xpos, ypos[j], COURT_WIDTH, COURT_HEIGHT);
				} else {
					aliens[3][j] = new Orange(xpos, ypos[j], COURT_WIDTH, COURT_HEIGHT);
				}
			}
			this.xposition = 200;
			score++;
		}
	}

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {
			// advance all the respective pieces in their positions
			// remove anything that needs to be removed
			spaceship.move();
			moveAliens();
			moveBoulders();
			updateAliens();
			//check if spaceship collides with the aliens
			for (int j = 0; j < 3; j++) {
				if (aliens[0][j] != null) {
					if (spaceship.collides(aliens[0][j], aliens[0][j].getImg())) {
						spaceship.changeHealth(aliens[0][j].getDamage());
						aliens[0][j] = null;
					}
				}
			}
			//check if boulder hits the aliens
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 3; j++) {
					Iterator<Boulder> iterator = boulders.iterator();
					while (iterator.hasNext()) {
						Boulder b = iterator.next();
						if (aliens[i][j] != null) {
							if (b.collides(aliens[i][j], aliens[i][j].getImg())) {
								aliens[i][j].hit();
								iterator.remove();
								if (aliens[i][j].isDead()) {
									score += aliens[i][j].getScore();
									aliens[i][j] = null;
								}
							}
						}
					}
				}
			}
			if (spaceship.getHealth() <= 0) {
				playing = false;
				status.setText("You lose!");
			}
			// update the display
			repaint();
		}
	}
	//draws the aliens individually
	private void drawAliens(Graphics g) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				if (aliens[i][j] != null)
					aliens[i][j].draw(g);
			}
		}
	}
	//update score
	private void updateScore(Graphics g) {
		g.setColor(Color.BLACK);
		Font f = new Font("Calibri", Font.PLAIN, 24);
		g.setFont(f);
		String s = "Score: " + score + "";
		g.drawString(s, 700, 30);
	}
	//update health
	private void updateHealth(Graphics g) {
		g.setColor(Color.BLACK);
		Font f = new Font("Calibri", Font.PLAIN, 24);
		g.setFont(f);
		String s = "Health: " + spaceship.getHealth();
		g.drawString(s, 700, 50);
	}
	// create a new boulder when one is shot
	private void createBoulder(int mp_x, int mp_y, int xvel, int yvel) {
		Boulder b = new Boulder(mp_x, mp_y, xvel, yvel, COURT_WIDTH, COURT_HEIGHT);
		boulders.add(b);
	}
	//draws all the boulders on the screen
	private void drawBoulders(Graphics g) {
		Iterator<Boulder> i = boulders.iterator();
		while (i.hasNext()) {
			Boulder b = i.next();
			b.draw(g);
		}
	}
	//move the boulders in their respective path and removes any if they go off the screen
	private void moveBoulders() {
		Iterator<Boulder> i = boulders.iterator();
		while (i.hasNext()) {
			Boulder b = i.next();
			b.move();
			if (b.getY() >= COURT_HEIGHT - Boulder.SIZE || 
				b.getX() >= COURT_WIDTH - Boulder.SIZE ||
				b.getX() == 0) {
				i.remove();
			}
		}
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		spaceship.draw(g);
		drawAliens(g);
		drawBoulders(g);
		updateScore(g);
		updateHealth(g);
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
