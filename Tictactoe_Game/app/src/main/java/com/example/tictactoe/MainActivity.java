package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView playerOneScore,playerTwoScore,PlayerStatus,winner;
    private final Button[] buttons = new Button[9];
    private Button resetGame,Save;
    private int playerOneScoreCount,playerTwoScoreCount,roundCount;
    boolean activePlayer;
    MediaPlayer sound;
    String winnerStr;
    ImageView btn;
// p1 - 0
    // p2 - 1
    // empty 3

     int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerOneScore =(TextView)findViewById(R.id.oScore);
        playerTwoScore = (TextView)findViewById(R.id.xScore);
        PlayerStatus = (TextView)findViewById(R.id.status);
        resetGame = (Button)findViewById(R.id.resetgame);
        btn = (ImageView)findViewById(R.id.btn) ;

        resetGame.setTranslationX(800);
        resetGame.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        btn.setTranslationY(800);
        btn.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();



        // winner = (TextView)findViewById(R.id.winner);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        for(int i = 0 ; i<buttons.length;i++){
            String buttonID = "btn" + i;
            int resourceID =getResources().getIdentifier(buttonID,"id",getPackageName());
            buttons[i] =(Button)findViewById(resourceID);

            buttons[i].setOnClickListener(this);






        }

        // background sound
        sound = MediaPlayer.create(MainActivity.this,R.raw.background);
        sound.setLooping(true);
        sound.start();


        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));

        if (activePlayer){
            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor("#ED1515"));
            //game sound
            if (sound == null) {
                sound = MediaPlayer.create(this, R.raw.tic);
                sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
//                    stopPlayer();
                    }
                });
            }
            sound.start();
            gameState[gameStatePointer] = 0;
        }else{
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor("#39E30A"));
            //
            if (sound == null) {
                sound = MediaPlayer.create(this, R.raw.o);
                sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
//                    stopPlayer();
                    }
                });
            }
            sound.start();
            gameState[gameStatePointer] = 1;

        }
        roundCount++;
        if (checkWinner()){
            if(activePlayer){
                playerOneScoreCount++;

                updatePlayerScore();
//                TextView statu = findViewById(R.id.stat);
//                statu.setText(winnerStr);
//                winnerStr ="Player one has won the game";


            }else{
                playerTwoScoreCount++;
                updatePlayerScore();
//                TextView statu = findViewById(R.id.stat);
//                statu.setText(winnerStr);
//                winnerStr = "Player Two has won the game";


            }
            playAgain();

        }else if(roundCount == 9){
            playAgain();

            Toast.makeText(this, "NO WINNER", Toast.LENGTH_SHORT).show();
//            TextView statu = findViewById(R.id.stat);
//            statu.setText("Match Draw");


        }else{
            activePlayer =! activePlayer;
        }
        if(playerOneScoreCount >playerTwoScoreCount){
            PlayerStatus.setText("Player one is winning");

        }else if (playerTwoScoreCount > playerOneScoreCount){
            PlayerStatus.setText("Player two is winning");
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                PlayerStatus .setText(" ");


                //

                updatePlayerScore();



            }
        });
    }
    public  boolean checkWinner(){
        boolean winnerResult = false;

        for (int [] winningPosition : winPositions){
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                    && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                    && gameState[winningPosition[0]]!=2) {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.winner);
                mediaPlayer.start();


                winnerResult = true;



            }

        }
        return winnerResult;
    }
    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));

        int scoreOne = Integer.parseInt(playerOneScore.getText().toString());
        int scoreTwo = Integer.parseInt(playerTwoScore.getText().toString());
        String status = PlayerStatus.getText().toString();
        FirebaseFirestore.getInstance().collection("gameScore")
                .add(new database(scoreOne,scoreTwo,status)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
               // Toast.makeText(MainActivity.this, "Success ", Toast.LENGTH_SHORT).show();
            }
        });




    }
    public void playAgain(){
        roundCount = 0;
        activePlayer = true;
        for(int i = 0 ; i< buttons.length;i++){
            gameState[i] = 2;
            buttons[i].setText("");

        }
    }


    public void showHistoryScore(View view) {
        Intent intent = new Intent(getApplicationContext(),ShowHistory.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sound.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        sound.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sound.stop();
        sound.release();

    }
}