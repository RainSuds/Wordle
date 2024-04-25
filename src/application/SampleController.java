package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class SampleController 
{
	
	
	@FXML private Label box00, box01, box02, box03, box04;
    @FXML private Label box10, box11, box12, box13, box14;
    @FXML private Label box20, box21, box22, box23, box24;
    @FXML private Label box30, box31, box32, box33, box34;
    @FXML private Label box40, box41, box42, box43, box44;
    @FXML private Label box50, box51, box52, box53, box54;
	
	@FXML private Button SAVE, LOAD, HELP, RESET, ENTER, DELETE;
	@FXML private Button Q, W, E, R, T, Y, U, I, O, P, A, S, D, F, G, H, J, K, L, Z, X, C, V, B, N, M;
	
	private TextField guessInput;
	private GameBoard newGame;
	private Label[][] table;
	
	public void initialize() 
	{	
		table = new Label[][] 
				{
		            {box00, box01, box02, box03, box04},
		            {box10, box11, box12, box13, box14},
		            {box20, box21, box22, box23, box24},
		            {box30, box31, box32, box33, box34},
		            {box40, box41, box42, box43, box44},
		            {box50, box51, box52, box53, box54}
		        };
		guessInput = new TextField();        
		ShadowData sd = new ShadowData(0, table);
		this.newGame = new GameBoard(new Log(), new SelectWord(), sd, new Stats());
	}
	
	private void showAlertDialog(String message) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Input Error");
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	
	public void addCharacterToGuess(String c)
	{
		guessInput.appendText(c.toUpperCase());
		updateLabels();
	}
	
	public void removeLastCharacter()
	{
		int len = guessInput.getLength();
		if (len > 0)
		{
			guessInput.deleteText(len - 1, len);
			updateLabels();
		}
		
	}
	
	public void checkGuess()
	{
		// Check current input
		boolean won = false;
		if (newGame.isValidInput(guessInput.getText().toUpperCase()))
		{
			won = newGame.checkGuesses(guessInput);
			if (won)
			{
				newGame.getStats().updateCurrentStats(newGame.getLog());
			}
			else
			{
				guessInput.clear();
			}
		}
		else
		{
			if (guessInput.getLength() < 5)
			{
				showAlertDialog("Too Short!");
			}
			else 
			{
				guessInput.clear();
				showAlertDialog("Invalid word or not in list!");
			}
			
		}
		updateLabels();
	}
	
    private void handleKeyPress(KeyCode keyCode) 
    {
    	// Record key press actions
        if (keyCode.isLetterKey()) 
        {
        	addCharacterToGuess(keyCode.getChar().toString());
        } 
        else if (keyCode == KeyCode.BACK_SPACE) 
        {
        	removeLastCharacter();
        } 
        else if (keyCode == KeyCode.ENTER) 
        {
        	checkGuess();
        }
    }
    
    public void handleButtonPress(String character) {
    	if (character.equals("SAVE"))
    	{
    		//
    		newGame.saveGame(); // it should update the current log
    	}
    	else if (character.equals("LOAD"))
    	{
    		// Save current progress, open file explorer for another log.txt file
    		newGame.saveGame();
    		String fileName = "test1.txt";
			newGame.loadFile(fileName);
    	}
    	else if (character.equals("HELP"))
    	{
    		// Pop a help menu
    		newGame.saveGame();
    	}
    	else if (character.equals("RESET"))
        {
    		// it should only erase the current game from log and keep the previous logs on file
        	newGame.resetGameBoard();
        }
    	else if (character.equals("ENTER")) 
    	{
        	checkGuess();
        } 
    	else if (character.equals("DELETE")) 
        {
            removeLastCharacter();
        }
    	else 
    	{
            addCharacterToGuess(character);
        }
    }
    
    @FXML
    private void handleButtonAction(ActionEvent e) 
    {
        if (e.getSource() instanceof Button) 
        {
            Button btn = (Button) e.getSource();
            handleButtonPress(btn.getId());
        }
    }
    
	private void updateLabels() 
	{
		// update label inputs
		int currRow = newGame.getShadowData().getCurrentRow();
		Label[] currLabels = newGame.getShadowData().getCurrentGame()[currRow];
		
		String currGuess = guessInput.getText().toUpperCase();
        for (int i = 0; i < currGuess.length(); i++) 
        {
        	currLabels[i].setText(String.valueOf(currGuess.charAt(i)));
        }

        // Clear any labels past the input text length
        for (int i = currGuess.length(); i < currLabels.length; i++) {
        	currLabels[i].setText("");
        }
    }
	

}
