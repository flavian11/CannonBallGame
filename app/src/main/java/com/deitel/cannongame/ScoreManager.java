package com.deitel.cannongame;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ScoreManager {
    public static final String SCORES = "SCORES";

    private int level;
    private int currentScore;
    private String sharedScores;
    private List<Integer> scores;

    private SharedPreferences sharedPreferences;

    public ScoreManager(Context context) {
        level = 1;
        currentScore = 0;
        sharedPreferences = context.getSharedPreferences(SCORES, 0);
        scores = new ArrayList<>();
        if (sharedPreferences.contains(SCORES)) {
            sharedScores = sharedPreferences.getString(SCORES, null);
            for (String s : sharedScores.split(",")) {
                scores.add(new Integer(s));
            }
        }
        else
            sharedScores = new String();
    }

    public int getLevel() { return level; }
    public int getScore() { return currentScore; }
    public void nextLevel() { level++; }

    public void resetScore() {
        level = 1;
        currentScore = 0;
    }


    public void updateScore(boolean win) {
        if (win)
            currentScore = currentScore + (10 * level);
        else
            currentScore = currentScore - (15 * level);
    }

    public String saveScore() {
        String newScores = new String();
        String printedScore = new String();
        boolean star = false;

        scores.add(currentScore);
        Collections.sort(scores);
        Collections.reverse(scores);
        for(int i = 0; i < scores.size() && i < 5; i++) {
            String s = scores.get(i).toString();
            printedScore += s;
            newScores += s;
            if (!star && currentScore == scores.get(i)) {
                printedScore += "*";
                star = true;
            }
            if (i < scores.size() - 1 && i != 4) {
                newScores += ",";
                printedScore += "\n";
            }
        }
        sharedPreferences.edit().putString(SCORES, newScores).apply();
        return printedScore;
    }

}
