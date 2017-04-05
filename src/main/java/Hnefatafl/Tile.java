package Hnefatafl;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class Tile extends JButton
{
	
	private int _x, _y, _type;
	private GameLogic _gl;
	private Tile _thisTile;
	
	/**
	* Tile Constructor
	*/
	public Tile(int x, int y, int type, GameLogic gl)
	{
		super();
		addActionListener(new TileListener());
		_x=x;
		_y=y;
		_type = type;
		_setTileImage(type);
		_gl = gl;
		_thisTile = this;
	}
	
	/** this function sets one of the images used in this game to this tile.
	* @param type: an int corresponding to each of the different images used in game
	* type - 0 ground tile
	* type - 1 blue ground tile
	* type - 2 ground tile with soldier
	* type - 3 green ground tile with soldier
	* type - 4 ground with wizard
	* type - 5 green ground with wizard
	* type - 6 lava with wizard
	* type - 7 green lava with wizard
	* type - 8 ground with enemy
	* type - 9 green ground with enemy
	* type - 10 lava tile
	* type - 11 blue lava tile
	*/
	private void _setTileImage(int type)
	{
		Image img;
		try
		{
			if(type == 0)
			{
				img = ImageIO.read(getClass().getResource("/img/GroundTile.png"));
				img = img.getScaledInstance(70,70,Image.SCALE_SMOOTH);
				Icon i = new ImageIcon(img);
				this.setIcon(i);
			}
			else if(type == 1)
			{
				img = ImageIO.read(getClass().getResource("/img/SelectableGroundTile.png"));
				img = img.getScaledInstance(70,70,Image.SCALE_SMOOTH);
				Icon i = new ImageIcon(img);
				this.setIcon(i);
			}
			else if(type == 2)
			{
				img = ImageIO.read(getClass().getResource("/img/GroundTilePlusSoldier.png"));
				img = img.getScaledInstance(70,70,Image.SCALE_SMOOTH);
				Icon i = new ImageIcon(img);
				this.setIcon(i);
			}
			else if(type == 3)
			{
				img = ImageIO.read(getClass().getResource("/img/SelectableGroundPlusSoldier.png"));
				img = img.getScaledInstance(70,70,Image.SCALE_SMOOTH);
				Icon i = new ImageIcon(img);
				this.setIcon(i);
			}
			else if(type == 4)
			{
				img = ImageIO.read(getClass().getResource("/img/GroundTilePlusWizard.png"));
				img = img.getScaledInstance(70,70,Image.SCALE_SMOOTH);
				Icon i = new ImageIcon(img);
				this.setIcon(i);
			}
			else if(type == 5)
			{
				img = ImageIO.read(getClass().getResource("/img/SelectableGroundPlusWizard.png"));
				img = img.getScaledInstance(70,70,Image.SCALE_SMOOTH);
				Icon i = new ImageIcon(img);
				this.setIcon(i);
			}
			else if(type == 6)
			{
				img = ImageIO.read(getClass().getResource("/img/LavaTilePlusWizard.png"));
				img = img.getScaledInstance(70,70,Image.SCALE_SMOOTH);
				Icon i = new ImageIcon(img);
				this.setIcon(i);
			}
			else if(type == 7)
			{
				img = ImageIO.read(getClass().getResource("/img/SelectableLavaTilePlusWizard.png"));
				img = img.getScaledInstance(70,70,Image.SCALE_SMOOTH);
				Icon i = new ImageIcon(img);
				this.setIcon(i);
			}
			else if(type == 8)
			{
				img = ImageIO.read(getClass().getResource("/img/GroundTilePlusEnemy.png"));
				img = img.getScaledInstance(70,70,Image.SCALE_SMOOTH);
				Icon i = new ImageIcon(img);
				this.setIcon(i);
			}
			else if(type == 9)
			{
				img = ImageIO.read(getClass().getResource("/img/SelectableGroundPlusEnemy.png"));
				img = img.getScaledInstance(70,70,Image.SCALE_SMOOTH);
				Icon i = new ImageIcon(img);
				this.setIcon(i);
			}
			else if(type == 10)
			{
				img = ImageIO.read(getClass().getResource("/img/LavaTile.png"));
				img = img.getScaledInstance(70,70,Image.SCALE_SMOOTH);
				Icon i = new ImageIcon(img);
				this.setIcon(i);
			}
			else if(type == 11)
			{
				img = ImageIO.read(getClass().getResource("/img/SelectableLavaTile.png"));
				img = img.getScaledInstance(70,70,Image.SCALE_SMOOTH);
				Icon i = new ImageIcon(img);
				this.setIcon(i);
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	/**
	* @return returns the x position of the tile on the board
	*/
	public int _getX()
	{
		return _x;
	}
	/**
	* @param int x the number to update x to
	*/
	public void _setX(int x)
	{
		_x = x;
	}
	/**
	* @return returns the y position of the tile on the board
	*/
	public int _getY()
	{
		return _y;
	}
	/**
	* @param int y the number to update y to
	*/
	public void _setY(int y)
	{
		_y = y;
	}
	/**
	* @return returns the type of the tile on the board
	*/
	public int _getType()
	{
		return _type;
	}
	/**
	* @param int type the number to update the type to, a number between 0-11
	*/
	public void _setType(int type)
	{
		assert(type >= 0 && type <= 11);
		_type = type;
		_setTileImage(type);
	}
	
	/**
	* returns a value describing what type of unit the tile is. 0 = kingdom, 1 = hoard, 2 = other
	* @return Integer a value corresponding to the type of unit
	*/
	public int _isKingdomUnit()
	{
		if(_type > 1 && _type < 8)
			return 0;
		else if(_type == 8 || _type == 9)
			return 1;
		else
			return 2;
	}
	
	/**
	* @return This returns a string version of the tile information
	*/
	public String _toString()
	{
		String temp = _x + " " + _y + " " + _type;
		return temp;
	}
	
	class TileListener implements ActionListener
	{
		/**
		* Whenever a tile is clicked it is sent to gamelogic to be interpreted.
		*/
		public void actionPerformed(ActionEvent e)
		{
			_gl._tileClicked(_thisTile);
		}
	}
}