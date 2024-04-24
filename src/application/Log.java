package application;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Log 
{
	// Tracks and logs game actions, can read in file to read progress and updates the file with every move.
	private File logFile;
	private GameSession currentSession;
	private List<GameSession> totalGames;
	
	public Log() 
	{
		// Default Constructor
		this.logFile = new File("currentSave.txt");
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
	}
	
	public Log(String filename)
	{		
		// Constructor
		this.logFile = new File(filename);
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
		this.totalGames = loadGames();
		if (!getLastSession().isFinished())
		{
			currentSession = getLastSession();
		}
		else
		{
			currentSession = new GameSession(totalGames.size() + 1);
		}
	}
	
	
	public GameSession getLastSession()
	{
		return totalGames.get(totalGames.size() - 1);
	}
	
	public GameSession getCurrentGame()
	{
		return currentSession;
	}
	
	public void updateSession(GameSession session, int gameNumber, boolean won, String targertWord, List<String> previousGuesses)
	{
		// Use for update currentSession
		session.setGameNumber(gameNumber);
		session.setWon(won);
		session.setTargetWord(targertWord);
		session.setPreviousGuesses(previousGuesses);
	}
	
	public void saveGame(GameSession session) 
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


	public List<GameSession> loadGames() 
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
