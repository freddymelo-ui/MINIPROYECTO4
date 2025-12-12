package com.example.MP4.view;

import com.example.MP4.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

// to represent the main game window and manage its controller

public class GameStage extends Stage {
    /**
     * The controller associated with the game view.
     */
    private final GameController gameController;

    /**
     * Creates a new instance of {@code GameStage}, loads the FXML file,
     * and configures the interface properties.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    public GameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/MP4/game-view.fxml"));
        Parent root = loader.load();
        gameController = loader.getController();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Battleship!");
        getIcons().add(new Image(String.valueOf(
                getClass().getResource("/com/example/MP4/images/favicon.png"))));
        setResizable(false);
        setWidth(1000);
        setHeight(570);
        show();
    }

    /**
     * Returns the {@code GameController} associated with this {@code GameStage}.
     *
     * @return the instance of {@code GameController} that manages the game logic.
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * Static inner class that stores the single (singleton) instance
     * of {@code GameStage}.
     */
    private static class GameStageHolder {
        /**
         * Singleton instance of {@code GameStage}.
         */
        private static GameStage INSTANCE;
    }

    /**
     * Returns the singleton instance of {@code GameStage},
     * creating it if necessary.
     *
     * @return the unique instance of {@code GameStage}.
     * @throws IOException if the FXML file cannot be loaded during creation.
     */
    public static GameStage getInstance() throws IOException {
        GameStageHolder.INSTANCE = GameStageHolder.INSTANCE != null ? GameStageHolder.INSTANCE : new GameStage();

        return GameStageHolder.INSTANCE;
    }

    /**
     * Closes the current instance of {@code GameStage}
     * and resets the singleton reference to null.
     */
    public static void deleteInstance() {
        GameStageHolder.INSTANCE.close();
        GameStageHolder.INSTANCE = null;
    }
}
