package Hnefatafl;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class ScoreBoard extends JPanel
{
	/*
	* These are the variables used within the scoreboard
	*/
	private GameBoard _gb;
	private GameLogic _gl;
	private int _numKingdom;
	private int _numHoard;
	
	private JLabel _KingUnits;
	private JLabel _Turn;
	private JLabel _HoardUnits;
	
	/**
	* This sets up the score board which tells the player whose turn it is and how many units each team has left.
	*/
	public ScoreBoard(GameBoard gb, GameLogic gl)
	{
		super(new BorderLayout());
		
		_gb = gb;
		_gl = gl;
		
		_numKingdom = _gb._getNumKingdom();
		_numHoard = _gb._getNumHoard();
		
		_KingUnits = new JLabel("Kingdom Units " + _numKingdom);
		_Turn = new JLabel("Kingdom Turn");
		_HoardUnits = new JLabel("Hoard Units " + _numHoard);
		
		this.add(_KingUnits, BorderLayout.WEST);
		this.add(_Turn, BorderLayout.CENTER);
		this.add(_HoardUnits, BorderLayout.EAST);
		
		_Turn.setHorizontalAlignment(JLabel.CENTER);
	}
	
	/**
	 * This function updates the strings within the scoreboard.
	 * @param gameOver Is the game over? (T/F)
	 * @param winner 1 = Hoard | 2 = Kingdom
	 */
	public void _update(boolean gameOver, byte winner)
	{
		_numKingdom = _gb._getNumKingdom();
		_numHoard = _gb._getNumHoard();
		
		_KingUnits.setText("Kingdom Units " + _numKingdom);
		
		if(!gameOver) {
			if(_gl._isKingdomTurn())
				_Turn.setText("Kingdom Turn");
			else
				_Turn.setText("Hoard Turn");
		} else {
			if(winner == 1)
				_Turn.setText("Hoard Wins!");
			else if (winner == 2)
				_Turn.setText("Kingdom Wins!");
		}
		
		_HoardUnits.setText("Hoard Units " + _numHoard);
	}
}