package gui.menus;

import gui.Main;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuMain extends AbstractMenu {
	public MenuMain() {	
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{135, 230, 135};
		gridBagLayout.rowHeights = new int[]{70, 190, 40, 40, 40, 70};
		setLayout(gridBagLayout);
		
		JLabel logo = new JLabel(new ImageIcon(Main.class.getResource("res/Logo.png")));
		GridBagConstraints gbc_imageLogo = new GridBagConstraints();
		gbc_imageLogo.insets = new Insets(0, 0, 20, 0);
		gbc_imageLogo.gridx = 1;
		gbc_imageLogo.gridy = 1;
		add(logo, gbc_imageLogo);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnPlay.addActionListener(new Play());
		GridBagConstraints gbc_btnPlay = new GridBagConstraints();
		gbc_btnPlay.fill = GridBagConstraints.BOTH;
		gbc_btnPlay.insets = new Insets(5, 0, 5, 0);
		gbc_btnPlay.gridx = 1;
		gbc_btnPlay.gridy = 2;
		add(btnPlay, gbc_btnPlay);
		
		JButton btnOptions = new JButton("Options");
		btnOptions.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnOptions.addActionListener(new Options());
		GridBagConstraints gbc_btnOptions = new GridBagConstraints();
		gbc_btnOptions.fill = GridBagConstraints.BOTH;
		gbc_btnOptions.insets = new Insets(5, 0, 5, 0);
		gbc_btnOptions.gridx = 1;
		gbc_btnOptions.gridy = 3;
		add(btnOptions, gbc_btnOptions);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new Exit());
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.fill = GridBagConstraints.BOTH;
		gbc_btnExit.insets = new Insets(5, 0, 5, 0);
		gbc_btnExit.gridx = 1;
		gbc_btnExit.gridy = 4;
		add(btnExit, gbc_btnExit);
	}
	
	class Play implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			Main.main.addMenuToForeground(new MenuPlay());
		}
	}
	
	class Options implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

		}
	}
	
	class Exit implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}
}
