package finalProjectCS110;

import java.util.Scanner;

import static javax.swing.WindowConstants.*;

import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import java.io.*;

@SuppressWarnings("unused")
public class MainGame {
	
	
	public static void main(String[] args) throws IOException, InterruptedException{
		
		//frame object
			JFrame f = new JFrame();
		//frame settings
			f.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    	//f.setSize(1000, 1000); //no need for size, game panel covers that
	    	f.setLocation(100, 100);
	    	f.setResizable(false);
	    	f.setTitle("The Game");
    	
    	//game panel object
	    	//this is everything that is on screen and everything related
	    	GamePanel gp = new GamePanel();
    	
    	//adds the game panel to the frame
	    	f.add(gp);
    	//make the gp the correct size
	    	f.pack();
	    
        f.setVisible(true);
        
        //starts the main threads for the game
        	GamePanel.panelThread();
        
		//the looping component of the game
        	gp.run();
    	//closes the window when run() is over (when esc is pressed)
        	f.dispose();
	}
}
