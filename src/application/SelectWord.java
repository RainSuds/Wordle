package application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelectWord 
{
	// Handles the selection of a random word for the game by reading in a txt file.
	private int WORD_LENGTH = 5;
	private String targetWord;
	private List<String> wordList;
	
	public SelectWord()
	{
		// Constructor
		this.wordList = loadWordsFromResource("resources/wordList.txt");
		//this.wordList = loadWordsFromAbsolutePath("C:\\Users\\13198\\OneDrive\\Documents\\TAMU\\CSCE314\\Java\\Wordle\\src\\resources\\wordList.txt");
        this.targetWord = selectRandomWord(); // Selects a random word after list is populated.
	}

	public int getWORD_LENGTH() {
		return WORD_LENGTH;
	}
	
	public String getTargetWord() 
	{
		return targetWord;
	}
	
	public void setWORD_LENGTH(int w) {
		WORD_LENGTH = w;
	}

	public void setTargetWord(String targetWord) 
	{
		this.targetWord = targetWord;
	}
	
	public void setWordList(List<String> ls)
	{
		this.wordList = ls;
	}
	
	public List<String> loadWordsFromResource(String resourcePath)
	{
		// load file
		List<String> wordList = new ArrayList<>();
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(resourcePath);
        if (is == null) 
        {
            throw new RuntimeException("Resource file not found: " + resourcePath);
        }
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
	            String[] words = line.split("\\s+"); // "\\s+" matches 2+ whitespace
	            for (String word : words) 
	            {
	                if (!word.isEmpty()) 
	                {
	                    wordList.add(word.trim().toUpperCase());
	                }
	            }
            }
        } 
        catch (IOException e) 
        {
        	e.printStackTrace();
            throw new RuntimeException("Failed to read the word list", e);
        }
        
                
		return wordList;
	}
	
//	private List<String> loadWordsFromAbsolutePath(String filePath) {
//        try {
//            return Files.readAllLines(Paths.get(filePath));
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to read the word list from absolute path", e);
//        }
//    }
	
	public String selectRandomWord()
	{
		// Create a List of GameSessions, read in files and create separate game sessions for each game
        if (wordList.isEmpty()) 
        {
            return "";
        }
        Random random = new Random();
        int index = random.nextInt(wordList.size());
        return wordList.get(index);
	}
	
	public boolean wordInWordList(String i)
	{
		return wordList.contains(i);
	}
	
	public String wordListToString(List<String> wordList) {
	    if (wordList == null || wordList.isEmpty()) {
	        return "No words loaded.";
	    }
	    return String.join(", ", wordList);
	}
	
	@Override
    public String toString() {
        return wordListToString(wordList);
    }
}
