package application;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Log 
{
	// Tracks and logs game actions, can read in file to read progress and updates the file with every move.
	private GameSession currentSession;
	private List<GameSession> totalGames;
	
	public Log() 
	{
		// Default Constructor
		File logFile = new File("currentSave.txt");
		if (!logFile.exists())
		{
			try
			{
				logFile.createNewFile();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		this.currentSession = new GameSession();
		this.totalGames = new ArrayList<>();
		totalGames.add(currentSession);
	}
	
	public Log(String filename)
	{		
		// Constructor
		File logFile = new File(filename);
		if (!logFile.exists())
		{
			try
			{
				logFile.createNewFile();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		this.totalGames = loadGames(logFile);
		if (!getLastSession().isFinished())
		{
			currentSession = getLastSession();
		}
		else
		{
			currentSession = new GameSession(totalGames.size() + 1);
		}
	}
	
	
	public GameSession getCurrentSession()
	{
		return currentSession;
	}
	
	public List<GameSession> getTotalGames()
	{
		return totalGames;
	}
	
	public GameSession getLastSession()
	{
		return totalGames.get(totalGames.size() - 1);
	}
	
	public GameSession newSession()
	{
		GameSession newSession = new GameSession(totalGames.size() + 1);
		totalGames.add(currentSession);
		return newSession;
	}
	
	public void updateCurrentSession(int gameNum, String guessWord, String targertWord)
	{
		// Use for update currentSession
		currentSession.setGameNumber(gameNum);
		currentSession.setTargetWord(targertWord);
		currentSession.getPreviousGuesses().add(guessWord);
	}
	
	public void logCompletedSession(int gameNum, boolean won, String guessWord, String targertWord)
	{
		// Use for update currentSession
		updateCurrentSession(gameNum, guessWord, targertWord);
		currentSession.setWon(won);
		currentSession = newSession();
	}
	
	public void saveGame(GameSession session, File logFile) 
	{
		// Save games
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) 
        {
            writer.write(session.gameNumber + "\n");
            writer.write((session.won ? "1" : "0") + "\n");
            writer.write(session.targetWord + "\n");
            writer.write(session.toString() + "\n");
        } 
        catch (IOException e) 
        {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }


	public List<GameSession> loadGames(File logFile)
	{
		// Create a List of GameSessions, read in files and create separate game sessions for each game
        List<GameSession> sessions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) 
        {
            String line;
            while ((line = reader.readLine()) != null) {
                int gameNumber = Integer.parseInt(line);
                boolean won = reader.readLine().equals("1");
                String targetWord = reader.readLine();
                List<String> previousGuesses = new ArrayList<>(Arrays.asList(reader.readLine().split(" ")));
                sessions.add(new GameSession(gameNumber, won, targetWord, previousGuesses));
            }
        } 
        catch (IOException e) 
        {
            System.err.println("Error reading from log file: " + e.getMessage());
        }
        return sessions;
    }
	
	
}
