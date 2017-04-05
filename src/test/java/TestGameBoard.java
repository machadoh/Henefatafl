import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Hnefatafl.Tile;
import Hnefatafl.GameLogic;
import Hnefatafl.GameBoard;
import Hnefatafl.ScoreBoard;

public class TestGameBoard
{
	@Test
	public void testGetNumKingdom()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		assertTrue(gb._getNumKingdom() > 0);
	}
	
	@Test
	public void testKingdomUnitTaken()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		int before = gb._getNumKingdom();
		gb._kingdomUnitTaken();
		int after = gb._getNumKingdom();
		assertTrue(before > after);
	}
	
	@Test
	public void testGetNumHoard()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		assertTrue(gb._getNumHoard() > 0);
	}
	
	@Test
	public void testHoardUnitTaken()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		int before = gb._getNumHoard();
		gb._hoardUnitTaken();
		int after = gb._getNumHoard();
		assertTrue(before > after);
	}
	
	@Test
	public void testGetTile()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		Tile t = gb._getTile(0,1);
		assertTrue(t._getX() == 0);
		assertTrue(t._getY() == 1);
	}
	
	
	@Test
	public void testGetTileTypes()
	{
		GameLogic gl = new GameLogic();
		GameLogic gl2 = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		GameBoard gb2 = new GameBoard(gl2);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		ScoreBoard sb2 = new ScoreBoard(gb2, gl2);
		
		gl._setGameBoard(gb);
		gl2._setGameBoard(gb2);
		gl._setScoreBoard(sb);
		gl2._setScoreBoard(sb2);
		
		gl._startNewGame();
		gl2._startNewGame();
		
		
		byte[][] arr = gb._getTileTypes();
		byte[][] arr2 = gb2._getTileTypes();
		
		for(int i =0; i<11;i++)
		{
			for(int j =0; j<11;j++)
			{
				assertTrue(arr[i][j] == arr2[i][j]);
			}
		}
	}
	
	
	@Test
	public void testEquals()
	{
		GameLogic gl = new GameLogic();
		GameLogic gl2 = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		GameBoard gb2 = new GameBoard(gl2);
		
		assertTrue(gb._equals(gb2));
		
		gb._getTile(10, 6)._setType(11);
		
		assertFalse(gb._equals(gb2));
	}
}