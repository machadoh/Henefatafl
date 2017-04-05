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

public class GameLogic
{
	private int _turnCount, _turnsSinceCapture=0;
	private boolean _kingdomTurn, _pieceSelected, gameOver = false;
	private GameBoard _gb;
	private ScoreBoard _sb;
	private Tile _selected;
	private Queue<String> _moveHistory = new LinkedList<String>();
	private BoardHistory _boardHistory;
	
	byte winner = 0; // 1 = attackers, 2 = defenders

	/**
	* Constructor for GameLogic
	*/
	public GameLogic()
	{
		_turnCount = 1;
		_boardHistory = new BoardHistory();
	}
	

	/**
	* this sets the GameBoard variable
	* @param GameBoard this parameter sets up the gameboard the gamelogic will be attached to
	*/
	public void _setGameBoard(GameBoard gb)
	{
		_gb = gb;
	}
	
	/**
	* this sets the scoreBoard variable
	*/
	public void _setScoreBoard(ScoreBoard sb)
	{
		_sb = sb;
		_sb._update(gameOver, winner);
	}
	
	/**
	* @return queue returns each of the moves that have been made in this game.
	*/
	public Queue _getHistory()
	{
		return _moveHistory;
	}
	
	public boolean _getGameOver()
	{
		return gameOver;
	}
	

	public void _startNewGame()
	{
		gameOver = false;
		_sb._update(gameOver, winner);
		_boardHistory._clearHistory();
		_clearBoard();
		_setMovableGreen();
	}
	
	/**
	* This function sets all movable units to a green background
	*/
	public void _setMovableGreen()
	{
		for(int k=0; k<11;k++)
		{
			for(int j=0; j<11;j++)
			{
				if(_isPieceMovable(_gb._getTile(j,k)))
				{
					_gb._getTile(j,k)._setType(_gb._getTile(j,k)._getType()+1);
				}
			}
		}
	}
	
	/**
	* this function clears the board of all green and blue squares setting them to normal
	*/
	public void _clearBoard()
	{
		for(int k=0; k<11;k++)
		{
			for(int j=0; j<11;j++)
			{
				if(_gb._getTile(j,k)._getType() == 1 || _gb._getTile(j,k)._getType() == 3 ||
					_gb._getTile(j,k)._getType() == 5 || _gb._getTile(j,k)._getType() == 7 ||
					_gb._getTile(j,k)._getType() == 9 || _gb._getTile(j,k)._getType() == 11)
				{
					_gb._getTile(j,k)._setType(_gb._getTile(j,k)._getType()-1);
				}
			}
		}
	}
	
	/**
	* This function cleans up the display at the end of the turn 
	* updates whose turn it is
	* sets up the next turn
	*/
	public void _turnEnd()
	{
		_clearBoard();
		
				
 		if(_boardHistory._repeatedStateX3(_gb))		
 		{			
 			gameOver=true;		
 			if(_kingdomTurn)		
 				winner = 1;		
 			else		
 				winner = 2;		
 		}
		
		//find the wizard and check if he is in an exit fort
		boolean [][] path = new boolean[11][11];
		int x=0, y=0, type=0;
		for(int i=0; i<11;i++)
		{
			for(int j=0; j<11;j++)
			{
				path[i][j] = false;
				if(_gb._getTile(j,i)._getType() > 3 && _gb._getTile(j,i)._getType() < 8)
				{
					x = j; 
					y = i; 
					type = _gb._getTile(j, i)._getType();
				}
			}
		}
		if(x == 0 || x == 10 || y == 0 || y == 10)
		{
			if(_checkForExitFort(_gb._getTile(x, y), x, y, type, path, true))
			{
				gameOver = true;
				winner = 2;
			}
		}
		
		//Check to see if the hoard has surrounded the kingdom
		boolean edgeFound = false;
		for(int i=0; i<11;i++)
		{
			for(int j=0; j<11;j++)
			{
				path = _setPathFalse(path);
				if(_gb._getTile(j, i)._getType() == 2 || _gb._getTile(j, i)._getType() == 4 || _gb._getTile(j, i)._getType() == 6)
				{
					x = j; 
					y = i; 
					type = _gb._getTile(j, i)._getType();
					edgeFound = _searchForEdge(_gb._getTile(j, i), j, i, type, type, path, false);
					if(edgeFound)
					{
						break;
					}
				}
			}
			if(edgeFound)
			{
				break;
			}
		}
		if(!edgeFound)
		{
			gameOver = true;
			winner = 1;
		}
		
		//check if the wizard has escaped and the defenders have won
		if(_gb._getTile(0,0)._getType() == 6 || _gb._getTile(10,0)._getType() == 6 || 
		   _gb._getTile(0,10)._getType() == 6 || _gb._getTile(10,10)._getType() == 6)
		{
			gameOver=true;
			winner = 2;
		}
		
		//check to see if 50 turns have passed without a capture
		if(_turnsSinceCapture == 100)
		{
			gameOver = true;
			if(_kingdomTurn)
				winner = 1;
			else
				winner = 2;
		}
		
		_kingdomTurn = !_kingdomTurn;

		_sb._update(gameOver, winner);

		if(!gameOver)
			_setMovableGreen();
	}
	
	/**
	* this function is called when a tile is clicked and checks to see what logic should be done.
	*/
	public void _tileClicked(Tile t)
	{
		//checks if a unit has been selected to move
		if(_pieceSelected)
		{
			//check if the location clicked is a location the selected piece can move to
			//if not deselect the piece
			if(t._getType() == 1 || t._getType() == 11)
			{
				if(_selected._getType() == 5 && t._getType() == 11)
					t._setType(_selected._getType()+2);
				else if(_selected._getType() == 7 && t._getType() == 1)
					t._setType(_selected._getType()-2);
				else
					t._setType(_selected._getType());
				
				if(_selected._getType() == 7)
					_selected._setType(10);
				else
					_selected._setType(0);
				_moveHistory.add(_selected._getX() + " " + _selected._getY() + " " + t._getX()  + " " +  t._getY());
				
				_turnsSinceCapture++;
				
				//check if a piece was taken by moving here.
				int [][] adjacent = _locationsOfAdjacentEnemies(t);
				getAdjacent(adjacent);
				
				//check if shield wall capture occurs
				String [] edges = _closeToEdge(t);
				for(int i=0;i<edges.length;i++) _shieldCapture(edges[i], t);
				
				
				_pieceSelected = !_pieceSelected;
				_selected = null;
				_turnEnd();
			}
			else
			{
				//if not valid location deselect the piece and set all movable pieces back to green
				_clearBoard();
				_setMovableGreen();
				_pieceSelected = !_pieceSelected;
				_selected = null;
			}
		}
		else//if a unit has not been selected check if the tile passed in contains a selectable piece.
		{
			if(t._getType() == 3 || t._getType() == 5 || t._getType() == 7 || t._getType() == 9)
			{
				_pieceSelected = !_pieceSelected;
				_selected = t;
				_clearBoard();
				t._setType(t._getType()+1);
				
				int [][] moves = _availableMoves(t);
				for(int i=0; i<moves.length; i++)
				{
					int x = moves[i][0];
					int y = moves[i][1];
					
					if(_gb._getTile(x, y)._getType() == 0)
						_gb._getTile(x, y)._setType(1);
					else
						_gb._getTile(x, y)._setType(11);
				}
			}
		}
	}
	
	/**
	* checks each of the locations passed in for a captured piece
	* @Param int [][] adjacent: holds the x and y locations of each unit to checked for capture
	*/
	private void getAdjacent(int[][] adjacent) 
	{
		for(int i=0; i<adjacent.length; i++)
		{
			if(adjacent[i][0] != -1)
			{
				if(_captured(_gb._getTile(adjacent[i][0], adjacent[i][1])))
				{
					if(_gb._getTile(adjacent[i][0], adjacent[i][1])._getType() >= 4 && _gb._getTile(adjacent[i][0], adjacent[i][1])._getType() <= 7)
					{
						_gb._getTile(adjacent[i][0], adjacent[i][1])._setType(0);
						_gb._kingdomUnitTaken();
						gameOver = true;
						winner = 1;
					}
					else
					{
						if(_gb._getTile(adjacent[i][0], adjacent[i][1])._getType() > 7)
							_gb._hoardUnitTaken();
						else
							_gb._kingdomUnitTaken();
						
						_turnsSinceCapture = 0;
						_gb._getTile(adjacent[i][0], adjacent[i][1])._setType(0);
					}
				}
			}
		}
	}
	
	/**
	* takes a 2D array of booleans and sets the entire array to false
	* @Param [][] Boolean path the array to be set to false
	* @Return [][] Boolean path the array that has been set to false
	*/
	public boolean [][] _setPathFalse(boolean [][] path)
	{
		for(int i=0; i<path.length;i++)
		{
			for(int j=0; j<path[0].length;j++)
			{
				path[i][j] = false;
			}
		}
		return path;
	}
	
	/**
	* Call this function every time the wizard is on an edge to check if the wizard is in an exit fort
	* @Param Tile t: current tile in the pathfinding algorithm
	* @Param int x: the x position of the current tile
	* @Param int Y: the y position of the current tile
	* @Param int type: the type of the current tile
	* @Param [][]boolean path: which tiles have we already checked in our pathfinding algorithm
	* @Param boolean result: whether we have found the edge or not
	*/
	public boolean _checkForExitFort(Tile t, int x, int y, int type, boolean [][] path, boolean result)
	{
		path[x][y] = true;
		if(type == 8) return false;
		
		if(x > 0)
		{
			if(_gb._getTile(x-1, y)._getType() != 2 && !path[x-1][y] && result)
			{
				result = _checkForExitFort(_gb._getTile(x-1, y), x-1, y, _gb._getTile(x-1, y)._getType(), path, result);
			}
		}	
		if(y > 0)
		{
			if(_gb._getTile(x, y-1)._getType() != 2 && !path[x][y-1] && result)
			{
				result = _checkForExitFort(_gb._getTile(x, y-1), x, y-1, _gb._getTile(x, y-1)._getType(), path, result);
			}
		}
		if(x < 10)
		{
			if(_gb._getTile(x+1, y)._getType() != 2 && !path[x+1][y] && result)
			{
				result = _checkForExitFort(_gb._getTile(x+1, y), x+1, y, _gb._getTile(x+1, y)._getType(), path, result);
			}
		}
		if(y < 10)
		{
			if(_gb._getTile(x, y+1)._getType() != 2 && !path[x][y+1] && result)
			{
				result = _checkForExitFort(_gb._getTile(x, y+1), x, y+1, _gb._getTile(x, y+1)._getType(), path, result);
			}
		}
		
		return result;
	}
	
	/**
	* Takes a given tile and searches for the edge of the board and returns true if there is a path
	* @Param Tile t: current tile in the pathfinding algorithm
	* @Param int x: the x position of the current tile
	* @Param int Y: the y position of the current tile
	* @Param int type: the type of the current tile
	* @Param int originType: the type of the origin tile
	* @Param [][]boolean path: which tiles have we already checked in our pathfinding algorithm
	* @Param boolean result: whether we have found the edge or not
	* @Return boolean result: whether we have found the edge or not
	*/
	public boolean _searchForEdge(Tile t, int x, int y, int type, int originType, boolean [][] path, boolean result)
	{
		path[x][y] = true;
		
		if(x == 0 || x == 10 || y ==0 || y == 10) return true;
		if(originType == 4 || originType == 6)
		{
			if((_gb._getTile(x-1, y)._getType() != 2 && _gb._getTile(x-1, y)._getType() != 4 &&
				_gb._getTile(x-1, y)._getType() != 6 && _gb._getTile(x-1, y)._getType() != 8) && !path[x-1][y] && !result)
			{
				result = _searchForEdge(_gb._getTile(x-1, y), x-1, y, _gb._getTile(x-1, y)._getType(), originType, path, result);
			}
			if((_gb._getTile(x, y-1)._getType() != 2 && _gb._getTile(x, y-1)._getType() != 4 &&
				_gb._getTile(x, y-1)._getType() != 6 && _gb._getTile(x, y-1)._getType() != 8) && !path[x][y-1] && !result)
			{
				result = _searchForEdge(_gb._getTile(x, y-1), x, y-1, _gb._getTile(x, y-1)._getType(), originType, path, result);
			}	
			if((_gb._getTile(x+1, y)._getType() != 2 && _gb._getTile(x+1, y)._getType() != 4 &&
				_gb._getTile(x+1, y)._getType() != 6 && _gb._getTile(x+1, y)._getType() != 8) && !path[x+1][y] && !result)
			{
				result = _searchForEdge(_gb._getTile(x+1, y), x+1, y, _gb._getTile(x+1, y)._getType(), originType, path, result);
			}
			if((_gb._getTile(x, y+1)._getType() != 2 && _gb._getTile(x, y+1)._getType() != 4 &&
				_gb._getTile(x, y+1)._getType() != 6 && _gb._getTile(x, y+1)._getType() != 8) && !path[x][y+1] && !result)
			{
				result = _searchForEdge(_gb._getTile(x, y+1), x, y+1, _gb._getTile(x, y+1)._getType(), originType, path, result);
			}
		}
		else
		{
			if((_gb._getTile(x-1, y)._getType() != 2 && _gb._getTile(x-1, y)._getType() != 4 && _gb._getTile(x-1, y)._getType() != 6 && 
				_gb._getTile(x-1, y)._getType() != 8 && _gb._getTile(x-1, y)._getType() != 10)     && !path[x-1][y] && !result)
			{
				result = _searchForEdge(_gb._getTile(x-1, y), x-1, y, _gb._getTile(x-1, y)._getType(), originType, path, result);
			}
			if((_gb._getTile(x, y-1)._getType() != 2 && _gb._getTile(x, y-1)._getType() != 4 && _gb._getTile(x, y-1)._getType() != 6 && 
				_gb._getTile(x, y-1)._getType() != 8 && _gb._getTile(x, y-1)._getType() != 10)      && !path[x][y-1] && !result)
			{
				result = _searchForEdge(_gb._getTile(x, y-1), x, y-1, _gb._getTile(x, y-1)._getType(), originType, path, result);
			}
			if((_gb._getTile(x+1, y)._getType() != 2 && _gb._getTile(x+1, y)._getType() != 4 && _gb._getTile(x+1, y)._getType() != 6 && 
				_gb._getTile(x+1, y)._getType() != 8 && _gb._getTile(x+1, y)._getType() != 10)      && !path[x+1][y] && !result)
			{
				result = _searchForEdge(_gb._getTile(x+1, y), x+1, y, _gb._getTile(x+1, y)._getType(), originType, path, result);
			}			
			if((_gb._getTile(x, y+1)._getType() != 2 && _gb._getTile(x, y+1)._getType() != 4 && _gb._getTile(x, y+1)._getType() != 6 && 
				_gb._getTile(x, y+1)._getType() != 8 && _gb._getTile(x, y+1)._getType() != 10)      && !path[x][y+1] && !result)
			{
				result = _searchForEdge(_gb._getTile(x, y+1), x, y+1, _gb._getTile(x, y+1)._getType(), originType, path, result);
			}
		}
		return result;
	}
	
	/**
	* This function returns a string if the tile is on the edge or one square away from it
	* @param Tile t the tile whose location is being checked
	* @return String [] returns a string corresponding to each edge the tile is close to
	*/
	public String [] _closeToEdge(Tile t)
	{
		int numEdges = 0;
		if(t._getX() < 2)numEdges++;
		if(t._getX() > 8)numEdges++; 
		if(t._getY() < 2)numEdges++;
		if(t._getY() > 8)numEdges++;
		String [] edges = new String[numEdges];
		int count = 0;
		if(t._getX() < 2)
		{
			edges[count] = "left";
			count++;
		}
		if(t._getX() > 8)
		{
			edges[count] = "right";
			count++;
		}
		if(t._getY() < 2)
		{
			edges[count] = "top";
			count++;
		}
		if(t._getY() > 8)
		{
			edges[count] = "bottom";
			count++;
		}
		
		return edges;
	}
	
	/**
	* This method checks the edge passed into it to see if a shield captures occurs
	* @Param String edge: the edge the unit has moved onto. Tile t: The tile the unit has moved onto.
	*/
	public void _shieldCapture(String edge, Tile t)
	{
		int outer =0, inner =0, team =0;
		boolean edgeIsVertical = false;
		//check which team the shield capture is going against
		if(t._isKingdomUnit() == 0) team = 1;
		if(t._isKingdomUnit() == 1) team = 0;
		
		//check which edge we are working with
		if(edge == "top")
		{
			outer = 0;
			inner = 1;
			edgeIsVertical = false;
		}
		if(edge == "left")
		{
			outer = 0;
			inner = 1;
			edgeIsVertical = true;
		}
		if(edge == "right")
		{
			outer = 10;
			inner = 9;
			edgeIsVertical = true;
		}
		if(edge == "bottom")
		{
			outer = 10;
			inner = 9;
			edgeIsVertical = false;
		}
		
		//record the tiles along the edge in question assigning values based on this situation
		int [] movableOpponents = new int[11];
		_kingdomTurn = !_kingdomTurn;
		for(int i=0; i < 11; i++)
		{
			movableOpponents[i] = -1;
			if(edgeIsVertical)
			{
				Tile temp = _gb._getTile(outer, i);
				if(temp._isKingdomUnit() == team)
				{
					if(_isPieceMovable(temp))
					{
						movableOpponents[i] = 2;
					}
					else
					{
						movableOpponents[i] = 1;
					}
				}
				else
				{
					if(temp._getType() != 0  && temp._getType() != 1)
						movableOpponents[i] = -2;
				}
			}
			else
			{
				Tile temp = _gb._getTile(i, outer);
				if(temp._isKingdomUnit() == team)
				{
					if(_isPieceMovable(temp))
					{
						movableOpponents[i] = 2;
					}
					else
					{
						movableOpponents[i] = 1;
					}
				}
				else
				{
					if(temp._getType() != 0 && temp._getType() != 1)
						movableOpponents[i] = -2;
				}
			}
		}
		_kingdomTurn = !_kingdomTurn;
		
		//iterate through the edge looking for the pattern that indicates a shield wall capture and if found capture those units
		boolean possibleCapture = true;
		int count = 0, start =0;
		for(int i=0; i<11; i++)
		{
			if(movableOpponents[i] == -2)
			{
				if(count>0)
				{
					start = i-count;
					boolean captured = true;
					for(int j = start; j<i;j++)
					{
						if(edgeIsVertical)
						{
							if(_gb._getTile(inner, j)._isKingdomUnit() != team)
							{
								count = 0;
								break;
							}
						}
						else
						{
							if(_gb._getTile(j, inner)._isKingdomUnit() != team)
							{
								count = 0;
								break;
							}
						}
					}
					if(captured)
					{
						for(int j = start; j<i;j++)
						{
							if(edgeIsVertical)
							{
								if(_gb._getTile(outer, j)._getType() < 4 || _gb._getTile(outer, j)._getType() > 7)
								{
									if(_gb._getTile(outer, j)._isKingdomUnit() == 0)
										_gb._kingdomUnitTaken();
									else
										_gb._hoardUnitTaken();
									
									_turnsSinceCapture = 0;
									_gb._getTile(outer, j)._setType(0);
								}
							}
							else
							{
								if(_gb._getTile(j, outer)._getType() < 4 || _gb._getTile(j, outer)._getType() > 7)
								{
									if(_gb._getTile(j, outer)._isKingdomUnit() == 0)
										_gb._kingdomUnitTaken();
									else
										_gb._hoardUnitTaken();
									
									
									_turnsSinceCapture = 0;
									_gb._getTile(j, outer)._setType(0);
									
								}
							}
						}
					}
				}
				possibleCapture = true;
			}
			if(movableOpponents[i] == -1)
			{
				count = 0;
				possibleCapture = false;
			}
			if(movableOpponents[i] == 1)
			{
				if(possibleCapture)
					count++;
			}
			if(movableOpponents[i] == 2)
			{
				count = 0;
				possibleCapture = false;
			}
		}
	}
	
	
	/**
	* checks if the piece passed in can be moved based on position and type of piece
	* @param tile: this is the tile the function is checking to see if it can be moved
	* @return boolean: returns whether or not the piece can be moved
	*/
	public boolean _isPieceMovable(Tile t)
	{
		boolean leftEdge = false, bottomEdge = false, rightEdge = false, topEdge = false, foundMove = false;
		int x = t._getX(), y = t._getY();
		
		if(x == 0)  leftEdge = true;
		if(x == 10) rightEdge = true;
		if(y == 0)  topEdge = true;
		if(y == 10) bottomEdge = true;
		
		if(_kingdomTurn)
		{
			if(t._getType() == 2)
			{
				if(!topEdge)
					if(_gb._getTile(x, y-1)._getType() == 0)
						foundMove = true;
				if(!leftEdge)
					if(_gb._getTile(x-1, y)._getType() == 0)
						foundMove = true;
				if(!bottomEdge)
					if(_gb._getTile(x, y+1)._getType() == 0)
						foundMove = true;
				if(!rightEdge)
					if(_gb._getTile(x+1, y)._getType() == 0)
						foundMove = true;
			}
			if(t._getType() == 4 || t._getType() == 6)
			{
				if(!topEdge)
					if(_gb._getTile(x, y-1)._getType() == 0 || _gb._getTile(x, y-1)._getType() == 10)
						foundMove = true;
				if(!leftEdge)
					if(_gb._getTile(x-1, y)._getType() == 0 || _gb._getTile(x-1, y)._getType() == 10)
						foundMove = true;
				if(!bottomEdge)
					if(_gb._getTile(x, y+1)._getType() == 0 || _gb._getTile(x, y+1)._getType() == 10)
						foundMove = true;
				if(!rightEdge)
					if(_gb._getTile(x+1, y)._getType() == 0 || _gb._getTile(x+1, y)._getType() == 10)
						foundMove = true;
			}
		}
		else
		{
			if(t._getType() == 8)
			{
				if(!topEdge)
					if(_gb._getTile(x, y-1)._getType() == 0)
						foundMove = true;
				if(!leftEdge)
					if(_gb._getTile(x-1, y)._getType() == 0)
						foundMove = true;
				if(!bottomEdge)
					if(_gb._getTile(x, y+1)._getType() == 0)
						foundMove = true;
				if(!rightEdge)
					if(_gb._getTile(x+1, y)._getType() == 0)
						foundMove = true;
			}
		}
		return foundMove;
	}
	
	/**
	* checks every tile in the same column and row as the tile passed in to see if they can be moved to
	* @param tile the tile we are checking to see what moves are available for.
	* @return a 2 dimensional array holding the x and y position of each available move.
	*/
	public int [][] _availableMoves(Tile t)
	{
		boolean isWizard = (t._getType() == 5 || t._getType() == 7), isGround, isLava;
		int x = t._getX(), y = t._getY();
		ArrayList<int[]> moves = new ArrayList<int[]>();
		
		for(int i=x-1; i>=0; i--)
		{
			int [] position = new int[2];
			isGround = (_gb._getTile(i, y)._getType() == 0);
			isLava = (_gb._getTile(i, y)._getType() == 10);
			if(isGround)
			{
				position[0] = _gb._getTile(i, y)._getX();
				position[1] = _gb._getTile(i, y)._getY();
				moves.add(position);
			}
			else if(isWizard && (isGround || isLava))
			{
				position[0] = _gb._getTile(i, y)._getX();
				position[1] = _gb._getTile(i, y)._getY();
				moves.add(position);
			}
			else
				break;
		}
		for(int i=y-1; i>=0; i--)
		{
			int [] position = new int[2];
			isGround = (_gb._getTile(x, i)._getType() == 0);
			isLava = (_gb._getTile(x, i)._getType() == 10);
			if(isGround)
			{
				position[0] = _gb._getTile(x, i)._getX();
				position[1] = _gb._getTile(x, i)._getY();
				moves.add(position);
			}
			else if(isWizard && (isGround || isLava))
			{
				position[0] = _gb._getTile(x, i)._getX();
				position[1] = _gb._getTile(x, i)._getY();
				moves.add(position);
			}
			else
				break;
		}
		for(int i=x+1; i<=10; i++)
		{
			int [] position = new int[2];
			isGround = (_gb._getTile(i, y)._getType() == 0);
			isLava = (_gb._getTile(i, y)._getType() == 10);
			if(isGround)
			{
				position[0] = _gb._getTile(i, y)._getX();
				position[1] = _gb._getTile(i, y)._getY();
				moves.add(position);
			}
			else if(isWizard && (isGround || isLava))
			{
				position[0] = _gb._getTile(i, y)._getX();
				position[1] = _gb._getTile(i, y)._getY();
				moves.add(position);
			}
			else
				break;
		}
		for(int i=y+1; i<=10; i++)
		{
			int [] position = new int[2];
			isGround = (_gb._getTile(x, i)._getType() == 0);
			isLava = (_gb._getTile(x, i)._getType() == 10);
			if(isGround)
			{
				position[0] = _gb._getTile(x, i)._getX();
				position[1] = _gb._getTile(x, i)._getY();
				moves.add(position);
			}
			else if(isWizard && (isGround || isLava))
			{
				position[0] = _gb._getTile(x, i)._getX();
				position[1] = _gb._getTile(x, i)._getY();
				moves.add(position);
			}
			else
				break;
		}
		
		int [][] returnVal = new int[moves.size()][2];
		for(int i=moves.size()-1; i>=0; i--)
		{
			returnVal[i] = (int[])moves.get(i);
		}
		return returnVal;
	}
	
	/**
	* checks if the destination is a space that can be moved to.
	* @param tile this is the destination we checking to make sure it is valid.
	* @return boolean whether or not the destination is valid.
	*/
	public boolean _canBeMovedTo(Tile destination)
	{
		if(destination._getType() == 1 || destination._getType() == 11)
			return true;
		else
			return false;
	}
	
	/**
	* This function takes in a tile location and checks each of the four adjacent locations for enemy units.
	* @param Tile the tile to check around for enemies.
	* @return int [][] The x and y positions of the 4 adjacent locations if no enemy is there the x and  y value is set to -1.
	* each row in the 2D array is a tile's x and y.
	*/
	public int [][] _locationsOfAdjacentEnemies(Tile t)
	{
		boolean leftEdge = false, bottomEdge = false, rightEdge = false, topEdge = false, foundMove = false, kingdomUnit = true;
		int x = t._getX(), y = t._getY();
		
		if(t._getType() == 8 || t._getType() == 9) kingdomUnit = false;
		
		if(x == 0)  leftEdge = true;
		if(x == 10) rightEdge = true;
		if(y == 0)  topEdge = true;
		if(y == 10) bottomEdge = true;
		
		int [][] locations = new int [4][2];
		for(int i=0; i<locations.length; i++)
		{
			locations[i][0] = -1;
			locations[i][1] = -1;
		}
		
		if(!leftEdge)
		{
			if(kingdomUnit)
			{
				int leftTile = _gb._getTile(x-1, y)._getType();
				if(leftTile == 8 || leftTile == 9 || leftTile >= 10)
				{
					locations[0][0] = x-1;
					locations[0][1] = y;
				}
			}
			else
			{
				int leftTile = _gb._getTile(x-1, y)._getType();
				if((leftTile > 1 && leftTile < 8) || leftTile >= 10)
				{
					locations[0][0] = x-1;
					locations[0][1] = y;
				}
			}
		}
		if(!bottomEdge)
		{
			if(kingdomUnit)
			{
				int bottomTile = _gb._getTile(x, y+1)._getType();
				if(bottomTile == 8 || bottomTile == 9 || bottomTile >= 10)
				{
					locations[1][0] = x;
					locations[1][1] = y+1;
				}
			}
			else
			{
				int bottomTile = _gb._getTile(x, y+1)._getType();
				if((bottomTile>1 && bottomTile<8) || bottomTile >= 10)
				{
					locations[1][0] = x;
					locations[1][1] = y+1;
				}
			}
		}
		if(!rightEdge)
		{
			if(kingdomUnit)
			{
				int rightTile = _gb._getTile(x+1, y)._getType();
				if(rightTile == 8 || rightTile == 9 || rightTile >= 10)
				{
					locations[2][0] = x+1;
					locations[2][1] = y;
				}
			}
			else
			{
				int rightTile = _gb._getTile(x+1, y)._getType();
				if((rightTile>1 && rightTile<8) || rightTile >= 10)
				{
					locations[2][0] = x+1;
					locations[2][1] = y;
				}
			}
		}
		if(!topEdge)
		{
			if(kingdomUnit)
			{
				int topTile = _gb._getTile(x, y-1)._getType();
				if(topTile == 8 || topTile == 9 || topTile >= 10)
				{
					locations[3][0] = x;
					locations[3][1] = y-1;
				}
			}
			else
			{
				int topTile = _gb._getTile(x, y-1)._getType();
				if((topTile>1 && topTile<8) || topTile >= 10)
				{
					locations[3][0] = x;
					locations[3][1] = y-1;
				}
			}
		}
		return locations;
	}
	
	/**
	* checks if the piece passed in should be captured
	* @param Tile the unit that might be captured
	* @return Boolean whether or not the unit is captured
	*/
	public boolean _captured(Tile t)
	{
		boolean isWizard = false;
		if(t._getType() > 3 && t._getType() < 8)
			isWizard = true;
		
		if(isWizard)
		{
			int [][] adjacent = _locationsOfAdjacentEnemies(t);
			if(adjacent[0][0] >= 0 && adjacent[1][0] >= 0 && adjacent[2][0] >= 0 && adjacent[3][0] >= 0)
				return true;
		}
		else
		{
			int [][] adjacent = _locationsOfAdjacentEnemies(t);
			if(adjacent[0][0] >= 0 && adjacent[2][0] >= 0)
				return true;
			if(adjacent[1][0] >= 0 && adjacent[3][0] >= 0)
				return true;
		}
		return false;
	}
	
	/**
	* returns true if its the kingdom's turn, false otherwise.
	* @return returns if it is the kingdom's turn.
	*/
	public boolean _isKingdomTurn()
	{
		return _kingdomTurn;
	}
	
	/**
	* This function allows the turn to be set by a boolean
	* @Param boolean turn: whether the game is on the kingdom's turn or not.
	*/
	public void _setKingdomTurn(boolean turn)
	{
		_kingdomTurn = turn;
	}
}
