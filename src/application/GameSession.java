package application;

import java.util.ArrayList;
import java.util.List;

public class GameSession 
{
    int gameNumber;
    boolean won;
    String targetWord;
    List<String> previousGuesses;
    
    public GameSession()
    {
    	this.gameNumber = 0;
        this.won = false;
        this.targetWord = "";
        this.previousGuesses = new ArrayList<String>();

    }
    
    public GameSession(int gameNumber) 
    {
        this.gameNumber = gameNumber;
        this.won = false;
        this.targetWord = "";
        this.previousGuesses = new ArrayList<String>();
    }

    public GameSession(int gameNumber, boolean won, String targetWord, List<String> previousGuesses) 
    {
        this.gameNumber = gameNumber;
        this.won = won;
        this.targetWord = targetWord;
        this.previousGuesses = previousGuesses;
    }
    
    public void setGameNumber(int n)
    {
    	this.gameNumber = n;
    }

    public void setWon(boolean b)
    {
    	this.won = b;
    }
    
    public void setTargetWord(String w)
    {
    	this.targetWord = w;
    }
    
    public void setPreviousGuesses(List<String> l)
    {
    	this.previousGuesses = l;
    }
    
    
    public boolean isFinished() 
    {
    	// check if the current session is finished
    	if (won == true)
    	{
    		return true;
    	}
    	else
    	{
    		if (previousGuesses.size() == 6)
    		{
    			return true;
    		}
    	}
		return false;
    }
    


    @Override
    public String toString() 
    {
        // Convert the previous guesses list to a space-separated string
        return String.join(" ", previousGuesses);
    }
}

