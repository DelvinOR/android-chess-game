package com.example.android52;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android52.models.ChessBoard;
import com.example.android52.models.ChessPiece;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public ChessBoard chessBoard;
    public HashMap<String, Button> buttonBoard;

    int pressCount = 0;
    char f1, r1, f2, r2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chess_game_layout);

        chessBoard = new ChessBoard();
        buttonBoard = new HashMap<>();
        chessBoard.initializePieces();
        mapBoard(chessBoard);
    }

    @Override
    public void onClick(View v){
        pressCount++;
        String clickedLocation = v.getResources().getResourceName(v.getId());
        clickedLocation = clickedLocation.substring(clickedLocation.indexOf('_')+1);

        if(pressCount == 1) {
            f1 = clickedLocation.charAt(0);
            r1 = clickedLocation.charAt(1);
            //check if the button holds any piece, if so highlight the possible moves they can make on the board
            //if button does not hold a piece, we must reset pressCount so that a new possible move can be made
        }

        if (pressCount == 2) {
            f2 = clickedLocation.charAt(0);
            r2 = clickedLocation.charAt(1);
            //check if the move they're trying to make is valid, gotta use the isMoveValid method on chessPieces.
            ChessPiece pieceSelected = chessBoard.board.get(f1)[r1 - 1];

            if (pieceSelected.isMoveValid(chessBoard.board, f2, r2)) {
                //make the change to set the text of this chess piece to the desired button
                String buttonID = "button_"+f1 + r1;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                Button pieceButton = findViewById(resourceID);

                String pieceSelectedString = pieceButton.getText().toString();
                pieceButton.setText("");

                String desiredButtonID = "button_" + f2 + r2;
                resourceID = getResources().getIdentifier(desiredButtonID,"id", getPackageName());
                pieceButton = findViewById(resourceID);
                pieceButton.setText(pieceSelectedString);
                pieceButton.setTextSize(20);
                pressCount = 0;
                return;
            }else{
                // create toast asking user to try again
                // pressCount goes back to zero for the current player to make a move again
                Toast.makeText(this,"Sorry, invalid move", Toast.LENGTH_LONG).show();
                pressCount = 0;
            }
        }
    }

    public void mapBoard(ChessBoard cb) {
        for (char f = 'a'; f <= 'h'; f++) {
            for (int r = 1; r <= 8; r++) {
                String buttonID = "button_"+f+r;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());

                Button pieceButton = findViewById(resourceID);
                //pieceButton.setTag(1, ""+f+r);
                pieceButton.setOnClickListener(this);
                pieceButton.setText("");
                pieceButton.setTextSize(20);

                String l = f+ "" +r;
                buttonBoard.put(l, pieceButton);

                ChessPiece piece = cb.board.get(f)[r-1];
                if(piece != null && pieceButton != null){
                    pieceButton.setText(piece.getCorrectColorPiece()+"");
                }
            }
        }
    }


}
