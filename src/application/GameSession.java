package application;

import java.util.ArrayList;
import java.util.List;

public class GameSession 
{
    int gameNumber;
    boolean won;
    SelectWord currentWord;
    List<String> previousGuesses;
    
    public GameSession()
    {
    	this.gameNumber = 1;
        this.won = false;
        this.currentWord = new SelectWord(); // Initialize with a new SelectWord
        this.previousGuesses = new ArrayList<String>();
    }
    
    public GameSession(int gameNumber) 
    {
        this.gameNumber = gameNumber;
        this.won = false;
        this.currentWord = new SelectWord(); // Initialize with a new SelectWord
        this.previousGuesses = new ArrayList<String>();
    }

    public GameSession(int gameNumber, boolean won, SelectWord targetWord, List<String> previousGuesses) 
    {
        this.gameNumber = gameNumber;
        this.won = won;
        this.currentWord = targetWord; // Expecting a SelectWord object
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
    
    public SelectWord getCurrentWord()
    {
    	return currentWord;
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
    
    public void setCurrentWord(SelectWord sw)
    {
    	this.currentWord = sw;
    }
    
    public void setPreviousGuesses(List<String> l)
    {
    	this.previousGuesses = l;
    }
    
    public boolean guessInPreviousGuesses(String g)
    {
    	return previousGuesses.contains(g);
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
    	String s1 = gameNumber + "\n" + won + "\n" + currentWord + "\n";
    	String s2 = "";
    	
    	for (String s : previousGuesses)
    	{
    		s2 += s + " ";
    	}
        return s1 + s2;
    }
}

