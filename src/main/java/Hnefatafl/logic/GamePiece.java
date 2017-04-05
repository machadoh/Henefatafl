package Hnefatafl.logic;

public class GamePiece {
	
	int x_coord, y_coord;
	int pieceType;
	
	GamePiece(int x, int y, int type) {
		
		x_coord = x;
		y_coord = y;
		
		pieceType = type;
	}
	
	//Getters----------------------------------------------
	public int getX_coord() {
		return x_coord;
	}
	public int getY_coord() {
		return y_coord;
	}
	public int getPieceType() {
		return pieceType;
	}
	
	
}
