// Authors: Caleb Pan, Ethan Perry

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Player {
	
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private Set<BoardCell> targets;			// Records the current player's targets.
	protected ArrayList<Card> playerCards;	// note: each player has 3 cards
	protected ArrayList<Card> weaponsSeen;	// The weapons observed from disproved suggestions.
	protected ArrayList<Card> roomsSeen;	// The rooms observed from disproved suggestions.
	protected ArrayList<Card> playersSeen;	// The players observed from disproved suggestions.
	protected BoardCell justVisited;		// board cell to represent the last room they just visited
	protected boolean justDisproven; 		// boolean to keep track if player suggestion was disproved last round.
	protected Solution savedSuggestion;		// a suggestion that is preserved to become an accusation the next round
	
	public Player(String playerName, int row, int column, String color) {
		this.row = row;
		this.column = column;
		this.playerName = playerName;
		this.color = convertColor(color); // The colors passed in must be in all caps (i.e. RED, GREEN, etc.).
		this.playerCards = new ArrayList<Card>();
		this.weaponsSeen = new ArrayList<Card>();
		this.roomsSeen = new ArrayList<Card>();
		this.playersSeen = new ArrayList<Card>();
		this.justVisited = null;
		this.justDisproven = true;
		this.savedSuggestion = null;
		
		for (Card card : playerCards) {	// adds cards in hand to seen decks
			updateSeen(card);
		}
		
	}
	
	public Player(String playerName, int row, int column, String color, ArrayList<Card> weaponsSeen, ArrayList<Card> playersSeen) {
		this.row = row;
		this.column = column;
		this.playerName = playerName;
		this.color = convertColor(color); // The colors passed in must be in all caps (i.e. RED, GREEN, etc.).
		this.playerCards = new ArrayList<Card>();
		this.weaponsSeen = weaponsSeen;
		this.roomsSeen = new ArrayList<Card>();
		this.playersSeen = playersSeen;
		this.justVisited = null;
		this.justDisproven = true;
		this.savedSuggestion = null;
		
		for (Card card : playerCards) {	// adds cards in hand to seen decks
			updateSeen(card);
		}
	}
	
	public void addCardHand() {
		for (Card card : playerCards) {
			updateSeen(card);
		}
	}
	
	// the player has seen a new card, the card will be added into the correct seen deck based on card type
	public void updateSeen(Card seen) {
		CardType type = seen.getType();
		
		if (type == CardType.ROOM) {
			roomsSeen.add(seen);
		} else if (type == CardType.WEAPON) {
			weaponsSeen.add(seen);
		} else {
			playersSeen.add(seen);
		}
	}

	public boolean getJustDisproven() {
		return justDisproven;
	}
	
	public void saveSuggestion(Solution suggestion) {
		this.savedSuggestion = suggestion;
	}
	
	public void updateDisproven(boolean disproved) {
		this.justDisproven = disproved;
	}
	
	public void updateTargets(Set<BoardCell> targets) {
		this.targets = new HashSet<BoardCell>();
		this.targets = targets;
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public void updateJustVisited(BoardCell update) {
		this.justVisited = update;
	}
	
	public String getName() {
		return playerName;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return column;
	}
	
	public void setRow(int r) {
		row = r;
	}
	
	public void setCol(int c) {
		column = c;
	}
	
	public ArrayList<Card> getCards() {
		return playerCards;
	}
	
	public ArrayList<Card> getWeaponsSeen() {
		return weaponsSeen;
	}
	
	public ArrayList<Card> getRoomsSeen() {
		return roomsSeen;
	}
	
	public ArrayList<Card> getPlayersSeen() {
		return playersSeen;
	}
	
	public void addCard(Card card) {
		playerCards.add(card);
	}
	
	@Override
	public String toString() {
		return "[Player: " + playerName + "]";
	}
	
	// Abstract method which is handled differently in human and computer player.
	public abstract void makeMove();

	// from CluePlayer.pdf
	// Be sure to trim the color, we don't want spaces around the name
	public Color convertColor(String strColor) {
		Color color; 
		try {     // We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null); 
		} catch (Exception e) {  
			color = null; // Not defined  
		}
		return color;
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		
		List<Card> matchingCards = new ArrayList<Card>();
		
		// If the suggestion contains any of the same cards as contained in the players hand,
		// return that card. Otherwise return a dummy card called 'x.'
		for (Card card: playerCards) {
			if (card.getName().equals(suggestion.getPerson()) ||
				card.getName().equals(suggestion.getWeapon()) ||
				card.getName().equals(suggestion.getRoom())) {
				matchingCards.add(card);
			}
		}
		
		if (matchingCards.size() == 0) { 		// If no cards are matching, return zero.
			return null;
		} else if (matchingCards.size() == 1) { // If one card matches, return that card.
			return matchingCards.get(0);
		} else { 								// Otherwise, if multiple match it, return a random selection.
			return matchingCards.get((int)(Math.random()*matchingCards.size()));
		}
	}
	
	// draws players onto board
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(column*Board.CELL_LENGTH, row*Board.CELL_LENGTH, Board.CELL_LENGTH, Board.CELL_LENGTH);
		g.setColor(Color.BLACK);
		g.drawOval(column*Board.CELL_LENGTH, row*Board.CELL_LENGTH, Board.CELL_LENGTH, Board.CELL_LENGTH);
	}
	
}
