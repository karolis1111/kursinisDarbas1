package org;

import lombok.Getter;

@Getter

public class Score {
    private int score;

    {
        score = 0;
    }


    public void updateScore(int mergeValue) {
        score += mergeValue;
    }

    public boolean isScoreReached(int tileValue) {
        return tileValue == 2048;
    }

    @Override
    public String toString() {
        return "Score: " + score;
    }
}

