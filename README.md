# GameOfLife
/* CSC171 Project 3
 * README File
 * Date: April 2021
 */



The file "GameOfLifeGUI" creates the JFrame within which the board and all other JPanels,
  JButtons, JSliders, etc. reside.
The file "Board" creates a JComponent, the board, and is also in charge of repainting the various
  components within GameOfLifeGUI. 
  
 Note: GameOfLifeGUI labels use fonts that were installed on the designer's computer. We
 recognize that these fonts may not be on the recieving TA's computer, so have attached
 a screenshot of what the game looked like within the zip file titled GameOfLifeScreenshot.

Flourishes Implemented
1. Colors. You can choose "Game Modes" from the dropdown menu on the right side of the control panel.
  This uses JComboBox gameModeList and is within the JPanel GameModeBox.
  
2. Density. You can change the density using the density slider on top of the grid.
  This uses the JSlider densitySlider and is within JPanel densitySpeedBox.
  
3. Speed. You can change the speed of the animation using the speed slider on top of the grid.
  This uses the JSlider speedSlider and is within JPanel densitySpeedBox.
  
4. Load from a file. You can load from any of the preset files in the dropdown menu on the left side of
  the control panel and then click the "LOAD" button to load the file.
  This uses JComboBox fileNameList and JButton FileLoadButton and is within JPanel FileNameBox.
  
5. Set Dimensions of Board. You can change the dimensions of the gameboard by entering a new value in 
  the Dimension text field. **NOTE, sometimes, you can only enter a value SMALLER than the previous value. 
  This uses JTextField dimension and is within JPanel densitySpeedBox. 
  
Other Fun Implementations
1. Information Popup
  By clicking on the "INFO" button, a popup will appear with more helpful information about the program!
  This uses JPanel popupPanel and JButton informationButton.
2. Box Borders 
  We were able to implement rounded borders for our boxes, the code for which can be found in the
  TextBubbleBorder class in GameOfLifeGUI.
  
