import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoWindow extends Frame implements WindowListener {

	private static final long serialVersionUID = 1L;
	public static String releaseDate = "26.05.2018";
	public static String name = "Kreaverse RSS Reader";
	public static String version = "v 0.9.9.9.9";
	public static String copy = "(c) Kreavita 2018";
	public static String[] strings;

	public Image icon = new ImageIcon(getClass().getResource("rss.gif")).getImage();

	InfoWindow() {
		super(name);
		this.setIconImage(icon);
		createStrings();
		this.setTitle(strings[10] + " " + name);
		this.setSize(new Dimension(500, 250));
		this.setResizable(false);
		this.addWindowListener(this);
		this.setLayout(new BorderLayout());
		JLabel title = new JLabel("    " + name.toUpperCase(Locale.GERMAN));
		title.setFont(MainWindow.fonts[0]);
		title.setForeground(MainWindow.color);
		JLabel line1 = new JLabel("    " + strings[9] + ": " + version);
		line1.setFont(MainWindow.fonts[1]);
		line1.setForeground(MainWindow.color);
		JLabel line2 = new JLabel("    " + strings[8] + ": " + releaseDate);
		line2.setFont(MainWindow.fonts[1]);
		line2.setForeground(MainWindow.color);
		JLabel line3 = new JLabel("    " + copy);
		line3.setFont(MainWindow.fonts[1]);
		line3.setForeground(MainWindow.color);

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
		titlePanel.setPreferredSize(new Dimension(Short.MAX_VALUE, 80));
		titlePanel.setBackground(MainWindow.buttonBackgroundColor);
		titlePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		titlePanel.add(title);
		titlePanel.add(Box.createHorizontalGlue());
		titlePanel.add(Box.createHorizontalGlue());
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
		contentPanel.setBackground(MainWindow.backgroundColor);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		contentPanel.add(line1);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		contentPanel.add(line2);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		contentPanel.add(line3);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
		this.add(titlePanel, BorderLayout.NORTH);
		this.add(contentPanel, BorderLayout.CENTER);
	}

	private void createStrings() {
		if (Locale.getDefault() != Locale.GERMAN && Locale.getDefault() != Locale.GERMANY) {
			strings = new String[] { "SETTINGS", "REFRESH ALL", "This feed source is already in your collection",
					"Middle click the article to open it",
					"There is no feed source to show yet. Head to settings and add one.", "Neuer RSS Artikel",
					"Username", "Password", "Releasedate", "Version", "About", "Show/Hide window", "Quit",
					"App was minimized to System Tray. " + "This behaviour can be disabled in Settings.", "COMMON" };
		} else {
			strings = new String[] { "EINSTELLUNGEN", "ALLE AKTUALISIEREN",
					"Diese Feedquelle ist schon in deiner Sammlung", "Mittelklicke den Artikel um ihn zu Öffnen",
					"Du hast noch keine RSS-Feedquelle hinzugefügt. Gehe zu EINSTELLUNGEN und füge dort eine hinzu.",
					"Neuer RSS Artikel", "Nutzername", "Passwort", "Veröffentlichungsdatum", "Version", "Info zu",
					"Fenster Anzeigen/Verstecken", "Beenden", "App wurde in den System Tray minimiert. "
							+ "Dieses Verhalten kann in den Einstellungen deaktiviert werden",
					"ALLGEMEIN" };
		}
		// id 6 = Nutzername
		// id 13 = minimize verhalten
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		this.setVisible(false);

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		this.setVisible(false);

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
}
