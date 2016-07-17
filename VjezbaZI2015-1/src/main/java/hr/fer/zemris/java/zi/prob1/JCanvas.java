package hr.fer.zemris.java.zi.prob1;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import hr.fer.zemris.java.zi.prob1.model.ModelCrteza;
import hr.fer.zemris.java.zi.prob1.model.ModelCrtezaListener;

public class JCanvas extends JPanel {
	
	private static final long serialVersionUID = 6791585863060189520L;

	private ModelCrtezaListener l = new ModelCrtezaListener() {
		@Override
		public void notifySelectedChanged(ModelCrteza model) {
			repaint();
		}

		@Override
		public void notifyNumberChanged(ModelCrteza model, int indexOld) {
			repaint();
		}
	};
	
	private ModelCrteza model;
	
	public JCanvas(ModelCrteza model) {
		this.model = model;
		model.addListener(l);
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for(int i = 0, broj=model.brojKrugova(); i<broj; i++) {
			Krug krug = model.dohvati(i);
			int rad = krug.getR();
			if (krug.getIspuna() != null) {
				Color ispuna = krug.getIspuna();
				if (i == model.dohvatiIndeksSelektiranogKruga()) {
					g.setColor(new Color(255-ispuna.getRed(),255-ispuna.getGreen(), 255-ispuna.getBlue()));
				} else {
					g.setColor(ispuna);
				}
				g.fillOval(krug.getCx()-rad, krug.getCy() - rad, rad, rad);
			}
			g.setColor(krug.getObrub());
			g.drawOval(krug.getCx()-rad, krug.getCy() - rad, rad, rad);
			
		}
	}

}
