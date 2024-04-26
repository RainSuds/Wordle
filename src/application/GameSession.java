package application;

import java.util.ArrayList;
import java.util.List;

public class GameSession 
{
    int gameNumber;
    boolean won;
    SelectWord targetWord;
    List<String> previousGuesses;
    
    public GameSession()
    {
    	this.gameNumber = 0;
        this.won = false;
        this.targetWord = new SelectWord(); // Initialize with a new SelectWord
        this.previousGuesses = new ArrayList<String>();
    }
    
    public GameSession(int gameNumber) 
    {
        this.gameNumber = gameNumber;
        this.won = false;
        this.targetWord = new SelectWord(); // Initialize with a new SelectWord
        this.previousGuesses = new ArrayList<String>();
    }

    public GameSession(int gameNumber, boolean won, SelectWord targetWord, List<String> previousGuesses) 
    {
        this.gameNumber = gameNumber;
        this.won = won;
        this.targetWord = targetWord; // Expecting a SelectWord object
        this.previousGuesses = previousGuesses;
    }
    
    public int getGameNumber()
    {
    	return gameNumber;
    }
    
    public boolean getWon()
    {
    	return won;
    }
    
    public SelectWord getTargetWord()
    {
    	return targetWord;
    }
    
    public List<String> getPreviousGuesses()
    {
    	return previousGuesses;
    }
    
    public void setGameNumber(int n)
    {
    	this.gameNumber = n;
    }

    public void setWon(boolean b)
    {
    	this.won = b;
    }
    
    public void setTargetWord(SelectWord sw)
    {
    	this.targetWord = sw;
    }
    
    public void setPreviousGuesses(List<String> l)
    {
    	this.previousGuesses = l;
    }
    
    
    public boolean isFinished() 
    {
    	// check if the current session is finished
    	if (won)
    	{
    		return true;
    	}
    	else
    	{
    		return previousGuesses.size() == 6;
    	}
		
    }


    @Override
    public String toString() {
        return "GameSession{" +
               "gameNumber=" + gameNumber +
               ", won=" + won +
               ", targetWord='" + targetWord + '\'' +
               ", previousGuesses=" + previousGuesses +
               '}';
    }
}

