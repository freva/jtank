package gui.menus;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MenuMain extends AbstractMenu {
	public MenuMain() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{135, 230, 135};
		gridBagLayout.rowHeights = new int[]{70, 150, 40, 40, 40, 70};
		setLayout(gridBagLayout);
		
		ImageIcon logo = new ImageIcon("res/Logo.png");
		GridBagConstraints gbc_imageLogo = new GridBagConstraints();
		gbc_imageLogo.insets = new Insets(0, 0, 20, 0);
		gbc_imageLogo.gridx = 1;
		gbc_imageLogo.gridy = 1;
		add(new JLabel(logo), gbc_imageLogo);
		
		JButton btnSingleplayer = new JButton("Singleplayer");
		btnSingleplayer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnSingleplayer = new GridBagConstraints();
		gbc_btnSingleplayer.fill = GridBagConstraints.BOTH;
		gbc_btnSingleplayer.insets = new Insets(5, 0, 5, 0);
		gbc_btnSingleplayer.gridx = 1;
		gbc_btnSingleplayer.gridy = 2;
		add(btnSingleplayer, gbc_btnSingleplayer);
		
		JButton btnMultiplayer = new JButton("Multiplayer");
		btnMultiplayer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnMultiplayer = new GridBagConstraints();
		gbc_btnMultiplayer.fill = GridBagConstraints.BOTH;
		gbc_btnMultiplayer.insets = new Insets(5, 0, 5, 0);
		gbc_btnMultiplayer.gridx = 1;
		gbc_btnMultiplayer.gridy = 3;
		add(btnMultiplayer, gbc_btnMultiplayer);
		
		JButton btnOptions = new JButton("Options");
		btnOptions.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnOptions = new GridBagConstraints();
		gbc_btnOptions.fill = GridBagConstraints.BOTH;
		gbc_btnOptions.insets = new Insets(5, 0, 5, 0);
		gbc_btnOptions.gridx = 1;
		gbc_btnOptions.gridy = 4;
		add(btnOptions, gbc_btnOptions);
	}
}
