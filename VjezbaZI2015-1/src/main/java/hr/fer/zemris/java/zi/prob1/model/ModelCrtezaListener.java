package hr.fer.zemris.java.zi.prob1.model;

public interface ModelCrtezaListener {

	void notifySelectedChanged(ModelCrteza model);
	
	void notifyNumberChanged(ModelCrteza model, int indexOld);
}
