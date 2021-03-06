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
	
	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", type=" + type + "]";
	}

	@Override
	public boolean equals(Object o) {
		
		if (((Card)o).getName().equals(cardName) &&
			((Card)o).getType() == type) {
			return true;
		}
		
		return false;
	}
	
	public String getName() {
		return cardName;
	}
	
	public CardType getType() {
		return type;
	}
}
