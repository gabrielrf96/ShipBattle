package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

import model.Game;
import view.tools.TextFieldFilter;
import controller.Main;

public class NewGameScreen extends JDialog {
	
	private static final long serialVersionUID = 3876402011293392993L;
	private JLabel playerNameLabel;
	private JTextField playerNameField;
	private JLabel difficultyLabel;
	private ButtonGroup difficulty;
	private JTextArea easyDescription;
	private JRadioButton easy;
	private JTextArea mediumDescription;
	private JRadioButton medium;
	private JTextArea hardDescription;
	private JRadioButton hard;
	private JButton acceptButton;

	public NewGameScreen() {
		this.setLayout(new FlowLayout());
		
		buildPlayerNameSection();
		buildDifficultySection();
		buildAcceptButton();
		
		this.setTitle(Main.lang.getString("newGame"));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setSize(340, 550);
		this.setLocationRelativeTo(Main.getGui());
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void buildPlayerNameSection() {
		Icon icon = new ImageIcon(getClass().getResource("images/keyboard.png"));
		playerNameLabel = new JLabel(Main.lang.getString("enterName"), icon, SwingUtilities.CENTER);
		playerNameLabel.setFont(playerNameLabel.getFont().deriveFont(Font.BOLD, 15.0F));
		playerNameLabel.setLabelFor(playerNameField);
		playerNameField = new JTextField(15);
		playerNameField.setHorizontalAlignment(JTextField.CENTER);
		playerNameField.setFont(playerNameField.getFont().deriveFont(Font.BOLD, 14.0F));
		((AbstractDocument) playerNameField.getDocument()).setDocumentFilter(new TextFieldFilter(12));
		playerNameField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				if (playerNameField.getText().length() == 12) {
					Toolkit.getDefaultToolkit().beep();
					playerNameField.setBackground(new Color(250, 180, 180));
				} else
					playerNameField.setBackground(Color.WHITE);
			}
		});
		JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		container.setPreferredSize(new Dimension(320, 90));
		container.add(playerNameLabel);
		container.add(playerNameField);
		this.add(container);
	}

	private void buildDifficultySection() {
		buildDifficultyDescriptions();
		buildDifficultyButtons();
		this.add(difficultyLabel);
		this.add(easy);
		this.add(easyDescription);
		this.add(medium);
		this.add(mediumDescription);
		this.add(hard);
		this.add(hardDescription);
	}
	
	private void buildDifficultyDescriptions() {
		Icon icon = new ImageIcon(getClass().getResource("images/icon.png"));
		difficultyLabel = new JLabel(Main.lang.getString("selectDifficulty"), icon, SwingUtilities.CENTER);
		difficultyLabel.setFont(difficultyLabel.getFont().deriveFont(Font.BOLD, 15.0F));
		difficultyLabel.setPreferredSize(new Dimension(320, 50));
		easyDescription = new JTextArea(Main.lang.getString("easyDescription"));
		mediumDescription = new JTextArea(Main.lang.getString("mediumDescription"));
		hardDescription = new JTextArea(Main.lang.getString("hardDescription"));
		for (JTextArea ta: new JTextArea[]{easyDescription, mediumDescription, hardDescription}) {
			ta.setEditable(false);
			ta.setLineWrap(true);
			ta.setWrapStyleWord(true);
			ta.setColumns(17);
			ta.setFont(ta.getFont().deriveFont(14.0F));
			ta.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createBevelBorder(BevelBorder.RAISED), 
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			ta.setBackground(this.getBackground());
		}
	}
	
	private void buildDifficultyButtons() {
		difficulty = new ButtonGroup();
		easy = new JRadioButton(Main.lang.getString("easy"));
		medium = new JRadioButton(Main.lang.getString("medium"), true);
		hard = new JRadioButton(Main.lang.getString("hard"));
		int currentDifficulty = 0;
		for (JRadioButton rb: new JRadioButton[]{easy, medium, hard}) {
			rb.setPreferredSize(new Dimension(85, 20));
			rb.setFont(easy.getFont().deriveFont(14.0F));
			rb.setMnemonic(currentDifficulty++);
			difficulty.add(rb);
		}
	}
	
	private void buildAcceptButton() {
		acceptButton = new JButton(Main.lang.getString("start"));
		acceptButton.setFont(acceptButton.getFont().deriveFont(Font.BOLD, 16.0F));
		acceptButton.setBackground(Color.WHITE);
		acceptButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String playerName = playerNameField.getText();
				int difficultyLevel = difficulty.getSelection().getMnemonic();
				Main.setGame(new Game(playerName.equals("") ? 
						Main.lang.getString("player"):playerName, difficultyLevel));
				NewGameScreen.this.dispose();
			}
			
		});
		this.add(Box.createRigidArea(new Dimension(320, 10)));
		this.add(acceptButton);
	}
	
}
