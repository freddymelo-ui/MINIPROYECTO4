package com.example.MP4.model.state;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Serializable snapshot of a Board used for saving/restoring a game.
 * Stores ship snapshots and lists of hit/miss coordinates.
 */
public class BoardState implements Serializable {
    private static final long serialVersionUID = 1L;

    public final List<ShipState> ships;
    public final List<int[]> hitCoordinates;
    public final List<int[]> missCoordinates;
    public final int boardSize;

    /**
     * Create a BoardState snapshot.
     * @param ships list of ShipState
     * @param hitCoordinates list of hit coordinates {row,col}
     * @param missCoordinates list of miss coordinates {row,col}
     * @param boardSize board size (typically 10)
     */
    public BoardState(List<ShipState> ships, List<int[]> hitCoordinates, List<int[]> missCoordinates, int boardSize) {
        this.ships = ships == null ? new ArrayList<>() : new ArrayList<>(ships);
        this.hitCoordinates = new ArrayList<>();
        if (hitCoordinates != null) {
            for (int[] c : hitCoordinates) {
                this.hitCoordinates.add(new int[] { c[0], c[1] });
            }
        }
        this.missCoordinates = new ArrayList<>();
        if (missCoordinates != null) {
            for (int[] c : missCoordinates) {
                this.missCoordinates.add(new int[] { c[0], c[1] });
            }
        }
        this.boardSize = boardSize;
    }
}
