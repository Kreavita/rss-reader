import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow extends Frame implements WindowListener {

	private static final long serialVersionUID = 1L;
	public JButton bline = new JButton("OK");
	public JTextField user = new JTextField();
	public JPasswordField pass = new JPasswordField();

	private IActionHandler action = new AppManager();

	LoginWindow() {
		super("Proxy Login");
		this.setIconImage(Main.info.icon);
		this.setSize(new Dimension(500, 250));
		this.setResizable(false);
		addWindowListener(this);
		this.setLayout(new BorderLayout());

		JLabel title = new JLabel("Proxy-Server Authentifizierung");
		title.setFont(MainWindow.fonts[0]);
		title.setForeground(MainWindow.color);

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
		titlePanel.setPreferredSize(new Dimension(Short.MAX_VALUE, 60));
		titlePanel.setBackground(MainWindow.buttonBackgroundColor);
		titlePanel.add(Box.createHorizontalGlue());
		titlePanel.add(title);
		titlePanel.add(Box.createHorizontalGlue());

		JLabel lline1 = new JLabel(InfoWindow.strings[6] + ": ");
		lline1.setFont(MainWindow.fonts[1]);
		lline1.setForeground(MainWindow.color);

		user.setFont(MainWindow.fonts[1]);
		user.setForeground(MainWindow.color);
		user.setOpaque(false);
		user.setCaretColor(MainWindow.color);

		JPanel line1 = new JPanel();
		line1.setOpaque(false);
		line1.setLayout(new BoxLayout(line1, BoxLayout.LINE_AXIS));
		line1.setAlignmentY(CENTER_ALIGNMENT);
		line1.add(Box.createRigidArea(new Dimension(50, 0)));
		line1.add(lline1);
		line1.add(Box.createHorizontalGlue());
		line1.add(user);
		line1.add(Box.createRigidArea(new Dimension(50, 0)));

		JLabel lline2 = new JLabel(InfoWindow.strings[7] + ": ");
		lline2.setFont(MainWindow.fonts[1]);
		lline2.setForeground(MainWindow.color);

		pass.setFont(MainWindow.fonts[1]);
		pass.setForeground(MainWindow.color);
		pass.setOpaque(false);
		pass.setCaretColor(MainWindow.color);

		JPanel line2 = new JPanel();
		line2.setOpaque(false);
		line2.setLayout(new BoxLayout(line2, BoxLayout.LINE_AXIS));
		line2.setAlignmentY(CENTER_ALIGNMENT);
		line2.add(Box.createRigidArea(new Dimension(50, 0)));
		line2.add(lline2);
		line2.add(Box.createHorizontalGlue());
		line2.add(pass);
		line2.add(Box.createRigidArea(new Dimension(50, 0)));

		bline.setFont(MainWindow.fonts[1]);
		bline.setBackground(MainWindow.buttonBackgroundColor);
		bline.setForeground(MainWindow.color);

		JPanel line3 = new JPanel();
		line3.setOpaque(false);
		line3.setLayout(new BoxLayout(line3, BoxLayout.LINE_AXIS));
		line3.setAlignmentY(RIGHT_ALIGNMENT);
		line3.add(bline);

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
		this.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

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
