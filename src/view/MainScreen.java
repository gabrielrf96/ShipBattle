package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import model.MachinePlayer;
import model.Player;
import view.tools.BoardRenderer;
import view.tools.MessageDispatcher;
import controller.MachineBoardListener;
import controller.Main;
import controller.MenuBarListener;
import controller.PlayerBoardListener;

public class MainScreen extends JFrame{
	
	private static final long serialVersionUID = 6167901931580703511L;
	
	private JMenuBar menu;
	
	private JPanel board;
	private JTable playerBoard;
	private JTable machineBoard;
	private JPanel playerCounterContainer;
	private JLabel playerCounterLabel;
	private JTextField playerShipCounter;
	private JPanel machineCounterContainer;
	private JLabel machineCounterLabel;
	private JTextField machineShipCounter;
	
	private JScrollPane scroll;
	private JTextArea messageDisplayer;
	
	private MenuBarListener menuBarListener = new MenuBarListener();
	private MachineBoardListener machineBoardListener = new MachineBoardListener();
	private PlayerBoardListener playerBoardListener = new PlayerBoardListener();
	private BoardRenderer renderer = new BoardRenderer();
	
	private Component[] resizableComponents;

	public JTable getPlayerBoard() {
		return playerBoard;
	}

	public JTable getMachineBoard() {
		return machineBoard;
	}

	public PlayerBoardListener getPlayerBoardListener() {
		return playerBoardListener;
	}

	public MachineBoardListener getMachineBoardListener() {
		return machineBoardListener;
	}

	public JLabel getPlayerCounterLabel() {
		return playerCounterLabel;
	}
	
	public Component[] getResizableComponents() {
		return resizableComponents;
	}

	public MainScreen() {
		super(Main.lang.getString("title"));
		this.setLayout(new BorderLayout());

		buildMenuBar();
		buildBoard();
		buildMessageDisplayer();
		
		resizableComponents = new Component[]{playerBoard, machineBoard};
		
		this.setIconImage(new ImageIcon(getClass().getResource("images/icon.png")).getImage());
		this.setMinimumSize(new Dimension(820, 670));
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screen.width/2-this.getWidth()/2, screen.height/2-this.getHeight()/2);
		setDefaultCloseOperation();
		this.setVisible(true);
		startCellRendering();
	}
	
	private void buildMenuBar() {
		menu = new JMenuBar();
		buildGameMenu();
		buildLangMenu();
		buildInfoMenu();
		this.setJMenuBar(menu);
	}

	private void buildGameMenu() {
		JMenu game = new JMenu(Main.lang.getString("game"));
		String[] names = {"newGame", "saveGame", "loadGame", "exit"};
		for (String name: names) {
			JMenuItem item = new JMenuItem(Main.lang.getString(name), 
					new ImageIcon(getClass().getResource("images/" + name + ".png")));
			item.setName(name);
			item.addActionListener(menuBarListener);
			game.add(item);
		}
		menu.add(game);
	}
	
	private void buildLangMenu() {
		JMenu lang = new JMenu(Main.lang.getString("lang"));
		String[] langs = new File(Main.appRoot + "/rsrc").list();
		lang.add(buildLangItem("LangResources_en"));
		if (langs != null)
			for (String langu: langs)
				if (langu.startsWith("Lang") && !langu.contains("LangResources_en"))
					lang.add(buildLangItem(langu));
		lang.setName("lang");
		menu.add(lang);
	}
	
	private JMenuItem buildLangItem(String lng) {
		JMenuItem item = new JMenuItem(new Locale(lng.substring(14, 16))
				.getDisplayLanguage(Main.lang.getLocale()).toUpperCase());
		item.setName(lng.substring(14, 16));
		item.addActionListener(menuBarListener);
		return item;
	}
	
	private void buildInfoMenu() {
		JMenu info = new JMenu("Info");
		String[] names = {"Info", "Version"};
		for (String name: names) {
			JMenuItem item = new JMenuItem(name, 
					new ImageIcon(getClass().getResource("images/info.png")));
			item.setName(name);
			item.addActionListener(menuBarListener);
			info.add(item);
		}
		menu.add(info);
	}

	private void buildBoard() {
		board = new JPanel(new BorderLayout());
		buildCounters();
		buildTables();
		JPanel counters = new JPanel(new FlowLayout(FlowLayout.CENTER, 290, 10));
		counters.add(machineCounterContainer);
		counters.add(playerCounterContainer);
		JPanel tables = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 10));
		tables.add(machineBoard);
		tables.add(playerBoard);
		board.add(counters, BorderLayout.NORTH);
		board.add(tables, BorderLayout.CENTER);
		this.add(board, BorderLayout.CENTER);
	}
	
	private void buildCounters() {		
		machineCounterContainer = new JPanel();
		machineCounterLabel = new JLabel(Main.lang.getString("machine"));
		machineShipCounter = new JTextField(1);
		
		playerCounterContainer = new JPanel();
		playerCounterLabel = new JLabel(Main.lang.getString("player"));
		playerShipCounter = new JTextField(1);
		
		JComponent[][] components = { {machineCounterContainer, machineShipCounter, machineCounterLabel},
				{playerCounterContainer, playerShipCounter, playerCounterLabel} };
		
		for (JComponent[] comp: components) {
			JPanel container = (JPanel) comp[0];
			JTextField counter = (JTextField) comp[1];
			JLabel label = (JLabel) comp[2];
			container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
			container.add(label); //Add label
			counter.setBackground(Color.WHITE);
			counter.setFont(counter.getFont().deriveFont(64.0F));
			counter.setHorizontalAlignment(JTextField.CENTER);
			counter.setEditable(false);
			container.add(counter);
			container.setPreferredSize(new Dimension(110, 110));
		}
	}

	private void buildTables() {
		Border compoundBorder = BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLUE.darker(), 4), 
				BorderFactory.createEtchedBorder());
		playerBoard = new JTable(new DefaultTableModel(5, 5));
		machineBoard = new JTable(new DefaultTableModel(5, 5));
		JTable[] tables = {playerBoard, machineBoard};
		for (JTable table: tables) {
			table.setName(table.equals(playerBoard) ? "playerBoard":"machineBoard");
			table.setPreferredSize(new Dimension(300, 300));
			table.setRowHeight(300/5);
			table.setBackground(Color.CYAN.darker());
			table.setBorder(compoundBorder);
			table.setDefaultEditor(Object.class, null);
			table.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}
	
	private void buildMessageDisplayer() {
		messageDisplayer = new JTextArea();
		messageDisplayer.setForeground(Color.BLUE.darker());
		messageDisplayer.setFont(messageDisplayer.getFont().deriveFont(Font.BOLD, 14.0F));
		messageDisplayer.setBackground(Color.WHITE);
		messageDisplayer.setMargin(new Insets(20, 40, 20, 40));
		scroll = new JScrollPane(messageDisplayer);
		scroll.setPreferredSize(new Dimension(0, 140));
		this.add(scroll, BorderLayout.SOUTH);
	}
	
	private void setDefaultCloseOperation() {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				if (MessageDispatcher.displayExitConfirmDialog() == 0)
					System.exit(0);
			}
			
		});
	}

	private void startCellRendering() {
		machineBoard.setDefaultRenderer(Object.class, renderer);
		playerBoard.setDefaultRenderer(Object.class, renderer);
	}
	
	public void addMessageToDisplayer(String text) {
		messageDisplayer.append("\n\n" + text);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
			}
		});
	}
	
	public void refreshCounters(Player player, MachinePlayer machine) {
		playerShipCounter.setText(player.getShips().toString());
		machineShipCounter.setText(machine.getShips().toString());
	}

	public void refreshBoards(Integer[][] playerMatrix, Integer[][] machineMatrix) {
		Object[] names = {"a","b","c","d","e"};
		playerBoard.setModel(new DefaultTableModel(playerMatrix, names));
		machineBoard.setModel(new DefaultTableModel(machineMatrix, names));
	}
	
	public void clearMessageDisplayer() {
		messageDisplayer.setText("");
	}
	
	public void clearBoardListeners(){
		playerBoard.removeMouseListener(playerBoardListener);
		machineBoard.removeMouseListener(machineBoardListener);
	}
	
}
