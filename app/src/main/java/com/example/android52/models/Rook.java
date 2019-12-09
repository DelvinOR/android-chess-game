package com.example.android52.models;

import java.util.HashMap;

/**
 * Class that embodies Rook piece blueprint.
 * 
 * @author Shazim Chaudhary
 * @author Delvin Ortiz
 *
 *
 */
public class Rook extends ChessPiece {

	/**
	 * Constructor for Rook
	 * 
	 * @param side which color is the piece
	 * @param location what location to spawn at
	 */
	public Rook(char side, String location) {
		super(side, location);
		// TODO Auto-generated constructor stub
	}

	public char getCorrectColorPiece(){
		if(this.side == 'w')
			return '\u2656';
		else return '\u265C';
	}

	@Override
	public boolean isMoveValid(HashMap<Character, ChessPiece[]> board, char file, int rank) {
		// Rook can move any number of vacant squares vertically or horizontally
		// It is also moved while the King does the special move of Castling *NEEDs
		// IMPLEMENTATION*
		if (this.file != file && this.rank != rank) {
			// Attempt to diagonally
			return false;
		}

		if (file == this.file) {
			// Rook move is vertical
			int rankDifference = Math.abs(this.rank - rank);
			if (rank > this.rank) {
				for (int cell = 1; cell < rankDifference; cell++) {
					if (board.get(file)[this.rank - 1 + cell] != null)
						return false;
				}
			} else {
				for (int cell = 1; cell < rankDifference; cell++) {
					if (board.get(file)[this.rank - 1 - cell] != null)
						return false;
				}
			}
		} else {
			// Rook move is horizontal
			if (rank == this.rank) {
				int fileDifference = Math.abs(this.file - file);
				if (file > this.file) {
					for (int cell = 1; cell < fileDifference; cell++) {
						if (board.get((char) (this.file + cell))[rank - 1] != null)
							return false;
					}
				} else {
					for (int cell = 1; cell < fileDifference; cell++) {
						if (board.get((char) (this.file - cell))[rank - 1] != null)
							return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * The string format of the Piece.
	 */
	@Override
	public String toString() {
		return this.side + "R";
	}

}
