import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class SettingsWindow extends Frame implements ActionListener, WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	IActionHandler action = new AppManager();

	private JPanel tabs = new JPanel();
	private JButton tab1 = new JButton(InfoWindow.strings[14]);
	private JButton tab2 = new JButton("FEEDS");

	private CardLayout cardManager = new CardLayout();

	private JTextField feedurl = new JTextField("");
	private JButton addfeed = new JButton("+");
	
	private JPanel content = new JPanel();
	
	private JButton notifcationsEnabled = new JButton();
	private JButton hideOnMinimize = new JButton();
	private JButton visibleOnStart = new JButton();
	
	private JPanel feedList = new JPanel();
	private JScrollPane feedScroller;

	public SettingsWindow() {
		super(InfoWindow.name + " " + InfoWindow.strings[0]);
		setControls();
		this.setIconImage(Main.info.icon);
		this.setLayout(new BorderLayout());
		this.setSize(800, 800);
		this.setResizable(false);
		this.addWindowListener(this);

		tab1.setBorder(BorderFactory.createEmptyBorder());
		tab1.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
		tab1.setMinimumSize(new Dimension(10000, 50));
		tab1.setMaximumSize(new Dimension(10000, 50));
		tab1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		tab1.addActionListener(this);

		tab2.setBorder(BorderFactory.createEmptyBorder());
		tab2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
		tab2.setMinimumSize(new Dimension(10000, 50));
		tab2.setMaximumSize(new Dimension(10000, 50));
		tab2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		tab2.addActionListener(this);

		tab1.setBackground(MainWindow.color);
		tab1.setForeground(MainWindow.buttonBackgroundColor);
		tab2.setBackground(MainWindow.buttonBackgroundColor);
		tab2.setForeground(MainWindow.color);

		tabs.setOpaque(false);
		tabs.setLayout(new BoxLayout(tabs, BoxLayout.LINE_AXIS));
		tabs.setPreferredSize(new Dimension(Short.MAX_VALUE, 60));
		tabs.add(Box.createRigidArea(new Dimension(10, 0)));
		tabs.add(tab1);
		tabs.add(Box.createRigidArea(new Dimension(20, 0)));
		tabs.add(tab2);
		tabs.add(Box.createRigidArea(new Dimension(10, 0)));

		JLabel ltitle = new JLabel((InfoWindow.name).toUpperCase(Locale.GERMAN) + " - " + InfoWindow.strings[0]);
		ltitle.setFont(MainWindow.fonts[0]);
		ltitle.setHorizontalAlignment(JLabel.LEFT);
		ltitle.setForeground(MainWindow.color);

		JPanel title = new JPanel();
		title.setLayout(new BoxLayout(title, BoxLayout.LINE_AXIS));
		title.setMinimumSize(new Dimension(Short.MAX_VALUE, 70));
		title.setMaximumSize(new Dimension(Short.MAX_VALUE, 70));
		title.setOpaque(false);
		title.add(Box.createRigidArea(new Dimension(50, 0)));
		title.add(ltitle);
		title.add(Box.createHorizontalGlue());
		title.add(Box.createHorizontalGlue());

		JPanel head = new JPanel();
		head.setBackground(MainWindow.backgroundColor);
		head.setLayout(new BoxLayout(head, BoxLayout.PAGE_AXIS));
		head.setMinimumSize(new Dimension(Short.MAX_VALUE, 120));
		head.setMaximumSize(new Dimension(Short.MAX_VALUE, 120));
		head.add(Box.createRigidArea(new Dimension(0, 20)));
		head.add(title);
		head.add(Box.createRigidArea(new Dimension(0, 20)));
		head.add(tabs);
		head.add(Box.createRigidArea(new Dimension(0, 10)));

		hideOnMinimize.setPreferredSize(new Dimension(100, 50));
		hideOnMinimize.setFont(MainWindow.fonts[0]);
		hideOnMinimize.addActionListener(this);
		notifcationsEnabled.setPreferredSize(new Dimension(100, 50));
		notifcationsEnabled.setFont(MainWindow.fonts[0]);
		notifcationsEnabled.addActionListener(this);
		visibleOnStart.setPreferredSize(new Dimension(100, 50));
		visibleOnStart.setFont(MainWindow.fonts[0]);
		visibleOnStart.addActionListener(this);

		JScrollPane common = getCommon();

		feedList.setLayout(new BoxLayout(feedList, BoxLayout.PAGE_AXIS));

		JPanel feeds = getFeeds();

		content.setLayout(cardManager);
		content.add(common, "common");
		content.add(feeds, "feeds");

		cardManager.show(content, "common");

		this.add(head, BorderLayout.NORTH);
		this.add(content, BorderLayout.CENTER);
		action.init();
		setControls();
	}

	private JScrollPane getCommon() {
		JPanel commonContent = new JPanel();
		commonContent.setLayout(new BoxLayout(commonContent, BoxLayout.PAGE_AXIS));

		JLabel line1text = new JLabel("Hide on Minimize");
		line1text.setFont(MainWindow.fonts[1]);

		JPanel line1 = new JPanel();
		line1.setLayout(new BoxLayout(line1, BoxLayout.LINE_AXIS));
		line1.setPreferredSize(new Dimension(100, 80));
		line1.add(Box.createRigidArea(new Dimension(20, 0)));
		line1.add(line1text);
		line1.add(Box.createHorizontalGlue());
		line1.add(hideOnMinimize);
		line1.add(Box.createRigidArea(new Dimension(20, 0)));

		JLabel line2text = new JLabel("Start hidden");
		line2text.setFont(MainWindow.fonts[1]);

		JPanel line2 = new JPanel();
		line2.setLayout(new BoxLayout(line2, BoxLayout.LINE_AXIS));
		line2.setPreferredSize(new Dimension(100, 80));
		line2.add(Box.createRigidArea(new Dimension(20, 0)));
		line2.add(line2text);
		line2.add(Box.createHorizontalGlue());
		line2.add(visibleOnStart);
		line2.add(Box.createRigidArea(new Dimension(20, 0)));

		JLabel line3text = new JLabel("Enable Notifications");
		line3text.setFont(MainWindow.fonts[1]);

		JPanel line3 = new JPanel();
		line3.setLayout(new BoxLayout(line3, BoxLayout.LINE_AXIS));
		line3.setPreferredSize(new Dimension(100, 80));
		line3.add(Box.createRigidArea(new Dimension(20, 0)));
		line3.add(line3text);
		line3.add(Box.createHorizontalGlue());
		line3.add(notifcationsEnabled);
		line3.add(Box.createRigidArea(new Dimension(20, 0)));

		commonContent.add(Box.createRigidArea(new Dimension(0, 20)));
		commonContent.add(line1);
		commonContent.add(Box.createRigidArea(new Dimension(0, 20)));
		commonContent.add(line2);
		commonContent.add(Box.createRigidArea(new Dimension(0, 20)));
		commonContent.add(line3);
		commonContent.add(Box.createRigidArea(new Dimension(0, 20)));

		JScrollPane common = new JScrollPane(commonContent);
		common.getVerticalScrollBar().setUnitIncrement(16);
		common.setBorder(BorderFactory.createEmptyBorder());
		return common;
	}

	private JPanel getFeeds() {

		feedScroller = new JScrollPane(feedList);
		feedScroller.getVerticalScrollBar().setUnitIncrement(16);
		feedScroller.setBorder(BorderFactory.createEmptyBorder());

		addfeed.setFont(MainWindow.fonts[2]);
		addfeed.setForeground(MainWindow.color);
		addfeed.setBackground(MainWindow.buttonBackgroundColor);
		addfeed.addActionListener(this);
		addfeed.setMinimumSize(new Dimension(50, 50));
		addfeed.setMaximumSize(new Dimension(50, 50));

		feedurl.setFont(MainWindow.fonts[1]);
		feedurl.setForeground(MainWindow.color);
		feedurl.setOpaque(false);
		feedurl.setCaretColor(MainWindow.color);
		feedurl.setMinimumSize(new Dimension(0, 50));
		feedurl.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));

		JPanel feedAdder = new JPanel();
		feedAdder.setLayout(new BoxLayout(feedAdder, BoxLayout.LINE_AXIS));
		feedAdder.setBackground(MainWindow.backgroundColor);
		feedAdder.setPreferredSize(new Dimension(Short.MAX_VALUE, 70));
		feedAdder.add(Box.createRigidArea(new Dimension(20, 0)));
		feedAdder.add(feedurl);
		feedAdder.add(Box.createRigidArea(new Dimension(20, 0)));
		feedAdder.add(addfeed);
		feedAdder.add(Box.createRigidArea(new Dimension(20, 0)));

		JPanel feeds = new JPanel(new BorderLayout());
		feeds.add(feedScroller, BorderLayout.CENTER);
		feeds.add(feedAdder, BorderLayout.SOUTH);
		return feeds;
	}

	private void setControls() {
		if (AppManager.hideOnMinimize) {
			hideOnMinimize.setText("ON");
			hideOnMinimize.setBackground(MainWindow.buttonBackgroundColor);
			hideOnMinimize.setForeground(MainWindow.color);
		} else {
			hideOnMinimize.setText("OFF");
			hideOnMinimize.setBackground(MainWindow.color);
			hideOnMinimize.setForeground(Color.BLACK);
		}
		hideOnMinimize.revalidate();
		hideOnMinimize.repaint();
		if (AppManager.notifcationsEnabled) {
			notifcationsEnabled.setText("ON");
			notifcationsEnabled.setBackground(MainWindow.buttonBackgroundColor);
			notifcationsEnabled.setForeground(MainWindow.color);
		} else {
			notifcationsEnabled.setText("OFF");
			notifcationsEnabled.setBackground(MainWindow.color);
			notifcationsEnabled.setForeground(Color.BLACK);
		}
		notifcationsEnabled.revalidate();
		notifcationsEnabled.repaint();
		if (AppManager.hideOnstart) {
			visibleOnStart.setText("ON");
			visibleOnStart.setBackground(MainWindow.buttonBackgroundColor);
			visibleOnStart.setForeground(MainWindow.color);
		} else {
			visibleOnStart.setText("OFF");
			visibleOnStart.setBackground(MainWindow.color);
			visibleOnStart.setForeground(Color.BLACK);
		}
		visibleOnStart.revalidate();
		visibleOnStart.repaint();
	}

	public void refreshFeedList() {
		feedList.removeAll();

		for (int i = 0; i < AppManager.feedNames.size(); i++) {
			feedList.add(Box.createRigidArea(new Dimension(0, 10)));
			feedList.add(new FeedSourcePanel(i, AppManager.feedURLS.get(i)));
			feedList.add(Box.createRigidArea(new Dimension(0, 10)));
		}
		feedScroller.revalidate();
		feedScroller.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tab1) {
			tab1.setBackground(MainWindow.color);
			tab1.setForeground(MainWindow.buttonBackgroundColor);
			tab2.setBackground(MainWindow.buttonBackgroundColor);
			tab2.setForeground(MainWindow.color);
			cardManager.show(content, "common");
			setControls();
		} else if (e.getSource() == tab2) {
			tab1.setBackground(MainWindow.buttonBackgroundColor);
			tab1.setForeground(MainWindow.color);
			tab2.setBackground(MainWindow.color);
			tab2.setForeground(MainWindow.buttonBackgroundColor);
			cardManager.show(content, "feeds");
		} else if (e.getSource() == addfeed && feedurl.getText() != "") {
			action.addFeed(feedurl.getText());
			feedurl.setText("");
		} else if (e.getSource() == visibleOnStart) {
			AppManager.hideOnstart = !AppManager.hideOnstart;
			setControls();
		} else if (e.getSource() == notifcationsEnabled) {
			AppManager.notifcationsEnabled = !AppManager.notifcationsEnabled;
			setControls();
		} else if (e.getSource() == hideOnMinimize) {
			AppManager.hideOnMinimize = !AppManager.hideOnMinimize;
			setControls();
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		this.setVisible(false);

	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.setVisible(false);

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
