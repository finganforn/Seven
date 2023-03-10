import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFunc {
	
	
	static boolean matchOn;
	static SevenMatch currentMatch;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		matchOn = false;
		//SevenMatch currentMatch;
		JFrame frame = new JFrame("SevenCloves");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 500);
		
		JPanel topPanel = new JPanel();
		JPanel midPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		JTextField inputBox = new JTextField("INPUT");
		JButton send = new JButton("Kör");
		JButton showCardsBtn = new JButton("Visa kort");
		JLabel ord = new JLabel("No match playing");
		
		topPanel.add(inputBox);
		midPanel.add(send);
		midPanel.add(showCardsBtn);
		bottomPanel.add(ord);
		
		frame.getContentPane().add(BorderLayout.NORTH, topPanel);
        frame.getContentPane().add(BorderLayout.CENTER, midPanel);        
        frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
        frame.setVisible(true);
		
		
		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String playersStr = inputBox.getText();
				if (playersStr.equals("0")) {
					matchOn = false;
					System.out.println("RESET!");
				}
					
				else if (matchOn == false) {
					
					try {
						int pl = Integer.parseInt(playersStr);
						currentMatch = new SevenMatch(pl);
						matchOn = true;
						currentMatch.getDeck().shuffleDeck();
						currentMatch.removePlayerCards();
						currentMatch.deal();
						currentMatch.sortAllPlayerDecks();
						String msg = "match for " + pl + " players started";
						System.out.println(msg);
						JOptionPane.showMessageDialog(null, msg);
						System.out.println("house deck: " + currentMatch.getDeck());
						int opener = currentMatch.findOpeningPlayer();
						currentMatch.currentTurn = opener;
						if (matchOn) {
							System.out.println("house deck: " + currentMatch.getDeck());
							for (int i = 0; i < currentMatch.getSize(); i++) {
								System.out.println("player " + (i+1) + " deck: " + currentMatch.getPlayers().get(i).getPlayerDeck());
								String cp = "player" + (i+1);
								cp += currentMatch.getPlayers().get(i).canPlay(currentMatch) ? " can play" : " CAN NOT PLAY";
								System.out.println(cp);
							}
							
							System.out.println("player " + (currentMatch.currentTurn+1) + " is playing next!"); 
						}
					}
					catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
					
					
				}
				else
				{
					//match is ongoing
					if (currentMatch.matchDone) {
						matchOn = false;
						return;
					}
					int pi = currentMatch.currentTurn;
					//System.out.println("asking playerI " + pi + " for a card");
					Player p = currentMatch.getPlayers().get(pi);
					System.out.println("player" + (pi+1) + " can play: " + p.playableCards(currentMatch));
					if (p.getPlayerDeck().count() > 0) {
						Card nc = currentMatch.getPlayers().get(pi).playCard(currentMatch);
						if (currentMatch.currentTurn == 0) {
							//player 1
							
							//String rnk = playersStr.substring(0, 1);
							//String sut = playersStr.substring(1);
							//nc = Card.fromStrings(rnk, sut);
						}
						
						System.out.println(currentMatch.matchStatus());
						if ( nc != null) {
							
							currentMatch.playCard(nc);
							System.out.println("player" + (pi+1) + " played " + nc);
							
							if (p.getPlayerDeck().count() == 0) {
								System.out.println("player " + (pi+1) + " is done!");
								
							}
						}
						else {
							System.out.println("" + (pi+1) + " cant play!");
							int previousPlayer = pi-1;
							if (previousPlayer == -1)
								previousPlayer = currentMatch.getPlayers().size() -1;
							Player pp = currentMatch.getPlayers().get(previousPlayer);
							boolean stillPlayingPlayer = false;
							while (!stillPlayingPlayer) 
							{
								if (pp.getPlayerDeck().count() == 0) {
									previousPlayer--;
									if (previousPlayer == -1)
										previousPlayer = currentMatch.getPlayers().size() - 1;
									pp = currentMatch.getPlayers().get(previousPlayer);
								}
								else stillPlayingPlayer = true;
								
							}
							Card trash = currentMatch.getPlayers().get(previousPlayer).selectTrashCard(currentMatch);
							p.giveCard(trash);
							currentMatch.sortAllPlayerDecks();
							
							System.out.println("player " + (pi+1) + " was given " + trash + " from  player " + (previousPlayer+1));
							if (pp.getPlayerDeck().count() == 0) {
								System.out.println("player " + (previousPlayer+1) + " is done!");
								
							}
						}
					}
					else {
						System.out.println("player " + (pi+1) + " is done already");
					}
					if (pi < currentMatch.getPlayers().size() -1) {
						currentMatch.currentTurn++;
					}
					else
						currentMatch.currentTurn = 0;
					
						
					
				}
			}
        	
        });
		
		showCardsBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				//currentMatch//
				//currentMatch.getDeck().shuffleDeck();
				//currentMatch.removePlayerCards();
				//currentMatch.deal();
				//currentMatch.sortAllPlayerDecks();
				if (matchOn) {
					//System.out.println("house deck: " + currentMatch.getDeck());
					for (int i = 0; i < currentMatch.getSize(); i++) {
						System.out.println("player " + (i+1) + " deck: " + currentMatch.getPlayers().get(i).getPlayerDeck() + 
								" difficultyRating: " + currentMatch.getPlayers().get(i).shittinessRating(currentMatch));
						//System.out.println("player " + (i+1) + " deck: " + currentMatch.getPlayers().get(i).cardsAsStrings());
					}
					System.out.println(currentMatch.matchStatus());	
					System.out.println("player " + (currentMatch.currentTurn+1) + " is playing next!"); 
				}
				else
				{
					System.out.println("match off");
				}
			}
        	
        });
		
		
		
		

	}

}

