
public interface IActionHandler {
	public void addFeed(String url);

	public void showFeed(String feedName);

	public void init();

	public void quit();

	public void changeVisibility();

	public void refresh_all();

	void removeFeed(int id);

}
