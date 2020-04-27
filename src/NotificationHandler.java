import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotificationHandler implements ActionListener {
	private IActionHandler action = new AppManager();

	private PopupMenu popup = new PopupMenu();
	private TrayIcon trayIcon = new TrayIcon(Main.info.icon);
	private SystemTray tray = SystemTray.getSystemTray();

	public NotificationHandler() {
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip(InfoWindow.name + " " + InfoWindow.version);

		MenuItem aboutItem = new MenuItem(InfoWindow.strings[10] + " " + InfoWindow.name);
		MenuItem displayMenu = new MenuItem(InfoWindow.strings[11]);
		MenuItem exitItem = new MenuItem(InfoWindow.strings[12]);

		popup.add(aboutItem);
		popup.addSeparator();
		popup.add(displayMenu);
		popup.addSeparator();
		popup.add(exitItem);

		trayIcon.setPopupMenu(popup);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
			return;
		}

		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.info.setVisible(true);
			}
		});

		displayMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action.changeVisibility();
			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tray.remove(trayIcon);
				action.quit();
			}
		});
		trayIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action.changeVisibility();
			}
		});
	}

	public void articleNotification(String content) {
		trayIcon.displayMessage(InfoWindow.strings[5], content, MessageType.INFO);
	}

	public void systemNotification(String content) {
		trayIcon.displayMessage(InfoWindow.name, content, MessageType.INFO);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == trayIcon) {

		}

	}
}
