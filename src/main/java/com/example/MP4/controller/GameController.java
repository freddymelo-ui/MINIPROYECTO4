package com.example.MP4.controller;

import com.example.MP4.model.Board;
import com.example.MP4.model.Game;
import com.example.MP4.model.MachineAdapter;
import com.example.MP4.model.Ship;
import com.example.MP4.utils.GameSaveManager;
import com.example.MP4.utils.ImageUtils;
import com.example.MP4.view.OpponentBoardStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GameController {

    public Button viewMachineBoardButton; // Button to view the machine's board
    public GridPane ownBoardGrid; // Player's own board
    public GridPane machineBoardGrid; // Attack board for targeting the machine
    private MachineAdapter machinePlayer; // Machine player reference
    private Game game;

    @FXML
    private Label resultLabel;

    /**
     * Configures the game when receiving a Game instance.
     *
     * @param game Game instance provided by PlacementController.
     */
    public void setGame(Game game) {
        this.game = game;
        this.machinePlayer = game.machine; // Initialize the reference to MachineAdapter
        com.example.MP4.utils.CurrentGameHolder.set(game);
        initializeHumanBoardUIWithImages();
        initializeMachineBoardUI();
    }

    /**
     * Returns the currently set Game instance.
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * Initializes the human player's board and displays the placed ships.
     */
    private void initializeHumanBoardUIWithImages() {
        ownBoardGrid.getChildren().clear(); // Clear existing content

        // Configure GridPane with fixed-size cells
        configureGridPane(ownBoardGrid, 30.0); // Each cell is 30x30

        var humanBoard = game.human.getBoard();
        var ships = humanBoard.getShips();

        // Cell dimensions on the board
        double cellWidth = 30.0;
        double cellHeight = 30.0;

        // Add ship images to the board
        for (Ship ship : ships) {
            String imagePath = getImagePathForShip(ship.getSize());
            if (imagePath == null)
                continue;

            // Create the ImageView for the ship image
            ImageView shipImage = ImageUtils.loadImage(imagePath);

            // Rotate and resize according to ship orientation
            if (ship.isHorizontal()) {
                ImageUtils.resizeImage(shipImage, cellWidth * ship.getSize(), cellHeight);
            } else {
                ImageUtils.resizeImage(shipImage, cellWidth, cellHeight * ship.getSize());
                ImageUtils.rotateImage(shipImage, 90);
            }

            // Ship starting coordinates
            int[] startCoord = ship.getCoordinates().get(0);
            int startRow = startCoord[0];
            int startCol = startCoord[1];

            // Add image to the GridPane
            ownBoardGrid.add(shipImage, startCol, startRow);
            if (ship.isHorizontal()) {
                GridPane.setColumnSpan(shipImage, ship.getSize());
            } else {
                GridPane.setRowSpan(shipImage, ship.getSize());
            }
        }

        // Add empty cells or shot indicators over the images
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle cell = new Rectangle(cellWidth, cellHeight);
                cell.setStroke(Color.BLACK);

                if (humanBoard.isHit(row, col)) {
                    cell.setFill(Color.RED);
                } else if (humanBoard.isMiss(row, col)) {
                    cell.setFill(Color.YELLOW);
                } else if (!humanBoard.occupiesCell(row, col)) {
                    cell.setFill(Color.LIGHTBLUE);
                } else {
                    cell.setFill(Color.TRANSPARENT); // Transparent to not hide the ship images
                }

                ownBoardGrid.add(cell, col, row);
            }
        }
    }

    private void configureGridPane(GridPane gridPane, double cellSize) {
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        // Configure columns and rows with fixed size
        for (int i = 0; i < 10; i++) { // 10 is the board size
            ColumnConstraints colConstraints = new ColumnConstraints(cellSize);
            colConstraints.setHalignment(HPos.CENTER); // Center horizontally
            gridPane.getColumnConstraints().add(colConstraints);

            RowConstraints rowConstraints = new RowConstraints(cellSize);
            rowConstraints.setValignment(VPos.CENTER); // Center vertically
            gridPane.getRowConstraints().add(rowConstraints);
        }
    }

    /**
     * Returns the image path corresponding to the ship size.
     */
    private String getImagePathForShip(int size) {
        return switch (size) {
            case 4 -> "/com/example/MP4/images/portaaviones.png";
            case 3 -> "/com/example/MP4/images/submarino.png";
            case 2 -> "/com/example/MP4/images/destructor.png";
            case 1 -> "/com/example/MP4/images/fragata.png";
            default -> null;
        };
    }

    /**
     * Initializes the machine's board so the human player can shoot.
     */
    private void initializeMachineBoardUI() {
        machineBoardGrid.getChildren().clear();
        var machineBoard = game.machine.getBoard();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle cell = new Rectangle(29, 29);
                cell.setStroke(Color.BLACK);
                cell.setFill(Color.LIGHTBLUE);

                int finalRow = row;
                int finalCol = col;

                // Attack board interactivity
                cell.setOnMouseClicked(e -> handleHumanShot(finalRow, finalCol));
                machineBoardGrid.add(cell, col, row);
            }
        }
    }

    /**
     * Handles the human player's shot on the machine's board.
     *
     * @param row Selected row.
     * @param col Selected column.
     */
    private void handleHumanShot(int row, int col) {
        System.out.println("Human shot at (" + row + ", " + col + ")...");
        String result = game.processHumanShot(row, col);
        System.out.println("Human shot result: " + result);

        // Update machine board
        updateMachineBoardUI();

        // Save after human shot so the state is persisted immediately
        try {
            GameSaveManager.saveGame(new File("game_state.ser"), game);
        } catch (IOException e) {
            System.err.println("Failed to autosave after human shot: " + e.getMessage());
        }

        // Finish only if checkVictory confirms someone has won
        if (game.checkVictory()) {
            System.out.println("Victory detected after human shot.");
            endGame(game.getWinner());
            return;
        }

        // If no winner, proceed with machine's turn
        handleMachineTurn();
    }

    /**
     * The machine performs a shot on the human player's board.
     */
    private void handleMachineTurn() {
        int row, col;
        if (machinePlayer != null) {
            int[] shot = machinePlayer.nextShot();
            row = shot[0];
            col = shot[1];
        } else {
            // fallback
            row = (int) (Math.random() * 10);
            col = (int) (Math.random() * 10);
        }

        // Register the machine shot on the human board
        String result = game.processMachineShot(row, col);
        System.out.println("Machine shot result: " + result);

        // Update human board
        updateHumanBoardUI();

        // Save after machine shot as well
        try {
            GameSaveManager.saveGame(new File("game_state.ser"), game);
        } catch (IOException e) {
            System.err.println("Failed to autosave after machine shot: " + e.getMessage());
        }

        // Check if machine has won
        if (game.checkVictory()) {
            endGame(game.getWinner());
        }
    }

    /**
     * Updates the machine board UI.
     */
    private void updateMachineBoardUI() {
        var machineBoard = game.machine.getBoard();

        for (var node : machineBoardGrid.getChildren()) {
            int row = GridPane.getRowIndex(node);
            int col = GridPane.getColumnIndex(node);
            Rectangle rect = (Rectangle) node;

            if (machineBoard.isHit(row, col)) {
                rect.setFill(Color.RED);
            } else if (machineBoard.isMiss(row, col)) {
                rect.setFill(Color.YELLOW);
            }
        }
    }

    /**
     * Updates the human board UI.
     */
    private void updateHumanBoardUI() {
        var humanBoard = game.human.getBoard();

        for (var node : ownBoardGrid.getChildren()) {
            // Verify if the node is a Rectangle
            if (node instanceof Rectangle rect) {
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);

                if (humanBoard.isHit(row, col)) {
                    rect.setFill(Color.RED);
                } else if (humanBoard.isMiss(row, col)) {
                    rect.setFill(Color.YELLOW);
                }
            }
            // If it is an ImageView (ship), ignore
            else if (node instanceof ImageView) {
                // Do nothing because ships do not change
            }
        }
    }

    /**
     * Ends the game and displays the winner.
     * Also removes saved state so reopening the app starts a fresh game.
     *
     * @param winner Winner's name.
     */
    private void endGame(String winner) {
        resultLabel.setText(winner.equals("Human") ? "Congratulations, you won!" : "Sorry, the machine won this one");
        ownBoardGrid.setDisable(true);
        machineBoardGrid.setDisable(true);

        // Remove saved game so that reopening starts a fresh game
        try {
            File f = new File("game_state.ser");
            if (f.exists()) {
                boolean deleted = f.delete();
                if (!deleted) {
                    System.err.println("Warning: could not delete save file on game end: " + f.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            System.err.println("Error deleting save file on game end: " + e.getMessage());
        }

        // Clear the in-memory reference as well
        com.example.MP4.utils.CurrentGameHolder.clear();
    }

    /**
     * Handles the button to view the full machine board.
     */
    @FXML
    private void handleViewMachineBoard() {
        try {
            if (machinePlayer != null) {
                OpponentBoardStage opponentBoardStage = new OpponentBoardStage(machinePlayer.getBoard());
                opponentBoardStage.show();
            } else {
                System.err.println("Error: machinePlayer is not initialized.");
            }
        } catch (IOException e) {
            System.err.println("Error loading opponent board view: " + e.getMessage());
        }
    }

}
