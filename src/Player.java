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
	
	
	public Card passTrashCard(SevenMatch match) {
		Random rnd = new Random();
		
		int i = rnd.nextInt(deck.count());
		return deck.getAtIndex(i);
		
	}
	public Card playCard(SevenMatch match) {
		Card c = null;
		Card res = c;
		int playedCardI = -1;
		if (this.canPlay(match) == false)
			return null;
		ArrayList<Card> playableCards = new ArrayList<Card>();
		for (int i = 0; i < deck.count(); i++) {
			c = deck.getAtIndex(i);
			if (c.suit == Suit.CLUBS) {
				if (c.rank.rankIndex() == match.cHigh && c.rank.rankIndex() == match.cLow) {
					res = c;
					playedCardI = i;
					playableCards.add(c);
				}
			}
			else if (c.suit == Suit.DIAMONDS) {
				if (c.rank.rankIndex() == match.dHigh && c.rank.rankIndex() == match.dLow) {
					res = c;
					playedCardI = i;
					playableCards.add(c);
				}
			}
			else if (c.suit == Suit.HEARTS) {
				if  (c.rank.rankIndex() == match.hHigh && c.rank.rankIndex() == match.hLow) {
					res = c;
					playedCardI = i;
					playableCards.add(c);
				}
			}
			else if (c.suit == Suit.SPADES) {
				if (c.rank.rankIndex() == match.sHigh && c.rank.rankIndex() == match.sLow) {
					res = c;
					playedCardI = i;
					playableCards.add(c);
				}
			}
		}
		System.out.println(playableCards);
		
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
		boolean res = false;
		
		for (Card c : deck) {
			if (c.suit == Suit.CLUBS) {
				if (c.rank.rankIndex() == match.cHigh || c.rank.rankIndex() == match.cLow)
					return true;
			}
			else if (c.suit == Suit.DIAMONDS) {
				if (c.rank.rankIndex() == match.dHigh || c.rank.rankIndex() == match.dLow)
					return true;
			}
			else if (c.suit == Suit.HEARTS) {
				if (c.rank.rankIndex() == match.hHigh || c.rank.rankIndex() == match.hLow)
					return true;
			}
			else if (c.suit == Suit.SPADES) {
				if (c.rank.rankIndex() == match.sHigh || c.rank.rankIndex() == match.sLow)
					return true;
			}
		}
		
		return res;
	}
	public String cardsAsStrings() {
		String res = "";
		for (Card c : deck) {
			res += "" + c.rank.rankIndex() + c.suit + " ";
		}
		return res;
	}

}
