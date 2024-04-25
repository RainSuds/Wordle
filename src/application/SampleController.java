package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SampleController 
{
	@FXML private Label currentGuess;
	@FXML private TextField guessInput;
	
	@FXML private Label box00 = new Label();
	@FXML private Label box01 = new Label();
	@FXML private Label box02 = new Label();
	@FXML private Label box03 = new Label();
	@FXML private Label box04 = new Label();
	
	private GameBoard newGame = new GameBoard();
	
	@FXML protected void checkGuess()
	{
		
		newGame.checkGuesses(row1, guessInput);
	}
	
	
	public void initialize() 
	{	
	}
}