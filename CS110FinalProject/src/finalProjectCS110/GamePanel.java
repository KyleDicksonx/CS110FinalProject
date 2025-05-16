package finalProjectCS110;
/*
 * Kyle Dickson
 * GamePanel.java
 */


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;

public class GamePanel extends JPanel{
	//eclipse is spitting a warning and wants a serialVersionUID so here it is
		private static final long serialVersionUID = -7872409611469678070L;
	
	//random is used to set gold pos and starting player pos
		Random random = new Random();
		
	//THE SCREEN "settings"
		//mostly unused for its intended purpose, just to organize now
			final int ogTileSize = 16; //16x16 is a retro size 
			final int upscale = 3; //upscale cus 16 pixels is tiny
			//tiles are 48x48 pixels for now
			//tested for scale on a 1920x1080 and 2560x1440 screens
			
			final int tile = ogTileSize * upscale;
			
			final int screenXDim = 18; //18x16 tiles on the screen
			final int screenYDim = 14;
			final int screenXMax = screenXDim * tile;
			final int screenYMax = screenYDim * tile;
		
		//create the thread for the panel
			static Thread panelThread;
			
		//player starting position
			public int playerXPos = tile*(random.nextInt(16)+1);
			public int playerYPos = tile*(random.nextInt(10)+1);
			
		//player speed and modifier
			public int playerDefaultSpeed = 8;
			public int playerSpeedModifier = 0;
	
	//KEY/MOUSE PRESSES
		KeyPresses keyP = new KeyPresses();
		MousePresses mouseP = new MousePresses();
	
	//FPS
		//used to set the delay for game rate
		int fps = 60;
		//the wait time between frames
			final public double waitTime = 1000/fps;
		
	//PLAYER COLISION
		int[] xWalls = {0,864};
		int[] yWalls = {0,672};
		
		boolean allowPositiveXMovement = true;
		boolean allowNegativeXMovement = true;
		boolean allowPositiveYMovement = true;
		boolean allowNegativeYMovement = true;
	
	//FONTS
		final String font = "Serif";
		
	//gold settings
		//make this read from a file later
			public int goldCount = 0;
		
		//set the default for gold pickup to false
			public boolean pickUpGold1 = false;
			public boolean pickUpGold2 = false;
			public boolean pickUpGold3 = false;
			public boolean pickUpGold4 = false;
		
		//random starting positions for the gold		
			public int gold1X = random.nextInt((tile*screenXDim));
			public int gold2X = random.nextInt((tile*screenXDim));
			public int gold3X = random.nextInt((tile*screenXDim));
			public int gold4X = random.nextInt((tile*screenXDim));
			public int gold1Y = random.nextInt((tile*screenYDim));
			public int gold2Y = random.nextInt((tile*screenYDim));
			public int gold3Y = random.nextInt((tile*screenYDim));
			public int gold4Y = random.nextInt((tile*screenYDim));
		//each main game loop will end when this count is reached
			public int goalGold = 16;
		
	//the menu will display b4 this
		public boolean mainGameStart = false;
		//ends each game play loop
			public  boolean gameEnd = false;
		//fully ends the game
			public boolean gameTrueEnd = false;
		//1st loop game starting countdown
			public boolean countdown = true;
			public int countdownNumber = 3;  
			public int countdownWaitTime;
		
	//counts the loops of the main game loop under run()	
			public int loops = 0;
			
	//time values used to calculate the score
		public long startTime;
		public long endTime;
		public long matchTime;
	
	//unused background texture attempt
		/**
		//background texture
			public String bgImageLocation = "C:\\Users\\redma\\eclipse-workspace\\CS110FinalProject\\src\\finalProjectCS110\\bg.png";
			public BufferedImage bgImage;
			
			public void loadBGImage() {
				try {
				//bgImage = ImageIO.read(getClass().getResourceAsStream("C:\\Users\\redma\\eclipse-workspace\\CS110FinalProject\\src\\finalProjectCS110\\bg.png"));
				//bgImage = ImageIO.read(getClass().getResource(bgImageLocation));
				bgImage = ImageIO.read(getClass().getResourceAsStream("/backgroundImages/bg.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
				
		**/
	
	//game panel constructor
		public GamePanel() {
			//sets screen size 
				this.setPreferredSize(new Dimension(screenXMax,screenYMax));
			//sets the background color of the panel
				this.setBackground(Color.black);//black is an easy color to look at
			//allows the panel to listen for the key and mouse
				this.addKeyListener(keyP);
				this.addMouseListener(mouseP);
			//allows the user to focus the window and requests focus 
				this.setFocusable(true);
				this.requestFocusInWindow();
			//this.createImage(48, 48);
		}
	//game panel thread
		public static void panelThread() {
			//creates the thread for the game panel
				panelThread = new Thread();
				panelThread.start();
			System.out.println("panelThread Started");
		}
		
	//main run loop
		public void run() throws InterruptedException, IOException {
			
			//gameTrueEnd brings the game to a full stop
				while (!gameTrueEnd) {
					//main menu loop
						while (!mainGameStart && !gameEnd) {
							//paints the main menu
								repaint();
							//loop continues until the mouse is pressed on the start button or q is clicked
								if (keyP.qPressed || menuMouseClickCheck()) {
									mainGameStart = true;
								}
							//allows the game to be closed with esc
								if (keyP.escPressed) {
									gameTrueEnd = true;
									System.out.println("Game ended.");	
									break;
								}
						}
					//main game loop
						//mainGameStart = true;
						while (panelThread != null && !gameEnd) {
							//starts the score timer
								if (loops == 1) {
									startTime = System.currentTimeMillis();
								}
						
							//updates gold and player positions and movement
								updatePanel();
								
							//paints all of the components of the main game loop
								repaint();	
								
							//ends the loops when the target gold count is reached
								if (goldCount == goalGold) {
									gameEnd = true;
									//gets the endTime for score
										endTime = System.currentTimeMillis();
								}
							//allows the game to be closed with esc
								if (keyP.escPressed) {
									gameTrueEnd = true;
									System.out.println("Game ended.");	
									break;	
								}
							//after the screen updates the program waits till the next frame to perform any actions again
								Thread.sleep((long) waitTime);
								loops ++;
								//System.out.println(loops);
								//System.out.println("Main game loop running");
								
						}
					//end game menu loop
						while (gameEnd) {
							//player score is match time
								matchTime = (endTime-startTime);
								
							//displays the game end screen
								repaint();
							//starts another main loop when left click or e is clicked
								if (keyP.ePressed || mouseP.leftClick) {
									gameEnd = false;
									//resets all required vars for the game to run again
										resetVars();
								}
							//when esc is pressed the game fully ends and the window closes
								else if (keyP.escPressed) {
									gameTrueEnd = true;
									System.out.println("Game ended.");	
									break;
							}
						}
				}
		}
	
	//handles player and gold movement in the main game loop	
		public void updatePanel() {
			allowMovementCheck();
			playerMovement();
			playerGoldIntersect();
			updateGoldPos();
			//System.out.println("updatePanel Successful");
			//System.out.println(playerXPos + " " + playerYPos);
	
		}
	
	//paints components on the screen
		//this is called with repaint(); this is built into java
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			//this will display the menu until the user starts the game
				if (!mainGameStart && !gameEnd) {
					paintMenu(g);
				}
			else if (mainGameStart) {
				System.out.println("countdown");
				//game start count down
					//System.out.println(loops);     
					if (countdown) {
						countdownWaitTime = 800;
						
						
						if (countdownNumber == 3) {
						
							//the first number dosent diplay so this is here with a 0 delay
								g.setColor(Color.black);
								g.fillRect(0, 0, tile*18, tile*14);
								g.setFont(new Font(font, Font.BOLD, random.nextInt(700)+100));
								g.setColor(Color.white);
								g.drawString(Integer.toString(countdownNumber), tile*8, tile*12);
							countdownNumber--;
							try {
								Thread.sleep(countdownWaitTime);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
						else if (countdownNumber == 2) {
							
							g.setColor(Color.black);
							g.fillRect(0, 0, tile*18, tile*14);
							g.setFont(new Font(font, Font.BOLD, random.nextInt(700)+100));
							g.setColor(Color.white);
							g.drawString(Integer.toString(countdownNumber), tile*8, tile*12);
							try {
								Thread.sleep(countdownWaitTime-200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							countdownNumber--;
						}
						else if (countdownNumber == 1) {
							g.setColor(Color.black);
							g.fillRect(0, 0, tile*18, tile*14);
							g.setFont(new Font(font, Font.BOLD, random.nextInt(700)+100));
							g.setColor(Color.white);
							g.drawString(Integer.toString(countdownNumber), tile*8, tile*12);
							try {
								Thread.sleep(countdownWaitTime-400);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
							countdown = false;
						}
				}
						
			//game over screen
				else if (gameEnd) {
					//set the screen to black
						g.setColor(Color.black);
						g.fillRect(0, 0, tile*18, tile*14);
						g.setColor(Color.white);
					//set font size 
						g.setFont(new Font(font, Font.BOLD, 50));
					//Game Over text
						g.drawString("GAME OVER",tile*2-25,tile*8);
					//play again text
						g.drawString("Press e or left click to play again",tile*2-25,tile*5);
					//score text
						g.drawString("Time: " + matchTime + " ms",tile*2-25,tile*2);
					//highscore text
						g.drawString("Highscore: 8671 ms",tile*2-25,tile*3);
				}
			else {
				System.out.println("else");
				/**
				//background
				if (bgImage != null) {
					g.drawImage(bgImage, 0, 0, 48, 48, 0, 16, 0, 16, this);
					System.out.println("Background drawn");
				}
				
				**/
				//the upper menu things
					//Gold: 00000
						g.setFont(new Font(font, Font.BOLD, 40));
						g.setColor(Color.white);
						g.drawString("Gold: " + Integer.toString(goldCount), (tile*screenXDim-200), 35);
				
				//the player
					g.setColor(Color.red);
					
					g.fillRect(playerXPos, playerYPos, tile, tile);
			
				//the gold
					g.setColor(Color.yellow);
					g.fillOval(gold1X,gold1Y,tile/2,tile/2);
					g.fillOval(gold2X,gold2Y,tile/2,tile/2);
					g.fillOval(gold3X,gold3Y,tile/2,tile/2);
					g.fillOval(gold4X,gold4Y,tile/2,tile/2);			
			}
			}
	
			//System.out.println("paintComponent Successful");
		}
	
	//prevents the player from leaving the screen
		public void allowMovementCheck() {
		allowPositiveXMovement = true;
		allowNegativeXMovement = true;
		allowPositiveYMovement = true;
		allowNegativeYMovement = true;
		
		
		for (int x : xWalls) {
			//prevent x wall clip
				if (playerXPos + tile == x) {
					allowPositiveXMovement = false;
					//System.out.println("allowPositiveXMovement = false");
				}
				if (playerXPos == x) {
					allowNegativeXMovement = false;
					//System.out.println("allowNegativeXMovement = false");
				}	
		}
		for (int y : yWalls) {
			//prevent y wall clip
				if (playerYPos + tile == y) {
					allowPositiveYMovement = false;
					//System.out.println("allowPositiveYMovement = false");
				}
				if (playerYPos == y) {
					allowNegativeYMovement = false;
					//System.out.println("allowNegativeYMovement = false");
				}	
		}
	}
	
	//uses w a s d for player movement 
		public void playerMovement() {
	
			int speed = playerDefaultSpeed + playerSpeedModifier;
			//w key (up)
				if (keyP.wPressed && allowNegativeYMovement) {
					playerYPos -= speed;
					//System.out.println("Negative Y Player Movement");
				}
				
			//s key (down)
				if (keyP.sPressed && allowPositiveYMovement) {
					playerYPos += speed;
					//System.out.println("positive Y Player Movement");
				}
			
			//a key (left)
				if (keyP.aPressed && allowNegativeXMovement) {
					playerXPos -= speed;
					//System.out.println("Negative X Player Movement");
				}
			
			//d key (right)
				if (keyP.dPressed && allowPositiveXMovement) {
					playerXPos += speed;
					//System.out.println("Positive X Player Movement");
				}
		}
		
	//checks to see if the player hits any gold
		public void playerGoldIntersect() {
			pickUpGold1 = false;
			pickUpGold2 = false;
			pickUpGold3 = false;
			pickUpGold4 = false;
			
			//gold 1 check
				if (playerXPos + tile >= gold1X && playerXPos <= gold1X + tile/2) {
					if (playerYPos + tile >= gold1Y && playerYPos <= gold1Y + tile/2) {
						pickUpGold1 = true;
						goldCount ++;
						//System.out.println("pickUpGold1 = true");
					}
				}
			
			//gold 2 check
				if (playerXPos + tile >= gold2X && playerXPos <= gold2X + tile/2) {
					if (playerYPos + tile >= gold2Y && playerYPos <= gold2Y + tile/2) {
						pickUpGold2 = true;
						goldCount++;
						//System.out.println("pickUpGold2 = true");
					}
				}
				
			//gold 3 check
				if (playerXPos + tile >= gold3X && playerXPos <= gold3X + tile/2) {
					if (playerYPos + tile >= gold3Y && playerYPos <= gold3Y + tile/2) {
						pickUpGold3 = true;
						goldCount++;
						//System.out.println("pickUpGold3 = true");
					}
				}
			
			//gold 4 check
				if (playerXPos + tile >= gold4X && playerXPos <= gold4X + tile/2) {
					if (playerYPos + tile >= gold4Y && playerYPos <= gold4Y + tile/2) {
						pickUpGold4 = true;
						goldCount++;
						//System.out.println("pickUpGold4 = true");
					}
				}	
	}
	
	//updates gold position when its hit by the player
		public void updateGoldPos(){
			//gold 1 update
				if (pickUpGold1) {
					gold1X = random.nextInt(tile*screenXDim);
					gold1Y = random.nextInt(tile*screenYDim);
					//System.out.printf("Gold1 pos changed to %d, %d\n",gold1X, gold1Y);
				}
				
			//gold 2 update
				if (pickUpGold2) {
					gold2X = random.nextInt(tile*screenXDim);
					gold2Y = random.nextInt(tile*screenYDim);
					
					//System.out.printf("Gold2 pos changed to %d, %d\n",gold2X, gold2Y);
				}
				
			//gold 3 update
				if (pickUpGold3) {
					gold3X = random.nextInt(tile*screenXDim);
					gold3Y = random.nextInt(tile*screenYDim);
					
					//System.out.printf("Gold2 pos changed to %d, %d\n",gold3X, gold3Y);
				}
				
			//gold 4 update
				if (pickUpGold4) {
					gold4X = random.nextInt(tile*screenXDim);
					gold4Y = random.nextInt(tile*screenYDim);
					
					//System.out.printf("Gold2 pos changed to %d, %d\n",gold4X, gold4Y);
				}
		}
		
	//resets vars at the end of the main game loop to allow for another loop
		public void resetVars() {
			goldCount = 0;
			loops = 0;
			mainGameStart = true;
			gameEnd = false;
			countdownNumber = 3;	
			countdown = true;
		}
		
	//checks for mouse clicks in the main menu // only checks for clicks on the start button
		public boolean menuMouseClickCheck() {
		boolean clicked = false;

		//the "Start" button
			if (mouseP.leftClick) {
				//System.out.println("left click true");
				
				//checks mouse x
						if (mouseP.mouseX >= tile*6 && mouseP.mouseX <= tile*12) {
							//System.out.println("Propper x cords");
							
							//checks mouse y
								if (mouseP.mouseY >= tile*2 && mouseP.mouseY <= tile*4) {
									//System.out.println("propper y cords");
									clicked = true;
								}
						}
			}
		//System.out.printf("%d, %d\n",mouseP.mouseX,mouseP.mouseY);
		return clicked;
	}	

	//paint the main menu
		public void paintMenu(Graphics g) {
			//big rectangle
				g.setColor(Color.cyan);
				g.fillRect(tile*1,tile*1,tile*16,tile*12);
		
			//start button
			//tile 6-12x and 2-4y
				g.setColor(Color.green);
				g.fillRect(tile*6,tile*2,tile*6,tile*2);
				g.setColor(Color.black);
				g.drawRect(tile*6,tile*2,tile*6,tile*2);
				g.drawRect(tile*1,tile*1,tile*16,tile*12);
			//the word "start"
				g.setFont(new Font(font, Font.BOLD, 100));
				g.drawString("Start",tile*7-8,tile*4-16);	
			
			//game goal statement
				g.setFont(new Font(font, Font.BOLD, 50));
				g.drawString("How fast can you collect 16 coins?",tile*2-25,tile*8);
			
			//explaining how to start the game
				g.setFont(new Font(font, Font.BOLD, 25));
				g.drawString("(Press q or left click to start)",tile*6,tile*5-16);
			
			//movement text
				g.drawString("move with w a s d",tile*7,tile*6-16);
		}

	//paint countdown
		public void paintCountdown(Graphics g) {
			g.setColor(Color.black);
			g.fillRect(0, 0, screenXDim, screenYDim);
			g.setColor(Color.white);
			
			
			
			
			
			
			
			
		}



}