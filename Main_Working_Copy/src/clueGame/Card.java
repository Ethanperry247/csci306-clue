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
	
	public void createDeck(Queue<Card> rooms, Queue<Card> people, Queue<Card> weapons) {	// cards will be split into three queues (decks) for each card type
		
		// 9 room cards
		rooms.add(new Card("Greenhouse", CardType.ROOM));
		rooms.add(new Card("Dungeon", CardType.ROOM));
		rooms.add(new Card("Studio", CardType.ROOM));
		rooms.add(new Card("Cellar", CardType.ROOM));
		rooms.add(new Card("Atrium", CardType.ROOM));
		rooms.add(new Card("Veranda", CardType.ROOM));
		rooms.add(new Card("Lounge", CardType.ROOM));
		rooms.add(new Card("Parlor", CardType.ROOM));
		rooms.add(new Card("Office", CardType.ROOM));
		
		// 6 people cards
		people.add(new Card("Miss Scarlett", CardType.PERSON));
		people.add(new Card("Rev. Green", CardType.PERSON));
		people.add(new Card("Colonel Mustard", CardType.PERSON));
		people.add(new Card("Professor Plum", CardType.PERSON));
		people.add(new Card("Mrs. Peacock", CardType.PERSON));
		people.add(new Card("Mrs. White", CardType.PERSON));
		
		// 6 weapon cards
		weapons.add(new Card("Candlestick", CardType.WEAPON));
		weapons.add(new Card("Dagger", CardType.WEAPON));
		weapons.add(new Card("Pipe", CardType.WEAPON));
		weapons.add(new Card("Revolver", CardType.WEAPON));
		weapons.add(new Card("Rope", CardType.WEAPON));
		weapons.add(new Card("Wrench", CardType.WEAPON));
		
	}
	
	public void dealCards(Queue<Card> rooms, Queue<Card> people, Queue<Card> weapons, ArrayList<Player> players) {	// another parameter is an array list of players in the game
		
		while (!rooms.isEmpty() || !people.isEmpty() || !weapons.isEmpty()) {	// will continue looping as long there is a non-empty deck, therefore all are cards dealt
			
			for (int i = 0; i < players.size(); i++) {	// iterates through players in the game
				
				// since each player gets dealt one card of each type at each iteration, the amount of cards each player has is roughly equal
				// since the cards are stored in queues, the cards will be removed as we deal them so no card will be dealt twice
				// each if statement will deal a card from one of the three decks if they have any cards left
				if (!rooms.isEmpty()) {
					players.get(i).getCards().add(rooms.remove());
				}
				
				if (!people.isEmpty()) {
					players.get(i).getCards().add(people.remove());
				}
				
				if (!weapons.isEmpty()) {
					players.get(i).getCards().add(weapons.remove());
				}
			}
		}
		
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
