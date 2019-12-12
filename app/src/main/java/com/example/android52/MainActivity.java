package com.example.android52;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    public void onClick(View v) {
        pressCount++;
        String clickedLocation = v.getResources().getResourceName(v.getId());
        clickedLocation = clickedLocation.substring(clickedLocation.indexOf('_')+1);

        if(pressCount == 1){
            f1 = clickedLocation.charAt(0);
            r1 = clickedLocation.charAt(1);
            //check if the button holds any piece, if so highlight the possible moves they can make on the board
        }

        if(pressCount == 2){
            f2 = clickedLocation.charAt(0);
            r2 = clickedLocation.charAt(1);
            //check if the move they're trying to make is valid, gotta use the isMoveValid method on chessPieces.
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
