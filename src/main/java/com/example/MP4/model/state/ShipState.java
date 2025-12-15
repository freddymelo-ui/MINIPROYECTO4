package com.example.MP4.model.state;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShipState implements Serializable {
    private static final long serialVersionUID = 1L;

    public final String name;
    public final int size;
    public final boolean isHorizontal;
    public final List<int[]> coordinates; // cada int[] = {row, col}
    public final int hits;

    public ShipState(String name, int size, boolean isHorizontal, List<int[]> coordinates, int hits) {
        this.name = name;
        this.size = size;
        this.isHorizontal = isHorizontal;
        this.coordinates = new ArrayList<>();
        if (coordinates != null) {
            for (int[] c : coordinates) {
                // copia defensiva
                this.coordinates.add(new int[] { c[0], c[1] });
            }
        }
        this.hits = hits;
    }
}

