package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class that represents a core for each localizable component. Localizable
 * component, for example LJLabel should create a new instance of this class in
 * constructor and define its localizable changes in its update implementation.
 * <p>
 * What this class does is same for each component, registers itself as a
 * listner to a {@link ILocalizationProvider} and whenever a change in language
 * occurs, {@link #update(String)} is called.
 * 
 * @author Ante Spajic
 *
 */
public abstract class LocalizableElement {

	/**
	 * Constructor that updates text within a component and registers itself as
	 * a listener to localization provider.
	 * 
	 * @param key
	 *            property key inside .properties files
	 * @param provider
	 *            localization provider instance
	 */
	public LocalizableElement(String key, ILocalizationProvider provider) {
		update(provider.getString(key));
		provider.addLocalizationListener(() -> update(provider.getString(key)));
	}

	/**
	 * This method is called each time a language change occurs.
	 * 
	 * @param value
	 *            text value that will be displayed with language change
	 */
	public abstract void update(String value);
}
