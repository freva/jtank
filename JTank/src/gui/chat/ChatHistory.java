package gui.chat;

import gui.Main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatHistory extends JPanel {
	private static JTextField textField;
	private static JTextArea textArea;
	
	public ChatHistory() {
		this.setOpaque(false);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{500};
		gridBagLayout.rowHeights = new int[]{150, 30};
		setLayout(gridBagLayout);
		
		textArea = new JTextArea();
		textArea.setBackground(Chat.blackTransp);
		textArea.setForeground(Chat.whiteSolid);
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(500, 150));
		scrollPane.setOpaque(false);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 0;
		add(scrollPane, gbc_textArea);
		
		textField = new JTextField();
		textField.setBackground(Chat.blackTransp);
		textField.setForeground(Chat.whiteSolid);
		textField.setFocusable(true);
		textField.addActionListener(new Chat.MessageInput());
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		add(textField, gbc_textField);
	}
	
	public void getFocus() {
		textField.requestFocusInWindow();
	}
	
	public String getInputText() {
		return textField.getText();
	}
	
	public void clearInputText() {
		textField.setText("");
		Main.mainFrame.requestFocusInWindow();
	}
	
	public void addText(String s) {
		textArea.append("\n" + s);
	}
}
