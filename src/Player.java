import java.util.ArrayList;
import java.util.Random;



public class Player {
	
	private boolean ai;
	private Deck deck;
	private String name;
	private AiType aiType;
	
	public Player(boolean ai, String name) {
		this.ai = ai;
		deck = new Deck();
		this.name = name;
		Random rnd = new Random();
		int i = rnd.nextInt(4);
		if (i == 0)
			aiType = AiType.DUMB;
		else if (i == 1)
			aiType = AiType.FRIENDLY;
		else if (i == 2)
			aiType = AiType.EVIL;
		else if (i == 3)
			aiType = AiType.SUICIDAL;
		else
			aiType = AiType.HUMAN;
		
		
		//if (ai) aiType = AiType.SUICIDAL; //TODO
		
		if (!ai) aiType = AiType.HUMAN;
	}

	public Player(boolean ai, String name, AiType type) {
		this.ai = ai;
		deck = new Deck();
		this.name = name;
		this.aiType = type;
	}
	public void giveCard(Card c) {
		deck.addCard(c);
	}
	
	
	public Card selectTrashCard(SevenMatch match) {
		Random rnd = new Random();
		int biggestDistance = 0;
		int i = 0;
		
		if (aiType == AiType.DUMB || aiType == AiType.SUICIDAL) { //TODO välj snällaske kortet
			int r = rnd.nextInt(deck.count());
			try {
				Card c = deck.getAtIndex(r);
				deck.deleteCardAtIndex(r);
				return c;
				
			}
			catch (Exception ex) {
				System.out.println(ex);
			}
			deck.deleteCardAtIndex(r);
			return deck.getAtIndex(0);
		}
		
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
		for (int j = 0; j < res.size() && i == -1; j++) {
			Card c = res.get(j);
			if (c.suit == Suit.CLUBS && c.rank == Rank.SEVEN)
				i = j;
		}
		if (i == -1) {//inte klöver sju!
			
			if (aiType == AiType.DUMB) {
				ArrayList<Card> bestCards = mostUsefulCards(match, res, true);
				Random rnd = new Random();
				int r = rnd.nextInt(res.size());
				try {
					Card c = res.get(r);
					return c;
				}
				catch (Exception ex) {
					System.out.println(ex);
				}
				return deck.getAtIndex(0);
				
			}
			
			if (res.size() > 1) {
				if (aiType == AiType.SUICIDAL)
					res = mostUsefulCards(match, res, false);
				else
					res = mostUsefulCards(match, res, true);
			}
			int distFrom7 = 0;
			int in = 0;
			for (int j = 0; j < res.size(); j++) {
				int thisDistFrom7 = res.get(j).rank.rankIndex() - 7;
				if (thisDistFrom7 < 0)
					thisDistFrom7 = thisDistFrom7*-1;
				if (aiType == AiType.FRIENDLY) {
					if (thisDistFrom7 < distFrom7) {
						in = j;
						distFrom7 = thisDistFrom7;
					}
				}
				else {
					if (thisDistFrom7 > distFrom7) {
						in = j;
						distFrom7 = thisDistFrom7;
					}
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
	public String getName() {
		return name;
	}
	public AiType getType() {
		return aiType;
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
	private int cardOfSuit(Card card) {
		int res = 0;
		for (Card c : deck) {
			int c1Val = card.rank.rankIndex();
			int c2Val = c.rank.rankIndex();
			if (card.suit == c.suit && c1Val != c2Val) {
				if (c1Val == 7)
					res++;
				if (c1Val > 7 && c2Val > 7)
					res++;
				if (c1Val < 7 && c2Val < 7)
					res++;
			}
		}
		return res;
	}
	private int cardsMissing(Card c1) {
		int res = 0;
		for (Card c2 : deck) {
			int c1Val = c1.rank.rankIndex();
			int c2Val = c2.rank.rankIndex();
			if (c1.suit == c2.suit && c1Val != c2Val) {
				
				//System.out.println("finding gaps between " + c1 + " and " + c2);
				/*if (c2Val > 10 || c2Val == 1) {
					System.out.println("special card!");
				}*/
				if (c1Val == 7) {
					res++;
					for (int i = c1Val+1; i < c2Val; i++) {
						
						
						Card tempCard = new Card(Rank.fromString(Integer.toString(i)), c1.suit);
						
						if (!hasCard(tempCard)) {
							//System.out.println("missing " + tempCard);
							res++;
						}
					}
					for (int i = c1Val-1; i > c2Val; i--) {

						Card tempCard = new Card(Rank.fromString(Integer.toString(i)), c1.suit);
						
						if (!hasCard(tempCard)) {
							//System.out.println("missing " + tempCard);
							res++;
						}
					}
				}
				
				if (c1Val > 7 && c2Val > 7) {
					res++;
					for (int i = c1Val+1; i < c2Val; i++) {
						
						Card tempCard = new Card(Rank.fromString(Integer.toString(i)), c1.suit);
						
						if (!hasCard(tempCard)) {
							//System.out.println("missing " + tempCard);
							res++;
						}
					}
				}
				if (c1Val < 7 && c2Val < 7) {
					res++;
					for (int i = c1Val-1; i > c2Val; i--) {

						Card tempCard = new Card(Rank.fromString(Integer.toString(i)), c1.suit);
						
						if (!hasCard(tempCard)) {
							//System.out.println("missing " + tempCard);
							res++;
						}
					}
				}
			}
		}
		return res;
		
	}
	private boolean hasCard(Card c) {
		int b = deck.getIndexOfCard(c);
		return b >= 0;
	}
	private ArrayList<Card> mostUsefulCards(SevenMatch match, ArrayList<Card> cards, boolean win) {
		ArrayList<Card> res = new ArrayList<Card>();
		int mostSuitPals = 0;
		if (!win)
			mostSuitPals = 10000;
		for (int i = 0; i < cards.size(); i++) {
			Card c2 = cards.get(i);
			//orgiinal int suitPals = cardOfSuit(c2);
			int suitPals = cardsMissing(c2);
			if (suitPals > mostSuitPals && win)
				mostSuitPals = suitPals;
			
			if (suitPals < mostSuitPals && !win)
				mostSuitPals = suitPals;
		}
		for (Card c : cards) {
			//if (cardOfSuit(c) == mostSuitPals)
			if (cardsMissing(c) == mostSuitPals)
				res.add(c);
		}
			
		System.out.println((win ? "best card(s): " : "wost card(s)") + res  + ", rating " + mostSuitPals);
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
	public boolean hasIsolated7() {
		

		for (Card c : deck) {
			if (c.rank.rankIndex() == 7 && cardOfSuit(c) == 0)
				return true;
		}
		return false;
		
	}

}
