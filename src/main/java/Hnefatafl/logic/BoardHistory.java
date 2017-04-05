package Hnefatafl.logic;

import java.util.LinkedList;

public class BoardHistory {
	
	LinkedList<BoardState> _listOfStates;
	
	//Total attackers and defenders (including King)
	private int _totalAtt, _totalDef;
	
	//Length of one side of square board
	private int _boardSize;
	
	BoardHistory(BoardState board) {
		_listOfStates.addLast(board);
	}
	
	/**
	 * @return number of moves played so far
	 * */
	public int _getListLength() {
		return _listOfStates.size();
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
	 * @return total pieces left on board
	 * */
	public int _totalPieces() {		
		return _totalAtt + _totalDef;
	}
	
	/**
	 * @return board size
	 * */
	public int get_boardSize() {
		return _boardSize;
	}
	
}
