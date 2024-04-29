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
		String resourcePath = "src/resources/" + fileName;
		File f = new File(resourcePath);
		if (!f.exists())
		{
			try
			{
				f.createNewFile();
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
		clearFileContent(logFile);
		logFile = null;
		currentSession = null;
		totalGames.clear();
	}
	
	public void clearFileContent(File logFile) {
	    try (FileWriter fw = new FileWriter(logFile, false)) 
	    {
	        // By not writing anything to the file, it effectively clears it.
	    } 
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
	}
	
	public boolean isEmpty()
	{
		return totalGames.size() == 0;

	}
	
	public void reset()
	{
		removeSession(getLastSession());
		setCurrentSession(newSession());
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
	
	public GameSession newSession()
	{
		GameSession newSession = new GameSession(totalGames.size() + 1);
		currentSession = newSession;
		totalGames.add(newSession);
		return newSession;
	}
	
	public void logSession(int gameNum, boolean won, String guessWord, String targetWord)
	{
		// Use for update currentSession		
		List<String> newGuesses = new ArrayList<>(currentSession.getPreviousGuesses());
        if (!newGuesses.contains(guessWord))
        {
        	newGuesses.add(guessWord);
        }
        currentSession.setWon(won);
        currentSession.setPreviousGuesses(newGuesses);
	    currentSession.setCurrentWord(new SelectWord(targetWord));
	}
	
	public void saveGame(GameSession s, File f) 
	{
		// Save games
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, true))) 
        {
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
	            
	            GameSession s = new GameSession(gameNumber, won, new SelectWord(targetWord), previousGuesses);
	            totalGames.add(s);
	            System.out.println("Session: " + s);
	        }
	        
	        for (int i = 0; i < totalGames.size(); i++) {
	            if (i < totalGames.size() - 1) {
	                saveGame(totalGames.get(i), logFile);
	            }
	        }
	        
	        if (!totalGames.isEmpty() && getLastSession().isFinished()) 
	        {
	            currentSession = newSession(); 
	        }
	        else
	        {
	            currentSession = getLastSession(); 
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
