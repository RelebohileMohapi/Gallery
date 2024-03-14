package com.example.demo12;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javafx.animation.ScaleTransition;
import javafx.util.Duration;

public class Gallery extends Application {
    private static final int THUMBNAIL_SIZE = 140;
    private static final int GRID_GAP = 10;
    private static final int NUM_COLS = 3;
    private static final int NUM_ROWS = 3;

    private final String[] imagePaths = {
            "file:thumbnails/image1.jpg",
            "file:thumbnails/image2.jpg",
            "file:thumbnails/image3.jpg",
            "file:thumbnails/image4.jpg",
            "file:thumbnails/image5.jpg",
            "file:thumbnails/image6.jpg",
            "file:thumbnails/image7.jpg",
            "file:thumbnails/image8.jpg",
            "file:thumbnails/image9.jpg"
    };

    private GridPane thumbnailGrid;
    private Stage primaryStage;
    private int currentIndex = 0;
    private HBox navigationBox;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Create a grid pane to hold thumbnail images
        thumbnailGrid = new GridPane();
        thumbnailGrid.setPadding(new Insets(10));
        thumbnailGrid.setHgap(GRID_GAP);
        thumbnailGrid.setVgap(GRID_GAP);
        thumbnailGrid.getStyleClass().add("thumbnail-grid");

        // Add thumbnail images to the grid
        for (int i = 0; i < NUM_ROWS * NUM_COLS && i < imagePaths.length; i++) {
            ImageView thumbnail = createThumbnail(imagePaths[i]);
            thumbnailGrid.add(thumbnail, i % NUM_COLS, i / NUM_COLS);
        }

        // Create navigation controls
        Button previousButton = new Button("Previous");
        previousButton.setOnAction(event -> showPreviousImage());
        previousButton.getStyleClass().add("navigation-button");

        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> showNextImage());
        nextButton.getStyleClass().add("navigation-button");

        Button clearButton = new Button("Clear"); // Corrected label
        clearButton.setOnAction(event -> primaryStage.close());
        clearButton.getStyleClass().add("navigation-button");

        Button thumbnailButton = new Button("toThumbnailView");
        thumbnailButton.setOnAction(event -> returnToThumbnailView());
        thumbnailButton.getStyleClass().add("navigation-button");

        navigationBox = new HBox(previousButton, nextButton, clearButton, thumbnailButton);
        navigationBox.setAlignment(Pos.CENTER);
        navigationBox.setSpacing(10);
        navigationBox.setPadding(new Insets(10));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(thumbnailGrid);
        borderPane.setBottom(navigationBox);

        // Create the scene and set it to the stage
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setTitle("Image Gallery");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ImageView createThumbnail(String imageUrl) {
        Image image = new Image(imageUrl);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(THUMBNAIL_SIZE);
        imageView.setFitHeight(THUMBNAIL_SIZE);
        imageView.getStyleClass().add("thumbnail-image");
        imageView.setOnMouseClicked(event -> displayFullImage(image));

        // Create a scale transition for hover effect
        ScaleTransition scaleInTransition = new ScaleTransition(Duration.millis(100), imageView);
        scaleInTransition.setToX(1.1);
        scaleInTransition.setToY(1.1);

        ScaleTransition scaleOutTransition = new ScaleTransition(Duration.millis(100), imageView);
        scaleOutTransition.setToX(1.0);
        scaleOutTransition.setToY(1.0);

        // Set up mouse events
        imageView.setOnMouseEntered(event -> scaleInTransition.play());
        imageView.setOnMouseExited(event -> scaleOutTransition.play());

        return imageView;
    }

    private void showPreviousImage() {
        if (currentIndex > 0) {
            currentIndex--;
            displayFullImage(new Image(imagePaths[currentIndex]));
        }
    }

    private void showNextImage() {
        if (currentIndex < imagePaths.length - 1) {
            currentIndex++;
            displayFullImage(new Image(imagePaths[currentIndex]));
        }
    }

    private void displayFullImage(Image image) {
        ImageView currentImageView = new ImageView(image);
        currentImageView.setFitWidth(400);
        currentImageView.setFitHeight(400);

        // Create navigation controls for full image view
        Button previousButton = new Button("Previous");
        previousButton.setOnAction(event -> showPreviousImage());
        previousButton.getStyleClass().add("navigation-button");

        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> showNextImage());
        nextButton.getStyleClass().add("navigation-button");

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(event -> primaryStage.close());
        clearButton.getStyleClass().add("navigation-button");

        Button thumbnailButton = new Button("toThumbnailView");
        thumbnailButton.setOnAction(event -> returnToThumbnailView());
        thumbnailButton.getStyleClass().add("navigation-button");

        navigationBox.getChildren().setAll(previousButton, nextButton, clearButton, thumbnailButton);

        BorderPane fullImagePane = new BorderPane();
        fullImagePane.setCenter(currentImageView);
        fullImagePane.setBottom(navigationBox);

        // Set the content of the primaryStage to the fullImagePane
        primaryStage.getScene().setRoot(fullImagePane);
    }

    private void returnToThumbnailView() {
        currentIndex = 0;
        // Clear previous thumbnails
        thumbnailGrid.getChildren().clear();

        // Add thumbnail images to the grid
        for (int i = 0; i < NUM_ROWS * NUM_COLS && i < imagePaths.length; i++) {
            ImageView thumbnail = createThumbnail(imagePaths[i]);
            thumbnailGrid.add(thumbnail, i % NUM_COLS, i / NUM_COLS);
        }

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(thumbnailGrid);
        borderPane.setBottom(navigationBox);

        primaryStage.getScene().setRoot(borderPane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
