package com.example.android52.models;

import java.util.HashMap;

/**
 * Class that embodies the knight piece blueprint.
 * 
 * @author Shazim Chaudhary
 * @author Delvin Ortiz
 *
 */
public class Knight extends ChessPiece {

	/**Constructor for Knight piece.
	 * 
	 * @param side which color is the piece
	 * @param location what location to spawn at
	 */
	public Knight(char side, String location) {
		super(side, location);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isMoveValid(HashMap<Character, ChessPiece[]> board, char file, int rank) {
		// Knight can move to a square that is two squares away horizontally
		// and one square vertically.
		// Or two squares vertically and one square horizontal
		if (file == this.file || rank == this.rank)
			return false;

		
		if (rank <= this.rank + 2 && rank >= this.rank - 2) {
			if (rank == this.rank + 1 || rank == this.rank - 1)
				return file == this.file - 2 || file == this.file + 2;
			else 
				return file == this.file - 1 || file == this.file + 1;
		}
		//boolean inRankRange = ((rank <= this.rank + 2) && rank <= 8) && ((rank >= this.rank - 2) && rank >= 0);
		//boolean inFileRange = (file <= ((char)(this.file + 2)) && file <= 'h') && (file >= ((char)(this.file - 2)) && file >= 'a');
		
		//if (inRankRange && inFileRange) {
			// If rank is this.rank + 1 file must be this.file + 2
			// or
			// If rank is this.rank + 2 file must be this.file + 1
		//	switch (rank) {
		//		case this.rank + 1 :
		//			return file == (char) this.file - 2 || file == (char) this.file + 2;
		//		case this.rank + 2 :
		//			return file == (char) this.file - 1 ||  file == (char) this.file + 1;
		//		default :
		//			return false;
		//	}
		//}
		return false;
	}

	/**
	 * The string format of the Piece.
	 */
	@Override
	public String toString() {
		return this.side + "N";
	}
}
