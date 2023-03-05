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
		JLabel ord = new JLabel("Ord");
		
		topPanel.add(inputBox);
		midPanel.add(send);
		midPanel.add(showCardsBtn);
		
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
								cp += currentMatch.getPlayers().get(i).canPlay(currentMatch) ? "can play" : "CAN NOT PLAY";
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
					int pi = currentMatch.currentTurn;
					//System.out.println("asking playerI " + pi + " for a card");
					Player p = currentMatch.getPlayers().get(pi);
					Card nc = currentMatch.getPlayers().get(pi).playCard(currentMatch);
					if ( nc != null) {
						System.out.println("" + pi + " can play: " + nc);
					}
					else {
						System.out.println("" + pi + " cant play!");
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
					System.out.println("house deck: " + currentMatch.getDeck());
					for (int i = 0; i < currentMatch.getSize(); i++) {
						System.out.println("player " + (i+1) + " deck: " + currentMatch.getPlayers().get(i).getPlayerDeck());
						System.out.println("player " + (i+1) + " deck: " + currentMatch.getPlayers().get(i).cardsAsStrings());
					}
					
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

