package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * This class is a localized wrapper for {@link JMenu} class, so each
 * time language change occurs text in menu is updated properly.
 * 
 * @author Ante Spajic
 *
 */
public class LJMenu extends JMenu {
	private static final long serialVersionUID = -8601321172759040245L;

	/**
	 * Creates a localizable {@link JMenu}
	 * 
	 * @param key key for localizable menu name
	 * @param lp localization provider
	 */
	public LJMenu(String key, ILocalizationProvider lp) {
		new LocalizableElement(key,lp) {
			@Override
			public void update(String value) {
				setText(value);
			}
		};
	}
}
