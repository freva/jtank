package gui.chat;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChatCurrent extends JPanel {
	private static JLabel[] chatHistory = new JLabel[] {new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel()};
	private static long[] duration  = new long[chatHistory.length];
	private int chatHistoryDuration = 5000;
	
	public ChatCurrent() {
		this.setOpaque(false);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{500};
		gridBagLayout.rowHeights = new int[]{30, 30, 30, 30, 30};
		setLayout(gridBagLayout);
		
		for(int i=0; i<chatHistory.length; i++) {
			chatHistory[i].setOpaque(true);
			chatHistory[i].setBackground(Chat.blackTransp);
			chatHistory[i].setForeground(Chat.whiteSolid);
			chatHistory[i].setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
			
			GridBagConstraints temp = new GridBagConstraints();
			temp.anchor = GridBagConstraints.WEST;
			temp.gridx = 0;
			temp.gridy = i;
			add(chatHistory[i], temp);
		}		
	}
	
	public void insertMessage(String message){
		for(int i=0; i<chatHistory.length-1; i++) {
			chatHistory[i].setText(chatHistory[i+1].getText());
			duration[i] = duration[i+1];
		}
		chatHistory[chatHistory.length-1].setText(message);
		duration[duration.length-1] = System.currentTimeMillis() + chatHistoryDuration;
	}
	
	protected void paintComponent(Graphics g) {
		for(int i=0; i<chatHistory.length; i++) chatHistory[i].setVisible((duration[i]>System.currentTimeMillis()));
	}
}
