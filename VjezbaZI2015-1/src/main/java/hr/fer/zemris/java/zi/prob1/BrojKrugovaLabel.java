package hr.fer.zemris.java.zi.prob1;

import javax.swing.JLabel;

import hr.fer.zemris.java.zi.prob1.model.ModelCrteza;
import hr.fer.zemris.java.zi.prob1.model.ModelCrtezaListener;

public class BrojKrugovaLabel extends JLabel {
	private static final long serialVersionUID = 225490173914649657L;
	
	private ModelCrtezaListener l = new ModelCrtezaListener() {
		
		@Override
		public void notifySelectedChanged(ModelCrteza model) {
			updateText();
		}
		
		@Override
		public void notifyNumberChanged(ModelCrteza model, int indexOld) {
			updateText();
		}
	};
	
	private ModelCrteza model;
	
	public BrojKrugovaLabel(ModelCrteza model) {
		this.model = model;
		model.addListener(l);
		updateText();
	}

	private void updateText() {
		setText("Broj krugova: " + model.brojKrugova());
	}
}
