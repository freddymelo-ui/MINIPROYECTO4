package com.example.MP4.model;

import java.util.Random;

// Adapts the Player class to represent a machine player
public class MachineAdapter extends PlayerAdapter {
    public static final int BOARD_SIZE = 10; // Fixed board size
    private Random random;

    public MachineAdapter(Board board) {
        super(board);
        this.random = new Random();
    }

    public Board getBoard() {
        return super.getBoard();
    }

    /**
     * Automatically places all ships on the board.
     * Ships are placed in valid positions generated randomly.
     */
    public void placeShips() {
        for (Ship ship : ships) {
            boolean placed = false;

            while (!placed) {
                // Generate random starting row, column, and orientation
                int startRow = random.nextInt(BOARD_SIZE);
                int startCol = random.nextInt(BOARD_SIZE);
                boolean isHorizontal = random.nextBoolean();

                // Set the ship's orientation
                ship.setHorizontal(isHorizontal);

                // Attempt to place the ship on the board
                placed = board.placeShip(ship, startRow, startCol);
                if (!placed) {
                    // If placement is invalid, retry with new random values
                    System.out.println("Machine failed to place " + ship.getName() + " at (" + startRow + ", "
                            + startCol + "). Retrying...");
                }
            }
        }
        System.out.println("Machine placed all ships successfully!");
    }

    /**
     * Executes the machine player's turn. The row and column of the attack
     * are generated randomly.
     * 
     * @return A message indicating the result of the attack ("miss", "hit", or
     *         "sunk").
     */
    @Override
    public String playTurn(int row, int col, Board opponentBoard) {
        String result;

        // Attempt to register a shot at the given coordinates
        result = opponentBoard.registerShot(row, col);

        // If that cell was already shot, return an indicative message
        if (result.equals("already shot")) {
            return "Invalid move, cell already shot.";
        }

        // Return the result of the shot
        return result;
    }

}
