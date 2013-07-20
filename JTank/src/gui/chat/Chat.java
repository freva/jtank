package gui.chat;

import game.Game;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Chat extends JPanel {
	private static Chat chat;
	public static Color blackTransp = new Color(0, 0, 0, 180);
	public static Color whiteSolid = new Color(255, 255, 255);
	
	private static ChatCurrent cc = new ChatCurrent();
	private static ChatHistory ch = new ChatHistory();
	
	private Chat() {
		chat = this;
		this.setOpaque(false);
				
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{500, 770};
		gridBagLayout.rowHeights = new int[]{200, 150, 180, 190};
		setLayout(gridBagLayout);
		
		GridBagConstraints gbc_cc = new GridBagConstraints();
		gbc_cc.gridx = 0;
		gbc_cc.gridy = 1;
		add(cc, gbc_cc);
		
		GridBagConstraints gbc_ch = new GridBagConstraints();
		gbc_ch.gridx = 0;
		gbc_ch.gridy = 2;
		add(ch, gbc_ch);
		
		showChatBox(false);
	}
	
	public void showChatBox(boolean showBox) {
		ch.setVisible(showBox);
		cc.setVisible(!showBox);
		
		if(showBox) ch.getFocus();
	}
	
	public void addMessage(String from, String msg) {
		String tot = from + ": " + msg;
		ch.addText(tot);
		cc.insertMessage(tot);
	}
	
	public static void changeChatBoxState() {
		if(chat != null) chat.showChatBox(cc.isVisible());
	}
	
	public static Chat getInstance() {
		if(chat == null) chat = new Chat();
		return chat;
	}
	
	public static class MessageInput implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(ch.getInputText().length() != 0) {
				Game.getInstance().addUpdate("0£" + Game.getInstance().getPlayer().getUsername() + "@" + ch.getInputText());
				Chat.getInstance().addMessage(Game.getInstance().getPlayer().getUsername(), ch.getInputText());
				ch.clearInputText();
			}
			Chat.changeChatBoxState();
		}
	}
}
