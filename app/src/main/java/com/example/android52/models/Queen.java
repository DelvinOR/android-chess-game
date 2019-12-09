package com.example.android52.models;

import java.util.HashMap;

/**
 * Class that embodies the Queen piece.
 * 
 * @author Shazim Chaudhary
 * @author Delvin Ortiz
 */
public class Queen extends ChessPiece {

	/**
	 * Constructor for Queen piece.
	 * 
	 * @param side     which color is the piece
	 * @param location what location to spawn at
	 */
	public Queen(char side, String location) {
		super(side, location);
		// TODO Auto-generated constructor stub
	}

	public char getCorrectColorPiece(){
		if(this.side == 'w')
			return '\u2655';
		else return '\u265B';
	}

	@Override
	public boolean isMoveValid(HashMap<Character, ChessPiece[]> board, char file, int rank) {
		// Queen can move any number of vacant square diagonally, horizontaslly, or
		// vertically
		// return board.get(file)[rank-1] == null;

		return new Bishop(this.side, String.valueOf(this.file) + String.valueOf(this.rank)).isMoveValid(board, file,
				rank)
				|| new Rook(this.side, String.valueOf(this.file) + String.valueOf(this.rank)).isMoveValid(board, file,
						rank);
	}

	/**
	 * String format of the piece.
	 */
	@Override
	public String toString() {
		return this.side + "Q";
	}
}
