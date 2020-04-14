// Authors: Caleb Pan, Ethan Perry

package clueGame;

import java.awt.Graphics;
import java.io.File;
import java.util.Random;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {

	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	
	private Map<Character, String> legend;					  // stores room initial with the room name
	private Map<Character, ArrayList<String>> nameLocations;  // stores room initial with the cell location where room name will be drawn on BoardGUI 
	private List<Player> players;							  // stores all players in the game
	private Set<Card> deck;									  // stores all cards in the game
	
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	
	private Set<Character> initials = new HashSet<Character>();	// set to keep all the initials of the rooms
	private Set<BoardCell> visited = new HashSet<BoardCell>();	// set to keep track of cells already visited in a turn
	private Set<BoardCell> targets = new HashSet<BoardCell>();	// set to keep track of target cells
	
	// map with keys as cells on the board and a set of the cells adjacent to the key cell for the values 
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	private Solution solution;
	
	// Sets of individual cards, weapons, and players.
	private Set<Card> weaponCards;
	private Set<Card> roomCards;
	private Set<Card> playerCards;

	// Variable controls whose turn in the game it is.
	private int turn = 0;
	private int diceRoll;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public int getTurn() {
		return turn;
	}
	
	// Moves on to the turn of the next player.
	public void nextTurn() {
		turn++;
		if (turn > players.size()) {
			turn = 0;
		}
	}
	
	// Returns the current player.
	public Player currentPlayer() {
		return players.get(turn);
	}
	
	// A method to play a turn. Will be called by GUI classes.
	public void playTurn() {
		Player player = currentPlayer(); // Get the current player.
		rollDice(); // Get a new dice roll.
		calcTargets(player.getRow(), player.getCol(), getDiceRoll()); // Calculate the targets for this player.
		player.makeMove(targets); // Pass in the targets for the players to choose from.
	}
	
	// Make a random dice roll.
	public void rollDice() {
		diceRoll = (int)(Math.random()*7);
	}
	
	public int getDiceRoll() {
		return diceRoll;
	}

	public void initialize() {
		
		// The deck must be created in the initialization as to not get duplicate values.
		// Located in here for the purpose of testing.
		deck = new HashSet<Card>();
		
		// Catch both Bad Configuration and file not found here where they can be handled best.
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}

	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {

		// Reloading the legend to assure that there is a new legend every time a new config file is loaded.
		legend = new HashMap<Character, String>();
		nameLocations = new HashMap<Character, ArrayList<String>>();

		// Scanner will take in the room config file and parse it into the legend map.
		Scanner scanner = new Scanner(new File(roomConfigFile));
		while (scanner.hasNextLine()) {
			// Grab a line from the file.
			String row = scanner.nextLine();

			// Split that line into an array (will be length three for the five fields of the file.)
			String[] values = row.split(", ");

			// Put that into the legend.
			legend.put(values[0].charAt(0), values[1]);

			// Make sure that the item is either a card or other, otherwise throw an exception.
			if (!values[2].equals("Card") && !values[2].equals("Other")) {
				scanner.close();
				throw new BadConfigFormatException("Incorrect room type in room config file.");
			}
			
			// if the legend indicates the room will be a card, add the initial to the set of room initials
			if (values[2].equals("Card")) {	
				initials.add(values[0].charAt(0));
				loadCard(values[1], CardType.ROOM);		// Load the room into the deck.
			}
			
			ArrayList<String> location = new ArrayList<String>();	// array to hold location of cell where room name is drawn
			location.add(values[3]);								// index 0 to store row value
			location.add(values[4]);								// index 1 to store column value
			nameLocations.put(values[0].charAt(0), location);		// puts the room initial with the cell location into map
			
		}

		scanner.close();
	}

	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {

		// Reloading the board matrix to ensure that a new board is loaded each time loadBoardConfig is called.
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];

		Scanner scanner = new Scanner(new File(boardConfigFile));
		int numColumns = 0; // The length of the first row will populate this variable. All other rows will be checked against it for validity.
		int row = 0;	// Will be incremented as elements are added to grid.

		// This first if statement will get the length of the first column to be compared against all others.
		if (scanner.hasNextLine()) {
			// Grab a line from the file.
			String line = scanner.nextLine();

			// Split that line into an array.
			String[] values = line.split(",");

			// Assume every row should be the same length as the first row. Otherwise, exceptions will be thrown.
			numColumns = values.length;

			for (int column = 0; column < values.length; column++) {
				if (legend.get(values[column].charAt(0)) == null) {
					// If any member of the board config file has a different initial than in the legend, throw an exception.
					scanner.close();
					throw new BadConfigFormatException("Invalid board cell in board config file.");
				}
				board[row][column] = new BoardCell(row, column, values[column]);
			}
			row++;
		}
		// Loop through and populate the rest of board grid.
		while (scanner.hasNextLine()) {

			// Grab a line from the file.
			String line = scanner.nextLine();

			// Split that line into an array.
			String[] values = line.split(",");

			// If a row is found to have an inconsistent length from any other, throw an exception.
			if (values.length != numColumns) {
				scanner.close();
				throw new BadConfigFormatException("Inconsistant row length in board config file.");
			}

			for (int column = 0; column < values.length; column++) {

				// If any member of the board config file has a different initial than in the legend, throw an exception.
				if (legend.get(values[column].charAt(0)) == null) {
					scanner.close();
					throw new BadConfigFormatException("Invalid board cell in board config file.");
				}

				board[row][column] = new BoardCell(row, column, values[column]);
			}
			row++;
		}

		this.numRows = row; // Finally, update the the instance variables if the board config is valid.
		this.numColumns = numColumns;

		scanner.close(); // Protect resources
	}

	public void calcAdjacencies() {

		for (int row = 0; row < numRows; row++) {
			for (int column = 0; column < numColumns; column++) {
				Set<BoardCell> adjCells = new HashSet<BoardCell>(getAdjList(row,column));	// finds all adjacent cells that can be entered
				adjMatrix.put(board[row][column], adjCells);	// Adds the cell and its appropriate adjacent cells.
			}
		}
	}

	public void calcTargets(int row, int column, int moves) {

		calcAdjacencies();
		BoardCell cell = getCellAt(row,column); 

		if (visited.contains(cell)) {	// if the cell is already visited, return
			return;
		} else {
			visited.add(cell);	// else, add to set of cells visited
		}

		if (moves == 0 || cell.isDoorway()) {										// if there are no moves remaining or at a door cell
			if (visited.size() == 1) {												// if starting at a door cell
				for (BoardCell adjCell : adjMatrix.get(cell)) {						// loops through adjacent cells 
					calcTargets(adjCell.getRow(), adjCell.getColumn(), moves-1);	// recursive call with decremented moves left 
				}
				visited.remove(cell);	// Remove the door, as it is not a valid target when starting on the door.
			} else { 
				targets.add(cell);
			}
			visited.remove(cell);
			return;
		} else {
			for (BoardCell adjCell : adjMatrix.get(cell)) {
				calcTargets(adjCell.getRow(), adjCell.getColumn(), moves-1);	// recursive call with decremented moves left 
			}
			visited.remove(cell);
		}
	}

	public Set<BoardCell> getTargets() {
		Set<BoardCell> temp = new HashSet<BoardCell>(targets);	// makes copy of target cells
		// clear sets for future use of CalcTargets
		targets.clear();
		visited.clear();	
		return temp;	// returns preserved copy of target cells
	}

	public void setConfigFiles(String boardConfig, String roomConfig, String playerConfig, String weaponConfig) {
		boardConfigFile = boardConfig;
		roomConfigFile = roomConfig;	
		playerConfigFile = playerConfig;
		weaponConfigFile = weaponConfig;
	}
	
	// Takes three parameters and creates a solution from those strings, if they are valid cards. (Overloaded)
	// Also removes them from the deck.
	// Note that this method must run before the cards are dealt.
	public void createSolution(String name, String weapon, String room) {
		Card solutionName = getCard(name);
		Card solutionWeapon = getCard(weapon);
		Card solutionRoom = getCard(room);
		
		deck.remove(solutionName);
		deck.remove(solutionWeapon);
		deck.remove(solutionRoom);
		
		solution = new Solution(solutionName.getName(), solutionWeapon.getName(), solutionRoom.getName());
	}
	
	// Same as the above method, but instead creates a random solution. 
	public void createSolution() {
		ArrayList<Set<Card>> categorizedDeck = getCategorizedDeck();
		
		List<Card> players = new ArrayList<Card>(categorizedDeck.get(0));
		List<Card> weapons = new ArrayList<Card>(categorizedDeck.get(1));
		List<Card> rooms = new ArrayList<Card>(categorizedDeck.get(2));
		
		Card solutionName = players.get(((int)(Math.random())*players.size()));
		Card solutionWeapon = weapons.get(((int)(Math.random())*weapons.size()));
		Card solutionRoom = rooms.get(((int)(Math.random())*rooms.size()));
		
		createSolution(solutionName.getName(), solutionWeapon.getName(), solutionRoom.getName());
	}
	
	// getters for number of rows, columns, legend, and cell at a specific location are all below
	
	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public BoardCell getCellAt(int row, int column) {
		return board[row][column];
	}

	public Set<BoardCell> getAdjList(int row, int column) {
		BoardCell current = getCellAt(row,column);
		Set<BoardCell> adjacent = new HashSet<BoardCell>(); 

		if (current.isRoom()) {	// if in room, cannot move
			return adjacent;

		} else if (current.isDoorway()) {	// if on a door cell, finds adjacent cell to enter

			switch(current.getDoorDirection()) {
			case LEFT:	// check if can enter to the left cell
				adjacent.add(getCellAt(row, column-1));
				break;
			case RIGHT:	// check if can enter to the right cell
				adjacent.add(getCellAt(row, column+1));
				break;
			case UP:	// check if can enter to the cell above
				adjacent.add(getCellAt(row-1, column));
				break;
			case DOWN:	// check if can enter to the cell below
				adjacent.add(getCellAt(row+1, column));
				break;
			default:
				break;
			} 

		} else if (current.isWalkway()) {	// if on a walkway, check all adjacent cells if can move onto (other walkways or doors) 

			// check if can move to the cell to the right
			if (column > 0 && (getCellAt(row, column-1).isWalkway()
					|| getCellAt(row, column-1).getDoorDirection() == DoorDirection.RIGHT)) {
				adjacent.add(getCellAt(row, column-1));
			}
			
			// check if can move to the cell to the left
			if (column < numColumns-1 && (getCellAt(row, column+1).isWalkway()
					|| getCellAt(row, column+1).getDoorDirection() == DoorDirection.LEFT)) {
				adjacent.add(getCellAt(row, column+1));
			}
			
			// check if can move to the cell below
			if (row > 0 && (getCellAt(row-1, column).isWalkway()
					|| getCellAt(row-1, column).getDoorDirection() == DoorDirection.DOWN)) {
				adjacent.add(getCellAt(row-1, column));
			}
			
			// check if can move to the cell above
			if (row < numRows-1 && (getCellAt(row+1, column).isWalkway()
					|| getCellAt(row+1, column).getDoorDirection() == DoorDirection.UP)) {
				adjacent.add(getCellAt(row+1, column));
			}

		}

		return adjacent;	// return all adjacent cells that can be moved onto from current board cell
	}	
	
	public Set<Character> getInitials() {
		return initials;
	}
	
	public Map<Character, ArrayList<String>> getNameLocations() {
		return nameLocations;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void loadConfigFiles() {
		// Catch both Bad Configuration and file not found here where they can be handled best.
		try {
			loadPlayers();
			loadWeapons();
			populateCardSets();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	// This method takes the cards loaded into deck and populates the sets of weapons, players, and rooms.
	public void populateCardSets() {
		ArrayList<Set<Card>> categorizedDeck = getCategorizedDeck(); // Gets cards from the deck split up by their types.
		playerCards = new HashSet<Card>(categorizedDeck.get(0));
		weaponCards = new HashSet<Card>(categorizedDeck.get(1));
		roomCards = new HashSet<Card>(categorizedDeck.get(2));
	}
	
	public void loadPlayers() throws BadConfigFormatException, FileNotFoundException {
		
		// Prepare to scan in plays from the config file.
		Scanner scanner = new Scanner(new File(playerConfigFile));
		players = new ArrayList<Player>();
		
		// Run through config file to grab player information.
		while (scanner.hasNextLine()) {

			// Grab a line from the file.
			String line = scanner.nextLine();

			// Split that line into an array.
			String[] values = line.split(", ");
			
			// Load in either a human or computer player depending on a value from the computer file.
			Player player;
			if (values[4].equals("human")) {
				player = new HumanPlayer(values[0], Integer.parseInt(values[2]), Integer.parseInt(values[3]), values[1]);
			} else if (values[4].equals("computer")) {
				player = new ComputerPlayer(values[0], Integer.parseInt(values[2]), Integer.parseInt(values[3]), values[1]);
			} else {
				scanner.close();
				throw new BadConfigFormatException("Incorrect player type in player config file.");
			}
			
			// Add the player to the player set and create a new card for that player.
			players.add(player);
			loadCard(player.getName(), CardType.PERSON);
			
		}
		
		scanner.close();
		
	}
	
	public void loadWeapons() throws BadConfigFormatException, FileNotFoundException {
		
		// Prepare to load in weapons from the config file.
		Scanner scanner = new Scanner(new File(weaponConfigFile));
		
		// Run through config file to grab weapon information.
		while (scanner.hasNextLine()) {

			// Grab a line from the file.
			String line = scanner.nextLine();
			
			// Load the weapon into the deck.
			loadCard(line, CardType.WEAPON);
		}
		
		scanner.close();
	}
	
	// Creates a new card and loads it to the deck.
	public void loadCard(String name, CardType type) {
		deck.add(new Card(name, type));
	}
	
	public void selectAnswer() {
		
	}
	
	public Card handleSuggestion(Player accusingPlayer, Solution suggestion) {
		
		Card suggestionDisproval = null;
		
		// First, find the index of the player who is making the suggestion.
		int playerIndex = 0;
		for (int i = 0; i < players.size(); i++) {
			if (accusingPlayer.equals(players.get(i))) {
				playerIndex = i;
			}
		}
		
		// Next loop through all the players, starting with the player following the accusing player.
		int index = 0;
		while (index < players.size() - 1) { // The reason for the -1 is that we don't want to include the accusing player.
			suggestionDisproval = players.get((index+playerIndex+1)%players.size()).disproveSuggestion(suggestion);
			if (suggestionDisproval != null) { // If a card is used to disprove the suggestion, break the loop and return it.
				break;
			}
			index++;
		}
		
		return suggestionDisproval;
	}
	
	public boolean checkAccusation(Solution accusation) {
		if (solution.equals(accusation)) {
			return true;
		} else { 
			return false; 
		}
	}
	
	public void dealDeck() {
		List<Card> cards = new ArrayList<Card>(deck);	// creates a list of cards from the deck to iterate through
		int deckIndex = 0;	// variable to keep track of index in list, that way never visiting an index twice (cards only dealt once)
		Collections.shuffle(cards); // Shuffle the deck to get a random arrangement of cards.
		
		while (deckIndex < cards.size()) {	// while loop to iterate through list of cards
			for (Player player : players) {	// goes through each player to deal a card to them
				player.addCard(cards.get(deckIndex));
				deckIndex++;						// increment index
				if (deckIndex == cards.size()) {	// in the event deck runs out of cards early (not enough cards to deal evenly), break
					break;
				} 
			}
		}
	}
	
	
	
	
	
	// Getters for testing purposes: ////////////////////////////////////////////////////////////////
	
	public Set<String> getWeaponNames() {
		Set<String> weapons = new HashSet<String>();
		for (Card weapon: weaponCards) {
			weapons.add(weapon.getName());
		}
		
		return weapons;
	}
	
	public Set<String> getAllPlayerNames() {
		Set<String> Players = new HashSet<String>();
		for (Player player: players) {
				Players.add(player.getName());	
		}
		
		return Players;
	}
	
	// Returns the set of human players.
	public Set<String> getHumanPlayerNames() {
		// Temporarily returning random players.
		Set<String> humanPlayers = new HashSet<String>();
		
		// Loop through the player array to check for human players.
		for (Player player: players) {
			if (player instanceof HumanPlayer) {
				humanPlayers.add(player.getName());
			}
		}
		
		return humanPlayers;
	}
	
	// Returns the set of computer players.
	public Set<String> getComputerPlayerNames() {
		// Temporarily returning random players.
		Set<String> computerPlayers = new HashSet<String>();
		
		// Loop through the player array to check for human players.
		for (Player player: players) {
			if (player instanceof ComputerPlayer) {
				computerPlayers.add(player.getName());
				//System.out.println(player.getName());
			}
		}

		return computerPlayers;
	}
	
	// Prints out all cards in the deck.
	public void printCards() {
		for (Card card: deck) {
			System.out.println(card.getName());
		}
	}
	

	
	public Set<Card> getPlayers() {
		return playerCards;
	}
	
	public Set<Card> getWeapons() {
		return weaponCards;
	}
	
	public Set<Card> getRooms() {
		return roomCards;
	}
	
	// Returns the number of computer players.
	public int getNumComputerPlayers() {
		Set<String> computerPlayers = new HashSet<String>();
		
		// Loop through the player array to check for human players.
		for (Player player: players) {
			if (player instanceof ComputerPlayer) {
				computerPlayers.add(player.getName());
			}
		}
		
		return computerPlayers.size();
	}
	
	// Returns the number of human players.
	public int getNumHumanPlayers() {
		Set<String> humanPlayers = new HashSet<String>();
		
		// Loop through the player array to check for human players.
		for (Player player: players) {
			if (player instanceof HumanPlayer) {
				humanPlayers.add(player.getName());
			}
		}
		
		return humanPlayers.size();
	}
	
	// Returns the number of weapons cards in the deck.
	public int getNumWeapons() {
		int numWeapons = 0;
		
		for (Card card: deck) {
			if (card.getType() == CardType.WEAPON) {
				numWeapons++;
			}
		}
		
		return numWeapons;
	}
	
	// Returns the number of people cards in the deck.
	public int getNumPlayers() {
		int numPlayers = 0;
		
		for (Card card: deck) {
			if (card.getType() == CardType.PERSON) {
				numPlayers++;
			}
		}
		
		return numPlayers;
	}
	
	// Returns the number of rooms cards in the deck.
	public int getNumRooms() {
		int numRooms = 0;
		
		for (Card card: deck) {
			if (card.getType() == CardType.ROOM) {
				numRooms++;
				//System.out.println(card.getName());
			}
		}
		
		System.out.println(roomCards.size());
		
		return numRooms;
	}
	
	// Returns the deck.
	public Set<Card> getDeck() {
		return deck;
	}
	
	// Returns the card whose name was passed in.
	public Card getCard(String name) {
		for (Card card: deck) {
			if (card.getName().equals(name)) {
				return card;
			}
		}
		
		return null;
	}
	
	// Returns the player whose name was passed in.
	public Player getPlayer(String name) {
		for (Player player: players) {
			if (player.getName().equals(name)) {
				return player;
			}
		}
		
		return null;
	}
	
	
	
	public Set<Integer> getNumCardsDealt() {
		Set<Integer> numberOfCards = new HashSet<Integer>();	// set to hold the number of cards a player holds
		
		for (Player player : players) {	
			numberOfCards.add(player.getCards().size());	// go through players and add their number of cards into set
		}

		return numberOfCards;	
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	// Returns the deck divided into three parts: persons, weapons, and rooms.
	public ArrayList<Set<Card>> getCategorizedDeck() {
		
		ArrayList<Set<Card>> categorizedDeck = new ArrayList<Set<Card>>();
		categorizedDeck.add(new HashSet<Card>());
		categorizedDeck.add(new HashSet<Card>());
		categorizedDeck.add(new HashSet<Card>());
		
		for (Card card: deck) {
			if (card.getType() == CardType.PERSON) {
				categorizedDeck.get(0).add(card);
			}
		}
		
		for (Card card: deck) {
			if (card.getType() == CardType.WEAPON) {
				categorizedDeck.get(1).add(card);
			}
		}
		
		for (Card card: deck) {
			if (card.getType() == CardType.ROOM) {
				categorizedDeck.get(2).add(card);
			}
		}
		
		return categorizedDeck;
		
	}
	
	public void removePlayers() {
		players = new ArrayList<Player>();
	}
	
	public BoardCell[][] getBoard() {
		return board;
	}
	
	public void drawBoardCells(Graphics g) {
		// Loop through and call the draw method for all board cell.
		for (BoardCell[] row: board) {
			for (BoardCell cell: row) {
				cell.draw(g);
			}
		}
	}
	
	public ArrayList<Player> getPlayerList() {
		return (ArrayList<Player>) players;
	}
	
	
	
}