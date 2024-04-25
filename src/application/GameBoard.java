package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameBoard 
{
	// Track progress of the game and main implementation
	private Log log;
    private SelectWord selectWord;
    private ShadowData shadowData;
    private Stats stats;
	
	public GameBoard()
	{
		// Initialize components
        this.setLog(new Log());
        this.selectWord = new SelectWord();
        this.shadowData = new ShadowData();
        this.stats = new Stats();
	}

	public GameBoard(Log l, SelectWord sw, ShadowData sd, Stats s)
	{
		// Initialize components
        this.setLog(l);
        this.selectWord = sw;
        this.shadowData = sd;
        this.stats = s;
	}
	
	public Log getLog() 
	{
		return log;
	}

	public void setLog(Log log) 
	{
		this.log = log;
	}
	
	public void checkGuesses(TextField guessingWord)
	{
		// Check the input
		Label[][] currentGame = shadowData.getCurrentGame();
		Label[] currentRow = currentGame[shadowData.getCurrentRow()];
		String correctWord = selectWord.getTargetWord();
		String guess = guessingWord.getText().toUpperCase();
		
		for (int i = 0; i < correctWord.length(); i++)
		{
			String letter = guess.substring(i, i + 1);
			if (letter.equals(correctWord.substring(i, i + 1)))
			{
				currentRow[i].setText(letter);
				currentRow[i].setStyle("-fx-background-color: #538D4E;");
			}
			else if (correctWord.indexOf(letter) > -1)
			{
				currentRow[i].setText(letter);
				currentRow[i].setStyle("-fx-background-color: #B59F3B;");
			}
			else
			{
				currentRow[i].setText(letter);
				currentRow[i].setStyle("-fx-background-color: #3A3A3C;");
			}
		}
	}
	
	public void beginGame()
	{
		// Start/continue a game instance
	}

}
