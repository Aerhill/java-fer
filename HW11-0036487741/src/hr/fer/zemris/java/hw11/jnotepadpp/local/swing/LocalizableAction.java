package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * This class is a localized wrapper for {@link AbstractAction} class, so each
 * time language change occurs action name is updated properly.
 * 
 * @author Ante Spajic
 *
 */
public abstract class LocalizableAction extends AbstractAction {
	private static final long serialVersionUID = -1398110279578516996L;

	/**
	 * Creates a new localizable action with name under the key and and
	 * localization provider that will fetch the value under the key.
	 * 
	 * @param key
	 *            localization key
	 * @param lp
	 *            localization provider
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		new LocalizableElement(key, lp) {
			@Override
			public void update(String value) {
				putValue(NAME, value);
			}
		};
	}

}
