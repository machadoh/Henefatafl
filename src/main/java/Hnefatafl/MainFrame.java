package Hnefatafl;

import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.io.*;
import java.lang.*;

public class MainFrame
{
	/*
	*dimensions of the window
	*/
	private final int HEIGHT = 700;
	private final int WIDTH = 600;
	
	/*
	* name of the window
	*/
	private JFrame _frame = new JFrame("Copenhagen Hnefatafl");
	
	GameBoard _gameBoard;
	GameLogic _gameLogic;
	ButtonPanel _buttonPanel;
	ScoreBoard _scoreBoard;
	
	/**
	* Constructor
	* creates the window	
	* adds each of the panels
	* sets up some initial variables
	*/
	public MainFrame()
	{
		//load the image displayed on taskbar
		try
		{
			Image img = ImageIO.read(getClass().getResource("/img/Wizard.png"));
			img = img.getScaledInstance(70,70,Image.SCALE_SMOOTH);
			_frame.setIconImage(img);
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		//set up the variables of the window
		_frame.setSize(WIDTH, HEIGHT);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setResizable(false);
		_frame.setVisible(true);
		
		
		_gameLogic = new GameLogic();
		_gameBoard = new GameBoard(_gameLogic);
		_scoreBoard = new ScoreBoard(_gameBoard, _gameLogic);
		_buttonPanel = new ButtonPanel(_gameLogic, _gameBoard);
		
		
		_frame.add(_scoreBoard, BorderLayout.NORTH);
		_frame.add(_gameBoard, BorderLayout.CENTER);
		_frame.add(_buttonPanel, BorderLayout.SOUTH);
		
		_gameLogic._setGameBoard(_gameBoard);
		_gameLogic._setScoreBoard(_scoreBoard);

		_gameLogic._startNewGame();
		
		_frame.setResizable(false);
		_frame.setVisible(true);
	}
	
	
}
