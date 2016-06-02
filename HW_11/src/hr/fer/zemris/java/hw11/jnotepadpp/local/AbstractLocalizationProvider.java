package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The Class AbstractLocalizationProvider is responsible only for storing and
 * notifying listeners of a provider.
 * 
 * @author Ante Spajic
 * 
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/** The listeners. */
	private List<ILocalizationListener> listeners;

	/**
	 * Instantiates a new abstract localization provider.
	 */
	public AbstractLocalizationProvider() {
		listeners = new CopyOnWriteArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}

	@Override
	public abstract String getString(String s);

	/**
	 * Notifies all listeners of a change in a provider.
	 */
	public void fire() {
		listeners.forEach(l -> l.localizationChanged());
	}

}
