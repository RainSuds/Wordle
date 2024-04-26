package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application 
{

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) 
    {
        try 
        {
            // Load the FXML and get the root node
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 700, 700);

            GameController controller = loader.getController();
            
            setupKeyEventHandling(scene, controller);
            
            scene.setOnKeyPressed(event -> controller.handleKeyPress(event.getCode()));

            primaryStage.setTitle("Wordle");
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    private void setupKeyEventHandling(Scene scene, GameController controller) 
    {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> 
        {
            if (event.getCode() == KeyCode.ENTER) 
            {
                controller.handleEnterKeyPress();
                event.consume();
            }
        });

        scene.setOnKeyPressed(event -> 
        {
            // Handle all other key presses
            controller.handleKeyPress(event.getCode());
        });
    }
}
