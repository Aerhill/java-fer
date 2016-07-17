package hr.fer.zemris.java.zavrsni.zavrad.prob2;

import java.nio.file.Path;

import javax.swing.ListModel;

public interface MyListener {

	void selectionChanged(ListModel<Path> model, Path path);
}
