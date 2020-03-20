package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;

public class Player {
	
	private String playerName;
	private int row;
	private int column;
	private Color color;
	
	
	public Player(String playerName, int row, int column, Color color) {
		this.row = row;
		this.column = column;
		this.playerName = playerName;
		this.color = color;
	}
	
	
	// from CluePlayer.pdf
	// Be sure to trim the color, we don't want spaces around the name
	public Color convertColor(String strColor) {
		Color color; 
		try {     // We can use reflection to convert the string to a color
			Field field =Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null); 
		} catch (Exception e) {  
			color = null; // Not defined  
		}
		return color;
	}
	
	// to be implemented
	public Card disproveSuggestion(Solution suggestion) {
		Card x = new Card("x");
		return x;
	}
	
}
