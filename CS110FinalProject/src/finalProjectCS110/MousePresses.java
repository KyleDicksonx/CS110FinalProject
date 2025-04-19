
package finalProjectCS110;

/*
 * Kyle Dickson
 * MousePressses.java
 */

import java.awt.event.*;


public class MousePresses implements MouseListener{
	
	public boolean leftClick, rightClick, middleClick;
	public int mouseX, mouseY;
	
	//mouse clicks down
		@Override
		public void mousePressed(MouseEvent e) {
			//mouse pos for debug
				mouseX = e.getX();
				mouseY = e.getY();
				
			//get the pressed button
				int mouseB = e.getButton();
			
			//left click down
				if (mouseB == MouseEvent.BUTTON1) {
					leftClick = true;
					System.out.printf("leftClick true at %d, %d.\n", mouseX, mouseY);
				}
			//middle click down
				if (mouseB == MouseEvent.BUTTON2) {
					middleClick = true;
					//System.out.printf("middleClick true at %d, %d.\n", mouseX, mouseY);
				}
			//right click down
				if (mouseB == MouseEvent.BUTTON3) {
					rightClick = true;
					//System.out.printf("rightClick true at %d, %d.\n", mouseX, mouseY);
				}
		}
		
	//mouse click up
		@Override
		public void mouseReleased(MouseEvent e) {
			//mouse pos for debug
				mouseX = e.getX();
				mouseY = e.getY();
			//get the pressed button
				int mouseB = e.getButton();
			
			//left click up
				if (mouseB == MouseEvent.BUTTON1) {
					leftClick = false;
					//System.out.printf("leftClick false at %d, %d.\n", mouseX, mouseY);
				}
			//middle click up
				if (mouseB == MouseEvent.BUTTON2) {
					middleClick = false;
					//System.out.printf("middleClick false at %d, %d.\n", mouseX, mouseY);
				}
			//right click up
				if (mouseB == MouseEvent.BUTTON3) {
					rightClick = false;
					//System.out.printf("rightClick false at %d, %d.\n", mouseX, mouseY);
				}
		}
		
	//dont remove java gets mad
	//not used
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mouseClicked(MouseEvent e) {}
		
}
