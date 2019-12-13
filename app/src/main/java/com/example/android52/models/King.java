package com.example.android52.models;

import java.util.HashMap;

/**
 * Class that embodies the King Piece.
 * 
 * @author Shazim Chaudhary
 * @author Delvin Ortiz
 *
 */
public class King extends ChessPiece {
	boolean alreadyUsedCastling = false;

	/**
	 * Constructor for King
	 * 
	 * @param side     which color is the piece
	 * @param location what location to spawn at
	 */
	public King(char side, String location) {
		super(side, location);
		// TODO Auto-generated constructor stub
	}

	public char getCorrectColorPiece(){
		if(this.side == 'w')
			return '\u2654';
		else return '\u265A';
	}

	@Override
	public boolean isMoveValid(HashMap<Character, ChessPiece[]> board, char file, int rank) {
//		int rankDiff = Math.abs(rank - this.rank);
//		int fileDiff = Math.abs(file - this.file);
//
//		if (rankDiff <= 1 && fileDiff <= 1) {
//			if (!this.movingIntoCheck(board, file, rank))
//				return true;
//		}
		return false;
	}

	/**
	 * The string format of the Piece.
	 */

	@Override
	public String toString() {
		return this.side + "K";
	}

	public boolean movingIntoCheck(HashMap<Character, ChessPiece[]> board, char f, int r) {
		ChessPiece hold = board.get(f)[r - 1];
		char prevFile = this.file;
		int prevRank = this.rank;

		board.get(f)[r - 1] = board.get(prevFile)[prevRank - 1];
		if (this.isBeingChecked(board)) {
			board.get(prevFile)[prevRank - 1] = board.get(f)[r - 1];
			board.get(f)[r - 1] = hold;
			return true;
		}
		return false;
	}

	/**
	 * Method that returns whether this king piece is in check. True if it is.
	 * 
	 * @param board hashmap data structure that holds the pieces.
	 * @return boolean
	 */
	public boolean isBeingChecked(HashMap<Character, ChessPiece[]> board) {

		for (char f = 'a'; f <= 'h'; f++) {
			for (int r = 1; r <= 8; r++) {
				ChessPiece piece = board.get(f)[r - 1];
				if (piece != null) {
					if (piece.side != this.side && piece.isMoveValid(board, this.file, this.rank)) {
						return true;
					}
					// break;
				}
			}
		}

		return false;
	}

}
