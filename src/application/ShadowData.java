package application;

import java.util.List;
import javafx.scene.control.Label;

public class ShadowData 
{
	// Keep track of current game
	private Label[][] currentGame;
	
	public ShadowData()
	{
		Label[] row0 = {box00, box01, box02, box03, box04};
		Label[] row1 = {box10, box11, box12, box13, box14};
		Label[] row2 = {box00, box01, box02, box03, box24};
		Label[] row3 = {box00, box01, box02, box03, box34};
		Label[] row4 = {box00, box01, box02, box03, box44};
		Label[] row5 = {box00, box01, box02, box03, box54};
		currentGame = {Label[] row1 = {box00, box01, box02, box03, box04};}
	}
	
}
