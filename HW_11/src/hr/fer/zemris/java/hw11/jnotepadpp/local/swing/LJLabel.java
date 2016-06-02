package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * This class is a localized wrapper for {@link JLabel} class, so each time
 * language change occurs label text is displayed correctly.
 * 
 * @author Ante Spajic
 *
 */
public class LJLabel extends JLabel {
	private static final long serialVersionUID = -23856567305708852L;

	String key;

	/**
	 * Creates a new localizable {@link JLabel}
	 * 
	 * @param key
	 *            key that holds localizable text
	 * @param provider
	 *            provider for languagues
	 */
	public LJLabel(String key, ILocalizationProvider provider) {
		new LocalizableElement(key, provider) {
			@Override
			public void update(String value) {
				setText(value);
			}
		};
	}
}
