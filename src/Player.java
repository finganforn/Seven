import java.util.ArrayList;
import java.util.Random;

public class Player {
	
	private boolean ai;
	private Deck deck;
	
	public Player(boolean ai) {
		this.ai = ai;
		deck = new Deck();
	}
	public void giveCard(Card c) {
		deck.addCard(c);
	}
	
	
	public Card selectTrashCard(SevenMatch match) {
		//Random rnd = new Random();
		int biggestDistance = 0;
		int i = 0;
		
		for (int j = 0; j < deck.count(); j++) {
			int distance = 0;
			Card c2 = deck.getAtIndex(j) ;
			int cardRank = c2.rank.rankIndex();
			if (c2.suit == Suit.CLUBS) {
				if (cardRank > 7)
					distance = cardRank - match.cHigh;
				if (cardRank < 7)
					distance = match.cLow - cardRank;
			}
			if (c2.suit == Suit.DIAMONDS) {
				if (cardRank > 7)
					distance = cardRank - match.dHigh;
				if (cardRank < 7)
					distance = match.dLow - cardRank;
			}
			if (c2.suit == Suit.HEARTS) {
				if (cardRank > 7)
					distance = cardRank - match.hHigh;
				if (cardRank < 7)
					distance = match.hLow - cardRank;
			}
			if (c2.suit == Suit.SPADES) {
				if (cardRank > 7)
					distance = cardRank - match.sHigh;
				if (cardRank < 7)
					distance = match.sLow - cardRank;
			}
			if (distance > biggestDistance) {
				i = j;
				biggestDistance = distance;
			}
				
			
		}
		
		//i = rnd.nextInt(deck.count());
		Card c = deck.getAtIndex(i);
		deck.deleteCardAtIndex(i);
		return c;
		
	}
	public Card selectPlayCard(SevenMatch match) {
		int i = -1;
		ArrayList<Card> res = playableCards(match);
		if (res.size() == 0)
			return null;
		for (int j = 0; j < res.size(); j++) {
			Card c = res.get(j);
			if (c.suit == Suit.CLUBS && c.rank == Rank.SEVEN)
				i = j;
		}
		if (i == -1) {
			int mostSuitPals = 0;
			int in = 0;
			for (int j = 0; j < res.size(); j++) {
				Card c2 = res.get(j);
				int suitPals = cardOfSuit(c2.suit, c2.rank.rankIndex() > 7 ? true : false);
				if (suitPals > mostSuitPals) {
					in = j;
					mostSuitPals = suitPals;
				}
			}
			
			
			
			
			//Random rnd = new Random();
			//i = rnd.nextInt(res.size());
			i = in;
		}
		return res.get(i);
		
	}
	
	public ArrayList<Card> playableCards(SevenMatch m) {
		ArrayList<Card> res = new ArrayList<Card>();
		
		for (Card c : deck) {
			if (c.playable(m))
				res.add(c);
		}
		
		return res;
	}
	
	public Card playCard(SevenMatch match) {
		Card c = null;
		
		
		if (this.canPlay(match) == false)
			return null;
		ArrayList<Card> playableCards = this.playableCards(match);
		c = selectPlayCard(match);
		//System.out.println("playable cards: " + playableCards);
		
		int i = deck.getIndexOfCard(c);
		deck.deleteCardAtIndex(i);
		return c;
	}
	public void clearDeck() {
		deck = new Deck();
	}
	public Deck getPlayerDeck( ) {
		return deck;
	}
	public boolean openingPlayer() {
		return deck.contains7();
	}
	public boolean canPlay(SevenMatch match) {
		for (Card c : deck) {
			if (c.playable(match))
				return true;
		}
		
		return false;
	}
	
	
	
	public String cardsAsStrings() {
		String res = "";
		for (Card c : deck) {
			res += "" + c.rank.rankIndex() + c.suit + " ";
		}
		return res;
	}
	private int cardOfSuit(Suit suit, boolean above7) {
		int res = 0;
		for (Card c : deck) {
			if (c.suit == suit) {
				if (c.rank.rankIndex() > 7 && above7)
					res++;
				if (c.rank.rankIndex() < 7 && !above7)
					res++;
				if (c.rank.rankIndex() == 7)
					res++;
			}
		}
		return res;
	}
	public int shittinessRating(SevenMatch m) {
		int res = 0;
		for (Card c : deck) {
			int r = c.rank.rankIndex();
			if (c.suit == Suit.CLUBS) {
				if (r > 7)
					res+= r - m.cHigh;
				if (r < 7)
					res+= m.cLow - r;
			}
			else if (c.suit == Suit.DIAMONDS) {
				if (r > 7)
					res+= r - m.dHigh;
				if (r < 7)
					res+= m.dLow - r;
					}
			else if (c.suit == Suit.HEARTS) {
				if (r > 7)
					res+= r - m.hHigh;
				if (r < 7)
					res+= m.hLow - r;
			}
			else {
				if (r > 7)
					res+= r - m.sHigh;
				if (r < 7)
					res+= m.sLow - r;
				
			}
		}
		return res;
	}

}
