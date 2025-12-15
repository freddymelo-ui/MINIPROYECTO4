package com.example.MP4.view;

import com.example.MP4.controller.PlacementController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

// for the ship placement window
public class PlacementStage extends Stage {

    /**
     * Controller associated with the placement view.
     */
    private final PlacementController placementController;

    /**
     * Creates a new instance of {@code PlacementStage},
     * loads the FXML file, and configures the interface properties.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    public PlacementStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/MP4/placement-view.fxml"));
        Parent root = loader.load();
        placementController = loader.getController();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Posicionamiento");
        getIcons().add(new Image(String.valueOf(
                getClass().getResource("/com/example/MP4/images/favicon.png"))));
        setResizable(false);
        setWidth(700);
        setHeight(400);
        show();
    }

    /**
     * Returns the {@code PlacementController} associated with this
     * {@code PlacementStage}.
     *
     * @return the controller instance that manages placement logic.
     */
    public PlacementController getGameController() {
        return placementController;
    }

    /**
     * Static inner class that holds the singleton instance
     * of {@code PlacementStage}.
     */
    private static class GameStageHolder {
        /**
         * Singleton instance of {@code PlacementStage}.
         */
        private static PlacementStage INSTANCE;
    }

    /**
     * Returns the singleton instance of {@code PlacementStage}, creating it if
     * necessary.
     *
     * @return the unique instance of {@code PlacementStage}.
     * @throws IOException if the FXML file cannot be loaded during creation.
     */
    public static PlacementStage getInstance() throws IOException {
        GameStageHolder.INSTANCE = GameStageHolder.INSTANCE != null ? GameStageHolder.INSTANCE : new PlacementStage();

        // if there is an existing game in memory (e.g., loaded from disk), provide it to the controller
        com.example.MP4.model.Game current = com.example.MP4.utils.CurrentGameHolder.get();
        if (current != null) {
            GameStageHolder.INSTANCE.getGameController().setGame(current);
        }

        return GameStageHolder.INSTANCE;
    }

    /**
     * Closes the current instance of {@code PlacementStage}
     * and resets the singleton reference to null.
     */
    public static void deleteInstance() {
        GameStageHolder.INSTANCE.close();
        GameStageHolder.INSTANCE = null;
    }
}
