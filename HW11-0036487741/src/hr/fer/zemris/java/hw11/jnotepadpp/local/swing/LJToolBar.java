package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JToolBar;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * This class is a localized wrapper for {@link JToolBar} class, so each time
 * language change occurs toolbar name is updated properly.
 * 
 * @author Ante Spajic
 *
 */
public class LJToolBar extends JToolBar {
	private static final long serialVersionUID = -7452915153618000156L;

	/**
	 * Creates a localizable {@link JToolBar}.
	 * 
	 * @param key
	 *            key that holds localizable text
	 * @param lp
	 *            provider for languagues
	 */
	public LJToolBar(String key, ILocalizationProvider lp) {
		new LocalizableElement(key, lp) {
			@Override
			public void update(String value) {
				setName(value);
			}
		};
	}
}
