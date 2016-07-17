package hr.fer.zemris.JIR2015.prob2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class MyCentralComponent extends JPanel {
	
	private static final long serialVersionUID = -2588158959366986255L;

	private BufferedImage image;
	private boolean tiled;
	
	public MyCentralComponent() {
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public void setTiled(boolean tiled) {
		this.tiled = tiled;
		repaint();
	}
	
	public boolean isTiled() {
		return tiled;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (image != null) {
			if (!tiled) {
				Image temp = image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
				g.drawImage(temp, 0, 0, this.getWidth(), this.getHeight(), null);
			} else {
				int width = getWidth();  
		        int height = getHeight();  
		        for (int x = 0; x < width; x += image.getWidth()) {  
		            for (int y = 0; y < height; y += image.getHeight()) {  
		                g.drawImage(image, x, y, this);  
		            }  
		        }
			}
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
	}
	
}
