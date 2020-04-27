import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class NewsPanel extends JPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Font[] fonts = { new Font("Arial", Font.BOLD, 22), new Font("Arial", Font.PLAIN, 16) };
	private JTextArea ltitle;
	private JTextArea lcontent;

	public String title;
	public String content;
	public String hyperlink;
	public String date;

	public int width;
	public int height;

	public NewsPanel(String title, String content, String date, String hyperlink) {
		this.setLayout(new BorderLayout());
		this.addMouseListener(this);
		this.setToolTipText(InfoWindow.strings[3]);
		this.title = title;
		this.content = content;
		this.hyperlink = hyperlink;
		this.date = date;

		ltitle = new JTextArea(title);
		ltitle.setFont(fonts[0]);
		ltitle.setForeground(MainWindow.backgroundColor);
		ltitle.setOpaque(false);
		ltitle.setEditable(false);
		ltitle.setLineWrap(true);
		ltitle.setWrapStyleWord(true);
		ltitle.addMouseListener(this);
		ltitle.setToolTipText(InfoWindow.strings[3]);

		lcontent = new JTextArea(content);
		lcontent.setFont(fonts[1]);
		lcontent.setForeground(MainWindow.backgroundColor);
		lcontent.setOpaque(false);
		lcontent.setEditable(false);
		lcontent.setLineWrap(true);
		lcontent.setWrapStyleWord(true);
		lcontent.addMouseListener(this);
		lcontent.setToolTipText(InfoWindow.strings[3]);

		this.setBackground(MainWindow.color);

		this.setPreferredSize(new Dimension(100, 110));

		this.add(ltitle, BorderLayout.NORTH);
		this.add(lcontent, BorderLayout.CENTER);
		TitledBorder bordertitle = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(MainWindow.backgroundColor),
				date);
		bordertitle.setTitleJustification(TitledBorder.CENTER);
		bordertitle.setTitleColor(MainWindow.backgroundColor);
		this.setBorder(bordertitle);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (Desktop.isDesktopSupported() && arg0.getButton() == MouseEvent.BUTTON2) {
			try {
				Desktop.getDesktop().browse((new URL(hyperlink)).toURI());
			} catch (IOException | URISyntaxException e1) {
				e1.printStackTrace();
			}
		}
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
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
