package com.example.android52.models;

import java.util.HashMap;

/**
 * Pawn class that embodies the blueprint of a pawn piece.
 * 
 * @author Shazim Chaudhary
 * @author Delvin Ortiz
 *
 */
public class Pawn extends ChessPiece {
	boolean firstTime = true;
	// boolean enPassantCaptureCandidate = false;
	// boolean enPassantCaptureAttempt;

	/**
	 * Constructor for pawn piece.
	 * 
	 * @param side which color is the piece
	 * @param location what location to spawn at
	 */
	public Pawn(char side, String location) {
		super(side, location);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isMoveValid(HashMap<Character, ChessPiece[]> board, char file, int rank) {
		// TODO Auto-generated method stub
		// Pawn cannot move backwards
		// Pawn cannot jump over pieces
		this.enPassantCaptureAttempt = false;
		boolean canMove = false;
		if (this.side == 'w') {
			if (rank > this.rank) {
				if (file == this.file) {
					// If firstTime can move up to 2 spaces forward
					if (firstTime) {
						if (rank <= this.rank + 2) {
							// check that the spots in between are empty and the ending spot is vacant
							for (int i = 0; i < rank - this.rank; i++) {
								canMove = board.get(file)[this.rank + i] == null;
								if (canMove == false) {
									break;
								}
							}

							// If in the first move Pawn moved 2 spots then it is a candidate
							if (firstTime && canMove && rank - 2 == this.rank) {
								this.enPassantCaptureCandidate = true;
							}
							firstTime = false;
						} else {
							canMove = false;
						}
						// firstTime = false; En passant requires a check if a pawn is making a two step
						// move
						// in the first move
					} else {// only allowed to move one space foward
							// canMove = rank == this.rank + 1 && board.get(file)[rank-1] == null;
						if (rank == this.rank + 1 && board.get(file)[rank - 1] == null) {
							this.enPassantCaptureCandidate = false;
							canMove = true;
						}
					}
				} else {// The desired file is not the same and so the move is most likely a capture
					if (firstTime) {
						canMove = false;
					} else {
						if (rank == this.rank + 1 && (file == this.file + 1 || file == this.file - 1)) {
							if (board.get(file)[rank - 1] != null) {
								this.enPassantCaptureCandidate = false;
								canMove = true;
							} else {
								// May be an attempt of an En Passant capture
								if (file >= 'a' && file <= 'h') {
									if (board.get(file)[this.rank - 1] instanceof Pawn) {
										if (board.get(file)[this.rank - 1].enPassantCaptureCandidate) {
											this.enPassantCaptureCandidate = false;
											this.enPassantCaptureAttempt = true;
											canMove = true;
										}
									}
								}
							}
						}
						// canMove = false;
					}
				}
			}
		} else {
			if (rank < this.rank) {
				if (file == this.file) {
					if (firstTime) {
						if (rank >= this.rank - 2) {
							for (int i = 0; i < this.rank - rank; i++) {
								canMove = board.get(file)[this.rank - 2 - i] == null;
								if (canMove == false) {
									break;
								}
							}

							if (firstTime && canMove && rank + 2 == this.rank) {
								enPassantCaptureCandidate = true;
							}
							firstTime = false;
						} else {
							canMove = false;
						}
						// firstTime = false;
					} else {
						if (rank == this.rank - 1 && board.get(file)[rank - 1] == null) {
							enPassantCaptureCandidate = false;
							canMove = true;
						}
					}
				} else {
					// check that Pawn does not try to capture on the firstMove
					if (firstTime) {
						canMove = false;
					} else {
						if (rank == this.rank - 1 && (file == this.file + 1 || file == this.file - 1)
								&& board.get(file)[rank - 1] != null) {
							if (board.get(file)[rank - 1] != null) {
								enPassantCaptureCandidate = false;
								canMove = true;
							} else {
								// May be an attempt of En Passant capture
								if (file >= 'a' && file <= 'h') {
									if (board.get(file)[this.rank - 1] instanceof Pawn) {
										if (board.get(file)[this.rank - 1].enPassantCaptureCandidate) {
											enPassantCaptureCandidate = false;
											enPassantCaptureAttempt = true;
											canMove = true;
										}
									}
								}
							}

						}
					}
				}
			}
		}
		return canMove;
	}

	/**
	 * The string format of the Piece.
	 */
	@Override
	public String toString() {
		return this.side + "P";
	}
}
