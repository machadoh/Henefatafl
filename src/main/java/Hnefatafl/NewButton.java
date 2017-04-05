package Hnefatafl;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class NewButton extends JButton
{
	GameLogic _gl;
	GameBoard _gb;
	public NewButton(String s, GameLogic gl, GameBoard gb)
	{
		super(s);
		addActionListener(new NewButtonListener());
		_gl = gl;
		_gb = gb;
	}
	
	class NewButtonListener implements ActionListener
	{
		/**
		* loads information from the start file
		* @param ActionEvent the event caused by user clicking on the NewGame button
		*/
		public void actionPerformed(ActionEvent e)
		{
			_gb._getUnits("src/main/resources/start/standardStart.txt");

			_gl._startNewGame();
			
		}
	}
}