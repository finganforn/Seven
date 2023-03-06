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
		Random rnd = new Random();
		
		int i = rnd.nextInt(deck.count());
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
			Random rnd = new Random();
			i = rnd.nextInt(res.size());
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

}
