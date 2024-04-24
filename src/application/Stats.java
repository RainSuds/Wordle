package application;

import java.util.Map;
import java.util.HashMap;

public class Stats 
{
	// Tracks and displays statistics of the game
	
	private int totalGame;
	private float winRate;
	private int currentStreak;
	private int maxStreak;
	private Map<Integer, Integer> guessesDistribution;
	
	public Stats()
	{
		setTotalGame(0);
		setWinRate(0);
		setCurrentStreak(0);
		setMaxStreak(0);
		setGuessesDistribution(new HashMap<>());
	}
	
	public Stats(int n, float r, int cs, int ms, Map<Integer, Integer> hm)
	{
		this.setTotalGame(n);
		this.setWinRate(r);
		this.setCurrentStreak(cs);
		this.setMaxStreak(ms);
		this.setGuessesDistribution(hm);
	}

	public int getTotalGame() 
	{
		return totalGame;
	}

	public float getWinRate() 
	{
		return winRate;
	}
	
	public int getCurrentStreak() 
	{
		return currentStreak;
	}
	
	public int getMaxStreak() 
	{
		return maxStreak;
	}
	
	public Map<Integer, Integer> getGuessesDistribution() 
	{
		return guessesDistribution;
	}

	public void setTotalGame(int totalGame) 
	{
		this.totalGame = totalGame;
	}
	
	public void setWinRate(float winRate) 
	{
		this.winRate = winRate;
	}
	
	public void setCurrentStreak(int currentStreak) 
	{
		this.currentStreak = currentStreak;
	}

	public void setMaxStreak(int maxStreak) 
	{
		this.maxStreak = maxStreak;
	}

	public void setGuessesDistribution(Map<Integer, Integer> guessesDistribution) 
	{
		this.guessesDistribution = guessesDistribution;
	}
	
	
}
