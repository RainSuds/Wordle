package application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Label;

public class ShadowData 
{
	// Keep track of current game
	private int currentRowIndex;
	private Map<Character, ColorStyle> usedLetters;
	private Label[][] currentGameTable;
	
	public ShadowData()
	{
		// Constructor
		createNewGame();
	}
	
	public ShadowData(Label[][] cg)
	{
		this.currentRowIndex = 0;
		setUsedLetters(new HashMap<Character, ColorStyle>());
		this.currentGameTable = cg;
	}
	
	public ShadowData(int cr, Label[][] cg)
	{
		setCurrentRowIndex(cr);
		setUsedLetters(new HashMap<Character, ColorStyle>());
		setCurrentGame(cg);
	}
	
	public ShadowData(int cr, Map<Character, ColorStyle> ul, Label[][] cg)
	{
		setCurrentRowIndex(cr);
		setUsedLetters(ul);
		setCurrentGame(cg);
	}
	
	public void reset() 
	{
        createNewGame();
    }
	
	public void clear()
	{
		for (int rowIndex = 0; rowIndex < currentGameTable.length; rowIndex++) 
		{
	        Label[] currentRow = currentGameTable[rowIndex];
	        clearRowStyles(currentRow);
	    }
		currentRowIndex = 0;
		usedLetters = null;
		currentGameTable = null;
	}
	
	private void clearRowStyles(Label[] row) 
	{
	    for (Label label : row) 
	    {
	        label.setText("");
	        label.setStyle("-fx-background-color: none;");  // Reset styles; adjust if you have a default style to apply
	    }
	}
	
	public boolean isEmpty()
	{
		return currentGameTable.length == 0;
	}

	public int getCurrentRowIndex() 
	{
		return currentRowIndex;
	}
	
	public Map<Character, ColorStyle> getUsedLetters() {
		return usedLetters;
	}

	
	public Label[][] getCurrentGame() 
	{
		return currentGameTable;
	}
	
	public void setCurrentRowIndex(int c) 
	{
		this.currentRowIndex = c;
	}
	
	public void setUsedLetters(Map<Character, ColorStyle> usedLetters) {
		this.usedLetters = usedLetters;
	}

	public void setCurrentGame(Label[][] cg) 
	{
		this.currentGameTable = cg;
	}
	
	public void addUsedLetter(char c, ColorStyle cl)
	{
		usedLetters.put(c, cl);
	}

	public void incrementCurrentRowIndex() 
	{
		currentRowIndex++;
	}
	
	private void refreshLabels(Label[] labels) 
	{
	    for (Label label : labels) {
	        label.applyCss();
	        label.layout(); // Force layout update
	    }
	}
	
	public void createNewGame()
	{
		setCurrentRowIndex(0);
		setUsedLetters(new HashMap<Character, ColorStyle>());
        int rows = 6;
        int columns = 5;
        currentGameTable = new Label[rows][columns];

        // Initialize each Label in the array
        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < columns; j++) 
            {
            	currentGameTable[i][j] = new Label();
                currentGameTable[i][j].setStyle("");
            }
        }
	}
	
	public void loadGame(GameSession g)
	{
		if (!isEmpty())
		{
			clear();
		}
		
		reset();
		List<String> previousGuesses = g.getPreviousGuesses();
        String targetWord = g.getTargetWord().getTargetWord();

        for (String guess : previousGuesses) 
        {
            if (currentRowIndex < currentGameTable.length) 
            {
                updateGameState(guess, targetWord, currentGameTable[currentRowIndex]);
                currentRowIndex++;
            }
        }
	}
	
	public void updateGameState(String guessWord, String targetWord, Label[] currentRow) 
	{
        Map<Character, Integer> letterFrequency = new HashMap<>();
        
        for (char c : targetWord.toCharArray()) 
        {
            letterFrequency.put(c, letterFrequency.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < targetWord.length(); i++) 
        {
            char letter = guessWord.charAt(i);
            Label label = currentRow[i];
            label.setText(String.valueOf(letter).toUpperCase());

            if (letter == targetWord.charAt(i)) 
            {
                addUsedLetter(letter, ColorStyle.GREEN);
                ColorStyle.GREEN.applyStyle(label);
                letterFrequency.put(letter, letterFrequency.get(letter) - 1);
            }
            else if (letterFrequency.getOrDefault(letter, 0) > 0) 
            {
                addUsedLetter(letter, ColorStyle.YELLOW);
                ColorStyle.YELLOW.applyStyle(label);
                letterFrequency.put(letter, letterFrequency.get(letter) - 1);
            } 
            else 
            {
                ColorStyle.GREY.applyStyle(label);
                addUsedLetter(letter, ColorStyle.GREY);
            }
        }
        refreshLabels(currentRow);
    }
	
}
