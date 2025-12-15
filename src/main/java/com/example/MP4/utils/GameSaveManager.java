package com.example.MP4.utils;

import com.example.MP4.model.*;
import com.example.MP4.model.state.BoardState;
import com.example.MP4.model.state.GameState;
import com.example.MP4.model.state.ShipState;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class GameSaveManager {

    public static void saveGame(File file, Game game) throws IOException {
        if (file == null) throw new IllegalArgumentException("file is null");
        if (game == null) throw new IllegalArgumentException("game is null");

        Board humanBoard = game.human.getBoard();
        Board machineBoard = game.machine.getBoard();

        BoardState hb = boardToState(humanBoard);
        BoardState mb = boardToState(machineBoard);

        String winner = null;
        try {
            winner = game.getWinner();
        } catch (IllegalStateException ignored) {
        }

        GameState state = new GameState(hb, mb, winner, System.currentTimeMillis());

        // write to temp file then move atomically
        Path target = file.toPath();
        Path parent = target.getParent();
        if (parent == null) {
            parent = Path.of(System.getProperty("user.dir"));
        }
        Path tmp = Files.createTempFile(parent, "game-state-", ".tmp");
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(tmp)))) {
            oos.writeObject(state);
            oos.flush();
        }
        Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    public static Game loadGame(File file) throws IOException, ClassNotFoundException {
        if (file == null) throw new IllegalArgumentException("file is null");
        if (!file.exists()) throw new FileNotFoundException(file.getAbsolutePath());

        GameState state;
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            Object obj = ois.readObject();
            if (!(obj instanceof GameState)) {
                throw new InvalidObjectException("File does not contain a GameState");
            }
            state = (GameState) obj;
        }

        return stateToGame(state);
    }

    // Convert Board -> BoardState
    private static BoardState boardToState(Board board) {
        List<ShipState> ships = new ArrayList<>();
        for (Ship s : board.getShips()) {
            ships.add(new ShipState(s.getName(), s.getSize(), s.isHorizontal(), s.getCoordinates(), s.getHits()));
        }

        List<int[]> hits = new ArrayList<>();
        List<int[]> misses = new ArrayList<>();
        char[][] grid = board.getGrid();
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (board.isHit(r, c)) {
                    hits.add(new int[] { r, c });
                } else if (board.isMiss(r, c)) {
                    misses.add(new int[] { r, c });
                }
            }
        }

        return new BoardState(ships, hits, misses, Board.BOARD_SIZE);
    }

    // Convert GameState -> Game (rebuild boards, ships, adapters)
    private static Game stateToGame(GameState state) throws InvalidObjectException {
        if (state == null) throw new InvalidObjectException("GameState is null");
        if (state.humanBoard.boardSize != Board.BOARD_SIZE || state.machineBoard.boardSize != Board.BOARD_SIZE) {
            throw new InvalidObjectException("Incompatible board size");
        }

        Board humanBoard = new Board();
        Board machineBoard = new Board();

        // place human ships
        for (ShipState ss : state.humanBoard.ships) {
            if (ss.coordinates.isEmpty()) throw new InvalidObjectException("Ship has no coordinates");
            int[] start = ss.coordinates.get(0);
            Ship ship = new Ship(ss.name, ss.size, ss.isHorizontal);
            // attempt to place using first coordinate as start
            boolean placed = humanBoard.placeShip(ship, start[0], start[1]);
            if (!placed) {
                // try adjusting orientation
                ship.setHorizontal(ss.isHorizontal);
                placed = humanBoard.placeShip(ship, start[0], start[1]);
                if (!placed) {
                    throw new InvalidObjectException("Failed to place human ship: " + ss.name);
                }
            }
        }

        // place machine ships
        for (ShipState ss : state.machineBoard.ships) {
            if (ss.coordinates.isEmpty()) throw new InvalidObjectException("Ship has no coordinates");
            int[] start = ss.coordinates.get(0);
            Ship ship = new Ship(ss.name, ss.size, ss.isHorizontal);
            boolean placed = machineBoard.placeShip(ship, start[0], start[1]);
            if (!placed) {
                ship.setHorizontal(ss.isHorizontal);
                placed = machineBoard.placeShip(ship, start[0], start[1]);
                if (!placed) {
                    throw new InvalidObjectException("Failed to place machine ship: " + ss.name);
                }
            }
        }

        // apply hits for human and machine (after all ships placed)
        for (int[] hit : state.humanBoard.hitCoordinates) {
            humanBoard.registerShot(hit[0], hit[1]);
        }
        for (int[] hit : state.machineBoard.hitCoordinates) {
            machineBoard.registerShot(hit[0], hit[1]);
        }

        // apply misses that didn't hit ships
        for (int[] miss : state.humanBoard.missCoordinates) {
            if (!humanBoard.isHit(miss[0], miss[1])) {
                humanBoard.registerShot(miss[0], miss[1]);
            }
        }
        for (int[] miss : state.machineBoard.missCoordinates) {
            if (!machineBoard.isHit(miss[0], miss[1])) {
                machineBoard.registerShot(miss[0], miss[1]);
            }
        }

        HumanAdapter human = new HumanAdapter(humanBoard);
        MachineAdapter machine = new MachineAdapter(machineBoard);
        Game game = new Game(human, machine);

        // restore winner if present (package-private setter must exist)
        if (state.winner != null) {
            try {
                game.setWinnerForLoad(state.winner);
            } catch (NoSuchMethodError | UnsupportedOperationException e) {
                // If cannot set winner, ignore; the UI can read state.winner separately if needed
            }
        }

        return game;
    }
}
