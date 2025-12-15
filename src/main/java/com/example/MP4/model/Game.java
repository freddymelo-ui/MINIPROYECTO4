package com.example.MP4.model;

// Class that represents the battleship game between a human player and a machine.
/**
 * Core game model coordinating human and machine players and tracking winner.
 * Contains turn processing and victory detection logic.
 */
public class Game implements IGame {
    public final HumanAdapter human;
    public final MachineAdapter machine;
    private String winner;

    public Game(HumanAdapter human, MachineAdapter machine) {
        this.human = human;
        this.machine = machine;
        this.winner = null;
    }

    // to process the human's shot
    @Override
    public String processHumanShot(int row, int col) {
        String result = machine.playTurn(row, col, machine.getBoard());
        System.out.println("Machine Board State After Human Shot:");
        machine.getBoard().printBoard(); // To debug the machine's board state

        if (machine.getBoard().isGameOver()) {
            System.out.println("Machine has lost. Declaring Human as the winner.");
            winner = "Human";
        }
        return result;
    }

    // to process the machine's shot
    @Override
    public String processMachineShot(int row, int col) {
        String result = human.playTurn(row, col, human.getBoard());
        System.out.println("Human Board State After Machine Shot:");
        human.getBoard().printBoard(); // To debug the human's board state

        if (human.getBoard().isGameOver()) {
            System.out.println("Human has lost. Declaring Machine as the winner.");
            winner = "Machine";
        }
        return result;
    }

    // to check if there is a winner
    @Override
    public boolean checkVictory() {
        System.out.println("Checking victory condition...");
        boolean humanLost = human.getBoard().isGameOver();
        boolean machineLost = machine.getBoard().isGameOver();
        System.out.println("Human lost: " + humanLost + ", Machine lost: " + machineLost);

        return humanLost || machineLost;
    }

    // to get the winner
    @Override
    public String getWinner() {
        if (winner == null) {
            throw new IllegalStateException("No winner yet.");
        }
        return winner;
    }

    /**
     * Public setter used only when restoring a saved game to set the winner.
     * @param winner winner name ("Human" or "Machine")
     */
    public void setWinnerForLoad(String winner) {
        this.winner = winner;
    }

}
