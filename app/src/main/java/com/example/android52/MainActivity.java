package com.example.android52;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android52.models.ChessBoard;
import com.example.android52.models.ChessPiece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public ChessBoard chessBoard;
    public TextView turnText;
    public Button resignButton;
    public Button drawButton;
    public Button undoButton;
    public Button aiButton;
    public HashMap<String, Button> buttonBoard;

    public static boolean gameOver = false;
    public static int turn = 0;
    boolean canUndo = false;
    int pressCount = 0;
    char f1, f2;
    int r1, r2;
    ChessPiece attackingPiece;
    ChessPiece dyingPiece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chess_game_layout);
        turnText = findViewById(R.id.turn);
        resignButton = findViewById(R.id.resign_button);
        drawButton = findViewById(R.id.draw_button);
        undoButton = findViewById(R.id.undo_button);
        aiButton = findViewById(R.id.ai_button);

        chessBoard = new ChessBoard();
        buttonBoard = new HashMap<>();
        chessBoard.initializePieces();
        mapBoard(chessBoard);
    }

    @Override
    public void onClick(View v) {
        pressCount++;
        String clickedLocation = v.getResources().getResourceName(v.getId());
        clickedLocation = clickedLocation.substring(clickedLocation.indexOf('_') + 1);

        if (pressCount == 1) {
            f1 = clickedLocation.charAt(0);
            r1 = Character.getNumericValue(clickedLocation.charAt(1));
            attackingPiece = chessBoard.board.get(f1)[r1 - 1];
            if (attackingPiece == null) pressCount = 0;
                //check if the button holds any piece, if so highlight the possible moves they can make on the board
                //if button does not hold a piece, we must reset pressCount so that a new possible move can be made
            else {
                for (String move : attackingPiece.getPossibleMoves()) {
                    char file = move.charAt(0);
                    char rank = move.charAt(1);
                    String buttonID = "button_" + file + "" + rank;
                    int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                    Button box = findViewById(resourceID);
                    //box.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
            }
        }

        if (pressCount == 2) {
            f2 = clickedLocation.charAt(0);
            r2 = Character.getNumericValue(clickedLocation.charAt(1));
            dyingPiece = chessBoard.board.get(f2)[r2 - 1];
            String attemptedMove = f1 + "" + r1 + " " + f2 + "" + r2;
            System.out.println(f1 + "" + r1 + " to " + f2 + "" + r2);
            //check if the move they're trying to make is valid, gotta use the isMoveValid method on chessPieces.

            if (chessBoard.input(attemptedMove, turn)) {
                mapBoard(chessBoard);
                System.out.println("success");
                canUndo = true;
                turn++;
            } else {
                Toast.makeText(this, "Sorry, invalid move", Toast.LENGTH_LONG).show();
            }
            pressCount = 0;
            updateTurnText(turn);
        }
    }

    public void makeAiMove(View view) {
        System.out.println("Making AI move");

        ArrayList<ChessPiece> pieces;
        pieces = turn % 2 == 0 ? chessBoard.getWhitePieces() : chessBoard.getBlackPieces();
//        for(ChessPiece p: pieces){
//            System.out.println(p.toString());
//            for(String move: p.getPossibleMoves()){
//                System.out.println(move);
//            }
//        }

        int randomPieceIndex;
        int randomMoveIndex;
        ChessPiece randomPiece;
        String randomMove;
        boolean moveMade = false;

        while (!moveMade) {
            randomPieceIndex = new Random().nextInt(pieces.size());
            randomPiece = pieces.get(randomPieceIndex);
            if (randomPiece != null) {
                if (randomPiece.getPossibleMoves().size() > 0) {
                    randomMoveIndex = new Random().nextInt(randomPiece.getPossibleMoves().size());
                    randomMove = randomPiece.getPossibleMoves().get(randomMoveIndex);
                    if (randomMove != null && !randomMove.isEmpty()) {
                        if (chessBoard.input(randomPiece.file + "" + randomPiece.rank + " " + randomMove, turn)) {
                            moveMade = true;
                            canUndo = true;
                            turn++;
                            mapBoard(chessBoard);
                            updateTurnText(turn);
                        }
                    }
                }
            }
        }
    }

    public void resign(View view) {
        String winnerText = turn % 2 == 0 ? "White resigns, Black wins." : "Black resigns, White wins.";
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(winnerText).setTitle("Game Over");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                updateWinnerText(turn);
                disableBoard();
            }
        });
        AlertDialog resignDialog = builder.create();
        resignDialog.show();
        RealMainActivity.mMediaRecorder.stop();
        RealMainActivity.mMediaRecorder.reset();
        //startActivity(new Intent(MainActivity.this,RealMainActivity.class));

        // call for save recorded game pop out window
        new RealMainActivity().stopRecordScreen();
        Intent saveGameIntent = new Intent(MainActivity.this, SaveRecordedGame.class);
        startActivity(saveGameIntent);
        finish();
    }

    public void askForDraw(View view) {
        String drawQuestion = turn % 2 == 0 ? "White proposes a draw, does Black agree?" : "Black proposes a draw, does White agree?";
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(drawQuestion).setTitle("Draw?");
        builder.setPositiveButton("Agree to Draw", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                turnText.setText("Draw");
                disableBoard();
            }
        });
        builder.setNegativeButton("Refuse", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //bruh
            }
        });
        AlertDialog drawDialog = builder.create();
        drawDialog.show();
        RealMainActivity.mMediaRecorder.stop();
        RealMainActivity.mMediaRecorder.reset();

        // call for save recorded game pop out window
        new RealMainActivity().stopRecordScreen();;
        Intent saveGameIntent = new Intent(MainActivity.this, SaveRecordedGame.class);
        startActivity(saveGameIntent);
        finish();

        //startActivity(new Intent(MainActivity.this,RealMainActivity.class));
    }

    public void undoLastMove(View view) {
        if (canUndo) {
            chessBoard.board.get(f1)[r1 - 1] = attackingPiece;
            chessBoard.board.get(f2)[r2 - 1] = dyingPiece;
            attackingPiece.setLocation(f1, r1);
            if (dyingPiece != null)
                dyingPiece.setLocation(f2, r2);
            mapBoard(chessBoard);
            turn--;
            updateTurnText(turn);
            canUndo = false;
        }
    }

    public void goHome(View view) {
        Intent homeIntent = new Intent(MainActivity.this, RealMainActivity.class);
        MainActivity.this.startActivity(homeIntent);
    }

    public void disableBoard() {
        resignButton.setEnabled(false);
        undoButton.setEnabled(false);
        drawButton.setEnabled(false);
        aiButton.setEnabled(false);

        for (char f = 'a'; f <= 'h'; f++) {
            for (int r = 1; r <= 8; r++) {
                String buttonID = "button_" + f + "" + r;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                Button pieceButton = findViewById(resourceID);
                pieceButton.setEnabled(false);
            }
        }
    }

    public void mapBoard(ChessBoard cb) {
        if (gameOver) {

        } else {
            for (char f = 'a'; f <= 'h'; f++) {
                for (int r = 1; r <= 8; r++) {
                    String buttonID = "button_" + f + "" + r;
                    int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());

                    Button pieceButton = findViewById(resourceID);
                    //pieceButton.setTag(1, ""+f+r);
                    pieceButton.setOnClickListener(this);
                    pieceButton.setText("");
                    pieceButton.setTextSize(20);

                    String l = f + "" + r;
                    buttonBoard.put(l, pieceButton);

                    ChessPiece piece = cb.board.get(f)[r - 1];
                    if (piece != null && pieceButton != null) {
                        pieceButton.setText(piece.getCorrectColorPiece() + "");
                    }
                }
            }
        }
    }

    public void updateTurnText(int turn) {
        if (turn % 2 == 0)
            turnText.setText("White's Turn");
        else turnText.setText("Black's Turn");
    }

    public void updateWinnerText(int turn) {
        if (turn % 2 == 0)
            turnText.setText("Black Wins");
        else turnText.setText("White Wins");
    }
}
