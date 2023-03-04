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
				if (matchOn == false) {
					String playersStr = inputBox.getText();
					try {
						int pl = Integer.parseInt(playersStr);
						currentMatch = new SevenMatch(pl);
						matchOn = true;
						Deck d = currentMatch.getDeck();
						String msg = "match for " + pl + " players started";
						System.out.println(msg);
						JOptionPane.showMessageDialog(null, msg);
						System.out.println("deck: " + d);
					}
					catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
					
					
				}
				{
					//match is ongoing
					
				}
			}
        	
        });
		
		showCardsBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				//currentMatch//
				currentMatch.getDeck().shuffleDeck();
				currentMatch.removePlayerCards();
				currentMatch.deal();
				currentMatch.sortAllPlayerDecks();
				System.out.println("house deck: " + currentMatch.getDeck());
				for (int i = 0; i < currentMatch.getSize(); i++) {
					System.out.println("player " + (i+1) + " deck: " + currentMatch.getPlayers().get(i).getPlayerDeck());
				}
				System.out.println("player " + (currentMatch.findOpeningPlayer()+1) + " starts!");
			}
        	
        });
		
		
		
		

	}

}

