package com.example.android52.controllers;

import java.util.Scanner;

import com.example.android52.models.ChessBoard;
import com.example.android52.models.King;
import com.example.android52.models.Pawn;
import com.example.android52.models.Rook;

public class Controller {
	public static int turn;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ChessBoard myChessBoard = new ChessBoard();

		turn = 0;
		// boolean draw = false;
		int draw = 0;
		boolean gameOver = false;
		String input = "";

		myChessBoard.initializePieces();
//		ChessBoard.board.get('e')[0] =  new King('w', "e1");
//		ChessBoard.board.get('e')[7] =  new King('b', "e8");
//		ChessBoard.board.get('h')[0] =  new Rook('w', "h1");
//		ChessBoard.board.get('h')[7] =  new Rook('b', "h8");

		while (!gameOver) {
			myChessBoard.displayBoard();

			switch (turn % 2) {
			case (0):
				System.out.print("White's turn: ");
				input = sc.nextLine();
				input = input.toLowerCase();
				if (input.equals("resign")) {
					gameOver = true;
					System.out.println("Black wins");
				} else {
					if (isCorrectFormat(input)) {
						myChessBoard.input(input, turn);
						if (input.contains("draw"))
							draw++;
					} else {
						System.out.println("Please enter a valid command or move.");
						turn--;
					}
				}
				break;
			default:
				System.out.print("Black's turn: ");
				input = sc.nextLine();
				input = input.toLowerCase();
				if (input.equals("resign")) {
					gameOver = true;
					System.out.println("White wins");

				} else if (input.contains("draw")) {
					draw++;
				} else {
					if (isCorrectFormat(input)) {
						myChessBoard.input(input, turn);
					} else {
						System.out.println("Please enter a valid command or move.");
						turn--;
					}
				}
				break;
			}

			if (draw == 2)
				gameOver = true;
			turn++;
			System.out.println();
		}

		sc.close();

	}

	public static boolean isCorrectFormat(String s) {
		if (s.length() < 5 || s.charAt(2) != ' ')
			return false;

		char f1 = s.charAt(0);
		char r1 = s.charAt(1);
		char f2 = s.charAt(3);
		char r2 = s.charAt(4);

		boolean f1Valid = f1 >= 'a' && f1 <= 'h';
		boolean f2Valid = f2 >= 'a' && f2 <= 'h';
		boolean r1Valid = Character.getNumericValue(r1) >= 1 && Character.getNumericValue(r1) <= 8;
		boolean r2Valid = Character.getNumericValue(r2) >= 1 && Character.getNumericValue(r2) <= 8;

		return f1Valid && r1Valid && f2Valid && r2Valid;
	}

}
