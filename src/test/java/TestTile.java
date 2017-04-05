import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Hnefatafl.Tile;
import Hnefatafl.GameLogic;

public class TestTile
{
	@Test
	public void testGetX()
	{
		GameLogic gl = new GameLogic();
		Tile t = new Tile(0, 1, 2, gl);
		assertTrue(t._getX() == 0);
	}
	
	@Test
	public void testGetY()
	{
		GameLogic gl = new GameLogic();
		Tile t = new Tile(0, 1, 2, gl);
		assertTrue(t._getY() == 1);
	}
	
	@Test
	public void testGetType()
	{
		GameLogic gl = new GameLogic();
		Tile t = new Tile(0, 1, 2, gl);
		assertTrue(t._getType() == 2);
	}
	
	@Test
	public void testSetX()
	{
		GameLogic gl = new GameLogic();
		Tile t = new Tile(0, 1, 2, gl);
		assertTrue(t._getX() == 0);
		t._setX(1);
		assertTrue(t._getX() == 1);
	}
	
	@Test
	public void testSetY()
	{
		GameLogic gl = new GameLogic();
		Tile t = new Tile(0, 1, 2, gl);
		assertTrue(t._getY() == 1);
		t._setY(0);
		assertTrue(t._getY() == 0);
	}
	
	@Test
	public void testSetType()
	{
		GameLogic gl = new GameLogic();
		Tile t = new Tile(0, 1, 2, gl);
		assertTrue(t._getType() == 2);
		t._setType(0);
		assertTrue(t._getType() == 0);
	}
	
	@Test
	public void testIsKingdomUnit1()
	{
		GameLogic gl = new GameLogic();
		Tile t = new Tile(0, 1, 4, gl);
		assertEquals(t._isKingdomUnit(), 0);
	}
	
	@Test
	public void testIsKingdomUnit2()
	{
		GameLogic gl = new GameLogic();
		Tile t = new Tile(0, 1, 9, gl);
		assertEquals(t._isKingdomUnit(), 1);
	}
	
	@Test
	public void testIsKingdomUnit3()
	{
		GameLogic gl = new GameLogic();
		Tile t = new Tile(0, 1, 11, gl);
		assertEquals(t._isKingdomUnit(), 2);
	}
	
	@Test
	public void testToString()
	{
		GameLogic gl = new GameLogic();
		Tile t = new Tile(0, 1, 2, gl);
		assertTrue(t._toString().equals("0 1 2"));
	}
}