package com.example.MP4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import com.example.MP4.model.Board;

public class OpponentBoardController {

    @FXML
    private GridPane opponentBoardGrid;

    private Board machineBoard;

    /**
     * Sets the machine's board in the controller.
     *
     * @param board Machine's board.
     */
    public void setMachineBoard(Board board) {
        this.machineBoard = board;
        updateBoard();
    }

    /**
     * Updates the opponent board view.
     */
    private void updateBoard() {
        opponentBoardGrid.getChildren().clear();

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                Rectangle cell = new Rectangle(30, 30);
                cell.setStroke(Color.BLACK);

                // Get the state of the cell
                char cellState = machineBoard.getGrid()[row][col];

                // Assign color according to the cell state
                switch (cellState) {
                    case '-': // Empty
                        cell.setFill(Color.LIGHTBLUE);
                        break;
                    case 'X': // Hit
                        cell.setFill(Color.RED);
                        break;
                    case 'O': // Miss
                        cell.setFill(Color.YELLOW);
                        break;
                    case 'S': // Ship
                        cell.setFill(Color.DARKGRAY);
                        break;
                    default: // Unknown state
                        cell.setFill(Color.WHITE);
                }

                opponentBoardGrid.add(cell, col, row);
            }
        }
    }

    /**
     * Closes the current window.
     */
    @FXML
    private void closeWindow() {
        opponentBoardGrid.getScene().getWindow().hide();
    }
}
