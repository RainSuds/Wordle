package application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameBoard 
{
	// Track progress of the game and main implementation
	private Log log;
    private ShadowData shadowData;
    private Stats stats;
	
	public GameBoard()
	{
		// Initialize components
		createGameBoard();
	}

	public GameBoard(Log l, ShadowData sd, Stats s)
	{
		// Initialize components
        this.setLog(l);
        this.setShadowData(sd);
        this.setStats(s);
	}
	
	public void createGameBoard()
	{
		this.setLog(new Log());
        this.shadowData = new ShadowData();
        this.setStats(new Stats());
	}
	
	public Log getLog() 
	{
		return log;
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
	
	public void setShadowData(ShadowData sd) 
	{
		this.shadowData = sd;
	}
	
	public void setStats(Stats stats) {
		this.stats = stats;
	}
	
	public boolean isValidInput(String word)
	{
		return log.getCurrentSession().getCurrentWord().wordInWordList(word) && word.length() == log.getCurrentSession().getCurrentWord().getWORD_LENGTH();
	}

	public boolean checkGuesses(TextField guessingWord) 
	{
        String guess = guessingWord.getText().toUpperCase();
        Label[] currentRow = shadowData.getCurrentGame()[shadowData.getCurrentRowIndex()];
        String correctWord = log.getCurrentSession().getCurrentWord().getTargetWord();
        shadowData.updateGameState(guess, correctWord, currentRow);
        shadowData.incrementCurrentRowIndex();

        if (guess.equals(correctWord)) {
        	log.logSession(log.getTotalGames().size(), true, guess, correctWord);
            return true;
        }

        log.logSession(log.getTotalGames().size(), false, guess, correctWord);
        return false;
    }
	
	public void saveGame() 
	{
		log.saveGame(log.getCurrentSession(), log.getLogFile());
	}

	public void loadFile(String fileName, Label[][] t) 
	{
		try {
			shadowData.clear();
			log.clear();
		    stats.clear();
	        createGameBoard();

	        GameSession currentSession = log.loadGame(fileName);

	        if (currentSession != null) 
	        {
	            shadowData.loadGame(currentSession, t);
	            stats.updateCurrentStats(log);
	        } 
	        else 
	        {
	            throw new RuntimeException("Failed to load game session from file.");
	        }
	    } 
		catch (Exception e) 
		{
	        System.err.println("Error loading the game file: " + e.getMessage());
	    }
	}
	

}
