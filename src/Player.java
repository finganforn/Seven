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
	
	public boolean canPlay(SevenMatch match) {
		return false;
	}
	public Card passTrashCard(SevenMatch match) {
		Random rnd = new Random();
		
		int i = rnd.nextInt(deck.count());
		return deck.getAtIndex(i);
		
	}
	public Card playCard(SevenMatch match) {
		return null;
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

}
