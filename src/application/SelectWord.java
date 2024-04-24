package application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelectWord 
{
	// Handles the selection of a random word for the game by reading in a txt file.
	private File wordFile;
	private String targetWord;
	private List<String> wordList;
	
	public SelectWord()
	{
		// Constructor
		this.wordFile = new File("wordList.txt");
		this.wordList = setWordList(wordFile);
		this.targetWord = selectRandomWord();
	}

	public String getTargetWord() 
	{
		return targetWord;
	}

	public void setTargetWord(String targetWord) 
	{
		this.targetWord = targetWord;
	}
	
	public List<String> setWordList(File f)
	{
		// load file
		List<String> wordList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(wordFile))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
            	wordList.add(line.trim());
            }
        } 
        catch (IOException e) 
        {
            System.err.println("Error reading from log file: " + e.getMessage());
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
