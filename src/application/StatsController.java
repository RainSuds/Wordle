package application;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class StatsController 
{

	@FXML private Label gamesPlayed, winRate, currentStreak, maxStreak;
    @FXML private Label numWin1, numWin2, numWin3, numWin4, numWin5, numWin6;
    @FXML private Rectangle progress1, progress2, progress3, progress4, progress5, progress6;
    private GameBoard game;
    private Stats stats;
    
    public void initialize() 
	{	
    	game = new GameBoard();
    	initStats(game);
	}
    
    public void initStats(GameBoard gb) 
    {
    	setStats(gb.getStats());
    	stats.updateCurrentStats(gb.getLog());
    	updateScreenUI();
	}
    
    public void setStats(Stats stats) 
    {
        this.stats = stats;
    }
    
    @FXML
    private void closeScreen() 
    {
        Stage stage = (Stage) numWin1.getScene().getWindow();
        stage.close();
    }
    
    private void updateScreenUI()
    {
    	// change all the labels: gamesPlayed, winRate, currentStreak, maxStreak.
    	// update all the win distribution bar length and number
    	
    	gamesPlayed.setText(String.valueOf(stats.getGamesPlayed()));
    	winRate.setText(String.format("%.0f", stats.getWinRate()));
    	currentStreak.setText(String.valueOf(stats.getCurrentStreak()));
    	maxStreak.setText(String.valueOf(stats.getMaxStreak()));

        // Update labels and progress bars
        Label[] winLabels = { numWin1, numWin2, numWin3, numWin4, numWin5, numWin6 };
        Rectangle[] progressBars = { progress1, progress2, progress3, progress4, progress5, progress6 };
        Map<Integer, Integer> distribution = stats.getGuessesDistribution();

        int maxGuesses = distribution.values().stream().max(Integer::compare).orElse(1);

        for (int i = 1; i <= winLabels.length; i++) {
            int wins = distribution.getOrDefault(i, 0);
            winLabels[i - 1].setText(String.valueOf(wins));
            if (wins > 0) {
                double progressWidth = (200.0 * wins) / maxGuesses; // Assumes max width of 200 for the bar
                progressBars[i - 1].setWidth(progressWidth);
            }
        }
    }
    
}
