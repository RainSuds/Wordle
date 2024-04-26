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
		resetStats();
	}
	
	public Stats(int n, float r, int cs, int ms, Map<Integer, Integer> hm)
	{
		this.setTotalGame(n);
		this.setWinRate(r);
		this.setCurrentStreak(cs);
		this.setMaxStreak(ms);
		this.setGuessesDistribution(hm);
	}
	
	private void resetStats() {
        setTotalGame(0);
        setWinRate(0.0f);
        setCurrentStreak(0);
        setMaxStreak(0);
        setGuessesDistribution(new HashMap<>());
        for (int i = 1; i <= 6; i++) {
            guessesDistribution.put(i, 0);
        }
    }
	
	public void clear() 
	{
		resetStats();		
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
	
	public void updateCurrentStats(Log l)
	{
		// update the private members with log
		totalGame = l.getTotalGames().size();
		
		if (totalGame == 0)
		{
			return;
		}
		
		int sumWin = 0;
		int curWin = 0;
		int maxWin = 0;
		
		for (int i = 0; i < totalGame; i++)
		{
			GameSession currSession = l.getTotalGames().get(i);
			if (currSession.getWon())
			{
				sumWin++;
				curWin++;
				if (curWin > maxWin)
				{
					// Update max win streak
					maxWin = curWin;
				}
				
				int guessCount = currSession.getPreviousGuesses().size();
				guessesDistribution.put(guessCount, guessesDistribution.getOrDefault(guessCount, 0) + 1);
			}
			else
			{
				// Not win
				curWin = 0;
				
			}
		}
		winRate = (float) sumWin / (float) totalGame;
		currentStreak = curWin;
		maxStreak = maxWin;
	}
	
}
