package Hnefatafl;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class LoadButton extends JButton
{
	GameLogic _gl;
	GameBoard _gb;
	/**
	* constructor for the button
	* @param String what text you want on the button
	*/
	public LoadButton(String s, GameLogic gl, GameBoard gb)
	{
		super(s);
		addActionListener(new LoadButtonListener());
		_gl = gl;
		_gb = gb;
	}
	
	class LoadButtonListener implements ActionListener
	{
		/**
		* Create an option pane with all the possible save files to choose from
		* @param ActionEvent the event caused by user clicking on the LoadButton
		*/
		public void actionPerformed(ActionEvent e)
		{
			String message = "Please choose which file you would like to load.";
			String title = "Load File";
			File f = new File("src/main/resources/save/");
			File [] savedGames = f.listFiles();
			
			String [] options = new String[savedGames.length];
			for(int i=0; i<savedGames.length; i++)
			{
				options[i] = savedGames[i].getName();
			}
			int loadNum = JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if(loadNum >= 0)
			{
				_gb._getUnits("src/main/resources/save/"+options[loadNum]);

				_gl._startNewGame();
			}
		}
	}
}