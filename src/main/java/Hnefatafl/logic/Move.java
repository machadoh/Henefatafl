package Hnefatafl.logic;

public class Move {
	
	private int _fromX, _fromY;
	
	private int _toX, _toY;
	
	// 1 = enemy(attackers), 2 = soldier(defenders), 3 = wizard(king)
	private int _pieceType;
	
	//Movement along X or Y
	boolean _moveAlongX;
	
	
	//Constructor-----------------------------------------------------
	Move(int fromX, int fromY, int toX, int toY, int piece) {
		_fromX = fromX;
		_fromY = fromY;
		
		_toX = toX;
		_toY = toY;
		
		_pieceType = piece;
		
		if(fromX == toX) {
			_moveAlongX = true;
		} else {
			_moveAlongX = false;
		}
	}
	
	//Methods---------------------------------------------------------

	/**
	 * @return int array representation of from Tile attributes
	 * */
	public int[] _fromPosition() {
		
		int[] from = {_pieceType, _fromX, _fromY};
		
		return from;
	}
	
	/**
	 * @return int array representation of to Tile attributes
	 * */
	public int[] _toPosition() {
		
		int[] to = {_pieceType, _toX, _toY};
		
		return to;
	}
	
	
	//Getters---------------------------------------------------------
	
	/**
	 * @return true if moving on x-axis; false, otherwise
	 * */
	public boolean get_moveOnX() {
		return _moveAlongX;
	}

	/**
	 * @return from Tile x coordinate
	 * */
	public int get_fromX() {
		return _fromX;
	}

	/**
	 * @return from Tile y coordinate
	 * */
	public int get_fromY() {
		return _fromY;
	}

	/**
	 * @return to Tile x coordinate
	 * */
	public int get_toX() {
		return _toX;
	}

	/**
	 * @return to Tile y coordinate
	 * */
	public int get_toY() {
		return _toY;
	}
	
	/**
	 * @return Tile piece type
	 * */
	public int get_pieceType() {
		return _pieceType;
	}

}
