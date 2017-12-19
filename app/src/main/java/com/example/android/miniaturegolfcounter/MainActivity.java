package com.example.android.miniaturegolfcounter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int holes=18;
    int numberPlayers=2;

    int[] strokes=new int[holes*numberPlayers];      //saves all strokes done, needed for undo-function
    int[] strokesP=new int[holes*numberPlayers];     //saves the order of the players action, needed because of switch-function
    int strokeNo=0;                                  //number of next stroke due (1st strike = zero!)

    int player1Strokes = 0;                          //total score of Player 1
    int player1Hole = 1;                             //next pole of Player 1

    int player2Strokes = 0;                          //total score of Player 2
    int player2Hole = 1;                             //next pole of Player 2

    int activePlayer = 1;                            //Player 1 starts


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void stroke1(View view) {
        updateDisplay(1);
    }

    public void stroke2(View view) {
        updateDisplay(2);
    }

    public void stroke3(View view) {
        updateDisplay(3);
    }

    public void stroke4(View view) {
        updateDisplay(4);
    }

    public void stroke5(View view) {
        updateDisplay(5);
    }

    public void stroke6(View view) {
        updateDisplay(6);
    }

    public void stroke7(View view) {
        updateDisplay(7);
    }


    private void updateDisplay(int holeScore) {

        if (strokeNo==holes*numberPlayers) {         //all strokes done
            Toast toast=Toast.makeText(getApplicationContext(), "No more strokes left !",Toast.LENGTH_SHORT);
            toast.show();
            return;                                  //exit the method
        }

        strokesP[strokeNo]=activePlayer;

        if (activePlayer == 1) {
            displayPlayerHole(player1Hole++, holeScore);
            updatePlayerScore(player1Strokes += holeScore);
        } else {
            displayPlayerHole(player2Hole++, holeScore);
            updatePlayerScore(player2Strokes += holeScore);
        }
        strokes[strokeNo++]=holeScore;      //save the last holeScore

    }

    public void displayPlayerHole(int hole, int holeScore) {     //update scorecard of the active player

        TextView holeView;

        if (activePlayer == 1) {

            switch (hole) {
                case 1:
                    holeView = (TextView) findViewById(R.id.p1h1);
                    break;
                case 2:
                    holeView = (TextView) findViewById(R.id.p1h2);
                    break;
                case 3:
                    holeView = (TextView) findViewById(R.id.p1h3);
                    break;
                case 4:
                    holeView = (TextView) findViewById(R.id.p1h4);
                    break;
                case 5:
                    holeView = (TextView) findViewById(R.id.p1h5);
                    break;
                case 6:
                    holeView = (TextView) findViewById(R.id.p1h6);
                    break;
                case 7:
                    holeView = (TextView) findViewById(R.id.p1h7);
                    break;
                case 8:
                    holeView = (TextView) findViewById(R.id.p1h8);
                    break;
                case 9:
                    holeView = (TextView) findViewById(R.id.p1h9);
                    break;
                case 10:
                    holeView = (TextView) findViewById(R.id.p1h10);
                    break;
                case 11:
                    holeView = (TextView) findViewById(R.id.p1h11);
                    break;
                case 12:
                    holeView = (TextView) findViewById(R.id.p1h12);
                    break;
                case 13:
                    holeView = (TextView) findViewById(R.id.p1h13);
                    break;
                case 14:
                    holeView = (TextView) findViewById(R.id.p1h14);
                    break;
                case 15:
                    holeView = (TextView) findViewById(R.id.p1h15);
                    break;
                case 16:
                    holeView = (TextView) findViewById(R.id.p1h16);
                    break;
                case 17:
                    holeView = (TextView) findViewById(R.id.p1h17);
                    break;
                case 18:
                    holeView = (TextView) findViewById(R.id.p1h18);
                    break;
                default:
                    holeView = (TextView) findViewById(R.id.p1h1);
            }
        } else { // (player==2)
            switch (hole) {
                case 1:
                    holeView = (TextView) findViewById(R.id.p2h1);
                    break;
                case 2:
                    holeView = (TextView) findViewById(R.id.p2h2);
                    break;
                case 3:
                    holeView = (TextView) findViewById(R.id.p2h3);
                    break;
                case 4:
                    holeView = (TextView) findViewById(R.id.p2h4);
                    break;
                case 5:
                    holeView = (TextView) findViewById(R.id.p2h5);
                    break;
                case 6:
                    holeView = (TextView) findViewById(R.id.p2h6);
                    break;
                case 7:
                    holeView = (TextView) findViewById(R.id.p2h7);
                    break;
                case 8:
                    holeView = (TextView) findViewById(R.id.p2h8);
                    break;
                case 9:
                    holeView = (TextView) findViewById(R.id.p2h9);
                    break;
                case 10:
                    holeView = (TextView) findViewById(R.id.p2h10);
                    break;
                case 11:
                    holeView = (TextView) findViewById(R.id.p2h11);
                    break;
                case 12:
                    holeView = (TextView) findViewById(R.id.p2h12);
                    break;
                case 13:
                    holeView = (TextView) findViewById(R.id.p2h13);
                    break;
                case 14:
                    holeView = (TextView) findViewById(R.id.p2h14);
                    break;
                case 15:
                    holeView = (TextView) findViewById(R.id.p2h15);
                    break;
                case 16:
                    holeView = (TextView) findViewById(R.id.p2h16);
                    break;
                case 17:
                    holeView = (TextView) findViewById(R.id.p2h17);
                    break;
                case 18:
                    holeView = (TextView) findViewById(R.id.p2h18);
                    break;
                default:
                    holeView = (TextView) findViewById(R.id.p2h1);
            }
        }

        if (holeScore == 0) {                        //UNDO-Button was pressed
            holeView.setText(String.valueOf('-'));
        } else {
            holeView.setText(String.valueOf(holeScore));
        }
    }

    public void updatePlayerScore(int score) {      //updates the active player's Score

        TextView scoreViewP1 = (TextView) findViewById(R.id.player1Strokes);
        TextView scoreViewP2 = (TextView) findViewById(R.id.player2Strokes);

        if (activePlayer == 1) {
            scoreViewP1.setText(String.valueOf(score));
        } else {      //activePlayer==2
            scoreViewP2.setText(String.valueOf(score));
        }
        switchPlayer();
    }

    public void switchPlayer(View view) {
        switchPlayer();
    }

    private void switchPlayer() {    //switch between player 1 and 2
        activePlayer = 2 / activePlayer;

        TextView NameViewP1 = (TextView) findViewById(R.id.player1Name);
        TextView NameViewP2 = (TextView) findViewById(R.id.player2Name);

        if (activePlayer == 1) {
            NameViewP2.setTextColor(Color.parseColor("#59955e"));
            NameViewP1.setTextColor(Color.parseColor("#ff0000"));

            //scoreViewP2.setTypeface(scoreViewP2.getTypeface(), Typeface.BOLD);
            //scoreViewP1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            //scoreViewP1.setTextColor(Color.parseColor("#000000"));;
        } else {      //activePlayer==2
            NameViewP1.setTextColor(Color.parseColor("#59955e"));
            NameViewP2.setTextColor(Color.parseColor("#ff0000"));
        }
    }


    public void reset(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Reset the Scorecards?");
        //      .setMessage("Are you sure?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                while (strokeNo>0) {
                    undo();
                }
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void undo(View view) {
        undo();
    }

    private void undo() {

        if (strokeNo != 0) {        //if (strokeNo==0) then no stroke has been done

            strokeNo--;
            activePlayer=strokesP[strokeNo];    //switch to the player active before

            if (activePlayer == 1) {
                displayPlayerHole(--player1Hole, 0);
                updatePlayerScore(player1Strokes-=strokes[strokeNo]);
            } else {
                displayPlayerHole(--player2Hole, 0);
                updatePlayerScore(player2Strokes-=strokes[strokeNo]);
            }
        }
        strokes[strokeNo]=0;      //delete last stroke
        switchPlayer();

    }
}
