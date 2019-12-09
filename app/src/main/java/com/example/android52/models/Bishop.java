package com.example.android52.models;

import java.util.HashMap;

/**
 * Class that embodies the Bishop piece.
 * 
 * @author Shazim Chaudhary
 * @author Delvin Ortiz
 */
public class Bishop extends ChessPiece {

	/**
	 * Constructor for Bishop
	 * 
	 * @param side     which color is the piece
	 * @param location what location to spawn at
	 */
	public Bishop(char side, String location) {
		super(side, location);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isMoveValid(HashMap<Character, ChessPiece[]> board, char file, int rank) {
		// Bishop can move any number of vacant squares in any diagonal direction
		// file and rank can't be the same

		if (this.file == file || this.rank == rank) {
			return false;
		}

		// Confirm that the move is diagonal
		if (Math.abs(rank - this.rank) != Math.abs(file - this.file)) {
			return false;
		}
		// Moving up board
		if (rank - this.rank > 0) {
			// Moving right
			if (file - this.file > 0) {
				for (int i = 1; i < rank - this.rank; i++) {
					if (board.get((char) (this.file + i))[this.rank + i - 1] != null) {
						return false;
					}
				}
			} else {// Moving left
				for (int i = 1; i < rank - this.rank; i++) {
					if (board.get((char) (this.file - i))[this.rank + i - 1] != null) {
						return false;
					}
				}
			}
		} else {// Moving down the board
			if (file - this.file > 0) {
				for (int i = 1; i < this.rank - rank; i++) {
					if (board.get((char) (this.file + i))[this.rank - i - 1] != null) {
						return false;
					}
				}
			} else {
				for (int i = 1; i < this.rank - rank; i++) {
					if (board.get((char) (this.file - i))[this.rank - i - 1] != null) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public char getCorrectColorPiece(){
		if(this.side == 'w')
			return '\u2657';
		else return '\u265D';
	}

	/**
	 * The string format of the Piece.
	 */
	@Override
	public String toString() {
		return this.side + "B";
	}
}
