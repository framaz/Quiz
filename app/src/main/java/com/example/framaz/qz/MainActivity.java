package com.example.framaz.qz;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import java.util.Objects;


public class MainActivity extends Activity implements OnClickListener {
    /**
     * Called when the activity is first created.
     */
    private TextView Question;
    private Button[] Answers = new Button[4];
    private static final int LIVES = 3;
    private static final int QUESTIONS_TO_PASS = 10;
    private int QUESTIONS = 143;
    private static final int VARIANTS = 4;
    private static final char DELIMITER = '/';
    private String[][] AnsMatrix = new String[VARIANTS][QUESTIONS];
    private int[] RightAnswers = new int[QUESTIONS];
    private String[] Ques = new String[QUESTIONS];
    private int gamemode = 1; //1 — бесконечный, 2 — 10 вопросов
    private int time = 0;
    private int right = 0;
    private int wrong = 0;
    private int current_right = 0;
    private boolean alreadyClicked = false;
    private int whatClicked;
    private long whenStarted;
    private final Object obj = new Object();
    private Drawable buttonPicture;
    private Drawable rightChoice, wrongChoice;

    private class ThreadLocal extends Thread {
        @Override
        public void run() {
            long howManyToWait = 2000;
            try {
                long time = howManyToWait - (System.currentTimeMillis() - whenStarted);
                if (time < 0)
                    time = 0;

                sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (alreadyClicked) {
                            Answers[current_right].setBackground(buttonPicture);
                            Answers[whatClicked].setBackground(buttonPicture);
                            LoadQuestion();
                            alreadyClicked = false;

                            if ((gamemode == 1 && wrong == LIVES) ||
                                    (gamemode == 2 && time == QUESTIONS_TO_PASS)) {

                                //	Stats();
                                Intent intent = new Intent(
                                        MainActivity.this,
                                        ResultActivity.class);
                                intent.putExtra("right", right);
                                intent.putExtra("wrong", wrong);
                                startActivity(intent);
                                time = 0;
                                right = 0;
                                wrong = 0;
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            buttonPicture = ResourcesCompat.getDrawable(getResources(),
                    R.drawable.button_background,
                    null);
            rightChoice = ResourcesCompat.getDrawable(getResources(),
                    R.drawable.button_normal_activity_true,
                    null);
            wrongChoice = ResourcesCompat.getDrawable(getResources(),
                    R.drawable.button_normal_activity_false,
                    null);

            setContentView(R.layout.game);
            gamemode = getIntent().getIntExtra("gameMode", 1);

            Question = findViewById(R.id.textView);
            Answers[0] = findViewById(R.id.button);
            Answers[1] = findViewById(R.id.button1);
            Answers[2] = findViewById(R.id.button2);
            Answers[3] = findViewById(R.id.button3);

            for (int i = 0; i < 4; i++)
                Answers[i].setOnClickListener(this);

            RelativeLayout layout = findViewById(R.id.layout);
            layout.setOnClickListener(this);
            LoadQuestions();
            LoadQuestion();

        } catch (Exception e) {
            Toast.makeText(this, e + "", Toast.LENGTH_LONG).show();
        }
    }

    private void LoadQuestion() {
        int qs = (int) (System.currentTimeMillis() % QUESTIONS);
        Question.setText(Ques[qs]);
        for (int i = 0; i < VARIANTS; i++) {
            Answers[i].setText(AnsMatrix[i][qs]);
        }
        current_right = RightAnswers[qs] - 1;
    }

    private void LoadQuestions() {
        Resources res = getResources();
        TypedArray base = res.obtainTypedArray(R.array.Questions);
        QUESTIONS = base.length();
        for (int i = 0; i < QUESTIONS; i++) {
            Ques[i] = getSubstringBetweenDelimiters(
                    0,
                    1,
                    Objects.requireNonNull(base.getString(i)));
            for (int j = 0; j < VARIANTS; j++) {
                AnsMatrix[j][i] = getSubstringBetweenDelimiters(
                        j + 1,
                        j + 2,
                        Objects.requireNonNull(base.getString(i)));
            }
            RightAnswers[i] = Integer.parseInt(getSubstringBetweenDelimiters(
                    VARIANTS + 1,
                    VARIANTS + 2,
                    Objects.requireNonNull(base.getString(i))));
        }
        base.recycle();
    }

    private String getSubstringBetweenDelimiters(int k, int m, String str) {
        int index1 = 0;
        int index2 = 0;
        int len = str.length();
        int dels = 0;
        for (int i = 0; i < len; i++) {
            if (str.charAt(i) == DELIMITER) {
                dels++;
            }
            if (dels == k) {
                index1 = i;
            }
            if (dels == m) {
                index2 = i;
            }
        }
        //Toast.makeText(this, index1+" "+index2, Toast.LENGTH_LONG).show();
        return str.substring(index1 + 2, index2 + 1);
    }

    @Override
    public void onClick(View arg0) {
        synchronized (obj) {
            if (alreadyClicked) {
                Answers[current_right].setBackground(buttonPicture);
                Answers[whatClicked].setBackground(buttonPicture);
                LoadQuestion();
                alreadyClicked = false;

                if ((gamemode == 1 && wrong == LIVES) ||
                        (gamemode == 2 && time == QUESTIONS_TO_PASS)) {

                    //	Stats();
                    Intent intent = new Intent(
                            MainActivity.this,
                            ResultActivity.class);
                    intent.putExtra("right", right);
                    intent.putExtra("wrong", wrong);
                    startActivity(intent);
                    time = 0;
                    right = 0;
                    wrong = 0;
                }
                return;
            }
		/*if (time==total_time){
			Stats();
			time=0;
			right=0;
			wrong=0;
		}*/

        }
        if (arg0 != Question) {
            for (int i = 0; i < VARIANTS; i++) {
                if (arg0 == Answers[i]) {
                    whatClicked = i;
                    if (current_right == i) {
                        right++;
                    } else
                        wrong++;
                    Answers[current_right].setBackground(rightChoice);
                    if (whatClicked != current_right)
                        Answers[whatClicked].setBackground(wrongChoice);
                    time++;
                    alreadyClicked = true;
                    whenStarted = System.currentTimeMillis();
                    ThreadLocal thread = new ThreadLocal();
                    thread.start();
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}