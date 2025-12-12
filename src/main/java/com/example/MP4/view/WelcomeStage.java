package com.example.MP4.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

// for the welcome window and game title
public class WelcomeStage extends Stage {

    /**
     * Builds the WelcomeStage and initializes the window by loading the FXML file,
     * setting up the scene, the title, the icon, and the window properties.
     *
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public WelcomeStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/MP4/welcome-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("¡Batalla Naval!");
        getIcons().add(new Image(String.valueOf(
                getClass().getResource("/com/example/MP4/images/favicon.png"))));
        setResizable(false);
        show();
    }

    /**
     * Static inner class that stores the Singleton instance of
     * {@link WelcomeStage}.
     * This enables lazy initialization.
     */
    private static class WelcomeStageHolder {
        private static WelcomeStage INSTANCE;
    }

    /**
     * Provides access to the Singleton instance of {@link WelcomeStage}.
     * If the instance does not exist, it is created.
     *
     * @throws IOException If an error occurs while creating or loading the window.
     */
    public static void getInstance() throws IOException {
        WelcomeStageHolder.INSTANCE = WelcomeStageHolder.INSTANCE != null ? WelcomeStageHolder.INSTANCE
                : new WelcomeStage();

    }

    /**
     * Deletes the Singleton instance of {@link WelcomeStage} and closes the window.
     * After calling this method, a new instance may be created.
     */
    public static void deleteInstance() {
        WelcomeStageHolder.INSTANCE.close();
        WelcomeStageHolder.INSTANCE = null;
    }
}
