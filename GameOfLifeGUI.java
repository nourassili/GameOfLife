/*
 * Team Name: Team BellaSimps
 * CSC171 Project 3
 * Class: GameOfLifeGUI
 * Date: April 2021
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

import javax.swing.border.AbstractBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import javax.swing.*;

/*
 * GameOfLifeGUI creates the JFrame within which the board and all other JPanels,
 *   JButtons, JSliders, etc. reside. It also contains the methods for checking the currentArray and 
 *   calculating the number of live neighbors of a given cell.
 */

public class GameOfLifeGUI extends JFrame implements ActionListener, ChangeListener{

	/*
	 * JPanel containing title, subtitle, density and speed sliders, and dimension
	 */
	protected static JPanel titlePanel;
	
	/*
	 * JPanel containing the main title of the game
	 */
	protected static JPanel mainTitlePanel;
	
	/*
	 * JLabel title of game
	 */
	protected JLabel mainTitleLabel;
	
	/*
	 * JPanel containing the subtitle of game
	 */
	protected static JPanel subtitlePanel;
	
	/*
	 * JLabel subtitle of game
	 */
	protected JLabel subtitleLabel;
	
	/*
	 * JPanel containing the board
	 */
	protected JPanel gamePanel;
	
	/*
	 * JPanel containing the bottom controls
	 */
	protected JPanel controlPanel;
	
	/*
	 * JPanel containing the current iteration of the game
	 */
	protected static JPanel numGenerationsPanel;
	
	/*
	 * JLabel current iteration of the game
	 */
	protected JLabel numGenerations;
	
	/*
	 * JPanel containing the density/speed sliders and dimension
	 */
	protected static JPanel densitySpeedBox;
	
	/*
	 * JLabel density
	 */
	protected JLabel densityControl;

	/*
	 * JSlider to adjust density
	 */
	protected JSlider densitySlider;
	
	/*
	 * JLabel dimension
	 */
	protected JLabel dimensionLabel;
	
	/*
	 * JTextField to input new dimension
	 */
	protected static JTextField dimension;
	
	/*
	 * JLabel speed
	 */
	protected JLabel speedControl;
	
	/*
	 * JSlider to adjust speed
	 */
	protected JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 4);
	
	/*
	 * JPanel containing control panel buttons
	 */
	protected static JPanel buttonsBox;
	
	/*
	 * JButton linked to information popup
	 */
	protected JButton informationButton;
	
	/*
	 * JButton linked to starting and stopping the timer
	 */
	protected JButton startStopButton;
	
	/*
	 * JButton linked to moving one iteration ahead
	 */
	protected JButton nextButton;
	
	/*
	 * JButton linked to resetting the game board
	 */
	protected JButton resetButton;
	
	/*
	 * JPanel containing the list of game modes
	 */
	protected static JPanel gameModeBox;
	
	/*
	 * JComboBox containing list of game modes
	 */
	protected static JComboBox gameModeList;
	
	/*
	 * JPanel containing the list of file names and button to load
	 */
	protected static JPanel fileNameBox;
	
	/*
	 * JButton linked to loading the selected file
	 */
	protected JButton fileLoadButton;
	
	/*
	 * JComboBox containing list of file names
	 */
	protected static JComboBox fileNameList;
	
	
	/*
	 * JPanel popup information panel
	 */
	protected static JPanel popupPanel;
	protected Popup fullPopup;
	protected PopupFactory pf = new PopupFactory();
	protected JFrame f = new JFrame("pop");
	
	/*
	 * Board = new board from Board.java
	 */
	protected Board board = new Board();
	
	/*
	 * delay for speed slider
	 */
	protected int delay = 250;
	
	/*
	 * represents initial value for density slider
	 */
	protected static int density = 4;
	
	/*
	 * dimensions of the board
	 */
	protected static int DIM = Board.DIM;
	
	/*
	 * method checkArray() checks the current array using a switch and repaints the board.
	 *   Calls searchNumNeighbors.
	 */
	
	public void checkArray() {
		boolean[][] nextArray = new boolean[DIM][DIM];
		int result;
		
		/* 
		 * sets the nextArray at [row][column] with 
		 * the result (true or false) of cellLiveOrDie[row][column]
		 */
		
		for (int row = 0; row < DIM; row++) {
			for (int column = 0; column < DIM; column++) {
				result = searchnumNeighbors(row, column, Board.currentArray);	
					switch(result) {
						//DEAD: any cell with fewer than two numNeighbors neighbors dies (by underpopulation)
						case 0:
						case 1:
							nextArray[row][column] = Board.DEAD;
							break;
						case 2:
							nextArray[row][column] = Board.currentArray[row][column];
							break;
						//ALIVE: any cell with exactly 3 numNeighbors neighbors comes to life
						case 3: 
							nextArray[row][column] = Board.ALIVE;
							break;
						//DEAD: any cell with more than 3 numNeighbors neighbors dies (by overpopulation)
						default:
							nextArray[row][column] = Board.DEAD;
							break;
					}
			}
		}
		
		/* 
		 * sets the currentArray[row][column] with 
		 * the value of nextArray[row][column]
		 */
		
		for (int row = 0; row < DIM; row++) {
			for (int column = 0; column < DIM; column++) {
				Board.currentArray[row][column] = nextArray[row][column];
			}
		}
		
		repaint();
	}
	
	/*
	 * method searchnumNeighbors() takes the current row, column, and currentArray board from checkArray() and 
	 *   adds the number of live neighbors using if/else if/else branches to account for edge cases.
	 *   Returns numNeighbors with a value of the number of live neighbors surrounding the current
	 *   cell.
	 */
	
	public static int searchnumNeighbors(int row, int column, boolean[][]currentArray){
		int numNeighbors = 0;
		
	  //up-left corner
      if (row == 0 && column == 0) {
    	  if (currentArray[0][1] == Board.ALIVE) { //current row, one column right
    		  numNeighbors++;
    	  }
    	  if (currentArray[1][0] == Board.ALIVE) { //one row down, current column
    		  numNeighbors++;
    	  }
    	  if (currentArray[1][1] == Board.ALIVE) { //one row down, one column right
    		  numNeighbors++;
    	  }
      }

      //up-right corner
      else if (row == 0 && column == DIM - 1) {
    	  if (currentArray[0][DIM - 2] == Board.ALIVE) { //current row, one column left
    		  numNeighbors++;
    	  }
    	  if (currentArray[1][DIM - 2] == Board.ALIVE) { //one column left, one row down
    		  numNeighbors++;
    	  }
    	  if (currentArray[1][DIM - 1] == Board.ALIVE) { //one row down, current column
    		  numNeighbors++;
    	  }
      }

      //down-left corner
      else if (row == DIM - 1 && column == 0) {
    	  if (currentArray[DIM - 2][0] == Board.ALIVE) { //one row up, current column
    		  numNeighbors++;
    	  }
    	  if (currentArray[DIM - 2][1] == Board.ALIVE) { //one row up, one column right
    		  numNeighbors++;
    	  }
    	  if (currentArray[DIM - 1][1] == Board.ALIVE) { //current row, one column right
    		  numNeighbors++;
    	  }
      }

      //down-right corner
      else if (row == DIM - 1 && column == DIM - 1) {
    	  if (currentArray[DIM - 2][DIM - 1] == Board.ALIVE) { //one row up, current column
    		  numNeighbors++;
    	  }
    	  if (currentArray[DIM - 2][DIM - 2] == Board.ALIVE) { //one row up, one column up
    		  numNeighbors++;
    	  }
    	  if (currentArray[DIM - 1][DIM - 2] == Board.ALIVE) { //current row, one column up
    		  numNeighbors++;
    	  }
      }

      //first row
      else if (row == 0 && column != 0 && column != DIM - 1) {
    	  if (currentArray[row][column - 1] == Board.ALIVE) { //one column left
    		  numNeighbors++;
    	  }
    	  if (currentArray[row][column + 1] == Board.ALIVE) { //one column right
    		  numNeighbors++;
    	  }
    	  if (currentArray[row + 1][column - 1] == Board.ALIVE) { //one row down, one column left
    		  numNeighbors++;
    	  }
    	  if (currentArray[row + 1][column] == Board.ALIVE) { //one row down, current column
    		  numNeighbors++;
    	  }
    	  if (currentArray[row + 1][column + 1] == Board.ALIVE) { //one row down, one column right
    		  numNeighbors++;
    	  }
      }

      //last row 
      else if (row == DIM -1 && column != 0 && column != DIM - 1) {
    	  if (currentArray[row - 1][column - 1] == Board.ALIVE) { //one row up, one column left
    		  numNeighbors++;
    	  }
    	  if (currentArray[row - 1][column] == Board.ALIVE) { //one row up, current column
    		  numNeighbors++;
    	  }
    	  if (currentArray[row - 1][column + 1] == Board.ALIVE) { //one row up, one column right
    		  numNeighbors++;
    	  }
    	  if (currentArray[row][column - 1] == Board.ALIVE) { //current row, one column left
    		  numNeighbors++;
    	  }
    	  if (currentArray[row][column + 1] == Board.ALIVE) { //current row, one column right
    		  numNeighbors++;
    	  }
	    }

      //first column (not the first or last row)
      else if (column == 0 && row != 0 && row != DIM - 1) {
    	  if (currentArray[row - 1][column] == Board.ALIVE) { //one row up, current column
    		  numNeighbors++;
    	  }
    	  if (currentArray[row - 1][column + 1] == Board.ALIVE) { //one row up, one column right
    		  numNeighbors++;
    	  }
    	  if (currentArray[row][column + 1] == Board.ALIVE) { //current row, one column right
    		  numNeighbors++;
    	  }
    	  if (currentArray[row + 1][column] == Board.ALIVE) { //one row down, current column
    		  numNeighbors++;
    	  }
    	  if (currentArray[row + 1][column + 1] == Board.ALIVE) { //one row down, one column right
    		  numNeighbors++;
    	  }
      }	

      //last column (not the first or last row)
      else if (column == DIM - 1 && row != 0 && row != DIM - 1) {
    	  if (currentArray[row - 1][column - 1] == Board.ALIVE) { //one row up, one column left
    		  numNeighbors++;
    	  }
    	  if (currentArray[row - 1][column] == Board.ALIVE) { //one row up, current column
    		  numNeighbors++;
    	  }
    	  if (currentArray[row][column - 1] == Board.ALIVE) { //current row, one column left
    		  numNeighbors++;
    	  }
    	  if (currentArray[row + 1][column - 1] == Board.ALIVE) { //one row down, one column left
    		  numNeighbors++;
    	  }
    	  if (currentArray[row + 1][column] == Board.ALIVE) { //one row down, current column
    		  numNeighbors++;
    	  }
      }

      //everything else:
      else {
    	  if (currentArray[row - 1][column - 1] == Board.ALIVE) { //one row up, one column left
    		  numNeighbors++;
    	  }
    	  if (currentArray[row - 1][column] == Board.ALIVE) { //one row up, current column
    		  numNeighbors++;
    	  }
    	  if (currentArray[row - 1][column + 1] == Board.ALIVE) { //one row up, one column right
    		  numNeighbors++;
    	  }
    	  if (currentArray[row][column + 1] == Board.ALIVE) { //current row, one column right
    		  numNeighbors++;
    	  }
    	  if (currentArray[row][column - 1] == Board.ALIVE) { //current row, one column left
    		  numNeighbors++;
    	  }
    	  if (currentArray[row + 1][column - 1] == Board.ALIVE) { //one row down, one column left
    		  numNeighbors++;
    	  }
    	  if (currentArray[row + 1][column] == Board.ALIVE) { //one row down, current column
    		  numNeighbors++;
    	  }
    	  if (currentArray[row + 1][column + 1] == Board.ALIVE) { //one row down, one column right
    		  numNeighbors++;
    	  }
      }             
      return numNeighbors;
  }

	
	/*
	 * method GameOfLifeGUI() creates the JFrame within which the board and all other JPanels,
	 * JButtons, JSliders, etc. reside.
	 */
	
	@SuppressWarnings("unchecked")
	public GameOfLifeGUI() {
		setTitle("GAME OF LIFE");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		/*
		 * border for the control panels
		 */
		AbstractBorder brdr = new TextBubbleBorder(Color.BLACK,2,6,0);
		
		titlePanel = new JPanel();
		mainTitlePanel = new JPanel();
		subtitlePanel = new JPanel();
		
		gamePanel = new JPanel();
		controlPanel = new JPanel();
		
		densitySpeedBox = new JPanel();
		buttonsBox = new JPanel();
		numGenerationsPanel = new JPanel();
		
		popupPanel = new JPanel();
		
		gameModeBox = new JPanel();
		fileNameBox = new JPanel();
		
		titlePanel.setPreferredSize(new Dimension(300,140));
		gamePanel.setPreferredSize(new Dimension(300,300));
		
		gamePanel.setLayout(new BorderLayout());
		gamePanel.add(board);
	
		mainTitleLabel = new JLabel("CSC 171 GAME OF LIFE");
		mainTitleLabel.setFont(new Font("SF Distant Galaxy", Font.PLAIN, 24));
		mainTitlePanel.add(mainTitleLabel);
		titlePanel.add(mainTitlePanel, BorderLayout.NORTH);
		
		subtitleLabel = new JLabel("<html><div style='text-align: center;'>NOUR, NHUJA, JACOB <br> "
				+ "please click on 'INFO' button to learn more<html>");
		subtitleLabel.setFont(new Font("SF Distant Galaxy", Font.ITALIC, 18));
		subtitlePanel.add(subtitleLabel);
		titlePanel.add(subtitlePanel, BorderLayout.CENTER);
		
		String[] fileNameStrings = { "-BOARD CONFIGURATIONS-","20 * 20", "15 * 15", "10 * 10"};

		fileNameList = new JComboBox<Object>(fileNameStrings);
		fileNameList.setSelectedIndex(0);
		fileNameList.addActionListener(new fileNameListener());
		fileNameBox.add(fileNameList);
		fileNameBox.setBorder(brdr);
		
		informationButton = new JButton("INFO");
		informationButton.setFont(new Font("Press Start 2P", Font.PLAIN, 14));
		informationButton.addActionListener(new InformationButtonHandler());
		buttonsBox.add(informationButton);
		
		fileLoadButton = new JButton("LOAD");
		fileLoadButton.setFont(new Font("Press Start 2P", Font.PLAIN, 12));
		fileLoadButton.addActionListener(new FileLoadButtonHandler());
		fileNameBox.add(fileLoadButton);
		controlPanel.add(fileNameBox);
		
		startStopButton = new JButton("START");
		startStopButton.setFont(new Font("Press Start 2P", Font.PLAIN, 18));
		startStopButton.addActionListener(new StartStopButtonHandler());
		buttonsBox.add(startStopButton);
		
		nextButton = new JButton("NEXT");
		nextButton.setFont(new Font("Press Start 2P", Font.PLAIN, 14));
		nextButton.addActionListener(new NextButtonHandler());
		buttonsBox.add(nextButton);
		
		resetButton = new JButton("RESET");
		resetButton.setFont(new Font("Press Start 2P", Font.PLAIN, 14));
		resetButton.addActionListener(new ResetButtonHandler());
		buttonsBox.add(resetButton);
		buttonsBox.setBorder(brdr);
		controlPanel.add(buttonsBox);
		
		numGenerations = new JLabel("00");
		numGenerations.setFont(new Font("Press Start 2P", Font.PLAIN, 14));
		numGenerationsPanel.setBorder(brdr);
		numGenerationsPanel.add(numGenerations);
		controlPanel.add(numGenerationsPanel);
		
		speedControl = new JLabel("Speed (ms): ");
		speedControl.setFont(new Font("Press Start 2P", Font.PLAIN, 14));
		densitySpeedBox.add(speedControl);
		
		speedSlider.addChangeListener(new speedSliderListener());
		
		//displays slider ticks
		speedSlider.setMajorTickSpacing(1);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		densitySpeedBox.add(speedSlider);
		
		densityControl = new JLabel("Density: ");
		densityControl.setFont(new Font("Press Start 2P", Font.PLAIN, 14));
		densitySpeedBox.add(densityControl);
		
		densitySlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 4);
		densitySlider.addChangeListener(new densitySliderListener());
		
		//displays slider ticks
		densitySlider.setMajorTickSpacing(1);
		densitySlider.setPaintTicks(true);
		densitySlider.setPaintLabels(true);
		densitySpeedBox.add(densitySlider);
		
		densitySpeedBox.setBorder(brdr);
				
		dimensionLabel = new JLabel("Dimension: ");
		dimensionLabel.setFont(new Font("Press Start 2P", Font.PLAIN, 14));
		densitySpeedBox.add(dimensionLabel);
		
		dimension = new JTextField(String.valueOf(DIM));
		dimension.addActionListener(new dimensionTextListener());
		densitySpeedBox.add(dimension);
				
		titlePanel.add(densitySpeedBox, BorderLayout.SOUTH);
		
		String[] gameModeStrings = { "-GAME MODE-","CYAN", "YELLOW", "GREEN"};

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		gameModeList = new JComboBox<Object>(gameModeStrings);
		gameModeList.setSelectedIndex(0);
		gameModeList.addActionListener(new gameModeListener());
		gameModeBox.add(gameModeList);
		gameModeBox.setBorder(brdr);
		controlPanel.add(gameModeBox);
		
		mainTitlePanel.setBackground(Color.LIGHT_GRAY);
		subtitlePanel.setBackground(Color.LIGHT_GRAY);
		titlePanel.setBackground(Color.LIGHT_GRAY);
		controlPanel.setBackground(Color.LIGHT_GRAY);
		

		
		//title panel on "top", then game panel, then control panel on "bottom"
		this.add(titlePanel);
		this.add(gamePanel);
		this.add(controlPanel);
		
		pack();
		
		/*
		 * the following is for the popup panel
		 */
		
		/*
		 * creates button to exit the popup
		 */
        JButton button = new JButton("EXIT");
        button.addActionListener(new popupButtonListener());

        /*
    	 * creates text for the popup
    	 */
        JLabel l = new JLabel("<html>Welcome to our CS171 Project <br> Here is some important information: <br> "
        		+ "1. First, please select a game mode from the  <br> dropdown menu to the right of the control panel. <br>"
        		+ "2. When entering text in the Dimension Text Box, you must then <br>"
        		+ "hit reset in order to view the new board dimension.<br>"
        		+ "3. The maximum dimensions of the board is 20 x 20 <br>"
        		+ "4. You can choose to load a preset board file from<br>"
        		+ "the BOARD CONFIGURATIONS menu<html>");
        l.setFont(new Font("Courier New",Font.PLAIN, 14));

        popupPanel.add(l);
        popupPanel.add(button);
  
        /*
    	 * creates the popup
    	 */
        fullPopup = pf.getPopup(f, popupPanel, (gamePanel.getWidth()/2 - popupPanel.getWidth()/2), gamePanel.getHeight()/2);
	}
	
	/*
	 * class TextBubbleBorder creates the rounded borders for the boxes on the screen. 
	 */
	
	
	class TextBubbleBorder extends AbstractBorder {
		/*
		 * color of the border
		 */
	    private Color color;
	    /*
		 * thickness of the border
		 */
	    private int thickness = 6;
	    /*
		 * radii of the border
		 */
	    private int radii = 2;
	    private int pointerSize = 5;
	    private Insets insets = null;
	    private BasicStroke stroke = null;
	    private int strokePad;
	    private int pointerPad = 10;
	    private boolean left = true;
	    RenderingHints hints;

	    TextBubbleBorder(
	            Color color, int thickness, int radii, int pointerSize) {
	        this.thickness = thickness;
	        this.radii = radii;
	        this.pointerSize = pointerSize;
	        this.color = color;

	        stroke = new BasicStroke(thickness);
	        strokePad = thickness / 3;

	        hints = new RenderingHints(
	                RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);

	        int pad = radii + strokePad;
	        int bottomPad = pad + pointerSize + strokePad;
	        insets = new Insets(pad, pad, bottomPad, pad);
	    }

	    @Override
	    public Insets getBorderInsets(Component c) {
	        return insets;
	    }

	    @Override
	    public Insets getBorderInsets(Component c, Insets insets) {
	        return getBorderInsets(c);
	    }

	    /*
		 * paintBorder paints the borders of the boxes
		 */
	    
	    @Override
	    public void paintBorder(
	            Component c,
	            Graphics g,
	            int x, int y,
	            int width, int height) {

	        Graphics2D g2 = (Graphics2D) g;

	        int bottomLineY = height - thickness - pointerSize;

	        RoundRectangle2D.Double bubble = new RoundRectangle2D.Double(
	                0 + strokePad,
	                0 + strokePad,
	                width - thickness,
	                bottomLineY,
	                radii,
	                radii);

	        Polygon pointer = new Polygon();

	        if (left) {
	            // left point
	            pointer.addPoint(
	                    strokePad + radii + pointerPad,
	                    bottomLineY);
	            // right point
	            pointer.addPoint(
	                    strokePad + radii + pointerPad + pointerSize,
	                    bottomLineY);
	            // bottom point
	            pointer.addPoint(
	                    strokePad + radii + pointerPad + (pointerSize / 2),
	                    height - strokePad);
	        } else {
	            // left point
	            pointer.addPoint(
	                    width - (strokePad + radii + pointerPad),
	                    bottomLineY);
	            // right point
	            pointer.addPoint(
	                    width - (strokePad + radii + pointerPad + pointerSize),
	                    bottomLineY);
	            // bottom point
	            pointer.addPoint(
	                    width - (strokePad + radii + pointerPad + (pointerSize / 2)),
	                    height - strokePad);
	        }

	        Area area = new Area(bubble);
	        area.add(new Area(pointer));

	        g2.setRenderingHints(hints);

	        // Paint the BG color of the parent, everywhere outside the clip
	        // of the text bubble.
	        Component parent  = c.getParent();
	        if (parent!=null) {
	            Color bg = parent.getBackground();
	            Rectangle rect = new Rectangle(0,0,width, height);
	            Area borderRegion = new Area(rect);
	            borderRegion.subtract(area);
	            g2.setClip(borderRegion);
	            g2.setColor(bg);
	            g2.fillRect(0, 0, width, height);
	            g2.setClip(null);
	        }

	        g2.setColor(color);
	        g2.setStroke(stroke);
	        g2.draw(area);
	    }
	}
	
	/*
	 * creates a Timer for the game
	 */
	
	Timer timer = new Timer(delay, new TimerCallback());
	int time = 0;
	int speed = 0;
	
	/*
	 * class TimerCallback is called every delay ms.
	 * It checks the array using the checkArray() method, increments time, 
	 * prints this value, prints the current delay, and reflects the updated
	 * time value in the numGenerations box.
	 */
	
	protected class TimerCallback implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			checkArray();
			
			time++;
			
			System.out.println(time);
			
			System.out.println("Delay (ms): " + ((Timer) e.getSource()).getDelay());
			numGenerations.setText(String.valueOf(time));
		}
	}
	
	/*
	 * class popupButtonListener is called when the popup JButton is clicked. 
	 * If the EXIT button is pressed, it hides the popup, otherwise, when
	 * the INFO button is clicked, the popup is shown.
	 */
	
	protected class popupButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			 String buttonText = e.getActionCommand();
		        if (buttonText.equals("EXIT")) {
		        	fullPopup.hide();
		  
		        	fullPopup = pf.getPopup(f, popupPanel, (gamePanel.getWidth()/2 - popupPanel.getWidth()/2), gamePanel.getHeight()/2);
		        }
		        else
		        	fullPopup.show();
			
		}
		
	}
	
	/*
	 * class gameModeListener is called when a new game mode is selected. It repaints the
	 * board based on the currently selected game mode, and prints that string to 
	 * the console.
	 */
	
	
	protected class gameModeListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
	        String gameMode = (String)gameModeList.getSelectedItem();
	        repaint();
	        System.out.println("Game mode: " + gameMode);
	    }
	}
	
	/*
	 * method setGameMode is used in the Board class to correctly repaint
	 * certain items within the JComponent.
	 */
	
	protected static String setGameMode() {
		String gameMode = (String)gameModeList.getSelectedItem();
		return gameMode;
	}
	
	/*
	 * class fileNameListener is called when a new item from the fileNameStrings
	 * list is selected. It sets fileName to the selected string.
	 */
	
	protected class fileNameListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
	        String fileName = (String)fileNameList.getSelectedItem();
	        System.out.println("Game mode: " + fileName);
	    }
	}
	
	/*
	 * class dimensionTextListener is called when a new value is entered
	 * into the text field.
	 */
	
	protected class dimensionTextListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e){
			DIM = Integer.valueOf(dimension.getText());
			Board.reset();
		}
		
	}
	
	/*
	 * class checkDimension() is called if the dimensions of the board are 
	 * greater than 20. For some reason the program does not like numbers
	 * greater than 20.
	 */
	
	public static void checkDimension() throws ArrayIndexOutOfBoundsException{
		if (Integer.valueOf(dimension.getText()) > 20) {
			throw new ArrayIndexOutOfBoundsException("ArrayIndexOutOfBoundsException");
		}
	}
	/*
	 * method getDensity() is used in the Board class to properly randomize the board
	 * according to the currently set density from the density slider.
	 */
	public static int getDensity() {
		return density;
	}
	
	/*
	 * class densitySliderListener is called when the value of the density slider
	 * is changed. It resets the board according to that new density, then
	 * repaints it.
	 */
	
	protected class densitySliderListener implements ChangeListener {
		
		public void stateChanged(ChangeEvent e) {
            density = densitySlider.getValue();
            
            System.out.println("Density: " + density);
            Board.reset();

           repaint();
        }
	}
	
	/*
	 * class speedSliderListener is called when the value of the speed slider
	 * is changed. It updates the timer delay to reflect that new value.
	 */

	protected class speedSliderListener implements ChangeListener {
		
		public void stateChanged(ChangeEvent e) {
        	
            int value = speedSlider.getValue();
            /*
             * the bigger the number is, the faster the simulation moves, 
             * the bigger the number, the smaller the delay
             */
            timer.setDelay(1000 / value);
        }
	}
	
	/*
	 * method setFileName is used in loadButtonHandler to convert the 
	 * text displayed in the JComboBox into a readable filename.
	 */
	
	protected static String setFileName() {
		String fileName = (String)fileNameList.getSelectedItem();
		if (fileName.equals("20 * 20")) {
			fileName = "src/20by20";
			System.out.println(fileName);
		}
		if (fileName.equals("15 * 15")) {
			fileName = "src/15by15";
			System.out.println(fileName);
		}
		if (fileName.equals("10 * 10")) {
			fileName = "src/10by10";
			System.out.println(fileName);
		}
		return fileName;
	}
	
	/*
	 * class FileLoadButtonHandler is called when the LOAD button is clicked.
	 * It checks the currently selected filename against the files that exist
	 * using the setFileName() method. If the file exists, it sets that file to the
	 * current array and repaints the board according to the imported file.
	 */
	
	protected class FileLoadButtonHandler implements ActionListener {	
		public void actionPerformed(ActionEvent e) {
			System.out.println("FileLoadButtonHandler.actionPerformed");
			
			String filename = setFileName();
			System.out.println("new filename: " + filename);
			
			try {
				Board.currentArray = fileReader(filename);
				repaint();
			} catch (FileNotFoundException e1) {
				System.out.println("File not found. Sorry.");
			}
						
		}
	}
	
	/*
	 * method fileReader is called when within the FileLoadButtonHandler class. It
	 * reads the text file in using a scanner, and converts the 1s and 0s to 
	 * ALIVE or DEAD cells. It returns that board to the FileLoadButtonHandler.
	 */
	
	@SuppressWarnings("resource")
	public static boolean[][] fileReader(String filename) throws FileNotFoundException {
		System.out.println("Reading file...");
		Scanner inputFile;
		boolean[][]boardFromFile = new boolean[DIM][DIM];
		
		File fileToBeRead = new File(filename);
		
		inputFile = new Scanner(fileToBeRead);
		
		for(int row = 0; row < DIM; row++) {
            for(int column = 0; column < DIM; column++) {
            	if (inputFile.hasNextInt()){
            		if(inputFile.nextInt() == 1) {
            			boardFromFile[row][column] = Board.ALIVE;
            		}
            		else {
            			boardFromFile[row][column] = Board.DEAD;
            		}
            	}
            }
		}
		return boardFromFile;	
	}
	
	/*
	 * class InformationButtonHandler is called when the INFO button is pressed.
	 * It displays the popup. 
	 */
	
	protected class InformationButtonHandler implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {

	        String btnText = (informationButton.getText());
			if (btnText.equals("INFO")) {
				fullPopup.show();
			}
		}
		
	}
	
	/*
	 * class StartStopButtonHandler is called when the START/STOP button is pressed.
	 * If the button text is START, when clicked, the timer is started and the button
	 * text changes to STOP. 
	 * If the button text is STOP, when clicked, the timer is stopped and the button
	 * text changes to START. 
	 */
	
	protected class StartStopButtonHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			System.out.println("StartStopButtonHandler.actionPerformed");
			
			//changes startStopButton text to start or stop, depending on click
			String btnText = (startStopButton.getText());
			if (btnText.equals("START")) {
				timer.start();
				startStopButton.setText("STOP");
			}
			else {
				timer.stop();
				startStopButton.setText("START");
			}
		}
	}
	
	/*
	 * class NextButtonHandler is called when the NEXT button is pressed.
	 * As a precaution, if the button is clicked while the game is already
	 * running (i.e. the START button has already been pressed and now reads
	 * STOP), it changes that button to read START. 
	 * 
	 * It stops the timer, checks the array using the checkArray() method,
	 * then increases the timer by one "delay" increment, and updates 
	 * numGenerations and time accordingly.
	 */
	
	
	protected class NextButtonHandler implements ActionListener {	
		public void actionPerformed(ActionEvent e) {
			System.out.println("NextButtonHandler.actionPerformed");
			System.out.println("Delay (ms): " + delay);
			timer.stop();
			checkArray();
			startStopButton.setText("START");
			timer.start();
			time++;
			System.out.println(time);
			numGenerations.setText(String.valueOf(time));
			timer.stop();			
		}
	}

	/*
	 * class ResetButtonHandler is called when the RESET button is pressed.
	 * It stops the timer, resets time to zero, resets the speed slider to 
	 * its original value (4), resets numGenerations, density, and
	 * randomizes the board according to the current density.
	 */
	
	protected class ResetButtonHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			System.out.println("ResetButtonHandler.actionPerformed");
			timer.stop();
			time = 0;
			
			//resets the slider to 4
			speedSlider.setValue(4);
			numGenerations.setText(String.valueOf(time));
			
			startStopButton.setText("START");
			density = densitySlider.getValue();
			
			Board.reset();
			
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}
}