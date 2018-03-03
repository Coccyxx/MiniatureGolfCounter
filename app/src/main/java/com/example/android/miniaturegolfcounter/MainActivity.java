package com.example.android.miniaturegolfcounter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String STROKES = "strokes";
    private static final String STROKES_P = "strokesP";
    private static final String STROKE_NO = "strokeNo";
    private static final String PLAYER1_SCORE = "player1Score";
    private static final String PLAYER1_HOLE = "player1Hole";
    private static final String PLAYER2_SCORE = "player2Score";
    private static final String PLAYER2_HOLE = "player2Hole";
    private static final String ACTIVE_PLAYER = "activePlayer";

    final int holes = 18;
    int numberPlayers = 2;
    int[] strokes = new int[holes * numberPlayers];      //saves all strokes done, needed for undo-function
    int[] strokesP = new int[holes * numberPlayers];     //saves the order of the players action, needed because of switch-function
    int strokeNo = 0;                                  //number of next stroke due (1st stroke = zero!)
    int player1Score = 0;                            //total score of Player 1
    int player1Hole = 1;                             //next hole of Player 1
    int player2Score = 0;                            //total score of Player 2
    int player2Hole = 1;                             //next hole of Player 2
    int activePlayer = 1;                            //Player 1 starts
    EditText player1;
    EditText player2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        if (savedInstanceState!=null) {

            onRestoreInstanceState(savedInstanceState);

            //display restored state
            int strokeNext=strokeNo;
            strokeNo=0;

            for (int i = 0; i < strokeNext; i++) {
                activePlayer = strokesP[i];
                update(strokes[i]);
            }
        }
        player1 = findViewById(R.id.player1Name);
        player2 = findViewById(R.id.player2Name);

        if (player1.getText().toString().equals("") || player2.getText().toString().equals("")) {
            showToastTypePlayers();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        strokes = savedInstanceState.getIntArray(STROKES);
        strokesP = savedInstanceState.getIntArray(STROKES_P);
        strokeNo = savedInstanceState.getInt(STROKE_NO);
        player1Score = savedInstanceState.getInt(PLAYER1_SCORE);
        player1Hole = savedInstanceState.getInt(PLAYER1_HOLE);
        player2Score = savedInstanceState.getInt(PLAYER2_SCORE);
        player2Hole = savedInstanceState.getInt(PLAYER2_HOLE);
        activePlayer = savedInstanceState.getInt(ACTIVE_PLAYER);

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putIntArray(STROKES, strokes);
        savedInstanceState.putIntArray(STROKES_P, strokesP);
        savedInstanceState.putInt(STROKE_NO, strokeNo);
        savedInstanceState.putInt(PLAYER1_SCORE, player1Score);
        savedInstanceState.putInt(PLAYER1_HOLE, player1Hole);
        savedInstanceState.putInt(PLAYER2_SCORE, player2Score);
        savedInstanceState.putInt(PLAYER2_HOLE, player2Hole);
        savedInstanceState.putInt(ACTIVE_PLAYER, activePlayer);
    }

    @Override     //enables to get rid of the focus of the EditText views by clicking on screen
    public boolean onTouchEvent(MotionEvent event) {
        RelativeLayout rl = findViewById(R.id.root);
        rl.requestFocus();
        return super.onTouchEvent(event);
    }

    //Following methods are called when a stroke button (1-7) is pressed

    public void stroke1(View view) {
        update(1);
    }

    public void stroke2(View view) {
        update(2);
    }

    public void stroke3(View view) {
        update(3);
    }

    public void stroke4(View view) {
        update(4);
    }

    public void stroke5(View view) {
        update(5);
    }

    public void stroke6(View view) {
        update(6);
    }

    public void stroke7(View view) {
        update(7);
    }

    /**
     * Updates the score data and calls methods to update the display
     *
     * @param holeStrokes is the strokes needed at a hole
     */
    private void update(int holeStrokes) {

        //checks if players names are already typed in
        if (player1.getText().toString().equals("") || player2.getText().toString().equals("")) {
            showToastTypePlayers();
            return;                 //exit the method
        }

        if (strokeNo == holes * numberPlayers) {         //all strokes done
            Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_noHolesLeft, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (activePlayer == 1) {
            if (player1Hole == 19) {                     //active player has no strokes left
                Toast toast = Toast.makeText(getApplicationContext(), R.string.playerNoHolesLeft, Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            strokesP[strokeNo] = activePlayer;
            displayPlayerHole(player1Hole++, holeStrokes);
            updatePlayerScore(player1Score += holeStrokes);
        } else {    //player 2 is active
            if (player2Hole == 19) {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.playerNoHolesLeft, Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            strokesP[strokeNo] = activePlayer;
            displayPlayerHole(player2Hole++, holeStrokes);
            updatePlayerScore(player2Score += holeStrokes);
        }

        strokes[strokeNo++] = holeStrokes;      //save the last holeScore

    }

    /**
     * Shows toast message that players' names have to be typed in
     */
    private void showToastTypePlayers() {
        Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_typePlayers, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Update displayed scorecard of the active player
     *
     * @param hole      current hole of the active player
     * @param holeScore strokes needed at the current hole
     */
    public void displayPlayerHole(int hole, int holeScore) {

        TextView holeView;

        if (activePlayer == 1) {

            switch (hole) {
                case 1:
                    holeView = findViewById(R.id.p1h1);
                    break;
                case 2:
                    holeView = findViewById(R.id.p1h2);
                    break;
                case 3:
                    holeView = findViewById(R.id.p1h3);
                    break;
                case 4:
                    holeView = findViewById(R.id.p1h4);
                    break;
                case 5:
                    holeView = findViewById(R.id.p1h5);
                    break;
                case 6:
                    holeView = findViewById(R.id.p1h6);
                    break;
                case 7:
                    holeView = findViewById(R.id.p1h7);
                    break;
                case 8:
                    holeView = findViewById(R.id.p1h8);
                    break;
                case 9:
                    holeView = findViewById(R.id.p1h9);
                    break;
                case 10:
                    holeView = findViewById(R.id.p1h10);
                    break;
                case 11:
                    holeView = findViewById(R.id.p1h11);
                    break;
                case 12:
                    holeView = findViewById(R.id.p1h12);
                    break;
                case 13:
                    holeView = findViewById(R.id.p1h13);
                    break;
                case 14:
                    holeView = findViewById(R.id.p1h14);
                    break;
                case 15:
                    holeView = findViewById(R.id.p1h15);
                    break;
                case 16:
                    holeView = findViewById(R.id.p1h16);
                    break;
                case 17:
                    holeView = findViewById(R.id.p1h17);
                    break;
                case 18:
                    holeView = findViewById(R.id.p1h18);
                    break;
                default:
                    holeView = findViewById(R.id.p1h1);
            }
        } else { // (player==2)
            switch (hole) {
                case 1:
                    holeView = findViewById(R.id.p2h1);
                    break;
                case 2:
                    holeView = findViewById(R.id.p2h2);
                    break;
                case 3:
                    holeView = findViewById(R.id.p2h3);
                    break;
                case 4:
                    holeView = findViewById(R.id.p2h4);
                    break;
                case 5:
                    holeView = findViewById(R.id.p2h5);
                    break;
                case 6:
                    holeView = findViewById(R.id.p2h6);
                    break;
                case 7:
                    holeView = findViewById(R.id.p2h7);
                    break;
                case 8:
                    holeView = findViewById(R.id.p2h8);
                    break;
                case 9:
                    holeView = findViewById(R.id.p2h9);
                    break;
                case 10:
                    holeView = findViewById(R.id.p2h10);
                    break;
                case 11:
                    holeView = findViewById(R.id.p2h11);
                    break;
                case 12:
                    holeView = findViewById(R.id.p2h12);
                    break;
                case 13:
                    holeView = findViewById(R.id.p2h13);
                    break;
                case 14:
                    holeView = findViewById(R.id.p2h14);
                    break;
                case 15:
                    holeView = findViewById(R.id.p2h15);
                    break;
                case 16:
                    holeView = findViewById(R.id.p2h16);
                    break;
                case 17:
                    holeView = findViewById(R.id.p2h17);
                    break;
                case 18:
                    holeView = findViewById(R.id.p2h18);
                    break;
                default:
                    holeView = findViewById(R.id.p2h1);
            }
        }

        if (holeScore == 0) {                             //UNDO-Button was pressed
            holeView.setText(String.valueOf('-'));
        } else {
            holeView.setText(String.valueOf(holeScore));
        }
    }

    /**
     * Updates the display of the active player's total score
     *
     * @param score total score of the active player
     */
    public void updatePlayerScore(int score) {

        TextView scoreViewP1 = findViewById(R.id.player1Strokes);
        TextView scoreViewP2 = findViewById(R.id.player2Strokes);

        if (activePlayer == 1) {
            scoreViewP1.setText(String.valueOf(score));
        } else {      //activePlayer==2
            scoreViewP2.setText(String.valueOf(score));
        }
        switchPlayer();
    }

    /**
     * Called when Switch button is pressed
     *
     * @param view of the button
     */
    public void switchPlayer(View view) {

        if (player1.getText().toString().equals("") || player2.getText().toString().equals("")) {
            showToastTypePlayers();
        } else {
            switchPlayer();
        }
    }

    /**
     * Switch the active player and update the screen
     */
    private void switchPlayer() {
        activePlayer = 2 / activePlayer;

        TextView NameViewP1 = findViewById(R.id.player1Name);
        TextView NameViewP2 = findViewById(R.id.player2Name);
        View underline1P1 = findViewById(R.id.underline1P1);
        View underline2P1 = findViewById(R.id.underline2P1);
        View underline1P2 = findViewById(R.id.underline1P2);
        View underline2P2 = findViewById(R.id.underline2P2);
        Typeface typefaceActiv = ResourcesCompat.getFont(this, R.font.advent_pro_semibold);
        Typeface typefaceInactiv = ResourcesCompat.getFont(this, R.font.advent_pro);

        if (activePlayer == 1) {
            NameViewP2.setTypeface(typefaceInactiv);
            NameViewP1.setTypeface(typefaceActiv);
            underline1P1.setVisibility(View.VISIBLE);
            underline2P1.setVisibility(View.VISIBLE);
            underline1P2.setVisibility(View.INVISIBLE);
            underline2P2.setVisibility(View.INVISIBLE);
        } else {      //activePlayer==2

            NameViewP1.setTypeface(typefaceInactiv);
            NameViewP2.setTypeface(typefaceActiv);
            underline1P1.setVisibility(View.INVISIBLE);
            underline2P1.setVisibility(View.INVISIBLE);
            underline1P2.setVisibility(View.VISIBLE);
            underline2P2.setVisibility(View.VISIBLE);

        }
    }


    /**
     * Called when Reset button is pressed. Reset all scores.
     * Shows a dialog asking for confirmation.
     *
     * @param view of the button
     */
    public void reset(View view) {

        if (player1.getText().toString().equals("") || player2.getText().toString().equals("")) {
            showToastTypePlayers();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Reset the Scorecards?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                while (strokeNo > 0) {
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

    /**
     * Called when Undo button is pressed
     *
     * @param view of the button
     */

    public void undo(View view) {
        if (player1.getText().toString().equals("") || player2.getText().toString().equals("")) {
            showToastTypePlayers();
        } else {
            undo();
        }
    }

    /**
     * Undo the last stroke
     */
    private void undo() {

        if (strokeNo != 0) {        //if (strokeNo==0) then no stroke has been done

            strokeNo--;
            activePlayer = strokesP[strokeNo];    //switch to the player active before

            if (activePlayer == 1) {
                displayPlayerHole(--player1Hole, 0);
                updatePlayerScore(player1Score -= strokes[strokeNo]);
            } else {
                displayPlayerHole(--player2Hole, 0);
                updatePlayerScore(player2Score -= strokes[strokeNo]);
            }
        }
        strokes[strokeNo] = 0;      //delete last stroke
        switchPlayer();

    }
}
