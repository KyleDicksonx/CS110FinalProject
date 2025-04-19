
package finalProjectCS110;
/*
 * Kyle Dickson
 * KeyPresses.java
 */
import java.awt.event.*;

public class KeyPresses implements KeyListener{
	
	//vars for all the keys used
		public boolean wPressed;
		public boolean aPressed;
		public boolean sPressed;
		public boolean dPressed;
		public boolean escPressed;
		public boolean ePressed;
		public boolean qPressed;
	
	//all the key down events
		@Override
		public void keyPressed(KeyEvent e) {
			//get the code for the pressed key
				int keyCode = e.getKeyCode();
			//w key down
				if (keyCode == KeyEvent.VK_W) {
					wPressed = true;
					//System.out.println("wPressed True");
				}
			//a key down
				if (keyCode == KeyEvent.VK_A) {
					aPressed = true;	
					//System.out.println("aPressed True");
				}
			//s key down
				if (keyCode == KeyEvent.VK_S) {
					sPressed = true;
					//System.out.println("sPressed True");
				}
			//d key down
				if (keyCode == KeyEvent.VK_D) {
					dPressed = true;
					//System.out.println("dPressed True");
				}
			//e key down
				if (keyCode == KeyEvent.VK_E) {
					ePressed = true;
					//System.out.println("ePressed True");
				}
			//q key down
				if (keyCode == KeyEvent.VK_Q) {
					qPressed = true;
					//System.out.println("qPressed True");
				}
			//escape key down
				if (keyCode == KeyEvent.VK_ESCAPE) {
					escPressed = true;
					System.out.println("escPressed True");
				}
		}
	
	
	// all the key up events
		@Override
		public void keyReleased(KeyEvent e) {
			
			//get the code for the pressed key
				int keyCode = e.getKeyCode();
				
			//w key up
				if (keyCode == KeyEvent.VK_W) {
					wPressed = false;
					//System.out.println("wPressed False");
				}
			//a key up
				if (keyCode == KeyEvent.VK_A) {
					aPressed = false;	
					//System.out.println("aPressed False");
				}
			//s key up
				if (keyCode == KeyEvent.VK_S) {
					sPressed = false;
					//System.out.println("sPressed False");
				}
			//d key up
				if (keyCode == KeyEvent.VK_D) {
					dPressed = false;
					//System.out.println("dPressed False");
				}
			//e key up
				if (keyCode == KeyEvent.VK_E) {
					ePressed = false;
					//System.out.println("ePressed False");
				}
			//q key up
				if (keyCode == KeyEvent.VK_Q) {
					qPressed = false;
					//System.out.println("qPressed False");
				}
			//escape key up
				if (keyCode == KeyEvent.VK_ESCAPE) {
					escPressed = false;
					System.out.println("escPressed False");
				}
		}
	//unused
		//dont remove java gets mad
		@Override
		public void keyTyped(KeyEvent e) {}	
}
