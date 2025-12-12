package com.example.MP4.model;

import java.util.List;

// Defines the behavior of a player in the game
public interface IPlayer {

    /**
     * Executes the player's turn by targeting the opponent's board.
     * 
     * @return A message indicating the result of the shot ("miss", "hit", or
     *         "sunk").
     */
    String playTurn(int row, int col, Board opponentBoard);

    /**
     * Checks whether the player has lost the game.
     * 
     * @return true if all ships are sunk, false otherwise.
     */
    boolean hasLost();

    /**
     * Retrieves the player's board.
     * 
     * @return The board associated with the player.
     */
    Board getBoard();
}
