package Hnefatafl.logic;

import java.util.Stack;

public class Rulebook {
	
	BoardState _bs;
	int[][] _board;
	Move _move;
	
	//Coordinates of spot being moved to
	int x, y;
	
	
	//Constructors------------------------------------------------
	Rulebook(BoardState bs, Move move) {
		_bs = bs;
		_board = bs._getBoard();
		_move = move;
		
		x = move.get_toX();
		y = move.get_toY();
	}
	
	//Rule Checking-----------------------------------------------
	/**
	 * Checks following conditions to check move validity:
	 *   - Piece moving on single axis
	 *   - Piece not colliding with anything on path
	 *   - If moving to restricted space, must be King
	 * @return True if move allowed, else false
	 */
	public boolean _isMoveValid() {
		
		if(!(_movementOnSingleAxis())) return false;
		
		if(_collisionsOnPath()) return false;
		
		if(_movementToRestrictedSpace() && _move.get_pieceType() != 3) return false;
		
		//Don't need to check if moving between enemies since piece can't die on its move
		
		//If move is valid, update BoardState
		_board[_move.get_fromX()][_move.get_fromY()] = 0;
		_board[_move.get_toX()][_move.get_toY()] = _move.get_pieceType();
		_bs.set_board(_board);
		
		
		//Remove captured pieces from board
		_removeCaptured(_anyCaptures());
		
		return true;
	}
	
	public Stack<GamePiece> _anyCaptures() {
		
		Stack<GamePiece> captured = new Stack<GamePiece>();
		
		//Did attackers or defenders move?
		if(_move.get_pieceType() == 1) {
			
			//Attackers moved
			
			
			
		} else if(_move.get_pieceType() == 2 || _move.get_pieceType() == 3) {
			
			//Defenders moved
			
		}
		
		
		return captured;
	}
	
	
	//Supports methods-----------------------------------------------
	private boolean _movementToRestrictedSpace() {
		
		int size = _bs._getBoardSize() - 1;
		
		int x = _move.get_toX();
		int y = _move.get_toY();
		
		//Top left
		if(x == 0 && y == 0) return true;
		//Top right
		if(x == size && y == 0) return true;
		//Bottom left
		if(x == 0 && y == size) return true;
		//Bottom right
		if(x == size && y == size) return true;
		//Throne (center)
		if(x == size/2 && y == size/2) return true;
		
		return false;		
		
	}
	
	private boolean _movementOnSingleAxis() {
		
		//False if moved to same spot
		if((_move.get_fromX() == _move.get_toX()) && (_move.get_fromY() == _move.get_toY())) return false;
		
		//Single axis movement check
		if(!((_move.get_fromX() == _move.get_toX()) || (_move.get_fromY() == _move.get_toY()))) return false;
		
		return true;
	}
	
	private  boolean _collisionsOnPath() {
		
		if(_move.get_moveOnX()) {
			
			if(_move.get_fromX() < _move.get_toX()) {
				
				for(int i = _move.get_fromX(); i <= _move.get_toX(); i++) 
					if(_board[_move.get_fromY()][i] != 0) return true;
				
			} else {
				
				for(int i = _move.get_fromX(); i <= _move.get_toX(); i--) 
					if(_board[_move.get_fromY()][i] != 0) return true;
			}

			
		} else {
			
			if(_move.get_fromY() < _move.get_toY()) {
				
				for(int i = _move.get_fromY(); i <= _move.get_toY(); i++) 
					if(_board[i][_move.get_fromX()] != 0) return true;
				
			} else {
				
				for(int i = _move.get_fromY(); i <= _move.get_toY(); i--) 
					if(_board[i][_move.get_fromX()] != 0) return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns an boolean array containing whether or not a piece has enemies in the cardinal directions: N,E,S,W
	 * @param type If checking for attacker, pass in 1, otherwise 0 for defender
	 * @return Boolean array, relative enemy locations
	 */
	private boolean[] _adjacentEnemies(int type) {
		
		//[North(1), East(2), South(3), West(4)]
		boolean[] enemiesNESW = {false, false, false, false};

		
		if(type == 1) {
			if(_board[x][y+1] == 2 || _board[x][y+1] == 3) enemiesNESW[1] = true;
			if(_board[x+1][y] == 2 || _board[x+1][y] == 3) enemiesNESW[1] = true;
			if(_board[x][y-1] == 2 || _board[x][y-1] == 3) enemiesNESW[1] = true;
			if(_board[x-1][y] == 2 || _board[x-1][y] == 3) enemiesNESW[1] = true;
			
		} else {
			if(_board[x][y+1] == 1) enemiesNESW[1] = true;
			if(_board[x+1][y] == 1) enemiesNESW[1] = true;
			if(_board[x][y-1] == 1) enemiesNESW[1] = true;
			if(_board[x-1][y] == 1) enemiesNESW[1] = true;
		}
		
		return enemiesNESW;
	}
	
	/**
	 * Is the king surrounded by the enemy?
	 * @param x King's x position
	 * @param y King's y position
	 * @return True if king is surrounded
	 */
	public boolean _isKingCaptured(int x, int y) {
		
		int size = _bs._getBoardSize() - 1;
		
		//Is king on edge? Can't be captured
		if(x == 0 || x == size || y == 0 || y == size) return false;
		

		//Make throne hostile space if unoccupied
		if(x != size/2 && y != size/2) _board[size/2][size/2] = 1;
		
		//Is King surrounded?
		if(_board[x][y+1] == 1 && _board[x+1][y] == 1 && _board[x][y-1] == 1 && _board[x-1][y] == 1) {
			
			_board[size/2][size/2] = 0;
			return true;
		}
		
		
		//Reset throne
		_board[size/2][size/2] = 0;
		
		return false;
	}

	public boolean _isPieceCaptured() {
		
		int size = _bs._getBoardSize() - 1;
		
		return false;
	}
	
	
	/**
	 * Sets captured pieces to 0 on game board.
	 * @param cap Stack of captured game pieces
	 */
	private void _removeCaptured(Stack<GamePiece> cap) {
		
		GamePiece temp;
		
		while(!cap.empty()) {
			temp = cap.pop();
			
			_board[temp.getY_coord()][temp.getX_coord()] = 0;
		}
	}

	public int[] _getCardinalCoordinate(int direction) {
		
		//[North(1), East(2), South(3), West(4)]
		if(direction == 1) {
			int[] coordinates = {x, y-1};
			return coordinates;
			
		} else if(direction == 2) {
			int[] coordinates = {x+1, y};
			return coordinates;
			
		} else if(direction == 3) {
			int[] coordinates = {x, y+1};
			return coordinates;
			
		} else if(direction == 4) {
			int[] coordinates = {x-1, y};
			return coordinates;
		}
		
		return null;
	}
	
	
	public boolean _shieldwallCapture(){
		
		return false;
	}
}
