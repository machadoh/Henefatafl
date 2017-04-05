package Hnefatafl;

import java.util.*;
import java.io.*;
import java.lang.*;

public class BoardHistory
{

	/*
	* Store GameBoard states
	*/
	private ArrayList<byte[][]> _history;
	private ArrayList<byte[][]> _doubleState;


	/**
	* Constructor for BoardHistory
	*/

	public BoardHistory()
	{	
		_history = new ArrayList<byte[][]>();
		_doubleState = new ArrayList<byte[][]>();
		
	}

	/**
	* Adds every board state to the ArrayList 
	* @param GameBoard object
	*/

	public void _addHistory(GameBoard _gb)
	{	

		_history.add(_gb._getTileTypes());

	}

	/**
	* Adds board state that are repeated twice to the ArrayList 
	* @param GameBoard object
	*/

	public void _addDouble(GameBoard _gb)
	{

		_doubleState.add(_gb._getTileTypes());

	}
	
	public void _clearHistory()
	{
		_history.clear();
		_doubleState.clear();
	}

	/**
	* the function will return true if new BoardState is equal to a BoardState in the 
	*ArrayList _doubleState, and return false otherwise
	* @param GameBoard object
	*/
	
	public boolean _repeatedStateX3(GameBoard _gb)
	{
		
		byte[][] arr2 = _gb._getTileTypes();
		
		
		
		for(int j=_doubleState.size(); j > 0; j--){
			byte[][] arr1 = _doubleState.get(_doubleState.size()-j);

			if(_equals(arr1,arr2))
			{
				_history.clear();
				_doubleState.clear();
				return true;
			}
		}

		_repeatedStateX2(_gb);
		return false;
		

	}

	/**
	* the function will add call _addDouble() if new BoardState is equal to a BoardState in the
	*ArrayList _history, if not equal it call _addHistory()
	* @param GameBoard object
	*/

	public void _repeatedStateX2(GameBoard _gb)
	{
		
		byte[][] arr2 = _gb._getTileTypes();
		
		for(int j=_history.size(); j > 0; j--){
			byte[][] arr1 = _history.get(_history.size()-j);
			if(_equals(arr1,arr2))
			{
				_addDouble(_gb);
			}
		}
		_addHistory(_gb);

	}

	/**
	* The function will compare every element of the 2d arrays
	* if not equal it will return false, and true otherwise
	* @param two 2d arrays
	*/

	public boolean _equals(byte[][] arr1, byte[][] arr2) 
	{

		for(int y = 0; y < 11; y++) 
		{
			for(int x = 0; x < 11; x++)
			{
			 if(arr1[y][x] != arr2[y][x])
				return false;
			}
		}
		return true;
	}
	/**
	* The function print out the 2d array, mainly for debugging 
	* @param 2d arrays
	*/

	public void _print2dArray(byte[][] arr)
	{
		for(int i=0; i<arr.length; i++)
		{
			System.out.println(Arrays.toString(arr[i]));
		}
		System.out.println();
	}

	/**
	* return array list of board states
	*/
	public ArrayList<byte[][]> _getHistory()
	{
		return _history;
	}
	
	/**
	* return array list of duplicate board states
	*/
	public ArrayList<byte[][]> _getDoubleState()
	{
		return _doubleState;
	}
	
}