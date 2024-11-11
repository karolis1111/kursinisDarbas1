package org;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game {

    private JFrame frame;
    private JLabel[][] tiles;
    private JPanel gridPanel;
    private boolean winningScoreReached;

    public Game() {
        winningScoreReached = false;
        Board board = new Board();
        tiles = new JLabel[4][4];
        frame = createFrame();
        JLabel scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        frame.add(scoreLabel, BorderLayout.NORTH);
        gridPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        frame.add(gridPanel, BorderLayout.CENTER);
        fillGrid();
        setTileValues(board.getGameBoard());

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                boolean moved = false;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        if (board.canMergeVertically() || !board.isBoardFull()) {
                            moved = gameAction("w", board, scoreLabel);
                        }
                        break;
                    case KeyEvent.VK_A:
                        if (board.canMergeHorizontally() || !board.isBoardFull()) {
                            moved = gameAction("a", board, scoreLabel);
                        }
                        break;
                    case KeyEvent.VK_S:
                        if (board.canMergeVertically() || !board.isBoardFull()) {
                            moved = gameAction("s", board, scoreLabel);
                        }
                        break;
                    case KeyEvent.VK_D:
                        if (board.canMergeHorizontally() || !board.isBoardFull()) {
                            moved = gameAction("d", board, scoreLabel);
                            System.out.println("aaaaaaaaaaaaa");
                        }
                        break;
                }

                if (moved) {
                    board.generateRandomNumber();
                    setTileValues(board.getGameBoard());
                    scoreLabel.setText(String.valueOf(board.getScore()));
                }
                if (isGameWon(board) && !winningScoreReached) {
                    winningScoreReached = true;
                    JOptionPane.showMessageDialog(frame, "Game won!");
                }
                if (isGameOver(board)) {
                    JOptionPane.showMessageDialog(frame, "Game over! Your score: " + String.valueOf(board.getScore()));
                    System.exit(0);
                }
            }
        });

        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setVisible(true);
    }

    private boolean gameAction(String direction, Board board, JLabel scoreLabel) {
        boolean moved = board.move(direction);
        setTileValues(board.getGameBoard());
        scoreLabel.setText(String.valueOf(board.getScore()));
        return moved;
    }

    private boolean isGameWon(Board board){
        for (int i = 0; i < board.BOARD_SIZE; i++) {
            for (int j = 0; j < board.BOARD_SIZE; j++) {
                if (board.getScore().isScoreReached(board.getGameBoard()[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isGameOver(Board board) {
        boolean isFull = board.isBoardFull();
        boolean noMergePossible = !board.canMergeHorizontally() && !board.canMergeVertically();
        System.out.println("Board full: " + isFull + ", No merge possible: " + noMergePossible);
        return isFull && noMergePossible;
    }
    private JFrame createFrame() {
        JFrame frame = new JFrame("2048");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    private void fillGrid() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[i][j] = createTile();
                gridPanel.add(tiles[i][j]);
            }
        }
    }
    private JLabel createTile() {
        JLabel tile = new JLabel("", SwingConstants.CENTER);
        tile.setOpaque(true);
        tile.setBackground(Color.LIGHT_GRAY);
        tile.setFont(new Font("Arial", Font.BOLD, 24));
        tile.setText("0");
        return tile;
    }

    private void setTileValues(int[][] boardValue) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[i][j].setText(String.valueOf(boardValue[i][j]));
                if(boardValue[i][j] != 0){
                    tiles[i][j].setBackground(Color.ORANGE);
                }
                else {
                    tiles[i][j].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }
}
