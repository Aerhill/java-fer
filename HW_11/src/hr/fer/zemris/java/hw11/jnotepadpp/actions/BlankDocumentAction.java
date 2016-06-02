package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.JTabPP;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Blank document action creates a new blank document in notepad.
 *
 * @author Ante Spajic
 */
public class BlankDocumentAction extends AbstractSuperMegaCoolAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2530159511742075880L;

	/** The Constant ACTION_NAME. */
	public static final String ACTION_NAME = "action_open_new_document";

	/**
	 * Instantiates a new blank document action.
	 *
	 * @param lp
	 *            the localization provider
	 * @param notepad
	 *            the notepad
	 */
	public BlankDocumentAction(ILocalizationProvider lp, JNotepadPP notepad) {
		super(ACTION_NAME, lp, notepad);
		putValue(Action.SMALL_ICON, Icons.NEWFILE_ICON);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		putValue(Action.SHORT_DESCRIPTION, "New blank document");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTabPP tab = new JTabPP();
		tabs.add(tab);
		refreshTabs();
		tabs.setSelectedComponent(tab);
	}
}
