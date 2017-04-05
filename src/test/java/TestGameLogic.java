
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Hnefatafl.Tile;
import Hnefatafl.GameLogic;
import Hnefatafl.GameBoard;
import Hnefatafl.ScoreBoard;
import java.util.*;

public class TestGameLogic
{
	//test get history
	@Test
	public void testHistory()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		Tile t = gb._getTile(5,1);
		gl._tileClicked(t);
		Tile destination = gb._getTile(4,1);
		gl._tileClicked(destination);
		
		
		assertTrue(gl._getHistory().peek().equals("5 1 4 1"));
	}
	
	
	//test setPathFalse
	@Test
	public void testSetPathFalse()
	{
		GameLogic gl = new GameLogic();
		boolean [][] path = new boolean[11][11];
		for(int i=0;i<11;i++)
		{
			for(int j=0; j<11; j++)
			{
				path[i][j] = true;
			}
		}
		
		path = gl._setPathFalse(path);
		
		boolean setFalse = true;
		
		for(int i=0;i<11;i++)
		{
			for(int j=0; j<11; j++)
			{
				if(path[i][j])
				{
					setFalse = false;
					break;
				}
			}
		}
		
		assertTrue(setFalse);
	}
	
	
	//test checkForExitFor
	@Test
	public void testValidExitFort()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		boolean [][] path = new boolean[11][11];
		for(int i=0;i<11;i++)
		{
			for(int j=0; j<11; j++)
			{
				path[i][j] = false;
			}
		}
		
		//remove other wizard
		gb._getTile(5,5)._setType(2);
		
		//set up exit fort
		gb._getTile(10,3)._setType(2);
		gb._getTile(10,4)._setType(0);
		gb._getTile(10,5)._setType(4);
		gb._getTile(10,6)._setType(0);
		gb._getTile(10,7)._setType(2);
		
		gb._getTile(9,4)._setType(2);
		gb._getTile(9,5)._setType(2);
		gb._getTile(9,6)._setType(2);
		
		
		
		assertTrue(gl._checkForExitFort(gb._getTile(10,5), 10, 5, 4, path, true));
	}
	
	@Test
	public void testInvalidExitFort()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		boolean [][] path = new boolean[11][11];
		for(int i=0;i<11;i++)
		{
			for(int j=0; j<11; j++)
			{
				path[i][j] = false;
			}
		}
		
		//remove other wizard
		gb._getTile(5,5)._setType(2);
		
		//set up exit fort
		gb._getTile(10,3)._setType(2);
		gb._getTile(10,4)._setType(8);
		gb._getTile(10,5)._setType(4);
		gb._getTile(10,6)._setType(0);
		gb._getTile(10,7)._setType(2);
		
		gb._getTile(9,4)._setType(2);
		gb._getTile(9,5)._setType(2);
		gb._getTile(9,6)._setType(2);
		
		
		
		assertFalse(gl._checkForExitFort(gb._getTile(10,5), 10, 5, 4, path, true));
	}
	
	
	//test searchForEdge
	@Test
	public void testSearchForEdgeValid()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		boolean [][] path = new boolean[11][11];
		for(int i=0;i<11;i++)
		{
			for(int j=0; j<11; j++)
			{
				path[i][j] = false;
			}
		}
		
		//fill in all paths to the edge with hoard units
		gb._getTile(1,2)._setType(8);
		gb._getTile(1,1)._setType(8);
		gb._getTile(2,1)._setType(8);
		
		gb._getTile(8,1)._setType(8);
		gb._getTile(9,1)._setType(8);
		gb._getTile(9,2)._setType(8);
		
		gb._getTile(9,8)._setType(8);
		gb._getTile(9,9)._setType(8);
		gb._getTile(8,9)._setType(8);
		
		gb._getTile(1,8)._setType(8);
		gb._getTile(1,9)._setType(8);
		gb._getTile(2,9)._setType(8);
		
		assertFalse(gl._searchForEdge(gb._getTile(5,4), 5, 4, 2, 2, path, false));
	}
	
	@Test
	public void testSearchForEdgeInvalid()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		boolean [][] path = new boolean[11][11];
		for(int i=0;i<11;i++)
		{
			for(int j=0; j<11; j++)
			{
				path[i][j] = false;
			}
		}
		
		assertTrue(gl._searchForEdge(gb._getTile(5,3), 5, 3, 2, 2, path, false));
	}
	
	
	//test closeToEdge
	@Test
	public void testCloseToOneEdge()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		String [] location = gl._closeToEdge(gb._getTile(1, 4));
		
		assertTrue(location[0].equals("left") && location.length == 1);
	}
	
	@Test
	public void testCloseToTwoEdges()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		String [] location = gl._closeToEdge(gb._getTile(1, 1));
		
		assertTrue(location[0].equals("left") && location[1].equals("top") && location.length == 2);
	}
	
	@Test
	public void testCloseToZeroEdges()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		String [] location = gl._closeToEdge(gb._getTile(3, 4));
		
		assertTrue(location.length == 0);
	}
	
	
	//test shieldCapture
	@Test
	public void testShieldBottomHOverK()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		gb._getTile(4, 10)._setType(2);
		gb._getTile(5, 10)._setType(2);
		gb._getTile(6, 10)._setType(2);
		
		gb._getTile(4, 9)._setType(8);
		gb._getTile(6, 9)._setType(8);
		
		gl._shieldCapture("bottom", gb._getTile(4, 9));
		
		assertTrue(gb._getTile(4, 10)._getType() == 0);
	}
	
	@Test
	public void testShieldRightKingOverHoard()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		gb._getTile(9, 4)._setType(2);
		gb._getTile(9, 5)._setType(2);
		gb._getTile(9, 6)._setType(2);
		gb._getTile(10, 3)._setType(2);
		gb._getTile(10, 7)._setType(2);
		
		gl._shieldCapture("right", gb._getTile(10, 7));
		
		assertTrue(gb._getTile(10, 5)._getType() == 0);
	}
	
	
	//test isPieceMovable
	@Test
	public void testIsPieceMovableValid()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		gl._clearBoard();
		
		assertTrue(gl._isPieceMovable(gb._getTile(1,5)));
	}
	
	@Test
	public void testIsPieceMovableSurroundedUnit()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		gl._clearBoard();
		
		Tile HoardUnit = gb._getTile(0,5);
		assertFalse(gl._isPieceMovable(HoardUnit));
	}
	
	@Test
	public void testIsPieceMovableEnemyUnit()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		gl._clearBoard();
		
		Tile HoardUnit = gb._getTile(5,3);
		assertFalse(gl._isPieceMovable(HoardUnit));
	}
	
	
	//test canBeMovedTo
	@Test
	public void testCanBeMovedTo()
	{
		GameLogic gl = new GameLogic();
		
		Tile [] allTypes = new Tile[12];
		
		for(int i=0; i<11; i++)
		{
			allTypes[i] = new Tile(0, 0, i, gl);
			
			if(i == 1 || i == 11)
				assertTrue(gl._canBeMovedTo(allTypes[i]));
			else
				assertFalse(gl._canBeMovedTo(allTypes[i]));
		}
	}
	
	
	//test captured
	@Test
	public void testCapturedKingdomSoldier()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		gb._getTile(4,3)._setType(8);
		gb._getTile(6,3)._setType(8);
		
		assertTrue(gl._captured(gb._getTile(5,3)));
		
		gb._getTile(5,6)._setType(8);
		gb._getTile(5,8)._setType(8);
		
		assertTrue(gl._captured(gb._getTile(5,7)));
	}
	
	@Test
	public void testCapturedWizard()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		gb._getTile(4,5)._setType(8);
		gb._getTile(6,5)._setType(8);
		gb._getTile(5,4)._setType(8);
		gb._getTile(5,6)._setType(8);
		
		assertTrue(gl._captured(gb._getTile(5,5)));
	}
	
	@Test
	public void testCapturedHoardUnit()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		gb._getTile(4,1)._setType(2);
		gb._getTile(6,1)._setType(2);
		
		assertTrue(gl._captured(gb._getTile(5,1)));
		
		gb._getTile(5,8)._setType(2);
		gb._getTile(5,10)._setType(2);
		
		assertTrue(gl._captured(gb._getTile(5,9)));
	}
	
	
	//test getGameOver
	@Test
	public void testGetGameOverValid()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		gb._getTile(0,0)._setType(6);
		gb._getTile(5,5)._setType(2);
		
		gl._turnEnd();
		assertTrue(gl._getGameOver());
	}
	
	@Test
	public void testGetGameOverInvalid()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		assertFalse(gl._getGameOver());
	}
	
	
	//test isKingdomTurn
	@Test
	public void testIsKingdomTurn()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		byte[] numTurns = {1,2,3,4,5}; //Must be odd
		
		assertFalse(gl._isKingdomTurn());
		for(byte turn : numTurns)
			gl._turnEnd();
		assertTrue(gl._isKingdomTurn());
	}
	
	
	//test availableMoves
	@Test
	public void testAvailableMovesMovableHoardUnit()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		int [][] moves = (gl._availableMoves(gb._getTile(5, 1)));
		
		assertTrue(moves.length == 11);
	}
	
	@Test
	public void testAvailableMovesMovableKingdomUnit()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		int [][] moves = (gl._availableMoves(gb._getTile(5, 3)));
		
		assertTrue(moves.length == 9);
	}
	
	
	//test locationOfAdjacentEnemies
	@Test
	public void testAdjacentLocationsBottomLeftHoard()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		gb._getTile(6,3)._setType(8);
		
		int [][] enemies = gl._locationsOfAdjacentEnemies(gb._getTile(6,3));
		
		assertTrue(enemies[0][0] > -1 && enemies[1][0] > -1 && enemies[2][0] == -1 && enemies[3][0] == -1);
	}
	
	@Test
	public void testAdjacentLocationsTopRightKingdom()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		gb._getTile(9,6)._setType(2);
		
		int [][] enemies = gl._locationsOfAdjacentEnemies(gb._getTile(9,6));
		
		assertTrue(enemies[0][0] == -1 && enemies[1][0] == -1 && enemies[2][0] > -1 && enemies[3][0] > -1);
	}
	
	
	//test setKingdomTurn
	@Test
	public void testSetKingdomTurn()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		assertFalse(gl._isKingdomTurn());
		gl._setKingdomTurn(true);
		assertTrue(gl._isKingdomTurn());
	}
	
	@Test
	public void topLeftCornerMovable()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		Tile cornerTile = gb._getTile(0,0);
		assertFalse(gl._isPieceMovable(cornerTile));
	}

	@Test
	public void topRightCornerMovable()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		Tile cornerTile = gb._getTile(0,10);
		assertFalse(gl._isPieceMovable(cornerTile));
	}

	@Test
	public void bottomRightCornerMovable()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		Tile cornerTile = gb._getTile(10,0);
		assertFalse(gl._isPieceMovable(cornerTile));
	}

	@Test
	public void bottomLeftCornerMovable()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		Tile cornerTile = gb._getTile(10,10);
		assertFalse(gl._isPieceMovable(cornerTile));
	}

	@Test
	public void RandomTileMovable()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		Tile cornerTile = gb._getTile(2,1);
		assertFalse(gl._isPieceMovable(cornerTile));
	}

	@Test
	public void startKingMovable()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		Tile kingTile = gb._getTile(5,5);
		assertFalse(gl._isPieceMovable(kingTile));
	}

	@Test
	public void startKnightMovable()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		Tile KnightUnit = gb._getTile(3,5);
		assertFalse(gl._isPieceMovable(KnightUnit));
	}

	@Test
	public void startHoardMovable()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		Tile HoardUnit = gb._getTile(0,4);
		assertTrue(gl._isPieceMovable(HoardUnit));
	}
	
	@Test
	public void testAvailableMoves()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		Tile HoardUnit = gb._getTile(5, 1);
		int [][] moves = gl._availableMoves(HoardUnit);
		int movesToEast = 0, movesToWest = 0, movesToNorth = 0, movesToSouth = 0;
		for(int i=0;i<moves.length;i++)
		{
			if(moves[i][0] < 5) movesToEast++;
			if(moves[i][0] > 5) movesToWest++;
			if(moves[i][1] > 1) movesToSouth++;
		}
		assertTrue(movesToEast == 5);
		assertTrue(movesToWest == 5);
		assertTrue(movesToSouth == 1);
	}
	
	@Test
	public void testLocationsOfAdjacentEnemies()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		
		Tile t = gb._getTile(5,1);
		
		int [][] enemies = gl._locationsOfAdjacentEnemies(t);
		int numEnemies =0;
		for(int i=0; i<enemies.length; i++)
		{
			if(enemies[i][0] > -1) numEnemies++;
		}
		assertTrue(numEnemies == 0);
		
		gb._getTile(4,1)._setType(2);
		gb._getTile(6,1)._setType(2);
		
		enemies = gl._locationsOfAdjacentEnemies(t);
		for(int i=0; i<enemies.length; i++)
		{
			if(enemies[i][0] > -1) numEnemies++;
		}
		assertTrue(numEnemies == 2);
	}

	@Test
	public void testHoardCaptured()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		
		Tile t = gb._getTile(5,1);
		
		assertFalse(gl._captured(t));
		gb._getTile(4,1)._setType(2);
		gb._getTile(5,0)._setType(2);
		assertFalse(gl._captured(t));
		gb._getTile(6,1)._setType(2);
		assertTrue(gl._captured(t));
	}
	
	@Test
	public void testSoldierCaptured()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		
		gb._getTile(3, 2)._setType(2); //Set tile to soldier
		Tile t = gb._getTile(3,2);
		
		assertFalse(gl._captured(t));
		gb._getTile(2,2)._setType(8);
		gb._getTile(3,3)._setType(8);
		assertFalse(gl._captured(t)); //L surround
		gb._getTile(4,2)._setType(8);
		assertTrue(gl._captured(t)); //T surround
	}
	
	@Test
	public void testKingCaptured()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		
		gb._getTile(6,9)._setType(4); //Set tile to King
		Tile t = gb._getTile(6,9);
		
		assertFalse(gl._captured(t)); //L surround
		gb._getTile(6,8)._setType(8);
		assertFalse(gl._captured(t)); //T surround
		gb._getTile(7,9)._setType(8);
		assertTrue(gl._captured(t)); //+ surround
	}
	
	@Test
	/**
	 * Test: King is not capture when surrounded on edge.
	 */
	public void testKingNotCaptured()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		
		gb._getTile(8,10)._setType(4); //Set tile to King
		Tile t = gb._getTile(8,10);
		
		gb._getTile(8,9)._setType(8);
		assertFalse(gl._captured(t)); //L surround
		gb._getTile(9,10)._setType(8);
		assertFalse(gl._captured(t)); //T surround
	}

	
	@Test
	public void testIsKingdomturn()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		byte[] numTurns = {1,2,3,4,5}; //Must be odd
		
		assertFalse(gl._isKingdomTurn());
		for(byte turn : numTurns)
			gl._turnEnd();
		assertTrue(gl._isKingdomTurn());
	}
	
	/*
	@Test
	public void testSoldierMovable()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		gl._startNewGame();
		
		Tile soldier = gb._getTile(5,3);
		assertTrue(gl._isPieceMovable(soldier));
	}
	*/
	
	@Test
	public void testSoldierNotMovable()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		Tile soldier = gb._getTile(5,4);
		assertFalse(gl._isPieceMovable(soldier));
	}
	
	@Test
	public void testHoardMovable()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		Tile hoard = gb._getTile(1,5);
		assertTrue(gl._isPieceMovable(hoard));
	}
	
	@Test
	public void testHoardNotMovable()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		Tile hoard = gb._getTile(0,5);
		assertFalse(gl._isPieceMovable(hoard));
	}
	
	@Test
	public void testGetGameOver()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		gl._setGameBoard(gb);
		assertFalse(gl._getGameOver());
	}
}
