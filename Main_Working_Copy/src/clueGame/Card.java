package clueGame;

import java.util.HashSet;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card(String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
	}
	
	public void createDeck() {
		
		// 9 room cards
		Card Greenhouse = new Card("Greenhouse", CardType.ROOM);
		Card Dungeon = new Card("Dungeon", CardType.ROOM);
		Card Studio = new Card("Studio", CardType.ROOM);
		Card Cellar = new Card("Cellar", CardType.ROOM);
		Card Atrium = new Card("Atrium", CardType.ROOM);
		Card Veranda = new Card("Veranda", CardType.ROOM);
		Card Lounge = new Card("Lounge", CardType.ROOM);
		Card Parlor = new Card("Parlor", CardType.ROOM);
		Card Office = new Card("Office", CardType.ROOM);
		
		// 6 people cards
		Card Scarlett = new Card("Scarlett", CardType.PERSON);
		Card Green = new Card("Green", CardType.PERSON);
		Card Mustard = new Card("Mustard", CardType.PERSON);
		Card Plum = new Card("Plum", CardType.PERSON);
		Card Peacock = new Card("Peacock", CardType.PERSON);
		Card White = new Card("White", CardType.PERSON);
		
		// 6 weapon cards
		Card Candlestick = new Card("Candlestick", CardType.WEAPON);
		Card Dagger = new Card("Dagger", CardType.WEAPON);
		Card Pipe = new Card("Pipe", CardType.WEAPON);
		Card Revolver = new Card("Revolver", CardType.WEAPON);
		Card Rope = new Card("Rope", CardType.WEAPON);
		Card Wrench = new Card("Wrench", CardType.WEAPON);
		
	}
	
	// to be implemeted
	public boolean equals() {
		return false;
	}
}
