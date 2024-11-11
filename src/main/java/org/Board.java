package org;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class Board {
    static final int BOARD_SIZE = 4;
    private int[][] gameBoard;
    private int[][] temp_board = new int[BOARD_SIZE][BOARD_SIZE];
    private Score score;

    public Board() {
        this.gameBoard = new int[BOARD_SIZE][BOARD_SIZE];
        generateRandomNumber();
        generateRandomNumber();
        this.score = new Score();
    }

    public boolean moveLeft() {
        boolean moved = false;

        for (int i = 0; i < BOARD_SIZE; i++) {
            int[] tempRow = new int[BOARD_SIZE];
            int rowPosition = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (gameBoard[i][j] != 0) {
                    int currentValue = gameBoard[i][j];
                    gameBoard[i][j] = 0;

                    if (rowPosition > 0 && tempRow[rowPosition - 1] == currentValue) {
                        int mergeValue = currentValue * 2;
                        tempRow[rowPosition - 1] = mergeValue;
                        score.updateScore(mergeValue);
                        moved = true;
                    } else {
                        tempRow[rowPosition] = currentValue;
                        rowPosition++;
                        moved = true;
                    }
                }
            }
            System.arraycopy(tempRow, 0, gameBoard[i], 0, BOARD_SIZE);
        }
        return moved;
    }

    private void rotateRight() {
        int[][] rotated = new int[BOARD_SIZE][BOARD_SIZE];
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                rotated[c][BOARD_SIZE - 1 - r] = gameBoard[r][c];
            }
        }
        System.arraycopy(rotated, 0, gameBoard, 0, BOARD_SIZE);
    }

    public void generateRandomNumber() {
        Random rand = new Random();
        int value = (rand.nextInt(5) == 0) ? 4 : 2;
        int x, y;
        do {
            x = rand.nextInt(4);
            y = rand.nextInt(4);
        } while (gameBoard[x][y] != 0);
        gameBoard[x][y] = value;
    }


    public boolean move(String direction) {
        boolean moved = false;
        switch (direction) {
            case "a":
                return moveLeft();
            case "d":
                rotateRight();
                rotateRight();
                moved = moveLeft();
                rotateRight();
                rotateRight();
                return moved;
            case "w":
                rotateRight();
                rotateRight();
                rotateRight();
                moved = moveLeft();
                rotateRight();
                return moved;
            case "s":
                rotateRight();
                moved = moveLeft();
                rotateRight();
                rotateRight();
                rotateRight();
                return moved;
            default:
                return false;
        }
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gameBoard[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canMergeHorizontally() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE - 1; j++) {

                if (gameBoard[i][j] == gameBoard[i][j + 1] && gameBoard[i][j] != 0) {

                    return true;
                }
            }
        }
        return false;
    }
    public boolean canMergeVertically() {

        for (int i = 0; i < BOARD_SIZE - 1; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {

                if (gameBoard[i][j] == gameBoard[i + 1][j] && gameBoard[i][j] != 0) {
                    return true;
                }
            }
        }
        return false;
    }
    };
