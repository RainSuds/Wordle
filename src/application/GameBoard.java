package application;

import javafx.geometry.Pos;
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

        // Load or initialize necessary data
        this.selectWord.loadWords();
	}

	public VBox buildGameBoard() {
        // Create the main game board layout
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        // Create the grid that represents the Wordle grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setHgap(5);

        // Populate the grid with initial cells (e.g., empty cells at the beginning)
        for (int i = 0; i < 6; i++) { // assuming a 6x5 grid for Wordle (6 tries, 5 letters each)
            for (int j = 0; j < 5; j++) {
                Text cell = new Text("_");
                cell.getStyleClass().add("wordle-cell");
                grid.add(cell, j, i);
            }
        }

        // Add components to the layout
        layout.getChildren().add(grid);
        layout.getChildren().add(createStatsDisplay());

        return layout;
	}
	
	private VBox createStatsDisplay() {
        // Display for statistics
        VBox statsDisplay = new VBox(5);
        statsDisplay.setAlignment(Pos.CENTER);

        Text playTime = new Text("Total Play Time: " + stats.getTotalPlayTime());
        Text winRate = new Text("Win Rate: " + stats.getWinRate() + "%");
        Text streak = new Text("Current Streak: " + stats.getCurrentStreak());
        Text maxStreak = new Text("Max Streak: " + stats.getMaxStreak());

        statsDisplay.getChildren().addAll(playTime, winRate, streak, maxStreak);
        return statsDisplay;
    }

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

}
