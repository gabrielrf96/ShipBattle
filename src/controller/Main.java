package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import model.Game;
import view.MainScreen;
import view.tools.MessageDispatcher;

public class Main {

	public static String appRoot;		// The directory in which the application JAR is located
	public static ResourceBundle lang;	// Application language resources
	private static MainScreen gui;		// Application GUI
	private static Game game;			// Current game
	
	private static void setAppRoot() {
		 try {
			File location = new File(Main.class.getProtectionDomain().getCodeSource().getLocation()
					.toURI().getPath());
			appRoot = location.getParent();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private static String getLangFromFile() {
		String lang = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(appRoot + "/rsrc/lang")));
			lang = br.readLine();
		} catch (IOException | NullPointerException e) {
			MessageDispatcher.langCorruptedMessage();
			lang = "en";
		} finally {
			try {
				br.close();
			} catch (IOException | NullPointerException e) {
				e.printStackTrace();
			}
		}
		return lang;
	}
	
	public static void setLang(String newLang) {
		BufferedWriter bw = null;
		try {
			URL[] urls = new URL[]{new File(appRoot + "/rsrc").toURI().toURL()};
			lang = ResourceBundle.getBundle("LangResources", new Locale(newLang), 
					newLang.equals("en") ? Main.class.getClassLoader():new URLClassLoader(urls));
			bw = new BufferedWriter(new FileWriter(new File(appRoot + "/rsrc/lang")));
			bw.write(lang.getLocale().getLanguage());
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		} catch (MissingResourceException e) {
			MessageDispatcher.langResourcesMissingMessage(newLang);
			lang = ResourceBundle.getBundle("LangResources", Locale.ENGLISH);
		} finally {
			JOptionPane.setDefaultLocale(lang.getLocale());
			try {
				bw.close();
			} catch (IOException | NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	public static MainScreen getGui() {
		return gui;
	}
	
	public static void setGui(MainScreen newGui) {
		gui = newGui;
	}

	public static Game getGame() {
		return game;
	}

	public static void setGame(Game newGame) {
		game = newGame;
	}
	
	public static void main(String[] args) {
		setAppRoot();
		setLang(getLangFromFile());
		gui = new MainScreen();
	}

}
