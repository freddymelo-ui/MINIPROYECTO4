package com.example.MP4.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageUtils {

    /**
     * Loads an image from the given path.
     *
     * @param path the relative path to the image resource
     * @return an ImageView containing the loaded image
     */
    public static ImageView loadImage(String path) {
        try {
            Image image = new Image(ImageUtils.class.getResourceAsStream(path));
            ImageView imageView = new ImageView(image);

            // Configurar propiedades de ajuste usando dimensiones intrínsecas
            imageView.setFitWidth(image.getWidth());
            imageView.setFitHeight(image.getHeight());

            return imageView;
        } catch (Exception e) {
            throw new RuntimeException("Error loading image: " + path, e);
        }
    }

    /**
     * Resizes an ImageView to the given dimensions.
     *
     * @param imageView the ImageView to resize
     * @param width     the target width
     * @param height    the target height
     */
    public static void resizeImage(ImageView imageView, double width, double height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    /**
     * Rotates an ImageView by the given angle.
     *
     * @param imageView the ImageView to rotate
     * @param angle     the angle in degrees
     */
    public static void rotateImage(ImageView imageView, double angle) {
        imageView.setRotate(angle);

        // Ajustar automáticamente las dimensiones tras la rotación
        if (angle == 90 || angle == 270) {
            double originalWidth = imageView.getFitWidth();
            double originalHeight = imageView.getFitHeight();
            imageView.setFitWidth(originalHeight);
            imageView.setFitHeight(originalWidth);
        }
    }

}
