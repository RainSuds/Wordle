package application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Button;
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
		setCurrentRowIndex(0);
		setUsedLetters(new HashMap<Character, ColorStyle>());
        int rows = 6;
        int columns = 5;
        currentGameTable = new Label[rows][columns];

        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < columns; j++) 
            {
            	currentGameTable[i][j] = new Label();
            }
        }
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
	
	private void refreshLabels(Label[] r) 
	{
	    for (Label label : r) {
	        label.applyCss();
	        label.layout(); // Force layout update
	    }
	}
	
	public void createNewGame(Label[][] t)
	{
		setCurrentRowIndex(0);
		setUsedLetters(new HashMap<Character, ColorStyle>());
        currentGameTable = t;

        // Initialize each Label in the array
        if (currentGameTable != null)
        {
        	for (Label[] r : t) 
            {
        		refreshLabels(r);
            }
        }
	}
	
	public void loadGame(GameSession g, Label[][] t)
	{
		if (!isEmpty())
		{
			clear();
		}
		
		createNewGame(t);
		List<String> previousGuesses = g.getPreviousGuesses();
        String targetWord = g.getCurrentWord().getTargetWord();
        int index = 0;
        
        for (String guess : previousGuesses) 
        {
        	if (index < t.length) 
        	{  
        		// Using local variable to avoid side effects on global state
                updateGameState(guess, targetWord, t[index]);
                index++;
            } 
        	else 
        	{
                // Handle or log error: more guesses than rows available
                System.err.println("Error: More previous guesses than rows available in the game table.");
                break;
            }
        }
	}
	
	public void updateGameState(String guessWord, String targetWord, Label[] currentRow) 
	{
	    Map<Character, Integer> letterFrequency = calculateLetterFrequency(targetWord);
	    updateLabelsAndStyles(guessWord, targetWord, currentRow, letterFrequency);
	    refreshLabels(currentRow);
	}

	private Map<Character, Integer> calculateLetterFrequency(String word) 
	{
	    Map<Character, Integer> frequency = new HashMap<>();
	    for (char c : word.toCharArray()) {
	        frequency.put(c, frequency.getOrDefault(c, 0) + 1);
	    }
	    return frequency;
	}

	private void updateLabelsAndStyles(String guessWord, String targetWord, Label[] labels, Map<Character, Integer> frequency) 
	{
	    for (int i = 0; i < guessWord.length(); i++) 
	    {
	        char guess = guessWord.charAt(i);
	        Label label = labels[i];
	        label.setText(String.valueOf(guess).toUpperCase());

	        applyStylesBasedOnGuess(guess, i, targetWord, frequency, label);
	    }
	}

	private void applyStylesBasedOnGuess(char guess, int index, String targetWord, Map<Character, Integer> frequency, Label label) 
	{
	    if (guess == targetWord.charAt(index)) 
	    {
	        ColorStyle.GREEN.applyBackground(label);
	        frequency.put(guess, frequency.get(guess) - 1);
	        addUsedLetter(guess, ColorStyle.GREEN);
	    } 
	    else if (frequency.getOrDefault(guess, 0) > 0) 
	    {
	        ColorStyle.YELLOW.applyBackground(label);
	        frequency.put(guess, frequency.get(guess) - 1);
	        addUsedLetter(guess, ColorStyle.YELLOW);
	    } 
	    else 
	    {
	        ColorStyle.GREY.applyBackground(label);
	        addUsedLetter(guess, ColorStyle.GREY);
	    }
	}
}
