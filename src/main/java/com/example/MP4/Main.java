package com.example.MP4;

import com.example.MP4.model.Game;
import com.example.MP4.utils.CurrentGameHolder;
import com.example.MP4.utils.GameSaveManager;
import com.example.MP4.view.GameStage;
import com.example.MP4.view.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

// FREDDY ALEXANDER MELO BUITRAGO -- 202125498
// VERONICA GRANADOS -- 2123263
// JUAN DAVID SALAZAR -- 2344293

public class Main extends Application {

    private static final String SAVE_FILE = "game_state.ser";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Try to load saved game automatically
        File f = new File(SAVE_FILE);
        if (f.exists()) {
            try {
                Game loaded = GameSaveManager.loadGame(f);
                if (loaded != null) {
                    CurrentGameHolder.set(loaded);
                }
                // decide whether to show placement (if human hasn't placed ships) or full game
                boolean humanPlaced = loaded != null && !loaded.human.getBoard().getShips().isEmpty();
                if (loaded != null && humanPlaced) {
                    GameStage gs = GameStage.getInstance();
                    gs.getGameController().setGame(loaded);
                } else if (loaded != null) {
                    // show placement stage so player can continue placing ships
                    com.example.MP4.view.PlacementStage.getInstance().getGameController().setGame(loaded);
                }
                return;
            } catch (Exception e) {
                // if loading fails, rename the save to avoid repeated failures and fall back to welcome
                System.err.println("Failed to load saved game: " + e.getMessage());
                e.printStackTrace();
                try {
                    File bad = new File(SAVE_FILE);
                    File rename = new File(SAVE_FILE + ".corrupt");
                    if (bad.exists()) bad.renameTo(rename);
                    System.err.println("Renamed corrupted save to " + rename.getAbsolutePath());
                } catch (Exception ex) {
                    System.err.println("Failed to rename corrupted save: " + ex.getMessage());
                }
            }
        }

        // default: show welcome
        WelcomeStage.getInstance();
    }

    @Override
    public void stop() throws Exception {
        // on exit, persist current game if present
        Game current = CurrentGameHolder.get();
        if (current != null) {
            try {
                File f = new File(SAVE_FILE);
                GameSaveManager.saveGame(f, current);
                System.out.println("Game saved to " + f.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to save game on exit: " + e.getMessage());
                e.printStackTrace();
            }
        }
        super.stop();
    }
}
