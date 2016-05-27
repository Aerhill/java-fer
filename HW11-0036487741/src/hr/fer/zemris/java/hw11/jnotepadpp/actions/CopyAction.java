package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Copy action places the selected text into a clipboard.
 * 
 * @author Ante Spajic
 *
 */
public class CopyAction extends AbstractSuperMegaCoolAction {
	private static final long serialVersionUID = -2220483790010844455L;

	/** The Constant ACTION_NAME. */
	public static final String ACTION_NAME = "action_copy";

	/**
	 * Instantiates a new copy action.
	 *
	 * @param lp
	 *            the localizatoin provider
	 * @param notepad
	 *            the notepad
	 */
	public CopyAction(ILocalizationProvider lp, JNotepadPP notepad) {
		super(ACTION_NAME, lp, notepad);
		putValue(Action.SMALL_ICON, Icons.COPY_ICON);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		copyAction(false);
	}

}
