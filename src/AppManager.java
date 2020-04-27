import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AppManager implements IActionHandler {

	public static List<String> feedURLS = new ArrayList<String>();
	public static List<List<String[]>> feedData = new ArrayList<List<String[]>>();
	public static List<String> feedNames = new ArrayList<String>();

	public static String dirpath = System.getenv("APPDATA") + "\\KreaverseRSSReader\\";

	public static int activeFeed = -1;
	public static boolean comboIsIdle = true;

	public static Proxy proxy;
	public static String user = " ";
	public static String password = " ";

	public static boolean notifcationsEnabled = true;
	public static boolean hideOnstart = false;
	public static boolean hideOnMinimize = true;
	public static int reloadTimer = 5 * 60;

	public static Timer timer = new Timer();

	private void restore() {
		// Java can handle Proxy with start arguments, but for ease of use in my high school, i set up the proxy within the app
		// proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy_url, proxy_port));
		proxy = null
		String os = System.getProperty("os.name").toUpperCase();
		if (os.contains("MAC")) {
			dirpath = System.getProperty("user.home") + "/Library/Application Support/KreaverseRSSReader/";
		}
		if (os.contains("NUX")) {
			dirpath = System.getProperty("user.dir") + ".KreaverseRSSReader/";
		}
		if (Files.exists(Paths.get(dirpath + "config.txt"))) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File(dirpath + "config.txt")));
				if (proxy != null) {
					user = br.readLine();
					password = br.readLine();
				} else {
					br.readLine();
					br.readLine();
				}
				notifcationsEnabled = Boolean.parseBoolean(br.readLine());
				hideOnstart = Boolean.parseBoolean(br.readLine());
				hideOnMinimize = Boolean.parseBoolean(br.readLine());
				br.readLine();
				// reloadTimer = Integer.parseInt(br.readLine());

				String line;
				while ((line = br.readLine()) != null) {
					addFeed(line);
				}
				br.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Could not restore your settings! \n An error occured while trying to restore your settings.",
						"File System Error", JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
			}
		}
		checkProxy();
	}

	private List<String[]> parseFeed(String url) {
		List<String[]> newslist = new ArrayList<String[]>();
		try {
			URLConnection urc = handleConnection(url);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(urc.getInputStream());
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("item");
			for (int i = 0; i < nList.getLength(); i++) {
				NodeList itemChilds = nList.item(i).getChildNodes();
				String date = "";
				String content = "";
				String title = "";
				String link = "";
				for (int j = 0; j < itemChilds.getLength(); j++) {
					if (itemChilds.item(j).getNodeName() == "title") {
						title = itemChilds.item(j).getTextContent();
					} else if (itemChilds.item(j).getNodeName() == "description") {
						content = itemChilds.item(j).getTextContent();
					} else if (itemChilds.item(j).getNodeName() == "pubDate") {
						date = itemChilds.item(j).getTextContent();
					} else if (itemChilds.item(j).getNodeName() == "link") {
						link = itemChilds.item(j).getTextContent();
					}
				}
				newslist.add(new String[] { date, title.trim(), content.trim(), link });
			}
			return newslist;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Could not Load the Selected Feed! \nThe selected Feed contains formatting errors and could not be parsed",
					"Malformatted Feed", JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}

	private void checkProxy() {
		// Normally, this method would try to connect to the proxy and ask if it should connect without it, when the proxy is not reachable
		if (AppManager.proxy != null) {
			boolean fail = true;
			while (fail) {
				if ((password == " " || user == " ")) {
					LoginWindow lw = new LoginWindow();
					lw.bline.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							password = new String(lw.pass.getPassword());
							user = lw.user.getText();
							lw.setVisible(false);
						}
					});
				}
				while (password == " " || user == " ") {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				Authenticator.setDefault(new Authenticator() {

					public PasswordAuthentication getPasswordAuthentication() {
						return (new PasswordAuthentication(user, password.toCharArray()));
					}
				});
				try {
					new URL("http://8.8.8.8/").openConnection(AppManager.proxy).getInputStream();
					fail = false;
				} catch (IOException e1) {
					e1.printStackTrace();
					int ok = JOptionPane.showConfirmDialog(null,
							"Wrong username or password?\n Yes to retry or No to disable Proxy",
							"Connection though Proxy failed", JOptionPane.YES_NO_OPTION);
					if (ok == 1) {
						fail = false;
						proxy = null;

					} else {
						password = " ";
						user = " ";
						fail = true;
					}
				}
			}
		}
	}

	public URLConnection handleConnection(String url) {
		if (proxy == null) {
			try {
				return new URL(url).openConnection();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"An Error occured while establishing the Connection. \n Check your Network Connectivity!",
						"Connection Error", JOptionPane.WARNING_MESSAGE);
				return null;
			}
		} else {
			Authenticator.setDefault(new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return (new PasswordAuthentication(user, password.toCharArray()));
				}
			});
			try {
				return new URL(url).openConnection(proxy);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"An Error occured while establishing the Connection. \n Check the Proxy or your Network Connectivity!",
						"Connection Error", JOptionPane.WARNING_MESSAGE);
				return null;
			}
		}
	}

	public void showFeed(int id) {
		if (id > -1 && id < feedData.size()) {
			activeFeed = id;
			JPanel feedBoard = new JPanel();
			feedBoard.setBackground(MainWindow.color);
			feedBoard.setLayout(new BoxLayout(feedBoard, BoxLayout.PAGE_AXIS));

			List<String[]> feed = feedData.get(id);
			for (int i = 0; i < feed.size(); i++) {
				String[] feedItem = feed.get(i);
				feedBoard.add(Box.createRigidArea(new Dimension(0, 10)));
				feedBoard.add(new NewsPanel(feedItem[1], feedItem[2], feedItem[0], feedItem[3]));
				feedBoard.add(Box.createRigidArea(new Dimension(0, 10)));
			}
			JScrollPane scrollpane = new JScrollPane(feedBoard);
			scrollpane.getVerticalScrollBar().setUnitIncrement(16);
			scrollpane.setBorder(BorderFactory.createEmptyBorder());
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					scrollpane.getVerticalScrollBar().setValue(0);
					MainWindow.feedPort.removeAll();
					MainWindow.feedPort.add(scrollpane);
					refreshComboBox();
				}
			});
		} else {
			activeFeed = -1;
			JLabel info = new JLabel(InfoWindow.strings[4]);
			info.setForeground(MainWindow.color);
			info.setHorizontalAlignment(JLabel.CENTER);
			info.setFont(MainWindow.fonts[1]);

			MainWindow.feedPort.removeAll();
			MainWindow.feedPort.add(info);
			refreshComboBox();
		}
	}

	private void refreshComboBox() {
		MainWindow.feedPort.revalidate();
		MainWindow.feedPort.repaint();

		comboIsIdle = false;
		DefaultComboBoxModel<String> dcm = new DefaultComboBoxModel<String>();

		for (int i = 0; i < feedNames.size(); i++) {
			dcm.addElement(feedNames.get(i));
		}

		MainWindow.feedPicker.setModel(dcm);
		MainWindow.feedPicker.setSelectedIndex(activeFeed);
		MainWindow.feedPicker.revalidate();
		MainWindow.feedPicker.repaint();
		comboIsIdle = true;
		if (Main.settingswindow != null) {
			Main.settingswindow.refreshFeedList();
		}
	}

	@Override
	public void init() {
		this.restore();
		showFeed(activeFeed);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (notifcationsEnabled) {
					for (int i = 0; i < feedURLS.size(); i++) {
						String[] tempfeed = parseFeed(feedURLS.get(i)).get(0);

						if (!tempfeed[0].trim().equals(feedData.get(i).get(0)[0].trim())) {
							Main.notificationHandler.articleNotification(feedNames.get(i) + "\n" + tempfeed[1]);
						}
					}
				}
				refresh_all();
			}
		}, 0, 1000 * reloadTimer);
		if (hideOnstart) {
			Main.mainwindow.setVisible(false);
			Main.notificationHandler.systemNotification(InfoWindow.strings[13]);
		} else {
			Main.mainwindow.setVisible(true);
		}
	}

	@Override
	public void quit() {
		try {
			File output = new File(dirpath + "config.txt");
			File outputdir = new File(dirpath);

			if (!outputdir.exists()) {
				outputdir.mkdir();
			}

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));

			bw.write(user);
			bw.newLine();
			bw.write(password);
			bw.newLine();
			bw.write(String.valueOf(notifcationsEnabled));
			bw.newLine();
			bw.write(String.valueOf(hideOnstart));
			bw.newLine();
			bw.write(String.valueOf(hideOnMinimize));
			bw.newLine();
			bw.write(String.valueOf(reloadTimer));
			for (int i = 0; i < feedURLS.size(); i++) {
				bw.newLine();
				bw.write(feedURLS.get(i));
			}

			bw.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"An Error occured while saving. \n Make sure you have enough free space!", "Saving Error",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		System.exit(0);
	}

	@Override
	public void addFeed(String url) {
		if (!feedURLS.contains(url)) {
			try {
				List<String[]> feedData = parseFeed(url);
				URLConnection urc = handleConnection(url);

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(urc.getInputStream());
				doc.getDocumentElement().normalize();

				Node feedinfos = doc.getElementsByTagName("channel").item(0);
				NodeList fiList = feedinfos.getChildNodes();

				AppManager.feedURLS.add(url);
				AppManager.feedNames.add(fiList.item(1).getTextContent().trim());
				AppManager.feedData.add(feedData);

				activeFeed++;
				this.showFeed(activeFeed);

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Could not Load the Selected Feed! \nThe selected Feed contains formatting errors and could not be parsed",
						"Malformatted Feed", JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, InfoWindow.strings[2], "Feed Duplicate",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void refresh_all() {
		for (int i = 0; i < feedNames.size(); i++) {
			feedData.set(i, parseFeed(feedURLS.get(i)));
		}
		showFeed(activeFeed);
	}

	@Override
	public void removeFeed(int id) {
		if (id > -1 && id < feedData.size()) {
			feedData.remove(id);
			feedURLS.remove(id);
			feedNames.remove(id);

			activeFeed--;
			if (id < 1) {
				showFeed(activeFeed);
			} else {
				showFeed(id - 1);
			}
		}
	}

	@Override
	public void changeVisibility() {
		if (Main.mainwindow.isVisible()) {
			Main.mainwindow.setVisible(false);
			Main.notificationHandler.systemNotification(InfoWindow.strings[13]);
		} else {
			Main.mainwindow.setVisible(true);
			Main.mainwindow.setState(Frame.NORMAL);
		}
	}

	@Override
	public void showFeed(String feedName) {
		if (feedNames.contains(feedName)) {
			showFeed(feedNames.indexOf(feedName));
		}
	}
}
