package application;

import javafx.scene.control.Label;

public class ShadowData 
{
	// Keep track of current game
	private int currentRow;
	private char[] usedChar;
	private Label[][] currentGame;
	
	public ShadowData()
	{
		// Define dimensions
		setCurrentRow(0);
        int rows = 6;
        int columns = 5;
        currentGame = new Label[rows][columns];

        // Initialize each Label in the array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                currentGame[i][j] = new Label();
            }
        }
	}
	
	public ShadowData(int cr, Label[][] cg)
	{
		this.setCurrentRow(cr);
		this.currentGame = cg;
	}

	public int getCurrentRow() 
	{
		return currentRow;
	}
	
	public char[] getUsedChar() {
		return usedChar;
	}
	
	public Label[][] getCurrentGame() 
	{
		return currentGame;
	}
	
	public void setCurrentRow(int currentRow) 
	{
		this.currentRow = currentRow;
	}
	
	public void setUsedChar(char[] usedChar) {
		this.usedChar = usedChar;
	}

	public void setCurrentGame(Label[][] cg) 
	{
		this.currentGame = cg;
	}

	public void incrementCurrentRow() 
	{
		currentRow++;
	}	
}
