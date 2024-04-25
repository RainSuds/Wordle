package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SampleController 
{
	@FXML private TextField guessInput;
	
	@FXML private Label box00, box01, box02, box03, box04;
    @FXML private Label box10, box11, box12, box13, box14;
    @FXML private Label box20, box21, box22, box23, box24;
    @FXML private Label box30, box31, box32, box33, box34;
    @FXML private Label box40, box41, box42, box43, box44;
    @FXML private Label box50, box51, box52, box53, box54;
	
	@FXML private Button SAVE, LOAD, HELP, RESET, ENTER, DELETE;
	@FXML private Button Q, W, E, R, T, Y, U, I, O, P, A, S, D, F, G, H, J, K, L, Z, X, C, V, B, N, M = new Button();
	
	private GameBoard newGame;
	private Label[][] table;
	
	public void initialize() 
	{	
		table = new Label[][] {
            {box00, box01, box02, box03, box04},
            {box10, box11, box12, box13, box14},
            {box20, box21, box22, box23, box24},
            {box30, box31, box32, box33, box34},
            {box40, box41, box42, box43, box44},
            {box50, box51, box52, box53, box54}
        };
        
		ShadowData sd = new ShadowData(0, table);
		this.newGame = new GameBoard(new Log(), new SelectWord(), sd, new Stats());
	}
	
	@FXML protected void checkGuess()
	{
		// Check current input
		newGame.checkGuesses(guessInput);
	}
	
	@FXML protected void buttonPressed()
	{
		
	}
	
	

}
