package Hnefatafl;

import java.awt.*;
import java.awt.Image.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class GameBoard extends JPanel
{
	/*
	* keeps track of the individual tiles
	*/
	private Tile[][] _tiles;
	
	/*
	* size of the board
	*/
	private int _size = 11;
	
	/*
	* a link to the game logic
	*/
	private GameLogic _gl;
	
	/*
	* keeps track of the number of units on each team
	*/
	private int _numKingdomUnits = 0, _numHoardUnits = 0;
	
	
	private Queue<String> _history;

	/*
	* Keep track of total movement in the game
	*/
	private int _totalMoves = 0;
	
	/**
	* constructor sets up an empty board and sets up each tile as a ground tile
	*/
	public GameBoard(GameLogic gl)
	{
		//set up variable for the panel containing the board
		super();
		this.setLayout(new GridLayout(_size, _size));
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setSize(400,400);
		_tiles = new Tile[_size][_size];
		_gl = gl;
		
		//initialize each tile and add them to the board
		for (int j = 0; j < _size; j++) 
		{
			for (int k = 0; k < _size; k++) 
			{
				try
				{
					_tiles[j][k] = new Tile(k, j, 0, _gl);
					
				} catch (Exception ex)
				{
					System.out.println(ex);
				}
				this.add(_tiles[j][k]);
			}
		}
		_getUnits("src/main/resources/start/standardStart.txt");
	}
	
	/**
	* This function reads in a board state based off of a textfile
	* @param String the path to the textfile
	*/
	public void _getUnits(String pathName)
	{
		try
		{
			_numKingdomUnits = 0;
			_numHoardUnits = 0;
			//read in position of the three different types of units and the lava tiles
			BufferedReader bfUnits = new BufferedReader(new FileReader(new File(pathName)));
			StringTokenizer st;
			
			int unitCount = Integer.parseInt(bfUnits.readLine());
			
			int x,y,type;
			
			for(int i=0; i<unitCount; i++)
			{
				st = new StringTokenizer(bfUnits.readLine());
				
				x = Integer.parseInt(st.nextToken());
				y = Integer.parseInt(st.nextToken());
				type = Integer.parseInt(st.nextToken());
				
				
				_tiles[y][x]._setType(type);
				if(type >= 2 &&  type <= 7) _numKingdomUnits++;
				if(type == 8 || type == 9) _numHoardUnits++;
			}

			_totalMoves = Integer.parseInt(bfUnits.readLine());

			// Kingdom movement
			if((_totalMoves%2) == 0)
			{
				_gl._setKingdomTurn(false);
			}
			//enemy movement
			else
			{
				_gl._setKingdomTurn(true);
			}
			
		} 
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	
	/**
	* This function when called collects the board state and the move history
	* writes them out to a file based on the name passed as an argument
	* @param fileName the name of the save file to be made or updated
	*/

	public void _saveGame(String fileName)
	{
		try
		{
			//connect buffer to the file we will save to
			BufferedWriter bw;
			
			File f = new File("src/main/resources/save/" + fileName + ".txt");
			if(f.exists())
			{
				f.delete();
				f.createNewFile();
				bw = new BufferedWriter(new FileWriter(f));
			}
			else
			{
				f.createNewFile();
				bw = new BufferedWriter(new FileWriter(f));
			}


			//save the number of units followed by their position and type
			String temp = Integer.toString(_tiles.length*_tiles[0].length);
			bw.write(temp + "\n", 0, temp.length()+1);
			for(int i=0; i<_tiles.length; i++)
			{
				for(int j=0; j<_tiles[i].length; j++)
				{
					temp = _tiles[j][i]._toString();
					bw.write(temp + "\n", 0, temp.length()+1);
				}
			}
			
			//save the number of moves followed by their starting and ending position
			_history = _gl._getHistory();
			int histSize = _history.size();
			if(_history.size() > 0)
			{
				histSize = _totalMoves+histSize;
				temp = Integer.toString(histSize);
				bw.write(temp + "\n", 0, temp.length()+1);
			}
			else
			{
				histSize = _totalMoves;
				temp = Integer.toString(histSize);
				bw.write(temp, 0, temp.length());
			}
			for(int i=_history.size(); i>0; i--)
			{
				temp = _history.remove();
				if(i>1)
					bw.write(temp + "\n", 0, temp.length()+1);
				else
					bw.write(temp, 0, temp.length());
			}
			bw.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	/**
	* returns the number of units on the hoard team
	* @returns number of hoard units
	*/
	public  int _getNumHoard()
	{
		return _numHoardUnits;
	}
	
	/**
	* returns the number of units on the kingdom team
	* @return number of kingdom units
	*/
	public  int _getNumKingdom()
	{
		return _numKingdomUnits;
	}
	
	public void _kingdomUnitTaken()
	{
		_numKingdomUnits--;
	}
	
	public void _hoardUnitTaken()
	{
		_numHoardUnits--;
	}
	
	/**
	* allows access to a tile on the board based on position
	* @returns tile based on x and y positions on the board
	*/
	public Tile _getTile(int x, int y)
	{
		return _tiles[y][x];
	}
	/**
	* Access _tile[][] and get type for each position
	* @returns 2d array with _types[y][x] 
	*/
	
	public byte[][] _getTileTypes()
	{
		byte[][] _types = new byte[11][11];
		for(int y = 0; y<11; y++)
		{
			for(int x=0; x<11; x++)
			{
				_types[y][x] = (byte) _tiles[y][x]._getType();
			} 
		}
		return _types;
	}
	
	public boolean _equals(GameBoard bs) 
	{
		for(int y = 0; y < _size; y++) 
		{
			for(int x = 0; x < _size; x++)
			{
				System.out.println(_tiles[y][x]._getType() + " " + bs._getTile(x,y)._getType());
				if(_tiles[y][x]._getType() != bs._getTile(x,y)._getType())
				{
					
					return false;
				}
			}
		}
		return true;
	}
}