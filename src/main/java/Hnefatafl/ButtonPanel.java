package Hnefatafl;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class ButtonPanel extends JPanel
{
	/*
	* temporary holding place of buttons without functionality yet
	*/
	Button[] _buttons = new Button[1];
	
	/**
	* Constructor creates the buttons and adds them to the panel
	*/
	public ButtonPanel(GameLogic gl, GameBoard gb)
	{
		//set up layout of buttons
		super();
		setLayout(new GridLayout(1, 4));		
		
		//buttons with functionality
		NewButton nb = new NewButton("New Game", gl, gb);
		SaveButton sb = new SaveButton("Save", gb, gl);
		LoadButton lb = new LoadButton("Load", gl, gb);
		
		this.add(nb);
		this.add(sb);
		this.add(lb);
	}
}