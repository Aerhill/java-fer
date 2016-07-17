package hr.fer.zemris.java.zi.prob2;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 2411380359518171667L;

	private Image image;
	private boolean tile;

	public MyPanel(Path path) {
		try {
			image = ImageIO.read(path.toFile());
		} catch (IOException exc) {
			System.out.println("Couldn't read image");
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int iw = image.getWidth(this);
		int ih = image.getHeight(this);
		if (this.getWidth() > iw) {
			tile = true;
		}
		if (tile) {
			if (iw > 0 && ih > 0) {
				for (int x = 0; x < getWidth(); x += iw) {
					for (int y = 0; y < getHeight(); y += ih) {
						g.drawImage(image, x, y, iw, ih, this);
					}
				}
			}
		} else {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
}
