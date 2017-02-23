package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import view.tools.MessageDispatcher;
import controller.Main;

public class ChangelogScreen extends JDialog {

	private static final long serialVersionUID = 3293659214857272843L;
	private Container content;
	private JTextArea display;
	private JScrollPane scroll;
	
	public ChangelogScreen() {
		content = this.getContentPane();
		
		display = new JTextArea();
		display.setEditable(false);
		display.setMargin(new Insets(20, 20, 20, 20));
		display.setBackground(new Color(238, 238, 239));
		
		scroll = new JScrollPane(display);
		content.add(scroll);
		
		displayChangelog();
		
		this.setTitle("Changelog");
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setSize(350, 300);
		this.setLocationRelativeTo(Main.getGui());
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void displayChangelog() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(Main.appRoot + "/version/change.log"));
			display.read(in, "Changelog");
		} catch (FileNotFoundException e) {
			MessageDispatcher.changelogMissingMessage();
		} catch (IOException e) {
			MessageDispatcher.IOErrorMessage();
		}
	}
	
}
