import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Hnefatafl.Tile;
import Hnefatafl.GameLogic;
import Hnefatafl.GameBoard;
import Hnefatafl.ScoreBoard;
import Hnefatafl.BoardHistory;
import java.util.*;

public class TestBoardHistory
{

	@Test
	public void testRepeatedStateX3()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();

		BoardHistory bh = new BoardHistory();

		assertFalse(bh._repeatedStateX3(gb));	
		assertFalse(bh._repeatedStateX3(gb));	
		assertTrue(bh._repeatedStateX3(gb));		

	}

	@Test
	public void testRepeatedStateX2()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		ScoreBoard sb = new ScoreBoard(gb, gl);
		
		gl._setGameBoard(gb);
		gl._setScoreBoard(sb);
		
		gl._startNewGame();
		
		BoardHistory bh = new BoardHistory();
		ArrayList<byte[][]> _test1;
		ArrayList<byte[][]> _test2;
	
		bh._repeatedStateX2(gb);
		
		_test1 = bh._getHistory();
		_test2 = bh._getDoubleState();
		assertFalse(_test1.isEmpty());
		assertTrue(_test2.isEmpty());
		
		bh._repeatedStateX2(gb);

		_test1 = bh._getHistory();
		_test2 = bh._getDoubleState();
		assertFalse(_test1.isEmpty());
		assertFalse(_test2.isEmpty());

	}

	@Test
	public void testEquals()
	{
		GameLogic gl = new GameLogic();
		GameBoard gb = new GameBoard(gl);
		GameLogic gl2 = new GameLogic();
		GameBoard gb2 = new GameBoard(gl2);		


		BoardHistory bh = new BoardHistory();
		byte[][] arr1 = gb._getTileTypes();
		byte[][] arr2 = gb2._getTileTypes();
		
		assertTrue(bh._equals(arr1, arr2));

		arr1[2][9] = (byte) 1;
		
		assertFalse(bh._equals(arr1,arr2));
	}

}