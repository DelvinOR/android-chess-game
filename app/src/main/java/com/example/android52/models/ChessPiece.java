package com.example.android52.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An abstract class that holds all needed methods and fields that define what a
 * general chess piece is.
 * 
 * @author Shazim Chaudhary
 * @author Delvin Ortiz
 * 
 */
public abstract class ChessPiece {

	/**
	 * Character type field that specifies which board file the chess piece lies in.
	 */
	char file;
	/**
	 * Character type field that specifies if the chess piece is black or white.
	 */
	char side;
	/**
	 * Integer type field that specifies which board rank the chess piece lies in.
	 */
	int rank;
	/**
	 * Array of String locations that specify what possible valid locations the
	 * piece can take.
	 */
	ArrayList<String> possibleMoves;

	/**
	 * Boolean type field that specifies if this piece is enPassant capturable.
	 */
	boolean enPassantCaptureCandidate = false;
	/**
	 * Boolean type field that specifies if enPassant was attempted on this piece.
	 */
	boolean enPassantCaptureAttempt = false;

	/**
	 * 
	 * Constructor that generates a general chess piece and initializes the piece
	 * with side and location.
	 * 
	 * @param side     specifies which color the piece is initialized to.
	 * @param location specifies where the piece is initialized on the board.
	 */
	public ChessPiece(char side, String location) {
		this.possibleMoves = new ArrayList<String>();
		this.side = side;
		this.file = location.charAt(0);
		this.rank = Character.getNumericValue(location.charAt(1));
	}

	/**
	 * Boolean method that uses the board and location to tell whether the attempted
	 * move by this piece is valid.
	 * 
	 * @param board HashMap data structure of the board
	 * @param file  file character of the attempted move location
	 * @param rank  rank integer of the attempted move location
	 * @return boolean that specifies if the attempted move is valid.
	 */
	public abstract boolean isMoveValid(HashMap<Character, ChessPiece[]> board, char file, int rank);

	/**
	 * Sets the location of this piece with new parameters.
	 * 
	 * @param file file character of the new location.
	 * @param rank rank integer of the new location.
	 */
	public void setLocation(char file, int rank) {
		this.file = file;
		this.rank = rank;
	}

	
	/**
	 * Resets all possible moves this piece can take and updates it's possibleMoves array.
	 * 
	 * @param board the HashMap data structure of the board.
	 */
	public void resetPossibleMoves(HashMap<Character, ChessPiece[]> board) {
		for (char file = 'a'; file <= 'h'; file++) {
			for (int i = 1; i <= 8; i++) {
				if (this.isMoveValid(board, file, i)) {
					this.possibleMoves.add(file + " " + rank);
				}
			}
		}
	}
	
	
}
