// CS 0401 Fall 2016
// Assignment 5 "starter" program.  You may use this as a starting point for
// Assignment 5.  

// Note 1: You will only receive credit for your additions and not for any of
// the functionality that is already provided.
 
// Note 2: You are not obliged to use this code at all.  If you can implement
// the requirements of Assignment 5 from scratch with your own code, that is
// also fine.  However, you must use the Mosaic, MCircle and MSquare classes
// as given without any changes.

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.print.*;

public class Assig5
{
	public static String software = "Mosaic Art 1.0";
	private Mosaic m;
	private DrawPanel thePanel; 	// DrawPanel is a subclass of JPanel
									// See details below.
	private JPanel buttonPanel;
	private JFrame theWindow;
	private JButton paintIt, eraseIt, editIt;																	
		private JPopupMenu popUp;																				
	
	// ArrayList of Mosaic to store the individual shapes.  Note that
	// since Mosaic is the superclass of both MCircle and MSquare, both
	// shapes can be stored in this ArrayList
	private ArrayList<Mosaic> chunks;
	
	private double X, Y, tempX, tempY;
	private double newSize;
	private Color newColor, secColor, tempColor;
	private int selected;

	private boolean painting, erasing, editing;																	
	private boolean isCircle = true;																			
	private String currFile;
		
	private JMenuBar theBar;
	private JMenu fileMenu, defaultsMenu, effectsMenu, shapesMenu;												
	private JMenuItem endProgram, saveAs, printScene, newFile, openFile, save;													
	private JMenuItem setColor, setSize;																		
		private JMenuItem square, circle;																			
		private JMenuItem recolor, resize;																			
	private JMenuItem twistShapes, twistColors;																	
		private int shapeTwistCount = 1;
		private int colorTwistCount = 1;
		private int shapeTwist = 1;
		private int colorTwist = 1;
	
	private int numLines = 0;																					//number of lines in the file loaded in by the user
		
	public Assig5()
	{
		theWindow = new JFrame(software);
		theWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		thePanel = new DrawPanel(600, 600);
		newSize = 15;
		newColor = Color.RED;

		selected = -1;
		painting = false;
		erasing = false;
		editing = false;																						
		paintIt = new JButton("Paint");
		eraseIt = new JButton("Erase");
		editIt = new JButton("Edit");																			
		ActionListener bListen = new ButtonListener();
		paintIt.addActionListener(bListen);
		eraseIt.addActionListener(bListen);
		editIt.addActionListener(bListen);																		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,3));																
		buttonPanel.add(paintIt);
		buttonPanel.add(eraseIt);
		buttonPanel.add(editIt);																			
		theWindow.add(buttonPanel, BorderLayout.SOUTH);
		theWindow.add(thePanel, BorderLayout.NORTH);
		
		theBar = new JMenuBar();
		theWindow.setJMenuBar(theBar);				
//This block of prewritten code creates the file menu		
		fileMenu = new JMenu("File");
		theBar.add(fileMenu);
		newFile = new JMenuItem("New");																		
		openFile = new JMenuItem("Open");																	
		save = new JMenuItem("Save");
		saveAs = new JMenuItem("Save As");
		printScene = new JMenuItem("Print");
		endProgram = new JMenuItem("Exit");
		fileMenu.add(newFile);																				
		fileMenu.add(openFile);																				
		fileMenu.add(save);
		fileMenu.add(saveAs);
		fileMenu.add(printScene);
		fileMenu.add(endProgram);
		newFile.addActionListener(bListen);																		
		openFile.addActionListener(bListen);																	
		save.addActionListener(bListen);
		saveAs.addActionListener(bListen);
		
		printScene.addActionListener(bListen);
		endProgram.addActionListener(bListen);
//This block of code creates the defaults menu
		defaultsMenu = new JMenu("Defaults");
		theBar.add(defaultsMenu);
		setColor = new JMenuItem("Set Color");
		setSize = new JMenuItem("Set Size");
		shapesMenu = new JMenu("Set Shape");
		defaultsMenu.add(setColor);
		defaultsMenu.add(setSize);
		defaultsMenu.add(shapesMenu);
		setColor.addActionListener(bListen);
		setSize.addActionListener(bListen);
	//This block of code creates the submenu of set Shape
			square = new JMenuItem("Square");
			circle = new JMenuItem("Circle");
			shapesMenu.add(square);
			shapesMenu.add(circle);
			square.addActionListener(bListen);
			circle.addActionListener(bListen);
	//This block of code creates the popupMenu
			popUp = new JPopupMenu();
			recolor = new JMenuItem("Recolor");
			recolor.setHorizontalTextPosition(JMenuItem.RIGHT);
			popUp.add(recolor);
			recolor.addActionListener(bListen);
			resize = new JMenuItem("Resize");
			resize.setHorizontalTextPosition(JMenuItem.RIGHT);
			popUp.add(resize);
			resize.addActionListener(bListen);
//This block of code creates the effects menu
		effectsMenu = new JMenu("Effects");
		theBar.add(effectsMenu);
		twistShapes = new JMenuItem("Start Twisting Shapes");
		twistColors = new JMenuItem("Start Twisting Colors");
		effectsMenu.add(twistShapes);
		effectsMenu.add(twistColors);
		twistShapes.addActionListener(bListen);
		twistColors.addActionListener(bListen);
		
		theWindow.pack();
		theWindow.setVisible(true);
	}
	
	private class DrawPanel extends JPanel
	{
		private int prefwid, prefht;
		
		// Initialize the DrawPanel by creating a new ArrayList for the images
		// and creating a MouseListener to respond to clicks in the panel.
		public DrawPanel(int wid, int ht)
		{
			prefwid = wid;
			prefht = ht;
			
			chunks = new ArrayList<Mosaic>();
			
			// Add MouseListener to this JPanel to respond to the user
			// pressing the mouse.  In your assignment you will also need a
			// MouseMotionListener to respond to the user dragging the mouse.
			addMouseListener(new MListen());
			addMouseMotionListener(new MListen());																		//add this to enable mouseDragged
		}
		
		// This method allows a window that encloses this panel to determine
		// how much space the panel needs.  In particular, when the "pack()"
		// method is called from an outer JFrame, this method is called
		// and the result determines how much space is needed for
		// the JPanel
		public Dimension getPreferredSize()
		{
			return new Dimension(prefwid, prefht);
		}
		
		// This method is responsible for rendering the images within the
		// JPanel.  You should not have to change this code.
		public void paintComponent (Graphics g)       
		{
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			for (int i = 0; i < chunks.size(); i++)
			{
				chunks.get(i).draw(g2d);
			}
		}
		
		// Add a new Mosaic and repaint.  The repaint() method call requests
		// that the panel be redrawn.  Make sure that you call repaint()
		// after changes to your scenes so that the changes are actually
		// exhibited in the display.
		public void add(Mosaic m)
		{
			chunks.add(m);
			repaint();
		}
		
		// Remove the Mosaic at index i and repaint
		public void remove(int i)
		{
			if (chunks.size() > i)
				chunks.remove(i);
			repaint();
		}
		
		// Select a Mosaic that contains the point (x, y).  Note that this
		// is using the contains() method of the Mosaic class, which in turn
		// is checking within the underlying RectangularShape of the object.
		public int select(double x, double y)
		{
			for (int i = 0; i < chunks.size(); i++)
			{
				if (chunks.get(i).contains(x, y))
				{
					return i;
				}
			}
			return -1;
		}
	}
	
	// Save the images within the window to a file.  Run this program to see the 
	// format of the saved file.
	public void saveImages()
	{
		try
		{
			PrintWriter P = new PrintWriter(new File(currFile));
			P.println(chunks.size());
			for (int i = 0; i < chunks.size(); i++)
			{
				P.println(chunks.get(i).saveFile());
			}
			P.close();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(theWindow, "I/O Problem - File not Saved");
		}
		//If NullPointerException is handled, save function doesn't respond.
	}
	
	//Read in a file that was previously saved
	public void loadImage(String fileName)
	{
		Scanner in = new Scanner(System.in);																	//Create scanner defaulted to initialize to System.in
		File theFile;																							//Create the file to be loaded
		try
		{
			theFile = new File(fileName);																		//Initialize the file
			in = new Scanner(theFile);																			//Correctly initialize the scanner to the file
		}
		catch(NullPointerException g)																																			
		{
			
		}
		catch(FileNotFoundException h)																			//If the file can not be found							
		{
			JOptionPane.showMessageDialog(thePanel, "The file could not be found");								
		}
		MCircle tempC;																							//Create temporary circle mosaic																			
		MSquare tempS;																							//Create temporary square mosaic

		chunks.clear();																							//Clear the frame
																							
		if(in.hasNextLine())																					//If there is a first line of code
			numLines = Integer.parseInt(in.nextLine());															//Parse it for the total number of objects

		while(in.hasNextLine())																					//While there are more lines to the file
		{
			String currLine = in.nextLine();																	//Convert the next line into a string
			String [] eachWord = currLine.split(",");															//Convert the string into an array
			String shape = eachWord[0];																			//Set the shape equal to the first word in the array
			double width = Double.parseDouble(eachWord[1]);														//Set the width equal to the second word in the array
			double xPos = Double.parseDouble(eachWord[2]);														//Set the xPos equal to the third word in the array
			double yPos = Double.parseDouble(eachWord[3]);														//Set the yPos equal to the fourth word in the array
			String colors = eachWord[4];																		//Set a new String, colors, equal to the fifth word in the array
			String [] rgb = colors.split(":");																	//Convert that string into an array
			int red = Integer.parseInt(rgb[0]);																	//Set red equal to the first number in the array
			int green = Integer.parseInt(rgb[1]);																//Set green equal to the second number in the array
			int blue = Integer.parseInt(rgb[2]);																//Set blue equal to the third number in the array
			Color c = new Color(red,green,blue);																//Create a new color from these rgb values
			
			if(shape.equals("Circle"))																			//If the first word was Circle
			{
				tempC = new MCircle(width, xPos, yPos, c); 														//Create a circle of the specified dimensions and color
				chunks.add(tempC);																				//And add it
			}
			else																								//If it was Square
			{
				tempS = new MSquare(width, xPos, yPos, c); 														//Create a square of the specified dimensions and color
				chunks.add(tempS);																				//And add it
			}
		}
		thePanel.repaint();																						//Update the panel
		theWindow.setTitle(software + " - " + fileName);														//Update the name of the window
	}

	//Check if the file has been changed since its last save	return true if it is the same
	public boolean compareImage()
	{	
		//Scan in the last save
		Scanner in = new Scanner(System.in);
		File lastSaveFile;
		try																										//Attempt to read in the file and initialize the scanner
		{
			lastSaveFile = new File(currFile);
			in = new Scanner(lastSaveFile);
		}
		catch(NullPointerException g)																			
		{
			return false;	//There is no previous save
		}
		catch(FileNotFoundException j)																			//If there is no previous file, return false
		{
			return false; //There is no previous save
		}
		
		//Convert it to a stringbuilder
		StringBuilder lastSaveSB = new StringBuilder();
		while(in.hasNextLine())
			lastSaveSB.append(in.nextLine());
		
		//Save the current image
		currFile = (currFile+".temp");
		saveImages();
		File currStateFile = new File(currFile);
		
		//And do it all again
		//Scan it in
		try
		{
			in = new Scanner(currStateFile);
		}
		catch(FileNotFoundException j)
		{
			return false;
		}

		//Convert it to a stringbuilder
		StringBuilder currStateSB = new StringBuilder();
		while(in.hasNextLine())
			currStateSB.append(in.nextLine());
		
		//For the sake of cleanliness, delete all the temp files
		in.close();
		currStateFile.delete();

		//Compare the stringbuilders
		return lastSaveSB.toString().equals(currStateSB.toString());
	}
	
	//ASk the user if they would like to save
	public boolean savePrompt()
	{
		int response = JOptionPane.showConfirmDialog (null, "Would you like to save your progress?");
		if(response == JOptionPane.YES_OPTION)
		{	
			try
			{
				saveImages();
			}
			catch(NullPointerException g)
			{
				currFile = JOptionPane.showInputDialog(theWindow,"Enter file name");
				saveImages();
				theWindow.setTitle(software + " - " + currFile);
			}
			chunks.clear();																			//clear the ArrayList
			theWindow.setTitle(software);
		}
		else if(response == JOptionPane.NO_OPTION)
		{
			chunks.clear();																			//clear the ArrayList
			theWindow.setTitle(software);
		}
		else
		{
			return false;
		}
		
		return true;
	}

	// Listener for some buttons.  Note that the JMenuItems are also listened
	// for here.  Like JButtons, JMenuItems also generate ActionEvents when
	// they are clicked on.  You will need to add more JButtons and JMenuItems
	// to your program and the logic of handling them will also be more
	// complex.  See details in the Assignment 5 specifications.
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//Buttons
			if (e.getSource() == paintIt)
			{
				painting = true;
				paintIt.setForeground(Color.RED);
				erasing = false;
				eraseIt.setForeground(Color.BLACK);
				editing = false;
				editIt.setForeground(Color.BLACK);
			}
			else if (e.getSource() == eraseIt)
			{
				painting = false;
				paintIt.setForeground(Color.BLACK);
				erasing = true;
				eraseIt.setForeground(Color.RED);
				editing = false;
				editIt.setForeground(Color.BLACK);
			}
			else if (e.getSource() == editIt)															
			{
				painting = false;
				paintIt.setForeground(Color.BLACK);
				erasing = false;
				eraseIt.setForeground(Color.BLACK);
				editing = true;
				editIt.setForeground(Color.RED);
			}
			//File Menu
			else if (e.getSource() == newFile)															//Create a new file
			{		
				if(!compareImage())																		//If the file has changed
				{
					savePrompt();																		//Ask the user if they'd like to save
				}
				else																					//Otherwise
				{	
					chunks.clear();																		//Clear the image
					theWindow.setTitle(software);
				}
				thePanel.repaint();																		//Display the cleared screen
			}
			else if (e.getSource() == openFile)															//Open an existing file
			{
				if(!compareImage())
				{
					savePrompt();
				}
				String loadFile = JOptionPane.showInputDialog(theWindow, "Enter file name");
				if(loadFile != null)
					loadImage(loadFile);					
			}
			else if(e.getSource() == save)																//Save current file
			{
				try
				{
					saveImages();
				}
				catch(NullPointerException g)
				{
					currFile = JOptionPane.showInputDialog(theWindow,"Enter new file name");
					if(currFile != null)
					{
						saveImages();
						theWindow.setTitle(software + " - " + currFile);
					}
				}
			}
			else if (e.getSource() == saveAs)
			{
				currFile = JOptionPane.showInputDialog(theWindow,"Enter new file name");
				if(currFile != null)
				{
					saveImages();
					theWindow.setTitle(software + " - " + currFile);
				}
			}
			else if (e.getSource() == endProgram)
			{
				boolean cancelSwitch = true;
				if(!compareImage())
				{
					cancelSwitch = savePrompt();
				}
				
				if(cancelSwitch)
					System.exit(0);
			}
			else if (e.getSource() == printScene)
			{
				 Printable thePPanel = new thePrintPanel(thePanel); 
			     PrinterJob job = PrinterJob.getPrinterJob();
         		 job.setPrintable(thePPanel);
         		 boolean ok = job.printDialog();
         		 if (ok) 
         		 {
             	 	try {
                  		job.print();
             		} 
             		catch (PrinterException ex) {
              		/* The job did not successfully complete */
             		}
             	 }
        	}
			//Default Menu
			else if (e.getSource() == setColor)															//Set the color of the mosaics
			{
				newColor = JColorChooser.showDialog(null, "Set Default Color", newColor);
				if(colorTwistCount%2 == 0)
				{
					colorTwistCount = 1;
					JOptionPane.showMessageDialog(null, "Color Twisting has been turned off");
					twistColors.setText("Start Twisting Colors");
				}
				thePanel.repaint();
			}
			else if (e.getSource() == setSize)															//Set the size of the mosaics
			{
				String sizeChange = JOptionPane.showInputDialog(null, "Enter New Default Size");
				newSize = Double.parseDouble(sizeChange);
				
			}
			else if (e.getSource() == square)															//Set the shape of the mosaics																	
			{
				isCircle = false;
				if(shapeTwistCount%2 == 0)
				{
					shapeTwistCount = 1;
					JOptionPane.showMessageDialog(null, "Shape Twisting has been turned off");
					twistShapes.setText("Start Twisting Shapes");
				}
			}
			else if (e.getSource() == circle)															//Set the shape of the mosaics																
			{
				isCircle = true;
				if(shapeTwistCount%2 == 0)
				{
					shapeTwistCount = 1;
					JOptionPane.showMessageDialog(null, "Shape Twisting has been turned off");
					twistShapes.setText("Start Twisting Shapes");
				}
			}
			//Effects Menu
			else if (e.getSource() == twistShapes)														//Enable shape twisting
			{
				if(shapeTwistCount%2 == 0)
					twistShapes.setText("Start Twisting Shapes");
				else
					twistShapes.setText("Stop Twisting Shapes");
				shapeTwistCount++;
			}
			else if (e.getSource() == twistColors)														//Enable color twisting
			{
				if(colorTwistCount%2 == 0)
					twistColors.setText("Start Twisting Colors");
				else
				{
					twistColors.setText("Stop Twisting Colors");
					secColor = JColorChooser.showDialog(null, "Set Color to Twist", secColor);
				}
				colorTwistCount++;
			}
			//Edit Popup Menu
			else if (e.getSource() == recolor)															//Recolor a mosaic
			{
				int loc = thePanel.select(X,Y);
				if(loc != -1)
				{
					chunks.get(loc).setColor(JColorChooser.showDialog(null, "Change Color", newColor));
					chunks.get(loc).highlight(false);
				}
				thePanel.repaint();
			}
			else if (e.getSource() == resize)															//Resize a mosaic
			{
				int loc = thePanel.select(X,Y);
				if(loc != -1)
				{
					String sizeChangeIndiv = JOptionPane.showInputDialog(null, "Enter New Default Size");
					double newSizeIndiv = Double.parseDouble(sizeChangeIndiv);
					chunks.get(loc).setSize(newSizeIndiv);	
					chunks.get(loc).highlight(false);
				}
				thePanel.repaint();
			}
		}
	}
	
	// Simple mouse event handling to allow a mousePressed to add or remove
	// a Mosaic from the display.  You will need to enhance this
	// MouseAdapter and you will also need to add a MouseMotionListener to
	// your program.  In this simple program all of the Mosaics drawn are
	// MCircles and they all have the same size and color.  You must add in
	// your program the ability to change all of these attributes.
	int IX, IY;
	private class MListen extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			X = e.getX();  // Get the location where mouse was pressed
			Y = e.getY();
			IX = e.getX();
			IY = e.getY();
			
			if (painting)
			{
				// create new MCircle and add it to the ArrayList
				if(shapeTwistCount%2 == 1 && colorTwistCount%2 == 1)			//nothing selected
				{
					if(isCircle)																			//if it is a circle(true by default)
						m = new MCircle(newSize, X, Y, newColor);
					else																					//if not
						m = new MSquare(newSize, X, Y, newColor);											//make it a square
				}
				else if(shapeTwistCount%2 == 0 && colorTwistCount%2 == 1)		//shape twist selected
				{
					if(shapeTwist%2 == 1)
						m = new MCircle(newSize, X, Y, newColor);
					else
						m = new MSquare(newSize, X, Y, newColor);
					shapeTwist++;
				}
				else if(shapeTwistCount%2 == 1 && colorTwistCount%2 == 0)		//color twist selected
				{
					if(colorTwist%2 == 1)
						tempColor = newColor;
					else
						tempColor = secColor;
						
					if(isCircle)																			//if it is a circle(true by default)
						m = new MCircle(newSize, X, Y, tempColor);
					else																					//if not
						m = new MSquare(newSize, X, Y, tempColor);											//make it a square
					colorTwist++;
				}
				else															//both twists selected
				{
					if(colorTwist%2 == 1)
						tempColor = newColor;
					else
						tempColor = secColor;
					if(shapeTwist%2 == 1)
						m = new MCircle(newSize, X, Y, tempColor);
					else
						m = new MSquare(newSize, X, Y, tempColor);
					shapeTwist++;
					colorTwist++;
				}
				thePanel.add(m);
			}
			else if (erasing)
			{
				// see if the point is within a shape -- if so delete
				// that shape
				int loc = thePanel.select(X, Y);
				if (loc > -1)
				{
					thePanel.remove(loc);
				}
			}	
			else if (editing)
			{	
				if(e.getButton() == 3)		//if its right click AND its within a shape, popup menu with Recolor and Resize
				{
					int loc = thePanel.select(X,Y);
					if(loc != -1)
					{
						chunks.get(loc).highlight(true);
						thePanel.repaint();
					}
					popUp.show(thePanel, e.getX(), e.getY());
				}
			}
		}
		
		public void mouseDragged(MouseEvent e)
		{
			X = e.getX();  // Get the location where mouse was pressed
			Y = e.getY();

			if (painting)
			{		
				if(Math.sqrt(Math.pow((tempX-X),2) + Math.pow((tempY-Y),2)) > m.getSize())				//if the cursor is a full mosaic distance away from the last mosaic painted, place one
				{
					// create new MCircle and add it to the ArrayList
					if(shapeTwistCount%2 == 1 && colorTwistCount%2 == 1)
					{
						if(isCircle)																			//if it is a circle(true by default)
							m = new MCircle(newSize, X, Y, newColor);
						else																					//if not
							m = new MSquare(newSize, X, Y, newColor);											//make it a square
					}
					else if(shapeTwistCount%2 == 0 && colorTwistCount%2 == 1)		//shape twist selected
					{
						if(shapeTwist%2 == 1)
							m = new MCircle(newSize, X, Y, newColor);
						else
							m = new MSquare(newSize, X, Y, newColor);
						shapeTwist++;
					}
					else if(shapeTwistCount%2 == 1 && colorTwistCount%2 == 0)		//color twist selected
					{
						if(colorTwist%2 == 1)
							tempColor = newColor;
						else
							tempColor = secColor;
							
						if(isCircle)																			//if it is a circle(true by default)
							m = new MCircle(newSize, X, Y, tempColor);
						else																					//if not
							m = new MSquare(newSize, X, Y, tempColor);											//make it a square
						colorTwist++;
					}
					else															//both twists selected
					{
						if(colorTwist%2 == 1)
							tempColor = newColor;
						else
							tempColor = secColor;
						if(shapeTwist%2 == 1)
							m = new MCircle(newSize, X, Y, tempColor);
						else
							m = new MSquare(newSize, X, Y, tempColor);
						shapeTwist++;
						colorTwist++;
					}
				
					thePanel.add(m);
					tempX = X;
					tempY = Y;
				}
			}
			else if (erasing)																			//No changes necessary, works on drag as is
			{
				// see if the point is within a shape -- if so delete
				// that shape
				int loc = thePanel.select(X, Y);
				if (loc > -1)
				{
					thePanel.remove(loc);
				}
			}	
			else if (editing)																			//drag the mosaic to its new location
			{
				int loc = thePanel.select(X, Y);	//see if the point is within a shape -- if so return its spot in the arrayList
				if(loc > -1)
				{
					chunks.get(loc).highlight(true);
					thePanel.repaint();
					//Mosaic newM = chunks.get(loc);														//Create a temporary mosaic
					//thePanel.remove(loc);																	//Remove the current one
					//newM.move(X-IX, Y-IY);																//Move the new one						
					//thePanel.add(newM);																	//Add the new one
					chunks.get(loc).move(X-IX, Y-IY);														//Move the mosaic
					chunks.get(loc).highlight(false);
					thePanel.repaint();																		//Repaint it						//These both technically work but they're both shit
				}
			}
		}
	}

	public static void main(String [] args)
	{
		new Assig5();
	}
}

// This class is taken from the Web and is somewhat buggy but it does a basic
// print of the panel.
class thePrintPanel implements Printable
{
	JPanel panelToPrint;
	
	public int print(Graphics g, PageFormat pf, int page) throws
                                                        PrinterException
    {
        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform t = new AffineTransform();
        t.scale(0.9, 0.9);
        g2d.transform(t);
        g2d.translate(pf.getImageableX(), pf.getImageableY());
		//pf.setOrientation(PageFormat.REVERSE_LANDSCAPE);
        /* Now print the window and its visible contents */
        panelToPrint.printAll(g);

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
    
    public thePrintPanel(JPanel p)
    {
    	panelToPrint = p;
    }
}
