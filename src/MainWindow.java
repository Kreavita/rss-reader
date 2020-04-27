import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainWindow extends Frame implements ActionListener, WindowListener, MouseListener, ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IActionHandler action = new AppManager();

	static Color backgroundColor = new Color(44, 62, 80);
	static Color buttonBackgroundColor = new Color(39, 174, 96);

	static Color color = new Color(250, 250, 250);
	static Font[] fonts = { new Font(Font.SANS_SERIF, Font.PLAIN, 25), new Font(Font.SANS_SERIF, Font.PLAIN, 20),
			new Font(Font.SANS_SERIF, Font.PLAIN, 35) };

	static JPanel feedPort = new JPanel();
	static JComboBox<String> feedPicker = new JComboBox<String>();

	private JButton btn_settings = new JButton(InfoWindow.strings[0]);
	private JButton btn_refreshall = new JButton(InfoWindow.strings[1]);



	public MainWindow() {
		super(InfoWindow.name);
		this.setIconImage(Main.info.icon);
		this.setSize(1280, 720);
		this.addWindowListener(this);
		this.setLayout(new BorderLayout());
		this.setBackground(backgroundColor);
		this.setMinimumSize(new Dimension(768, 195));
		JLabel title = new JLabel(InfoWindow.name.toUpperCase(Locale.GERMAN));
		title.setForeground(color);
		title.setFont(fonts[0]);
		title.setHorizontalAlignment(JLabel.LEFT);

		Panel topBar = new Panel();
		topBar.setBackground(backgroundColor);
		topBar.setForeground(color);
		topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
		topBar.setPreferredSize(new Dimension(Short.MAX_VALUE, 70));
		topBar.add(Box.createRigidArea(new Dimension(50, 0)));
		topBar.add(title);
		topBar.add(Box.createHorizontalGlue());
		topBar.add(Box.createHorizontalGlue());

		btn_refreshall.setBackground(buttonBackgroundColor);
		btn_refreshall.setForeground(color);
		btn_refreshall.setBorder(BorderFactory.createEmptyBorder());
		btn_refreshall.setFont(fonts[1]);
		btn_refreshall.setMinimumSize(new Dimension(10000, 50));
		btn_refreshall.setMaximumSize(new Dimension(10000, 50));
		btn_refreshall.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn_refreshall.addActionListener(this);

		btn_settings.setBackground(buttonBackgroundColor);
		btn_settings.setForeground(color);
		btn_settings.setBorder(BorderFactory.createEmptyBorder());
		btn_settings.setFont(fonts[1]);
		btn_settings.setMinimumSize(new Dimension(10000, 50));
		btn_settings.setMaximumSize(new Dimension(10000, 50));
		btn_settings.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn_settings.addActionListener(this);

		feedPicker.setBackground(backgroundColor);
		feedPicker.setForeground(color);
		feedPicker.setEditable(false);
		feedPicker.setCursor(new Cursor(Cursor.HAND_CURSOR));
		feedPicker.setBorder(BorderFactory.createEmptyBorder());
		feedPicker.setMaximumSize(new Dimension(100, 50));
		feedPicker.setMinimumSize(new Dimension(100, 50));
		feedPicker.addItemListener(this);
		feedPicker.setAlignmentX(JComboBox.CENTER_ALIGNMENT);

		JPanel comboBar = new JPanel();
		comboBar.setLayout(new BoxLayout(comboBar, BoxLayout.X_AXIS));
		comboBar.setPreferredSize(new Dimension(Short.MAX_VALUE, 50));
		comboBar.setBackground(backgroundColor);
		comboBar.add(Box.createRigidArea(new Dimension(50, 0)));
		comboBar.add(feedPicker);
		comboBar.add(Box.createHorizontalGlue());
		comboBar.add(btn_refreshall);
		comboBar.add(Box.createRigidArea(new Dimension(20, 0)));
		comboBar.add(btn_settings);
		comboBar.add(Box.createRigidArea(new Dimension(50, 0)));

		JPanel bar = new JPanel();
		bar.setLayout(new BoxLayout(bar, BoxLayout.PAGE_AXIS));
		bar.setBackground(backgroundColor);
		bar.add(Box.createRigidArea(new Dimension(0, 20)));
		bar.add(topBar);
		bar.add(Box.createRigidArea(new Dimension(0, 10)));
		bar.add(comboBar);

		feedPort.setBorder(BorderFactory.createEmptyBorder());
		feedPort.setBackground(backgroundColor);
		feedPort.setLayout(new GridLayout(1, 1));

		this.add(feedPort, BorderLayout.CENTER);
		this.add(bar, BorderLayout.NORTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_refreshall) {
			action.refresh_all();
		} else if (e.getSource() == btn_settings) {
			Main.settingswindow.setVisible(true);
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		action.quit();
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		action.quit();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		if (AppManager.hideOnMinimize) {
			this.setVisible(false);
			Main.notificationHandler.systemNotification(InfoWindow.strings[13]);
		}

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if (arg0.getStateChange() == ItemEvent.SELECTED && AppManager.comboIsIdle) {
			action.showFeed((String) arg0.getItem());
		}

	}
}
