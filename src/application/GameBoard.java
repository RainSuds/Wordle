package application;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameBoard 
{
	// Track progress of the game and main implementation
	private Log log;
    private SelectWord selectWord;
    private ShadowData shadowData;
    private Stats stats;
	
	public GameBoard()
	{
		// Initialize components
        this.setLog(new Log());
        this.selectWord = new SelectWord();
        this.shadowData = new ShadowData();
        this.setStats(new Stats());
	}

	public GameBoard(Log l, SelectWord sw, ShadowData sd, Stats s)
	{
		// Initialize components
        this.setLog(l);
        this.setSelectWord(sw);
        this.setShadowData(sd);
        this.setStats(s);
	}
	
	public Log getLog() 
	{
		return log;
	}
	
	public SelectWord getSelectWord() 
	{
		return selectWord;
	}
	
	public ShadowData getShadowData() 
	{
		return shadowData;
	}
	
	public Stats getStats() {
		return stats;
	}

	public void setLog(Log l) 
	{
		this.log = l;
	}
	
	public void setSelectWord(SelectWord s) 
	{
		this.selectWord = s;
	}
	
	public void setShadowData(ShadowData sd) 
	{
		this.shadowData = sd;
	}
	
	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public boolean isValidInput(String word)
	{
		return selectWord.wordInWordList(word) || word.length() == selectWord.getWORD_LENGTH();
	}
	
	public boolean checkGuesses(TextField guessingWord)
	{
		// Check the input
		String guess = guessingWord.getText().toUpperCase();
		
		Label[][] currentGame = shadowData.getCurrentGame();
		Label[] currentRow = currentGame[shadowData.getCurrentRow()];
		String correctWord = selectWord.getTargetWord();
		
		Map<Character, Integer> letterFrequency = new HashMap<>();
		for (char c : correctWord.toCharArray()) {
	        letterFrequency.put(c, letterFrequency.getOrDefault(c, 0) + 1);
	    }
		
		// First pass: check for correct positions
	    for (int i = 0; i < correctWord.length(); i++) {
	        char letter = guess.charAt(i);
	        Label label = currentRow[i];
	        label.setText(String.valueOf(letter).toUpperCase()); // Set text as uppercase
	        if (letter == correctWord.charAt(i)) {
	            label.setStyle("-fx-background-color: #538D4E;"); // Green for correct position
	            letterFrequency.put(letter, letterFrequency.get(letter) - 1); // Decrease count
	        }
	    }

	    // Second pass: check for correct letters in wrong positions
	    for (int i = 0; i < correctWord.length(); i++) {
	        char letter = guess.charAt(i);
	        Label label = currentRow[i];
	        if (letter != correctWord.charAt(i)) {
	            if (letterFrequency.getOrDefault(letter, 0) > 0) {
	                label.setStyle("-fx-background-color: #B59F3B;"); // Yellow for correct letter, wrong place
	                letterFrequency.put(letter, letterFrequency.get(letter) - 1); // Decrease count
	            } else {
	                label.setStyle("-fx-background-color: #3A3A3C;"); // Gray for incorrect letter
	            }
	        }
	    }
		
	    shadowData.incrementCurrentRow();
	    
		if (guess.equals(correctWord))
		{
			return true;
		}
		
		log.updateCurrentSession(log.getTotalGames().size(), guess, correctWord);

		
		return false;
	}
	
	public void saveGame() 
	{
		// TODO Auto-generated method stub
		
	}

	public void resetGameBoard() {
		// TODO Auto-generated method stub
		
	}

	public void loadFile(String fileName) {
		// TODO Auto-generated method stub
		
	}

}
