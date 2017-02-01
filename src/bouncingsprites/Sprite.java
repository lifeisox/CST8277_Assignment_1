/* File Name: Sprite.java
 * Author Name: Algonquin College
 * Modified By: Byung Seon Kim
 * Date: 9 January 2017
 * Description: Draw a sprite as circle shape on SpritePanel and move the sprite
 */

package bouncingsprites;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * Draw a sprite as circle shape on SpritePanel and move the sprite
 * @author Byung Seon Kim
 */
public class Sprite implements Runnable {
	/** the object to generate random number */
	private final static Random random = new Random();
	/** the size of the sprite */
	private final static int RADIUS = 5;
	/** distance of movement in defined unit time */
	private final static int MAX_SPEED = 5;
	/** SpritePanel object */
	SpritePanel panel;
	/** x axis of the left corner of the sprite */
	private int x;	
	/** y axis of the upper corner of the sprite */
	private int y; 
	/** x movement of the sprite */
	private int dx;
	/** y movement of the sprite */
	private int dy; 
	/** color of the sprite */
	private Color color = Color.BLUE;
	/** represents whether sprite is inside of the circle or not */
	private boolean in;

	/**
	 * Constructor for initializing instance variables 
	 * @param panel SpritePanel object
	 */
    public Sprite (SpritePanel panel) {
    	this.panel = panel;
        x = random.nextInt( panel.getWidth() - RADIUS );
        y = random.nextInt( panel.getHeight() - RADIUS );
        // I don't want horizontal migration or vertical migration, so I changed algorithm for I get a number without zero.
        dx = (random.nextInt( MAX_SPEED ) + 1) * (random.nextInt( 2 ) == 1 ? 1 : -1); // dx should be -5, -4, -3, -2, -1, 1, 2, 3, 4, 5 not 0
        dy = (random.nextInt( MAX_SPEED ) + 1) * (random.nextInt( 2 ) == 1 ? 1 : -1); // dx should be -5, -4, -3, -2, -1, 1, 2, 3, 4, 5 not 0
        // Create all sprites with an initial status of being outside the circle, even if the new sprite is
        // physically inside the circle.
        in = false;
    }

    /**
     * Draw a sprite 
     * @param g the Graphics object to protect
     */
    public void draw(Graphics g){
        g.setColor(color);
	    g.fillOval(x, y, RADIUS*2, RADIUS*2);
    }
    
    /**
     * Move the sprite. If the sprite meet the boundaries of SpritePanel, change direction
     */
    public void move(){
        // check for bounce and make the ball bounce if necessary
    	// bounce off the left wall
        if (x < 0 && dx < 0) {  
            x = 0;
            dx = -dx;
        }
        //bounce off the top wall
        if (y < 0 && dy < 0){
            y = 0;
            dy = -dy;
        }
        // bounce off the right wall
        if (x > panel.getWidth() - (RADIUS * 2) && dx > 0){
            //bounce off the right wall
        	x = panel.getWidth() - RADIUS * 2;
        	dx = - dx;
        }   
        // bounce off the bottom wall
        if (y > panel.getHeight() - (RADIUS * 2) && dy > 0){
            //bounce off the bottom wall
        	y = panel.getHeight() - RADIUS * 2;
        	dy = -dy;
        }

        //make the ball move
        x += dx;
        y += dy;
    }

    /**
     * The collisionDection() returns the result of collision detection between sprite and the circle
     * [1] Darran Jamieson. (2012 Sep 27). When Worlds Collide: Simulating Circle-Circle Collisions [Online]. 
     *     Available: https://gamedevelopment.tutsplus.com/tutorials/when-worlds-collide-simulating-circle-circle-collisions--gamedev-769
     * @return the result of collision detection
     */
    private boolean collisionDetection() {
    	int center_x = x + RADIUS;
    	int center_y = y + RADIUS;
    	// Collision detection between circle and circle
    	double distance = Math.sqrt((( SpritePanel.CIRCLE_X - center_x ) * ( SpritePanel.CIRCLE_X - center_x )) + 
    			(( SpritePanel.CIRCLE_Y - center_y ) * ( SpritePanel.CIRCLE_Y - center_y )));
    	if ( distance < ( SpritePanel.CIRCLE_RADIUS + RADIUS ) ) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    /**
     * Move the sprite appropriately
     */
    @Override
    public void run() {
    	boolean curStatus;
    	
    	while (true){
    		move();  
    		curStatus = collisionDetection(); // If there is collision detection, curStatus should be true
    		if ( in != curStatus ) {
    			try {
    				if (in) panel.consume(); // leaving
        			else panel.produce(); // entering
    				in = curStatus;
    			} catch (InterruptedException exception) {
    				exception.printStackTrace();
    			}
    		}
    		try {
    			Thread.sleep(panel.getDelay());  // wake up
    		}
    		catch ( InterruptedException exception ) {
    			exception.printStackTrace();
    		}
    	}
    }
}
