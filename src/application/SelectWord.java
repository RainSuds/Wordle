package application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelectWord 
{
	// Handles the selection of a random word for the game by reading in a txt file.
	private String targetWord;
	private List<String> wordList;
	
	public SelectWord()
	{
		// Constructor
		this.wordList = loadWordsFromResource("wordList.txt");
        this.targetWord = selectRandomWord(); // Selects a random word after list is populated.
	}

	public String getTargetWord() 
	{
		return targetWord;
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
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) 
        {
            throw new RuntimeException("Resource file not found: " + resourcePath);
        }
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
            	wordList.add(line.trim());
            }
        } 
        catch (IOException e) 
        {
        	e.printStackTrace();
            throw new RuntimeException("Failed to read the word list", e);
        }
		return wordList;
	}
	
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
}
