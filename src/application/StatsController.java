package application;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StatsController {

    @FXML private Label totalGamesLabel;
    @FXML private Label winRateLabel;
    @FXML private Label currentStreakLabel;
    @FXML private Label maxStreakLabel;
    @FXML private BarChart<String, Number> distribution;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private Button CLOSE;

    private Stats Stats;

    @FXML
    public void initialize() 
    {
        initializeChart();
    }

    public void setStats(Stats data) 
    {
        this.Stats = data;
        updateUI();
    }

    private void updateUI() 
    {
        if (Stats != null) 
        {
            totalGamesLabel.setText(String.valueOf(Stats.getTotalGame()));
            winRateLabel.setText(String.format("%.2f%%", Stats.getWinRate() * 100));
            currentStreakLabel.setText(String.valueOf(Stats.getCurrentStreak()));
            maxStreakLabel.setText(String.valueOf(Stats.getMaxStreak()));
            
            updateGuessDistribution();
        }
    }

    private void initializeChart() 
    {
        distribution.setLegendVisible(false);
        xAxis.setLabel("Guess Count");
        yAxis.setLabel("Games");

        // Prepare the chart with default data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("1", 0));
        series.getData().add(new XYChart.Data<>("2", 0));
        series.getData().add(new XYChart.Data<>("3", 0));
        distribution.getData().add(series);
    }

    private void updateGuessDistribution() 
    {
        if (!distribution.getData().isEmpty()) 
        {
            XYChart.Series<String, Number> series = distribution.getData().get(0);
            series.getData().clear();
            Map<Integer, Integer> distribution = Stats.getGuessesDistribution();
            for (Map.Entry<Integer, Integer> entry : distribution.entrySet()) 
            {
                series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
            }
        }
    }

    @FXML
    private void closeScreen() 
    {
        Stage stage = (Stage) CLOSE.getScene().getWindow();
        stage.close();
    }

	
}
