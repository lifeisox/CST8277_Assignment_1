/* File Name: SpritePanel.java
 * Author Name: Algonquin College
 * Modified By: Byung Seon Kim
 * Date: 9 January 2017
 * Description: The class conducts JPanel to implement multithreaded sprite animation
 * and a solution to the Producer/Consumer problem
 */

package bouncingsprites;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * The class conducts JPanel to implement multithreaded sprite animation 
 * and a solution to the Producer/Consumer problem
 * @author Byung Seon Kim
 *
 */
@SuppressWarnings("serial")
public class SpritePanel extends JPanel {
	/** radius of the circle for collision detection */
	public static final int CIRCLE_RADIUS = 150;
	/** center's x axis of the circle */
	public static final int CIRCLE_X = 242;
	/** center's y axis of the circle */
	public static final int CIRCLE_Y = 230;
	/** the maximum number of sprite that can be moving within the boundaries of the circle at a time */
	private static final int MAX_SPRITES = 4;
	/** the minimum number of sprite that must be moving within the boundaries of the circle at a time */
	private static final int MIN_SPRITES = 2;
	/** ArrayList object to save created Sprite object */
	private ArrayList<Sprite> sprites;
	/** ArrayList object to save created Thread object for the preparation of the other thread function  */
	private ArrayList<Thread> threads;
	/** the number of sprite into the circle */
	private int numOfSprites;
	/** delay time (millisecond) */
	private int delay = 40; 
	
	/**
	 * Default constructor of SpritePanel class 
	 */
	public SpritePanel() {
		addMouseListener(new Mouse()); // Adds the specified mouse listener to receive mouse events from this component.
		addKeyListener(new Keyboard());
		setFocusable(true);
        setFocusTraversalKeysEnabled(false);
		sprites = new ArrayList<>();
		threads = new ArrayList<>();
	}
	
	/**
	 * The consume() method implements the process for when sprite is exiting the circle.
	 * 
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, 
	 * and the thread is interrupted, either before or during the activity.
	 */
	public synchronized void consume() throws InterruptedException {
		// if the number of sprite within the circle is less than equal MIN_SPRITES, a moving sprite exiting the circle will freeze
		while (numOfSprites <= MIN_SPRITES) { wait(); }
		// now, a moving sprite exiting the circle can move
		--numOfSprites;
		notifyAll(); // tell waiting thread to enter runnable state
	}	
	
	/**
	 * The produce() method implements the process for when sprite is entering the circle.
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied, 
	 * and the thread is interrupted, either before or during the activity.
	 */
	public synchronized void produce() throws InterruptedException {
		// if the number of sprite within the circle is maximum, a moving sprite entering the circle will freeze
		while (numOfSprites >= MAX_SPRITES) { wait(); }
		// now, a moving sprite entering the circle can enter
		++numOfSprites;
		notifyAll(); // tell waiting thread to enter runnable state
	}
	
	/**
	 * Create new thread Sprite object 
	 * @param event An event which indicates that a mouse action occurred in a component.
	 */
	private void newSprite (MouseEvent event){
		Sprite sprite = new Sprite(this);
		sprites.add(sprite);
		Thread th = new Thread(sprite);
		threads.add(th);
		th.start();
	}
	
	/**
	 * repaint JPanel in infinite loop
	 */
	public void animate(){
		while (true){
	        repaint();
	    }
	} 
	
	/**
	 * Inner class for implementing when mouse buttons are pressed
	 */
	private class Mouse extends MouseAdapter {
		@Override
	    public void mousePressed( final MouseEvent event ){
	        newSprite(event);
	    }
	}

	/**
	 * Getter for delay time
	 * @return return delay instance variable
	 */
	public int getDelay() { return delay; }
	
	/**
	 * Inner class for implementing when key pressed
	 */
	private class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (delay < 100) delay++;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (delay > 1) delay--;
			}
		}
	}

	/**
	 * Calls the UI delegate's paint method, if the UI delegate is non-null.
	 * Clear Jpanel and then draw circle and all sprites
	 * @param g the Graphics object to protect
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawOval( CIRCLE_X - CIRCLE_RADIUS, CIRCLE_Y - CIRCLE_RADIUS, 
				CIRCLE_RADIUS * 2, CIRCLE_RADIUS * 2 ); // draw circle
		g.drawString("All Sprites: " + threads.size() + "  Inside Circle: " + numOfSprites +
				"  \u25B2 sleep up \u25BC sleep down | sleep: " + delay, 20, getHeight() - 20 );
		// draw all sprites
		for (Sprite sp : sprites) {	
			sp.draw(g);
		}
	}
}
