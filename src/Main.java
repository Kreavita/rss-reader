public class Main {
	static MainWindow mainwindow;
	static SettingsWindow settingswindow;
	static NotificationHandler notificationHandler;
	static InfoWindow info;

	public static void main(String[] args) {
		System.out.println("Application started ...");
		info = new InfoWindow();
		notificationHandler = new NotificationHandler();
		mainwindow = new MainWindow();
		settingswindow = new SettingsWindow();
	}
}
