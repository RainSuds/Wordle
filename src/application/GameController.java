package application;

import java.io.IOException;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class GameController 
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
	private Map<Character, Button> keyboardButtons;
	
	public void initialize() 
	{	
		initializingTable();
		guessInput = new TextField();
		createGameController(table, new GameBoard(new Log(), new ShadowData(table), new Stats()));
	}
	
	public void initializingTable()
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
	}
	
	private void initializeKeyboardButtons() 
	{
        // Map for keys
		keyboardButtons = Map.ofEntries(
								        Map.entry('Q', Q), Map.entry('W', W), Map.entry('E', E), Map.entry('R', R), Map.entry('T', T), Map.entry('Y', Y), Map.entry('U', U), Map.entry('I', I), Map.entry('O', O), Map.entry('P', P),
								        Map.entry('A', A), Map.entry('S', S), Map.entry('D', D), Map.entry('F', F), Map.entry('G', G), Map.entry('H', H), Map.entry('J', J), Map.entry('K', K), Map.entry('L', L),
								        Map.entry('Z', Z), Map.entry('X', X), Map.entry('C', C), Map.entry('V', V),	Map.entry('B', B), Map.entry('N', N), Map.entry('M', M)
					        			);
    }
	
	public void initializingUI()
	{
		for (Label[] row : table) {
	        for (Label label : row) {
	        	ColorStyle.BLACK.applyBackground(label);
	            ColorStyle.GREY.applyBorder(label);
	            label.setText(null);
	        }
	    }
	}
	
	public void createGameController(Label[][] t, GameBoard g) 
	{	
		guessInput = new TextField();
		newGame = g;
		
		guessInput.setFocusTraversable(true);
		guessInput.requestFocus();
		initializeKeyboardButtons();
		initializingUI();
		guessInput.setOnKeyPressed(event -> handleKeyPress(event.getCode())); // Handles physical keyboard input
		
		System.out.println(newGame.getLog().getCurrentSession().getCurrentWord().getTargetWord()); // display target for testing
	}
	
	public void reset()
	{
		initializingTable();
		Log l = newGame.getLog();
		l.setCurrentSession(l.newSession());
		createGameController(table, new GameBoard(l, new ShadowData(table), new Stats()));
		updateLabelsUI();
		updateKeyboardUI();
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
		if (guessInput.getText().length() < 5 && !keyboardButtons.get(c.charAt(0)).isDisabled()) 
		{
            guessInput.appendText(c.toUpperCase());
            updateLabelsUI();
        }
	}
	
	public void removeLastCharacter()
	{
		int len = guessInput.getLength();
		if (len > 0)
		{
			guessInput.deleteText(len - 1, len);
			updateLabelsUI();
		}
		
	}
	
	public void checkGuess()
	{
		// Check current input
		if (newGame.isValidInput(guessInput.getText().toUpperCase()))
		{
			boolean won = newGame.checkGuesses(guessInput);
			guessInput.clear();
			if (won || newGame.getLog().getCurrentSession().isFinished())
			{
				newGame.saveGame();
				showStatScreen();
				reset();
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
				showAlertDialog("Invalid word or not in list!");
			}
		}
		updateLabelsUI();
		updateKeyboardUI();
	}
	
	public void handleEnterKeyPress() 
	{
		checkGuess();
    }
	
	public void handleBackspaceKeyPress() 
	{
		removeLastCharacter();
    }
	
    public void handleKeyPress(KeyCode keyCode) 
    {
    	// Record key press actions
        if (keyCode.isLetterKey()) 
        {
        	addCharacterToGuess(keyCode.getChar().toString().toUpperCase());
        } 
        else if (keyCode == KeyCode.BACK_SPACE) 
        {
        	handleBackspaceKeyPress();
        }
    }
    
    public void handleButtonPress(String character) 
    {
    	if (character.equals("SAVE"))
    	{
    		newGame.saveGame(); // it should update the current log
    	}
    	else if (character.equals("LOAD"))
    	{
    		// Save current progress, open file explorer for another log.txt file
    		guessInput.clear();
    		String fileName = "test1.txt";
    		initializingTable();
    		newGame.loadFile(fileName, table); // updates all the classes
    		createGameController(table, newGame);
    		
    		for (String s : newGame.getLog().getCurrentSession().getPreviousGuesses())
    		{
    			guessInput.setText(s);
    			checkGuess();
    		}
    	}
    	else if (character.equals("STATISTIC"))
    	{
    		// Pop a help menu
    		newGame.saveGame();
    		showStatScreen();
    		
    	}
    	else if (character.equals("RESET"))
        {
    		initializingTable();
    		Log l = newGame.getLog();
    		l.reset();
    		createGameController(table, new GameBoard(l, new ShadowData(table), new Stats()));
    		updateLabelsUI();
    		updateKeyboardUI();
        }
    	else if (character.equals("ENTER")) 
    	{
        	handleEnterKeyPress();
        } 
    	else if (character.equals("DELETE")) 
        {
            handleBackspaceKeyPress();
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
            guessInput.requestFocus();
        }
    }
    
	private void updateLabelsUI() 
	{
		// update label inputs
		
		int currRow = newGame.getShadowData().getCurrentRowIndex();
		
		if (currRow >= 6) {
	        // Do not update UI if all guesses are used
	        return;
	    }		
		
		Label[] currLabels = newGame.getShadowData().getCurrentGame()[currRow];
		String currGuess = guessInput.getText().toUpperCase();
		
        for (int i = 0; i < currGuess.length(); i++) 
        {
        	if (i < currLabels.length) 
        	{
                currLabels[i].setText(String.valueOf(currGuess.charAt(i)));
                ColorStyle.MEDIUMGREY.applyBorder(currLabels[i]);
        	}
        }

        // Clear any labels past the input text length
        for (int j = currGuess.length(); j < currLabels.length; j++) 
        {
        	currLabels[j].setText("");
        	ColorStyle.GREY.applyBorder(currLabels[j]);
        }
    }
	
	public void updateKeyboardUI() 
	{
		for (Map.Entry<Character, ColorStyle> entry : newGame.getShadowData().getUsedLetters().entrySet()) 
		{
	        char key = entry.getKey();
	        ColorStyle style = entry.getValue();
	        updateKeyboardButton(key, style); 
	    }
    }
	
	private void updateKeyboardButton(char letter, ColorStyle style) 
	{
		// update and disabling buttons
	    Button button = keyboardButtons.get(letter);
	    if (button != null) 
	    {
	        style.applyBackground(button);
	        if (style == ColorStyle.GREY) 
	        {
	            button.setDisable(true);
	        }
	    }
	}

	
	
	private void showStatScreen() {
	    try 
	    {
	        // Load the statistics screen
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/StatsScreen.fxml"));
	        Parent root = loader.load();

	        // You could pass data to your StatScreen controller if needed
	        StatsController statController = loader.getController();
	        statController.initStats(newGame);

	        // Set up the stage
	        Stage statStage = new Stage();
	        statStage.setTitle("Statistics");
	        statStage.setScene(new Scene(root));
	        statStage.show();
	    }
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
	}


}
