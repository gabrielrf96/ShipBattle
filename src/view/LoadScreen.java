package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import model.Game;
import controller.Main;

public class LoadScreen extends JDialog {

	private static final long serialVersionUID = -1212969477978635387L;
	private Container content;
	private JLabel noElements;
	private List<File> detectedFiles;
	private DefaultListModel<Date> model;
	private JList<Date> list;
	private JScrollPane scroll;
	private JPopupMenu popup;
	
	public LoadScreen() {
		content = this.getContentPane();
		
		buildList();
		buildPopup();
		detectSaveFiles();
		if (detectedFiles.size() == 0)
			buildNoElementsLabel();
		
		this.setTitle(Main.lang.getString("loadGame"));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setSize(300, 300);
		this.setLocationRelativeTo(Main.getGui());
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void buildList() {
		detectedFiles = new ArrayList<File>();
		model = new DefaultListModel<Date>();
		list = new JList<Date>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(new Color(238, 238, 239));
		scroll = new JScrollPane(list);
		content.add(scroll);
		list.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent ev) {
				if (list.getSelectedIndex() >= 0)
					popup.show(list, ev.getX(), ev.getY());
			}
		});
	}

	private void buildPopup() {
		popup = new JPopupMenu();
		JMenuItem loadOption = new JMenuItem(Main.lang.getString("load"));
		JMenuItem deleteOption = new JMenuItem(Main.lang.getString("delete"));
		loadOption.setName("load");
		deleteOption.setName("delete");
		popup.add(loadOption);
		popup.add(deleteOption);
		ActionListener act = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				switch( ((JMenuItem)ev.getSource()).getName() ) {
				case "load":
					LoadScreen.this.dispose();
					Game.loadGame((File) detectedFiles.get(list.getSelectedIndex()));
					break;
				case "delete":
					new File(detectedFiles.get(list.getSelectedIndex()).getPath()).delete();
					detectedFiles.remove(list.getSelectedIndex());
					model.removeElement(list.getSelectedValue());
					break;
				}
			}
		};
		loadOption.addActionListener(act);
		deleteOption.addActionListener(act);
	}

	private void buildNoElementsLabel() {
		noElements = new JLabel(Main.lang.getString("noSaves"), SwingConstants.CENTER);
		noElements.setEnabled(false);
		content.add(noElements);
	}

	private void detectSaveFiles() {
		File[] saves = null;
		try {
			saves = new File(Main.appRoot + "/saves").listFiles();
			for (int i=saves.length-1 ; i>=0 ; i--) {
				try {
					model.addElement(new Date(Long.parseLong(saves[i].getName().replaceAll(".sav", ""))));
					detectedFiles.add(saves[i]);
				} catch (NumberFormatException e) {
					continue; // Current file is not a valid save file, or its name has been altered
				}
			}
		} catch (NullPointerException e) {
			// "saves" folder does not exist.
		}
	}
	
}
