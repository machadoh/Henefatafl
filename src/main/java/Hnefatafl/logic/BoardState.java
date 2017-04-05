package Hnefatafl.logic;

import java.util.InputMismatchException;

public class BoardState {
	
	private int _boardSize;
	private int[][] _board;

	//Total attackers and defenders on board (including King)
	private int _totalAtt, _totalDef;
	
	//0 = attackers(default), 1 = defenders
	private int _whoseTurn;
	
	
	//Constructers-------------------------------------------------------------------
	
	BoardState(int[][] _board, int _whoseTurn) {
		
		this._board = _board;
		
		if(_whoseTurn == 0 || _whoseTurn == 1){
			this._whoseTurn = _whoseTurn;
		} else {
			throw new IllegalStateException("You must specify whose turn it is.");
		}

		
	}	
	
	BoardState(int[][] _board, int _whoseTurn, int _boardSize) {
		
		if(_board[0].length != _boardSize) {
			throw new IllegalStateException("The board is not the correct size!!!");
		}
		
		this._board = _board;
		this._boardSize = _boardSize;
		
		if(_whoseTurn == 0 || _whoseTurn == 1){
			this._whoseTurn = _whoseTurn;
		} else {
			throw new IllegalStateException("You must specify whose turn it is.");
		}

		
	}	
	
	BoardState(int _boardSize) {
		
		if(_boardSize == 11) {
			
			int[][] _newBoard = { 
					{0,0,0,1,1,1,1,1,0,0,0},
					{0,0,0,0,0,1,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0,0},
					{1,0,0,0,0,2,0,0,0,0,1},
					{1,0,0,0,2,2,2,0,0,0,1},
					
					{1,1,0,0,2,3,2,0,0,1,1},
					
					{1,0,0,0,2,2,2,0,0,0,1},
					{1,0,0,0,0,2,0,0,0,0,1},
					{0,0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,1,0,0,0,0,0},
					{0,0,0,1,1,1,1,1,0,0,0}
			};
			
			_board = _newBoard;
			
			_totalAtt = 24;
			_totalDef = 13;
			
			_whoseTurn = 0;
			
		} else if(_boardSize == 9) {
			
			int[][] _newBoard = { 
					{0,0,0,1,1,1,0,0,0},
					{0,0,0,0,1,0,0,0,0},
					{0,0,0,0,2,0,0,0,0},
					{1,0,0,0,2,0,0,0,1},
					
					{1,1,2,2,3,2,2,1,1},
					
					{1,0,0,0,2,0,0,0,1},
					{0,0,0,0,2,0,0,0,0},
					{0,0,0,0,1,0,0,0,0},
					{0,0,0,1,1,1,0,0,0}
			};
			
			_board = _newBoard;
			
			_totalAtt = 12;
			_totalDef = 9;
			
			_whoseTurn = 0;
			
		} else if (_boardSize%2 != 1) {
			
			throw new InputMismatchException("The board size must be an odd numbers!!!");
		}

	}
	
	
	//Methods-----------------------------------------------------------------------------
	
	/**
	 * @return 2D int array representation of board
	 * */
	public int[][] _getBoard() {
		return _board;
	}
	
	/**
	 * @return int array representation of attackers and defenders
	 * */
	public int[] _getAttDef() {
		//Indexes: attackers = 0, defenders = 1
		int[] AttDef = {_totalAtt, _totalDef};
		
		return AttDef;
	}
	
	/**
	 * @return total pieces on board
	 * */
	public int _totalPieces() {		
		return _totalAtt + _totalDef;
	}
	
	/**
	 * @return whose turn it is
	 * */
	public int _getWhoseTurn() {
		return _whoseTurn;
	}
	
	/**
	 * @return board size
	 * */
	public int _getBoardSize() {
		return _boardSize;
	}
	
	/**
	 * @param _board
	 * */
	public void set_board(int[][] _board) {
		this._board = _board;
	}
	
	/**
	 * @param _bs
	 * @return true if this board state is equal to _bs
	 * */
	public boolean equals(BoardState _bs) {
		
		int[][] _boardToComp = _bs._getBoard();
		
		for(int i = 0; i < _boardSize; i++) {
			for(int j = 0; j < _boardSize; j++) {
				
				if(this._board[i][j] != _boardToComp[i][j]) return false;
			}
		}
		
		return true;
	}

}