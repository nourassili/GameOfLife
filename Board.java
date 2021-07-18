/*
 * Team Name: Team BellaSimps
 * CSC171 Project 3
 * Class: Board
 * Date: April 2021
 */

import java.awt.*;
import java.util.Random;
import javax.swing.*;

/*
 * Board creates a game board and includes the repaint method for all of the different game modes. It also
 * contains the main class for the game.
 */

public class Board extends JComponent{
			
	public static void main(String[] args) {
	    GameOfLifeGUI frame1 = new GameOfLifeGUI();
		frame1.setVisible(true);
		frame1.setSize(1200,720);
	}
		/*
		 * If a cell is ALIVE, it will be represented by true. 
		 * If a cell is DEAD, it will be represented by false.
		 */
		public static final boolean ALIVE = true;
		public static final boolean DEAD = false;
		
		/*
		 * The initial dimensions of the board are 20 x 20.
		 */
		public static int DIM = 20;
		
		/*
		 * The spacing between cells is 5.
		 */
		public final int spacing = 5;
		
		/*
		 * The current game state, represented by a 2D boolean array.
		 */
		public static boolean[][] currentArray = new boolean[DIM][DIM];
		
		/*
		 * The initial density, set here as 3.
		 */
		public static int density = 3;
	
	/*
	 * method reset() randomizes the board. It checks to make sure the 
	 * dimensions are valid using GameOfLifeGUI.checkDimension(), then 
	 * prints the current dimensions and randomizes the board, using
	 * the density slider's value as a guide.
	 */
    public static void reset() {
    	try {
			GameOfLifeGUI.checkDimension();
	    	DIM = Integer.valueOf(GameOfLifeGUI.dimension.getText());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("DIM is too large.");
		}
    	System.out.println("DIM " + DIM);
    	
    	Random r = new Random();
    	for (int row = 0; row < DIM; row++) {
    		for (int column = 0; column < DIM; column++) {
    			if (r.nextInt(GameOfLifeGUI.getDensity()) == 0) {
    				currentArray[row][column] = ALIVE;
    			} else {
    				currentArray[row][column] = DEAD;
    			}
    		}
    	}
    }
    
    /*
	 * method paintComponent paints the current cells based on if they are
	 * dead or alive, and other game JPanels based on which game mode
	 * is selected.
	 */
		@Override
		public void paintComponent(Graphics g) {
			
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0,  0, this.getWidth(), this.getHeight());
			for(int row = 0; row < DIM; row++) {
				for (int column = 0; column < DIM; column++) {
					g.setColor(Color.DARK_GRAY);
					if (currentArray[row][column] == ALIVE) {
						paintAliveState(g);
					}
					if (currentArray[row][column] == DEAD) {
						paintDeadState(g);
					}
				g.fillRect((spacing + column * getWidth() / DIM), 
						(spacing + row * getHeight() / DIM), 
						((this.getWidth() - (DIM + 1) * spacing) / DIM), 
						((this.getHeight() - (DIM + 1) * spacing) / DIM));
				
				}	
			}
			
			/*
			 * paints according to selected game mode 
			 */
			
			String currentMode = GameOfLifeGUI.setGameMode();
			if (currentMode.equals("-GAME MODE-")) {
				GameOfLifeGUI.popupPanel.setBackground(Color.LIGHT_GRAY);
				GameOfLifeGUI.numGenerationsPanel.setBackground(Color.LIGHT_GRAY);
				GameOfLifeGUI.subtitlePanel.setBackground(Color.LIGHT_GRAY);
				GameOfLifeGUI.mainTitlePanel.setBackground(Color.LIGHT_GRAY);
				GameOfLifeGUI.densitySpeedBox.setBackground(Color.LIGHT_GRAY);
				GameOfLifeGUI.buttonsBox.setBackground(Color.LIGHT_GRAY);
				GameOfLifeGUI.gameModeBox.setBackground(Color.LIGHT_GRAY);
				GameOfLifeGUI.fileNameBox.setBackground(Color.LIGHT_GRAY);
				
			}
			if (currentMode.equals("CYAN")) {
				GameOfLifeGUI.popupPanel.setBackground(Color.CYAN);
				GameOfLifeGUI.numGenerationsPanel.setBackground(Color.CYAN);
				GameOfLifeGUI.subtitlePanel.setBackground(Color.CYAN);
				GameOfLifeGUI.mainTitlePanel.setBackground(Color.CYAN);
				GameOfLifeGUI.densitySpeedBox.setBackground(Color.CYAN);
				GameOfLifeGUI.buttonsBox.setBackground(Color.CYAN);
				GameOfLifeGUI.gameModeBox.setBackground(Color.CYAN);
				GameOfLifeGUI.fileNameBox.setBackground(Color.CYAN);
				
			}
			if (currentMode.equals("YELLOW")) {
				GameOfLifeGUI.popupPanel.setBackground(Color.YELLOW);
				GameOfLifeGUI.numGenerationsPanel.setBackground(Color.YELLOW);
				GameOfLifeGUI.subtitlePanel.setBackground(Color.YELLOW);
				GameOfLifeGUI.mainTitlePanel.setBackground(Color.YELLOW);
				GameOfLifeGUI.densitySpeedBox.setBackground(Color.YELLOW);
				GameOfLifeGUI.buttonsBox.setBackground(Color.YELLOW);
				GameOfLifeGUI.gameModeBox.setBackground(Color.YELLOW);
				GameOfLifeGUI.fileNameBox.setBackground(Color.YELLOW);
			}
			if (currentMode.equals("GREEN")) {
				GameOfLifeGUI.popupPanel.setBackground(Color.GREEN);
				GameOfLifeGUI.numGenerationsPanel.setBackground(Color.GREEN);
				GameOfLifeGUI.densitySpeedBox.setBackground(Color.GREEN);	
				GameOfLifeGUI.subtitlePanel.setBackground(Color.GREEN);
				GameOfLifeGUI.mainTitlePanel.setBackground(Color.GREEN);
				GameOfLifeGUI.buttonsBox.setBackground(Color.GREEN);
				GameOfLifeGUI.gameModeBox.setBackground(Color.GREEN);
				GameOfLifeGUI.fileNameBox.setBackground(Color.GREEN);
			}
			
			
			this.setVisible(true);
			
			
		}
		
		/*
		 * method paintAliveState() paints the ALIVE cells depending on which
		 * game mode is selected.
		 */
		public static void paintAliveState(Graphics g) {
			String currentMode = GameOfLifeGUI.setGameMode();
			if (currentMode.equals("CYAN")) {
				g.setColor(Color.CYAN);
			}
			if (currentMode.equals("YELLOW")) {
				g.setColor(Color.YELLOW);
			}
			if (currentMode.equals("GREEN")) {
				g.setColor(Color.GREEN);
			}
		}
		
		/*
		 * method paintDeadState() paints the DEAD cells. Leaves room for extension -
		 * dead cells to be recolored based on game mode, for example.
		 */
		public static void paintDeadState(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			
		}
}