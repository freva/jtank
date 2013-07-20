package gui.menus;

import game.Level;
import game.LevelData;
import game.Game;
import gui.Main;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.ConnectException;

import javax.swing.*;

import tools.Validator;
import networking.Client;
import networking.Server;


public class MenuPlay extends AbstractMenu implements ActionListener {
	private JTextField NicknameField;
	private JTextField IPAddressField;
	private JComboBox<LevelData> levelSelector = new JComboBox<LevelData>(LevelData.values());
	private JLabel levelPreview = new JLabel();
	
	public MenuPlay() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 80, 280, 80, 30};
		gridBagLayout.rowHeights = new int[]{70, 5, 40, 5, 40, 5, 40, 165, 40, 5, 40, 30};
		setLayout(gridBagLayout);
		
		JLabel lblMultiplayer = new JLabel("Multiplayer");
		lblMultiplayer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_lblMultiplayer = new GridBagConstraints();
		gbc_lblMultiplayer.insets = new Insets(0, 0, 5, 5);
		gbc_lblMultiplayer.gridx = 2;
		gbc_lblMultiplayer.gridy = 0;
		add(lblMultiplayer, gbc_lblMultiplayer);
		
		JSeparator separatorTitleNick = new JSeparator();
		GridBagConstraints gbc_separatorTitleNick = new GridBagConstraints();
		gbc_separatorTitleNick.gridwidth = 3;
		gbc_separatorTitleNick.fill = GridBagConstraints.BOTH;
		gbc_separatorTitleNick.insets = new Insets(0, 0, 5, 5);
		gbc_separatorTitleNick.gridx = 1;
		gbc_separatorTitleNick.gridy = 1;
		add(separatorTitleNick, gbc_separatorTitleNick);		
		
		JLabel lblNickname = new JLabel("Nickname:");
		GridBagConstraints gbc_lblNickname = new GridBagConstraints();
		gbc_lblNickname.insets = new Insets(0, 0, 5, 5);
		gbc_lblNickname.gridx = 1;
		gbc_lblNickname.gridy = 2;
		add(lblNickname, gbc_lblNickname);
		
		NicknameField = new JTextField();
		GridBagConstraints gbc_NicknameField = new GridBagConstraints();
		gbc_NicknameField.insets = new Insets(0, 0, 5, 5);
		gbc_NicknameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_NicknameField.gridx = 2;
		gbc_NicknameField.gridy = 2;
		add(NicknameField, gbc_NicknameField);
		NicknameField.setColumns(10);
		
		JSeparator separatorNickJoin = new JSeparator();
		GridBagConstraints gbc_separatorNickJoin = new GridBagConstraints();
		gbc_separatorNickJoin.gridwidth = 3;
		gbc_separatorNickJoin.fill = GridBagConstraints.BOTH;
		gbc_separatorNickJoin.insets = new Insets(0, 0, 5, 5);
		gbc_separatorNickJoin.gridx = 1;
		gbc_separatorNickJoin.gridy = 3;
		add(separatorNickJoin, gbc_separatorNickJoin);
		
		JLabel lblServerAddress = new JLabel("Server Address:");
		GridBagConstraints gbc_lblServerAddress = new GridBagConstraints();
		gbc_lblServerAddress.anchor = GridBagConstraints.EAST;
		gbc_lblServerAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerAddress.gridx = 1;
		gbc_lblServerAddress.gridy = 4;
		add(lblServerAddress, gbc_lblServerAddress);
		
		IPAddressField = new JTextField();
		IPAddressField.setText("127.0.0.1");
		GridBagConstraints gbc_IPAddressField = new GridBagConstraints();
		gbc_IPAddressField.insets = new Insets(0, 0, 5, 5);
		gbc_IPAddressField.fill = GridBagConstraints.HORIZONTAL;
		gbc_IPAddressField.gridx = 2;
		gbc_IPAddressField.gridy = 4;
		add(IPAddressField, gbc_IPAddressField);
		IPAddressField.setColumns(10);
		
		JButton btnJoinGame = new JButton("Join Game");
		btnJoinGame.addActionListener(new ButtonJoinGame());
		GridBagConstraints gbc_btnJoinGame = new GridBagConstraints();
		gbc_btnJoinGame.insets = new Insets(0, 0, 5, 5);
		gbc_btnJoinGame.gridx = 3;
		gbc_btnJoinGame.gridy = 4;
		add(btnJoinGame, gbc_btnJoinGame);
		
		JSeparator separatorJoinCreate = new JSeparator();
		GridBagConstraints gbc_separatorJoinCreate = new GridBagConstraints();
		gbc_separatorJoinCreate.gridwidth = 3;
		gbc_separatorJoinCreate.fill = GridBagConstraints.BOTH;
		gbc_separatorJoinCreate.insets = new Insets(0, 0, 5, 5);
		gbc_separatorJoinCreate.gridx = 1;
		gbc_separatorJoinCreate.gridy = 5;
		add(separatorJoinCreate, gbc_separatorJoinCreate);

		JLabel lblMap = new JLabel("Map:");
		GridBagConstraints gbc_lblMap = new GridBagConstraints();
		gbc_lblMap.anchor = GridBagConstraints.EAST;
		gbc_lblMap.insets = new Insets(0, 0, 5, 5);
		gbc_lblMap.gridx = 1;
		gbc_lblMap.gridy = 6;
		add(lblMap, gbc_lblMap);

		levelSelector.addActionListener(this);
		GridBagConstraints gbc_levelSelector = new GridBagConstraints();
		gbc_levelSelector.fill = GridBagConstraints.HORIZONTAL;
		gbc_levelSelector.gridx = 2;
		gbc_levelSelector.gridy = 6;
		add(levelSelector, gbc_levelSelector);

		levelPreview.setIcon(new ImageIcon(levelSelector.getItemAt(levelSelector.getSelectedIndex()).getLevelPreviewURL()));
		GridBagConstraints gbc_levelPreview = new GridBagConstraints();
		gbc_levelPreview.gridx = 2;
		gbc_levelPreview.gridy = 7;
		add(levelPreview, gbc_levelPreview);

		JButton btnCreateNewGame = new JButton("Create New Game");
		btnCreateNewGame.addActionListener(new ButtonCreateGame());
		GridBagConstraints gbc_btnCreateNewGame = new GridBagConstraints();
		gbc_btnCreateNewGame.fill = GridBagConstraints.BOTH;
		gbc_btnCreateNewGame.insets = new Insets(0, 0, 5, 5);
		gbc_btnCreateNewGame.gridx = 2;
		gbc_btnCreateNewGame.gridy = 8;
		add(btnCreateNewGame, gbc_btnCreateNewGame);
		
		JSeparator separatorCreateBack = new JSeparator();
		GridBagConstraints gbc_separatorCreateBack = new GridBagConstraints();
		gbc_separatorCreateBack.gridwidth = 3;
		gbc_separatorCreateBack.fill = GridBagConstraints.BOTH;
		gbc_separatorCreateBack.insets = new Insets(0, 0, 5, 5);
		gbc_separatorCreateBack.gridx = 1;
		gbc_separatorCreateBack.gridy = 9;
		add(separatorCreateBack, gbc_separatorCreateBack);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ButtonBack());
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.fill = GridBagConstraints.BOTH;
		gbc_btnBack.insets = new Insets(0, 0, 5, 5);
		gbc_btnBack.gridx = 2;
		gbc_btnBack.gridy = 11;
		add(btnBack, gbc_btnBack);
	}

	public void actionPerformed(ActionEvent e) {
		levelPreview.setIcon(new ImageIcon(levelSelector.getItemAt(levelSelector.getSelectedIndex()).getLevelPreviewURL()));
	}

	class ButtonBack implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			Main.main.removeTopMenu();
		}
	}
	
	class ButtonJoinGame implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(NicknameField.getText().length() == 0) JOptionPane.showMessageDialog(Main.mainFrame, "Please enter a nickname", "Error", JOptionPane.ERROR_MESSAGE);
			else {
				if(Validator.isValidIPv4(IPAddressField.getText())){
					try {
						new Client(IPAddressField.getText(), NicknameField.getText());

						Main.main.hideMenu();
						Main.mainFrame.add(new Game(NicknameField.getText()));
					} catch (ConnectException e1) {
						JOptionPane.showMessageDialog(Main.mainFrame, "Could not reach " + IPAddressField.getText(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else JOptionPane.showMessageDialog(Main.mainFrame, "Invalid IPv4 address!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class ButtonCreateGame implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(NicknameField.getText().length() == 0) {
				JOptionPane.showMessageDialog(Main.mainFrame, "Please enter a nickname", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				Level.setInstance(levelSelector.getItemAt(levelSelector.getSelectedIndex()));
				
				Main.main.hideMenu();
				Main.mainFrame.add(new Game(NicknameField.getText()));
				Server.startServer();
			}
		}
	}
}
