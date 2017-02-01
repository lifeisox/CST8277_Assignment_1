/* File Name: BouncingSprites.java
 * Author Name: Algonquin College
 * Modified By: Byung Seon Kim
 * Date: 9 January 2017
 * Description: Start point to implement multithreading and a solution to the Producer/Consumer problem.
 */

package bouncingsprites;

import javax.swing.JFrame;

/**
 * The class sets up basic GUI (Jframe) to implement multithreaded animation
 * @author Byung Seon Kim
 */
public class BouncingSprites {
	/** JFrame object for setting up basic GUI */
    private JFrame frame;
    /** SpritePanel object, which is extended from JPanel */ 
    private SpritePanel panel = new SpritePanel();

    /** Default constructor */
    public BouncingSprites() {
        frame = new JFrame("Bouncing Sprites 2017W");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
    }
    
    /**
     * The start() method is to start animation
     */
    public void start(){
    	 panel.animate();  // never returns due to infinite loop in animate method
    }

    /** 
     * Entry point of the program
     * @param args command line parameters
     */
    public static void main(String[] args) {
        new BouncingSprites().start();
    }
}
