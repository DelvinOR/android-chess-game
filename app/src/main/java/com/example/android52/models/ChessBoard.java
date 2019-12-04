package com.example.android52.models;

import java.util.HashMap;

import com.example.android52.controllers.Controller;

/**
 * Class that holds the ChessBoard's data structure, global fields and methods.
 * Blueprint of the ChessBoard.
 * 
 * @author Shazim Chaudhary
 * @author Delvin Ortiz
 *
 * 
 */
public class ChessBoard {
	/**
	 * HashMap Data structure that holds the pieces of the board.
	 */
	public HashMap<Character, ChessPiece[]> board;
	/**
	 * White side king, globally accessible by all pieces for check purposes.
	 */
	public static King whiteKing;
	/**
	 * Black side king, globally accessible by all pieces for check purposes.
	 */
	public static King blackKing;

	/**
	 * Constructor that makes an instance of the board.
	 */
	public ChessBoard() {
		board = new HashMap<Character, ChessPiece[]>();
		for (char file = 'a'; file <= 'h'; file++) {
			board.put(file, new ChessPiece[8]);
		}
	}

	/**
	 * Method that initializes the pieces on the board in the standard chess way.
	 */
	public void initializePieces() {
		for (char file = 'a'; file <= 'h'; file++) {

			switch (file) {
			case 'a':
				board.get(file)[0] = new Rook('w', file + "1");
				board.get(file)[1] = new Pawn('w', file + "2");
				board.get(file)[7] = new Rook('b', file + "8");
				board.get(file)[6] = new Pawn('b', file + "7");
				break;
			case 'b':
				board.get(file)[0] = new Knight('w', file + "1");
				board.get(file)[1] = new Pawn('w', file + "2");
				board.get(file)[7] = new Knight('b', file + "8");
				board.get(file)[6] = new Pawn('b', file + "7");
				break;
			case 'c':
				board.get(file)[0] = new Bishop('w', file + "1");
				board.get(file)[1] = new Pawn('w', file + "2");
				board.get(file)[7] = new Bishop('b', file + "8");
				board.get(file)[6] = new Pawn('b', file + "7");
				break;
			case 'd':
				board.get(file)[0] = new Queen('w', file + "1");
				board.get(file)[1] = new Pawn('w', file + "2");
				board.get(file)[7] = new Queen('b', file + "8");
				board.get(file)[6] = new Pawn('b', file + "7");
				break;
			case 'e':
				board.get(file)[0] = new King('w', file + "1");
				//whiteKing = (King) board.get(file)[0];
				board.get(file)[1] = new Pawn('w', file + "2");
				board.get(file)[7] = new King('b', file + "8");
				//blackKing = (King) board.get(file)[7];
				board.get(file)[6] = new Pawn('b', file + "7");
				break;
			case 'f':
				board.get(file)[0] = new Bishop('w', file + "1");
				board.get(file)[1] = new Pawn('w', file + "2");
				board.get(file)[7] = new Bishop('b', file + "8");
				board.get(file)[6] = new Pawn('b', file + "7");
				break;
			case 'g':
				board.get(file)[0] = new Knight('w', file + "1");
				board.get(file)[1] = new Pawn('w', file + "2");
				board.get(file)[7] = new Knight('b', file + "8");
				board.get(file)[6] = new Pawn('b', file + "7");
				break;
			case 'h':
				board.get(file)[0] = new Rook('w', file + "1");
				board.get(file)[1] = new Pawn('w', file + "2");
				board.get(file)[7] = new Rook('b', file + "8");
				board.get(file)[6] = new Pawn('b', file + "7");
				break;
			}
		}
	}

	/**
	 * @param move Chess move that the player makes by entering format like so :
	 *             "filerank filerank"
	 * @param side Which side is making the move. Side of the player.
	 */
	public void input(String move, int side) {
		char currFile = move.charAt(0);
		int currRank = Character.getNumericValue(move.charAt(1));
		char nextFile = move.charAt(3);
		int nextRank = Character.getNumericValue(move.charAt(4));
		char currSide = side % 2 == 0 ? 'w' : 'b';

		ChessPiece currPiece = board.get(currFile)[currRank - 1];
		ChessPiece nextPiece = board.get(nextFile)[nextRank - 1];

		if (currFile == nextFile && currRank == nextRank) {
			ill();
			Controller.turn--;
		} else if (currPiece == null || currPiece.side != currSide) {
			ill();
			Controller.turn--;
		} else if (nextPiece != null && nextPiece.side == currSide) {
			ill();
			Controller.turn--;
		} else {
			// other checks
			// check to see if the players move is valid
			if (currPiece.isMoveValid(board, nextFile, nextRank)) {
				// If this move leads to a capture of the King, the game ends.
				// or check if the nextPiece is a pawn en passant.

				if (currPiece instanceof Pawn) {
					// Check that there is not an attempt of en passant
					if (board.get(currPiece.file)[currPiece.rank - 1].enPassantCaptureAttempt) {
						movePiece(currFile, currRank, nextFile, nextRank);
						currPiece.setLocation(nextFile, nextRank);
						board.get(nextFile)[currRank - 1] = null;
						// checkForCheck();
					} else {
						movePiece(currFile, currRank, nextFile, nextRank);
						currPiece.setLocation(nextFile, nextRank);
						// checkForCheck();
					}

					// check for a possible Pawn promotion
					if (nextRank == 1 || nextRank == 8) {
						if (move.length() >= 7) {
							// Promotion specified
							char newPiece = move.charAt(6);
							switch (newPiece) {
							case 'r':
								board.get(currPiece.file)[currPiece.rank - 1] = new Rook(currPiece.side,
										String.valueOf(currPiece.file) + String.valueOf(currPiece.rank));
								break;
							case 'b':
								board.get(currPiece.file)[currPiece.rank - 1] = new Bishop(currPiece.side,
										String.valueOf(currPiece.file) + String.valueOf(currPiece.rank));
								break;
							case 'n':
								board.get(currPiece.file)[currPiece.rank - 1] = new Knight(currPiece.side,
										String.valueOf(currPiece.file) + String.valueOf(currPiece.rank));
								break;
							case 'q':
								board.get(currPiece.file)[currPiece.rank - 1] = new Queen(currPiece.side,
										String.valueOf(currPiece.file) + String.valueOf(currPiece.rank));
								break;
							default:
								board.get(currPiece.file)[currPiece.rank - 1] = new Queen(currPiece.side,
										String.valueOf(currPiece.file) + String.valueOf(currPiece.rank));
								break;
							}
						} else {
							// promotion was not specified
							// default promotion is to a Queen
							board.get(currPiece.file)[currPiece.rank - 1] = new Queen(currPiece.side,
									String.valueOf(currPiece.file) + String.valueOf(currPiece.rank));
						}
						// checkForCheck();
					}
				} else {// Not a Pawn
					movePiece(currFile, currRank, nextFile, nextRank);
					currPiece.setLocation(nextFile, nextRank);
					// checkForCheck();
				}

			} else {
				ill();
				Controller.turn--;
			}
		}
	}

	/**
	 * Method that moves the specified piece at currFile, currRank to the location
	 * nextFile, nextRank
	 * 
	 * @param currFile file of the piece attempted to be moved.
	 * @param currRank rank of the piece attempted to be moved.
	 * @param nextFile file of the location attempted to be moved into.
	 * @param nextRank rank of the location attempted to be moved into.
	 */
	public void movePiece(char currFile, int currRank, char nextFile, int nextRank) {
		board.get(nextFile)[nextRank - 1] = board.get(currFile)[currRank - 1];
		board.get(currFile)[currRank - 1] = null;
	}

	/**
	 * Checks if any of the kings are in check and notifies the users.
	 */
	public void checkForCheck() {
		if (whiteKing.isBeingChecked(board) || blackKing.isBeingChecked(board)) {
			System.out.println("Check");
		}
	}

	/**
	 * Displays the board in a specific format.
	 */
	public void displayBoard() {
		for (int i = 7; i >= 0; i--) {
			String row = "";
			for (char chr = 'a'; chr <= 'h'; chr++) {
				ChessPiece piece = board.get(chr)[i];
				if (piece == null) {
					boolean blackCell = (i % 2 == 0 && chr % 2 == 0) || (i % 2 != 0 && chr % 2 != 0);
					if (blackCell)
						row += "##";
					else
						row += "  ";
				} else
					row += board.get(chr)[i];
				row += " ";
			}
			int rank = i + 1;
			System.out.println(row + rank);
		}
		System.out.println(" a  b  c  d  e  f  g  h  ");
	}

	/**
	 * Prints the appropriate response to illegally attempted moves.
	 */
	public void ill() {
		System.out.println("Illegal move, try again.");
	}
}
