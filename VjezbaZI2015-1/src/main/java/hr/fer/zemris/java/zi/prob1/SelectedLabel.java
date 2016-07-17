package hr.fer.zemris.java.zi.prob1;

import javax.swing.JLabel;

import hr.fer.zemris.java.zi.prob1.model.ModelCrteza;
import hr.fer.zemris.java.zi.prob1.model.ModelCrtezaListener;

public class SelectedLabel extends JLabel {
	private static final long serialVersionUID = 8492568220056905038L;

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
	
	public SelectedLabel(ModelCrteza model) {
		this.model = model;
		model.addListener(l);
		updateText();
	}


	private void updateText() {
		String index = model.dohvatiIndeksSelektiranogKruga() == -1 ? "-" : String.valueOf(model.dohvatiIndeksSelektiranogKruga());
		setText("Selektirani krug: " + index);
	}

}
