package application;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
		logFile = createNewFile("currentSave.txt");
		this.currentSession = new GameSession();
		this.totalGames = new ArrayList<>();
		totalGames.add(currentSession);
	}
	
	public Log(String fileName)
	{		
		// Constructor
		this.currentSession = loadGame(fileName);
	}
	
	public File createNewFile(String fileName)
	{
		File f = new File(fileName);
		if (!f.exists())
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
		return f;
	}
	
	
	public void clear()
	{
		logFile = null;
		currentSession = null;
		totalGames.clear();
	}
	
	public boolean isEmpty()
	{
		return totalGames.size() == 0;

	}
	public File getLogFile()
	{
		return logFile;
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
	
	public void setLogFile(File f)
	{
		this.logFile = f;
	}
	
	public void setCurrentSession(GameSession s) 
	{
		this.currentSession = s;
	}
	
	public void setTotalGames(List<GameSession> t) 
	{
		this.totalGames = t;		
	}
	
	public void removeSession(GameSession g)
	{
		Iterator<GameSession> iterator = totalGames.iterator();
	    while (iterator.hasNext()) 
	    {
	        GameSession currentSession = iterator.next();
	        if (currentSession.equals(g)) {
	            iterator.remove();
	        }
	    }
	}
	
	public void reset()
	{
		removeSession(getLastSession());
		setCurrentSession(newSession());
	}
	
	public void saveCurrentGame() 
	{
		// Save current games
		saveGame(currentSession, logFile);
    }
	
	public GameSession newSession()
	{
		GameSession newSession = new GameSession(totalGames.size() + 1);
		totalGames.add(currentSession);
		return newSession;
	}
	
	public void logSession(int gameNum, boolean won, String guessWord, String targetWord)
	{
		// Use for update currentSession
		currentSession.setGameNumber(gameNum);
		currentSession.setWon(won);
		currentSession.getPreviousGuesses().add(guessWord);
		currentSession.setTargetWord(new SelectWord(targetWord));
	}
	
	public void saveGame(GameSession s, File f) 
	{
		// Save games
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, true))) 
        {
            writer.write(s.getGameNumber() + "\n");
            writer.write((s.getWon() ? "1" : "0") + "\n");
            writer.write(s.getTargetWord().getTargetWord() + "\n");
            writer.write(s.toString() + "\n");
        } 
        catch (IOException e) 
        {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
	
	public GameSession loadGame(String fileName) 
	{
		// Load log files
	    totalGames = new ArrayList<>();
	    
	    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("resources/" + fileName);
	    if (is == null) {
	        throw new RuntimeException("Resource file not found: " + fileName);
	    }
	    
	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	int gameNumber = Integer.parseInt(line.trim()); // Game number
	            boolean won = reader.readLine().trim().equals("1"); // Win status
	            String targetWord = reader.readLine().trim(); // Target word
	            List<String> previousGuesses = Arrays.asList(reader.readLine().trim().split(" "));  // Previous guesses
	            
	            GameSession session = new GameSession(gameNumber, won, new SelectWord(targetWord), previousGuesses);
	            totalGames.add(session);
	            System.out.println(session);
	        }
	        if (!totalGames.isEmpty() && getLastSession().isFinished()) {
	            currentSession = newSession(); // Start new session if the last one is finished
	        } else {
	            currentSession = getLastSession(); // Continue with the last session
	        }
	    } 
	    catch (IOException e) 
	    {
	        System.err.println("Error reading from file: " + e.getMessage());
	    }
	    catch (NumberFormatException e) 
	    {
	        System.err.println("Error parsing integer from file: " + e.getMessage());
	    }
	    return currentSession;
	}
}
