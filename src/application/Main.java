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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 700, 700);

            GameController controller = loader.getController();
            
            scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> 
            {
            	// ENTER handles bad, that's why I am handling it individually            	
                if (event.getCode() == KeyCode.ENTER) 
                {
                	System.out.println("Key Pressed: " + event.getCode());
                    controller.handleEnterKeyPress();
                    event.consume();
                }
            });
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
}
