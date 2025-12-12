package com.example.MP4.controller;

import com.example.MP4.model.*;
import com.example.MP4.view.GameStage;
import com.example.MP4.view.PlacementStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.Objects;

public class PlacementController {

    public Button startButton;
    private Game game;
    private Board humanBoard;
    private Board machineBoard;
    private final HumanAdapter human = new HumanAdapter(new Board());
    private final MachineAdapter machine = new MachineAdapter(new Board());

    private Ship currentShip; // Current ship that the player is positioning

    @FXML
    private GridPane ownBoardGrid;

    /**
     * Method called when initializing the controller.
     */
    @FXML
    public void initialize() {
        initializeOwnBoardUI();
    }

    /**
     * Configures the game when receiving a Game instance.
     * Also initializes the boards and prepares the initial state of the game.
     *
     * @param game Game instance provided by WelcomeController.
     */
    public void setGame(Game game) {
        this.game = game;

        // Access the boards directly from the players
        this.humanBoard = game.human.getBoard();
        this.machineBoard = game.machine.getBoard();

        // Reset the initial state of both boards
        this.humanBoard.reset();
        this.machineBoard.reset();

        // Configure the first ship that the human player must place
        if (!game.human.getShips().isEmpty()) {
            this.currentShip = game.human.getShips().get(0);
        }

        System.out.println("Game set successfully. Ready to start!");
    }

    /**
     * Initializes the GridPane to display the player's board.
     */
    @FXML
    private void initializeOwnBoardUI() {
        ownBoardGrid.getChildren().clear();

        // Configure the GridPane and its cells
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle cell = new Rectangle(25, 25);
                cell.setFill(Color.LIGHTBLUE);
                cell.setStroke(Color.BLACK);

                int finalRow = row;
                int finalCol = col;

                cell.setOnMouseEntered(e -> highlightPotentialShip(finalRow, finalCol));
                cell.setOnMouseExited(e -> clearHighlight());
                cell.setOnMouseClicked(e -> handleCellClick(finalRow, finalCol));

                ownBoardGrid.add(cell, col, row);
            }
        }

        // Event to change orientation using the Space key
        ownBoardGrid.setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.SPACE) {
                toggleOrientation(); // Change orientation
            }
        });

        // Allow the GridPane to receive keyboard events
        ownBoardGrid.setFocusTraversable(true);
    }

    @FXML
    private void handleStartButton() throws IOException {
        // Ensure that the machine's ships are placed before starting the game
        game.machine.placeShips();

        // Create and configure the GameStage
        GameStage.getInstance().getGameController().setGame(game);

        // Close the PlacementStage
        PlacementStage.deleteInstance();
    }

    /**
     * Highlights the cells where the current ship could be placed.
     *
     * @param startRow Initial row of the potential placement.
     * @param startCol Initial column of the potential placement.
     */
    private void highlightPotentialShip(int startRow, int startCol) {
        if (currentShip == null)
            return;

        boolean isHorizontal = currentShip.isHorizontal();
        int size = currentShip.getSize();

        boolean isValid = humanBoard.isPlacementValid(currentShip, startRow, startCol);

        for (int i = 0; i < size; i++) {
            int row = isHorizontal ? startRow : startRow + i;
            int col = isHorizontal ? startCol + i : startCol;

            for (var node : ownBoardGrid.getChildren()) {
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                    Rectangle rect = (Rectangle) node;
                    // Highlight only if the cell is not occupied
                    if (humanBoard.occupiesCell(row, col)) {
                        rect.setFill(Color.GRAY); // Occupied, already has a ship
                    } else {
                        rect.setFill(isValid ? Color.GREEN : Color.RED); // Valid/invalid highlight
                    }
                }
            }
        }
    }

    /**
     * Clears the highlight on the board.
     */
    private void clearHighlight() {
        for (var node : ownBoardGrid.getChildren()) {
            Rectangle rect = (Rectangle) node;
            int row = GridPane.getRowIndex(node);
            int col = GridPane.getColumnIndex(node);

            // Only clear if the cell is not occupied
            if (humanBoard.occupiesCell(row, col)) {
                rect.setFill(Color.GRAY); // Occupied, already has a ship
            } else {
                rect.setFill(Color.LIGHTBLUE); // Empty
            }
        }
    }

    /**
     * Handles the player's click to attempt placing a ship.
     *
     * @param startRow Selected initial row.
     * @param startCol Selected initial column.
     */
    private void handleCellClick(int startRow, int startCol) {
        if (currentShip == null) {
            System.out.println("No ship selected for placement.");
            return;
        }

        boolean isHorizontal = currentShip.isHorizontal();
        boolean placed = humanBoard.placeShip(currentShip, startRow, startCol);

        if (placed) {
            System.out.println(currentShip.getName() + " placed successfully!");
            updateOwnBoardUI(startRow, startCol, currentShip.getSize(), isHorizontal);
            proceedToNextShip();
        } else {
            System.out.println("Invalid placement. Try again.");
        }
    }

    /**
     * Updates the interface to show a placed ship.
     *
     * @param startRow     Initial row of the ship.
     * @param startCol     Initial column of the ship.
     * @param size         Size of the ship.
     * @param isHorizontal Ship orientation.
     */
    private void updateOwnBoardUI(int startRow, int startCol, int size, boolean isHorizontal) {
        for (int i = 0; i < size; i++) {
            int row = isHorizontal ? startRow : startRow + i;
            int col = isHorizontal ? startCol + i : startCol;

            for (var node : ownBoardGrid.getChildren()) {
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                    Rectangle rect = (Rectangle) node;
                    rect.setFill(Color.GRAY);
                }
            }
        }
    }

    /**
     * Checks if all ships have been placed and enables the "Start" button if true.
     */
    private void checkAllShipsPlaced() {
        if (human.getShips().stream().allMatch(ship -> humanBoard.getShips().contains(ship))) {
            startButton.setDisable(false);
            startButton.setStyle("-fx-background-color: #d6dbdf; -fx-text-fill: #1e2e51; -fx-font-weight: impact;");
            ownBoardGrid.setDisable(true); // Disable the board
            System.out.println("All ships placed! Board is now disabled.");
        }
    }

    /**
     * Proceeds to the next ship to be placed, or validates that all ships are
     * placed.
     */
    private void proceedToNextShip() {
        // Get the current index of the ship being placed
        int currentIndex = human.getShips().indexOf(currentShip);

        // If there are more ships to place, move to the next one
        if (currentIndex < human.getShips().size() - 1) {
            currentShip = human.getShips().get(currentIndex + 1);
            System.out.println("Select position for: " + currentShip.getName());
        } else {
            // If no more ships remain, all ships are placed
            System.out.println("All ships placed!");
        }

        // Validate ship placement status
        checkAllShipsPlaced();
    }

    private void toggleOrientation() {
        if (currentShip == null)
            return;

        // Change the orientation of the current ship
        currentShip.setHorizontal(!currentShip.isHorizontal());
        System.out.println("Orientation toggled: " + (currentShip.isHorizontal() ? "Horizontal" : "Vertical"));
    }

}
