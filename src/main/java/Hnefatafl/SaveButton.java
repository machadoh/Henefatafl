package Hnefatafl;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class SaveButton extends JButton
{
	GameBoard _gb;
	GameLogic _gl;
	/**
	* constructor for the button
	* @param String what text you want on the button
	*/
	public SaveButton(String s, GameBoard gb, GameLogic gl)
	{
		super(s);
		addActionListener(new SaveButtonListener());
		_gb = gb;
		_gl = gl;
	}
	
	class SaveButtonListener implements ActionListener
	{
		/**
		* prompts the user for a file name to save to
		* checks that the name is valid
		* calls the save function in GameBoard
		* @param ActionEvent the event caused by user clicking on the SaveButton
		*/
		public void actionPerformed(ActionEvent e)
		{
			if(!_gl._getGameOver())
			{
				boolean invalidInput = true, cannotSave = true;
				String saveName = "";
				while(cannotSave)
				{
					String dialog = "Please enter the name of this save file\n" +
						"Please note the following\n" +
						"If the file already exists it will be overwritten.\n" +
						"If the field is left empty your game will not save\n" +
						"Your name can only contain letters and numbers";
					saveName = JOptionPane.showInputDialog(dialog);
					if(saveName == null)
						break;
					if(saveName.equals(""))
						break;
					if(saveName.matches("[a-zA-Z0-9]*"))
						cannotSave = false;
				}
				if(!cannotSave)
					_gb._saveGame(saveName);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "You cannot save a completed game");
			}
		}
	}
}
