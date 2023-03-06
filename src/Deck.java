
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Deck is a collection of Cards, it allows iteration by:
 *   - rank
 *   - suit
 *   - random (shuffle)
 *
 * @author tlehman
 *
 */
public class Deck implements Iterable<Card> {
	private ArrayList<Card> cards;
	private int currentCardIndex = 0;

	/**
	 * Default constructor initializes an empty deck
	 */
	public Deck() {
		this.cards = new ArrayList<Card>();
	}
	
	/**
	 * Parses a string and instantiates a deck with the corresponding cards. 
	 * 
	 * @param deckStr  a string of cards such as "2C 3H JD"
	 */
	public Deck(String deckStr) {
		this.cards = new ArrayList<Card>();
		Matcher m = Pattern.compile("([2-9]|10|[AJKQ])([CDHS]) ?").matcher(deckStr);
		
		String rankStr, suitStr;
		while(m.find()) {
			rankStr = m.group(1);
			suitStr = m.group(2);
			cards.add(Card.fromStrings(rankStr, suitStr));
		}
	}
	
	public Deck(Card cards[]) {
		this.cards = new ArrayList<Card>();

		for(Card c : cards) {
			this.cards.add(c);
		}
	}
	
	public int count() {
		return cards.size();
	}
	
	public String toString() {
		StringBuffer deckStrBuf = new StringBuffer();
		
		for(int i = 0; i < cards.size(); i++) {
			deckStrBuf.append(cards.get(i).toString());
			if(i < cards.size()-1) {
				deckStrBuf.append(" ");
			}
		}
		
		return deckStrBuf.toString();
	}
	
	public void sortByRank() {
		Collections.sort(cards, Card.CardRankComparator);
	}
	
	public void sortBySuit() {
		Collections.sort(cards, Card.CardSuitComparator);
	}
	
	public Card getAtIndex(int index) {
		return cards.get(index);
	}

	public void addCard(Card card) {
		cards.add(card);
	}

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}

	public Card nextCardCircular() {
		if(cards.size() == 0) return null;
		
		Card card = cards.get(currentCardIndex);
		currentCardIndex = (currentCardIndex + 1) % count();
		return card;
	}
	
	public void fullDeck() {
		cards.clear();
		for (int i = 0; i < 4; i++) {
			for (int j = 1; j < 14; j++) {
				String suit = Integer.toString(i);
				String rank = Integer.toString(j);
				
				if (i == 0)
					suit = "C";
				else if (i == 1)
					suit = "D";
				else if (i == 2)
					suit = "S";
				else
					suit = "H";
				
				if (j == 1)
					rank = "A";
				else if (j == 11)
					rank = "J";
				else if (j == 12)
					rank = "Q";
				else if (j == 13)
					rank = "K";
					
				cards.add(Card.fromStrings(rank, suit));
			}
		}
	}
	public void shuffleDeck( ) {
		ArrayList<Card> newDeck = new ArrayList<Card>();
		while (cards.size() > 0) {
			Random rnd = new Random();
			int r = rnd.nextInt(cards.size());
			Card c = cards.get(r);
			cards.remove(r);
			newDeck.add(c);
			
		}
		cards = newDeck;
	}
	public boolean contains7() {
		for ( Card c : cards) {
			if (c.rank == Rank.SEVEN && c.suit == Suit.CLUBS)
				return true;
		}
		return false;
	}
	public void deleteCardAtIndex(int del) {
		ArrayList<Card> newCards = new ArrayList<Card>();
		for (int i = 0; i < cards.size(); i++) {
			if (del != i)
				newCards.add(cards.get(i));
		}
		cards = newCards;
	}
	public int getIndexOfCard(Card c) {
		int res = -1;
		
		for (int i = 0; i < cards.size(); i++)
		{	
			Card c2 = cards.get(i);
			if (c2 != null && c.equals(c2))
				res = i;
		}
		return res;
	}
}
