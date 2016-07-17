package hr.fer.zemris.JIR2015.prob2;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class MojComboBoxModel implements ComboBoxModel<Path> {

	private List<Path> data = new ArrayList<>();
	
	private List<ListDataListener> listeneri = new CopyOnWriteArrayList<>();

	private Object selItem;
	
	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public Path getElementAt(int index) {
		return data.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeneri.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeneri.remove(l);
	}

	public void add(Path podatak) {
		int index = data.size();
		data.add(podatak);
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index);
		for(ListDataListener l : listeneri) {
			l.intervalRemoved(event);
		}
	}

	public void remove(int index) {
		data.remove(index);
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index, index);
		for(ListDataListener l : listeneri) {
			l.intervalRemoved(event);
		}
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selItem = anItem;
	}

	@Override
	public Object getSelectedItem() {
		return selItem;
	}
}
