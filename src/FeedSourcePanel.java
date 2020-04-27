import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FeedSourcePanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lfeedName = new JLabel();
	private JButton remove = new JButton("X");

	public int feedID;
	private IActionHandler action = new AppManager();

	public FeedSourcePanel(int feedID, String hyperlink) {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.feedID = feedID;

		lfeedName.setText(hyperlink);
		lfeedName.setFont(MainWindow.fonts[1]);
		lfeedName.setForeground(MainWindow.color);
		lfeedName.setOpaque(false);
		lfeedName.setMinimumSize(new Dimension(0, 50));
		lfeedName.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));

		remove.setFont(MainWindow.fonts[2]);
		remove.setForeground(MainWindow.color);
		remove.setBackground(MainWindow.buttonBackgroundColor);
		remove.setMinimumSize(new Dimension(1000, 50));
		remove.setMaximumSize(new Dimension(1000, 50));
		remove.addActionListener(this);

		this.setBackground(MainWindow.buttonBackgroundColor);
		this.setMinimumSize(new Dimension(Short.MAX_VALUE, 60));
		this.setMaximumSize(new Dimension(Short.MAX_VALUE, 60));

		this.add(Box.createRigidArea(new Dimension(20, 0)));
		this.add(lfeedName);
		this.add(Box.createRigidArea(new Dimension(20, 0)));
		this.add(remove);
		this.add(Box.createRigidArea(new Dimension(20, 0)));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		action.removeFeed(feedID);
	}
}