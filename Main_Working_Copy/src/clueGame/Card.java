// Authors: Caleb Pan, Ethan Perry

package clueGame;

import java.util.ArrayList;
import java.util.Queue;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card(String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
	}
	
	
	// to be implemeted
	public boolean equals() {
		return false;
	}
	
	public String getName() {
		return cardName;
	}
	
	public CardType getType() {
		return type;
	}
}
