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
	public void playCard(Card c) {
		int i = c.rank.rankIndex();
		if (c.suit == Suit.CLUBS) {
			if (i == cLow)
				cLow--;
			if (i == cHigh)
				cHigh++;
		}
		else if (c.suit == Suit.DIAMONDS) {
			if (i == dLow)
				dLow--;
			if (i == dHigh)
				dHigh++;
		}
		else if (c.suit == Suit.HEARTS) {
			if (i == hLow)
				hLow--;
			if (i == hHigh)
				hHigh++;
		}
		else if (c.suit == Suit.SPADES) {
			if (i == sLow)
				sLow--;
			if (i == sHigh)
				sHigh++;
		}
		else {//WTF {
			System.out.println("WTF");
		}
	}
	public String matchStatus() {
		String res = "BOARD: ";
		if (cLow == 7)
			res += "C ";
		else {
			res += (cLow+1);
			res += "C";
			res += (cHigh-1);
			res += " ";
		}
		if (dLow == 7)
			res += "D ";
		else {
			res += (dLow+1);
			res += "D";
			res += (dHigh-1);
			res += " ";
		}
		if (hLow == 7)
			res += "H ";
		else {
			res += (hLow+1);
			res += "H";
			res += (hHigh-1);
			res += " ";
		}
		if (sLow == 7)
			res += "S ";
		else {
			res += (sLow+1);
			res += "S";
			res += (sHigh-1);
			res += " ";
		}
		
		
		
		
		return res;
	}
	public boolean MatchOver() {
		return cLow == 1 && cHigh == 13 && dLow == 1 && dHigh == 13 && hLow == 1 && hHigh == 13 && sLow == 1 && sHigh == 13;
			
	}

	
}
