import java.util.ArrayList;

public class SevenMatch {
	
	

	private ArrayList<Player> players;
	private int playerAmount;
	public int currentTurn = 0;
	boolean matchDone = false;
	private Deck deck;
	public int cLow;
	public int cHigh;
	public int dLow;
	public int dHigh;
	public int sLow;
	public int sHigh;
	public int hLow;
	public int hHigh;
	private ArrayList<String> results;
	
	public SevenMatch(int playerAmount) {
		
		cLow = cHigh = dLow = dHigh = sLow = sHigh = hLow = hHigh = 7;
		deck = new Deck();
		deck.fullDeck();
		deck.shuffleDeck();
		players = new ArrayList<Player>();
		results = new ArrayList<String>();
		
		if (playerAmount > 8)
			this.playerAmount = 8;
		else if (playerAmount < 3)
			this.playerAmount = 3;
		else
			this.playerAmount = playerAmount;
		for (int i = 0; i < this.playerAmount; i++) {
			players.add(new Player(true));
		}
		
		
		
	}
	public Deck getDeck() {
		return deck;
	}
	public void deal() {
		for (int i = 0; i < 52; i++) {
			Player p = players.get(i%players.size());
			p.giveCard(deck.getAtIndex(i));
		}
	}
	public void removePlayerCards() {
		for (Player p: players) {
			p.clearDeck();
		}
	}
	public int getSize() {
		return players.size();
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public int findOpeningPlayer() {
		int res = -1;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).openingPlayer())
				res = i;
		}
		return res;
	}
	public void sortAllPlayerDecks() {
		for (Player p: players)
			p.getPlayerDeck().sortBySuit();
	}

	
}
