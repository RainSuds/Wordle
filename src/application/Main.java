import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Set the title of the window (Stage)
            primaryStage.setTitle("Wordle Game");

            // Create the main layout pane
            BorderPane root = new BorderPane();

            // Initialize the game board or main game scene
            GameBoard gameBoard = new GameBoard();
            root.setCenter(gameBoard.buildGameBoard());

            // Set the scene with root pane and size
            Scene scene = new Scene(root, 800, 600);

            // Apply a CSS stylesheet if needed
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            // Set the primary scene
            primaryStage.setScene(scene);

            // Show the application window
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
