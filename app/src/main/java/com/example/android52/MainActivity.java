package com.example.android52;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android52.controllers.Controller;
import com.example.android52.models.ChessBoard;
import com.example.android52.models.ChessPiece;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public ChessBoard chessBoard;
    public HashMap<String, Button> buttonBoard;

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

    }

    public void mapBoard(ChessBoard cb) {
        for (char f = 'a'; f <= 'h'; f++) {
            for (int r = 1; r <= 8; r++) {
                String buttonID = "button_"+f+r;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());

                Button pieceButton = findViewById(resourceID);
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

    public class Location {
        public char file;
        public int rank;

        public Location(char f, int r) {
            file = f;
            r = rank;
        }
    }
}
